import groovy.json.JsonSlurper
import org.gradle.api.GradleException
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.api.tasks.testing.AbstractTestTask
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.support.serviceOf
import org.gradle.plugins.signing.Sign
import org.gradle.process.ExecOperations
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.wasm.yarn.WasmYarnRootEnvSpec
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.Base64
import java.util.UUID
import java.util.zip.ZipInputStream

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlinx.benchmark)
    alias(libs.plugins.kotlin.allopen)
    `maven-publish`
    signing
}

group = providers.gradleProperty("project.group").getOrElse("io.github.kotlinmania")
version = providers.gradleProperty("project.version").getOrElse("0.1.0-SNAPSHOT")
val frameworkName = providers.gradleProperty("project.frameworkName").getOrElse("Unnamed")
val projectNamespace = providers.gradleProperty("project.namespace").getOrElse("io.github.kotlinmania")
val kotlinVersion = providers.gradleProperty("versions.kotlin").getOrElse("2.4.0")
val commonMainBundleName = providers.gradleProperty("project.dependencies.commonMainBundle").get()
val commonMainDependencyBundle =
    extensions
        .getByType(VersionCatalogsExtension::class.java)
        .named("libs")
        .findBundle(commonMainBundleName)
        .orElseThrow { GradleException("Missing libs bundle '$commonMainBundleName'") }

fun csvProperty(name: String): Set<String> =
    providers
        .gradleProperty(name)
        .map { value ->
            value
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toSet()
        }.getOrElse(emptySet())

fun optionalTrimmedProperty(name: String): String? =
    providers
        .gradleProperty(name)
        .map { it.trim() }
        .orNull
        ?.takeIf { it.isNotEmpty() }

val enabledFeatureNames = csvProperty("project.features")
val benchmarkEnabled = "benchmark" in enabledFeatureNames
val benchmarkTargetNames = csvProperty("project.benchmark.targets")

// ============================================================================
// Native C bindings — per-channel opt-in gates (gradle.properties
// `project.ffi.{native,jvm,napi,name}`). All default off.
// Transliterated from libc-kotlin's build.gradle.kts; generalized so tasks,
// file paths, and npm package names all derive from project.ffi.name.
// ============================================================================
fun boolProperty(name: String, default: Boolean = false): Boolean =
    providers.gradleProperty(name).map { it.trim().lowercase() == "true" }.getOrElse(default)

val nativeFfiEnabled = boolProperty("project.ffi.native")
val jvmFfiEnabled = boolProperty("project.ffi.jvm")
val napiFfiEnabled = boolProperty("project.ffi.napi")

val ffiNameRaw = providers.gradleProperty("project.ffi.name").getOrElse("Ffi").trim()
val ffiName = ffiNameRaw.replaceFirstChar { it.uppercase() } // Libc, Png, ...
val ffiNameLower = ffiNameRaw.lowercase() // libc, png, ...
val ffiDefFile = "src/nativeInterop/cinterop/$ffiName.def"
val ffiWrapperBase = "${ffiNameLower}_wrapper" // libc_wrapper
val ffiNapiDir = "native/node-$ffiNameLower" // native/node-libc
val ffiNpmPackage = "@kotlinmania/$ffiNameLower-native-bindings" // @kotlinmania/libc-native-bindings

if (nativeFfiEnabled || jvmFfiEnabled || napiFfiEnabled) {
    println("FFI channels enabled: native=$nativeFfiEnabled jvm=$jvmFfiEnabled napi=$napiFfiEnabled name=$ffiName")
}
val commonBenchmarkBundleName = optionalTrimmedProperty("project.dependencies.commonBenchmarkBundle")
val commonBenchmarkDependencyBundle =
    commonBenchmarkBundleName?.let { bundleName ->
        extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")
            .findBundle(bundleName)
            .orElseThrow { GradleException("Missing libs bundle '$bundleName'") }
    }
val commonTestBundleName = optionalTrimmedProperty("project.dependencies.commonTestBundle")
val commonTestDependencyBundle =
    commonTestBundleName?.let { bundleName ->
        extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")
            .findBundle(bundleName)
            .orElseThrow { GradleException("Missing libs bundle '$bundleName'") }
    }
if (benchmarkEnabled && commonBenchmarkDependencyBundle == null) {
    throw GradleException("Feature 'benchmark' requires project.dependencies.commonBenchmarkBundle")
}
val benchmarkWarmups = providers.gradleProperty("project.benchmark.warmups").map { it.toInt() }.getOrElse(3)
val benchmarkIterations = providers.gradleProperty("project.benchmark.iterations").map { it.toInt() }.getOrElse(5)
val benchmarkIterationTime = providers.gradleProperty("project.benchmark.iterationTime").map { it.toLong() }.getOrElse(1L)
val benchmarkIterationTimeUnit = providers.gradleProperty("project.benchmark.iterationTimeUnit").getOrElse("s")

// Opt-ins shared across Kotlin targets.
val commonOptIns =
    listOf(
        "kotlin.time.ExperimentalTime",
        "kotlin.concurrent.atomics.ExperimentalAtomicApi",
        "kotlin.ExperimentalUnsignedTypes",
    )

val jvmToolchainVersion = providers.gradleProperty("jvm.toolchain").getOrElse("21").toInt()

// ============================================================================
// kotlin { … }
// ----------------------------------------------------------------------------
// Windows-relevant targets retained for winapi-kotlin (matching windows-sys-kotlin):
// mingwX64, jvm, js, wasmJs, wasmWasi.
// ============================================================================
kotlin {
    jvmToolchain(jvmToolchainVersion)

    applyDefaultHierarchyTemplate()

    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_4)
        apiVersion.set(KotlinVersion.KOTLIN_2_4)
        allWarningsAsErrors.set(true)
        optIn.addAll(commonOptIns)
        freeCompilerArgs.addAll("-Xexpect-actual-classes", "-Xsuppress-version-warnings")
    }

    fun KotlinTarget.configureBenchmarkCompilation() {
        if (!benchmarkEnabled || name !in benchmarkTargetNames) return
        val mainCompilation = compilations.getByName("main")
        compilations.create("benchmark") {
            associateWith(mainCompilation)
            defaultSourceSet.dependencies {
                implementation(commonBenchmarkDependencyBundle!!)
            }
        }
    }

    // Windows Native
    mingwX64 { configureBenchmarkCompilation() }

    // Web
    js {
        configureBenchmarkCompilation()
        browser {
            testTask {
                enabled = false
            }
        }
        nodejs()
    }

    // wasmJs is Stable as of Kotlin 2.2; @OptIn may be removable — verify before dropping on wasmWasi.
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        configureBenchmarkCompilation()
        browser {
            testTask {
                enabled = false
            }
        }
        nodejs()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmWasi {
        configureBenchmarkCompilation()
        nodejs()
    }

    // JVM — jvmTarget derived from the same toolchain property so they can't drift.
    jvm {
        configureBenchmarkCompilation()
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(jvmToolchainVersion.toString()))
        }
    }

    // ========================================================================
    // Native FFI — Kotlin/Native cinterop + per-target static wrapper library.
    // Gated by project.ffi.native=true in gradle.properties.
    // ========================================================================
    if (nativeFfiEnabled) {
        fun ensureKonanDependency(name: String, konanDeps: File, execOps: ExecOperations) {
            val targetDir = File(konanDeps, name)
            if (targetDir.exists()) return
            konanDeps.mkdirs()
            val cacheDir = File(konanDeps, "cache").apply { mkdirs() }
            val isWin =
                org.gradle.internal.os.OperatingSystem
                    .current()
                    .isWindows
            if (isWin && name.startsWith("msys2-mingw")) {
                val archive = File(cacheDir, "$name.zip")
                val url = "https://download.jetbrains.com/kotlin/native/$name.zip"
                if (!archive.exists() || archive.length() == 0L) {
                    println("Downloading Kotlin/Native dependency $name from $url ...")
                    URI(url).toURL().openStream().use { input ->
                        Files.copy(input, archive.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    }
                }
                println("Extracting $archive into $konanDeps ...")
                val canonicalDest = konanDeps.canonicalFile.toPath()
                ZipInputStream(archive.inputStream().buffered()).use { zipInput ->
                    generateSequence { zipInput.nextEntry }.forEach { entry ->
                        val target = konanDeps.resolve(entry.name).canonicalFile
                        if (!target.toPath().startsWith(canonicalDest)) {
                            throw GradleException("Refusing to extract archive entry outside $konanDeps: ${entry.name}")
                        }
                        if (entry.isDirectory) {
                            target.mkdirs()
                        } else {
                            target.parentFile.mkdirs()
                            Files.copy(zipInput, target.toPath(), StandardCopyOption.REPLACE_EXISTING)
                        }
                        zipInput.closeEntry()
                    }
                }
                return
            }
            val archive = File(cacheDir, "$name.tar.gz")
            val url = "https://download.jetbrains.com/kotlin/native/$name.tar.gz"
            if (!archive.exists() || archive.length() == 0L) {
                println("Downloading Kotlin/Native dependency $name from $url ...")
                execOps
                    .exec {
                        commandLine("curl", "-sSL", url, "-o", archive.absolutePath)
                    }.assertNormalExitValue()
            }
            println("Extracting $archive into $konanDeps ...")
            execOps
                .exec {
                    commandLine("tar", "-xzf", archive.absolutePath, "-C", konanDeps.absolutePath)
                }.assertNormalExitValue()
        }

        targets.withType<KotlinNativeTarget> {
            val konanTargetName = konanTarget.name
            val targetCapitalized = name.replaceFirstChar { it.uppercase() }
            val wrapperTaskName = "compile${ffiName}WrapperFor$targetCapitalized"
            val wrapperTask =
                tasks.register(wrapperTaskName) {
                    group = "build"
                    description = "Compiles $ffiWrapperBase.c into a static library for $konanTargetName"
                    val cSource = file("src/nativeInterop/cinterop/$ffiWrapperBase.c")
                    val hSource = file("src/nativeInterop/cinterop/$ffiWrapperBase.h")
                    val outDir =
                        layout.buildDirectory
                            .dir("cinterop-targets/$konanTargetName")
                            .get()
                            .asFile
                    val outFile = File(outDir, "$ffiWrapperBase.a")
                    inputs.file(cSource)
                    inputs.file(hSource)
                    outputs.file(outFile)

                    doLast {
                        outDir.mkdirs()
                        if (outFile.exists()) outFile.delete()
                        val tempObj = File(outDir, "$ffiWrapperBase.o")
                        val konanDeps = File(System.getProperty("user.home"), ".konan/dependencies")
                        val isMac =
                            org.gradle.internal.os.OperatingSystem
                                .current()
                                .isMacOsX
                        val isLinux =
                            org.gradle.internal.os.OperatingSystem
                                .current()
                                .isLinux
                        val isWindows =
                            org.gradle.internal.os.OperatingSystem
                                .current()
                                .isWindows
                        val execOps = project.serviceOf<ExecOperations>()

                        when {
                            konanTargetName.startsWith("mingw") -> {
                                ensureKonanDependency("msys2-mingw-w64-x86_64-2", konanDeps, execOps)
                                val mingwSysroot = File(konanDeps, "msys2-mingw-w64-x86_64-2")
                                val arExe = File(mingwSysroot, "bin/ar.exe")
                                if (isWindows && mingwSysroot.exists()) {
                                    execOps
                                        .exec {
                                            commandLine(
                                                "clang",
                                                "--target=x86_64-w64-mingw32",
                                                "--sysroot=${mingwSysroot.absolutePath}",
                                                "-Wall",
                                                "-Wextra",
                                                "-Werror",
                                                "-c",
                                                "-I${cSource.parent}",
                                                cSource.absolutePath,
                                                "-o",
                                                tempObj.absolutePath,
                                            )
                                        }.assertNormalExitValue()
                                    execOps
                                        .exec {
                                            commandLine(
                                                if (arExe.exists()) arExe.absolutePath else "ar",
                                                "rcs",
                                                outFile.absolutePath,
                                                tempObj.absolutePath,
                                            )
                                        }.assertNormalExitValue()
                                } else if (isMac) {
                                    ensureKonanDependency("target-toolchain-2-osx-android_ndk", konanDeps, execOps)
                                    val llvmAr = File(konanDeps, "target-toolchain-2-osx-android_ndk/bin/llvm-ar")
                                    if (mingwSysroot.exists() && llvmAr.exists()) {
                                        execOps
                                            .exec {
                                                commandLine(
                                                    "clang",
                                                    "--target=x86_64-w64-mingw32",
                                                    "--sysroot=${mingwSysroot.absolutePath}",
                                                    "-Wall",
                                                    "-Wextra",
                                                    "-Werror",
                                                    "-c",
                                                    "-I${cSource.parent}",
                                                    cSource.absolutePath,
                                                    "-o",
                                                    tempObj.absolutePath,
                                                )
                                            }.assertNormalExitValue()
                                        execOps
                                            .exec {
                                                commandLine(llvmAr.absolutePath, "rcs", outFile.absolutePath, tempObj.absolutePath)
                                            }.assertNormalExitValue()
                                    } else {
                                        val prebuilt = file("src/nativeInterop/cinterop/targets/mingw_x64/$ffiWrapperBase.a")
                                        if (prebuilt.exists()) prebuilt.copyTo(outFile, overwrite = true)
                                    }
                                } else if (isLinux && mingwSysroot.exists()) {
                                    execOps
                                        .exec {
                                            commandLine(
                                                "clang",
                                                "--target=x86_64-w64-mingw32",
                                                "--sysroot=${mingwSysroot.absolutePath}",
                                                "-Wall",
                                                "-Wextra",
                                                "-Werror",
                                                "-c",
                                                "-I${cSource.parent}",
                                                cSource.absolutePath,
                                                "-o",
                                                tempObj.absolutePath,
                                            )
                                        }.assertNormalExitValue()
                                    execOps
                                        .exec {
                                            commandLine("ar", "rcs", outFile.absolutePath, tempObj.absolutePath)
                                        }.assertNormalExitValue()
                                } else {
                                    val prebuilt = file("src/nativeInterop/cinterop/targets/mingw_x64/$ffiWrapperBase.a")
                                    if (prebuilt.exists()) prebuilt.copyTo(outFile, overwrite = true)
                                }
                            }

                            else -> {
                                val prebuilt = file("src/nativeInterop/cinterop/targets/$konanTargetName/$ffiWrapperBase.a")
                                if (prebuilt.exists()) prebuilt.copyTo(outFile, overwrite = true)
                            }
                        }
                        tempObj.delete()
                        if (!outFile.exists() || outFile.length() == 0L) {
                            throw GradleException(
                                "compile${ffiName}Wrapper failed: output library does not exist for $konanTargetName at ${outFile.absolutePath}",
                            )
                        }
                    }
                }

            compilations.getByName("main") {
                cinterops {
                    create(ffiNameLower) {
                        defFile(project.file(ffiDefFile))
                        packageName("$ffiNameLower.cinterop")
                        extraOpts(
                            "-libraryPath",
                            layout.buildDirectory
                                .dir("cinterop-targets/$konanTargetName")
                                .get()
                                .asFile.absolutePath,
                        )
                    }
                }
            }
            tasks.matching { it.name == "cinterop${ffiName}$targetCapitalized" }.configureEach {
                dependsOn(wrapperTask)
                inputs.files(wrapperTask)
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(commonMainDependencyBundle)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            commonTestDependencyBundle?.let { implementation(it) }
        }
        if (benchmarkEnabled) {
            val commonBenchmark = maybeCreate("commonBenchmark")
            commonBenchmark.dependencies {
                implementation(commonBenchmarkDependencyBundle!!)
            }
            benchmarkTargetNames.forEach { targetName ->
                findByName("${targetName}Benchmark")?.dependsOn(commonBenchmark)
            }
        }
    }
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
    annotation("kotlinx.benchmark.State")
}

if (benchmarkEnabled) {
    benchmark {
        targets {
            benchmarkTargetNames.forEach { targetName ->
                register("${targetName}Benchmark")
            }
        }
        configurations {
            named("main") {
                warmups = benchmarkWarmups
                iterations = benchmarkIterations
                iterationTime = benchmarkIterationTime
                iterationTimeUnit = benchmarkIterationTimeUnit
            }
        }
    }
}

// ============================================================================
// Test logging
// ============================================================================
tasks.withType<AbstractTestTask>().configureEach {
    testLogging {
        events(
            TestLogEvent.STARTED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.FAILED,
            TestLogEvent.STANDARD_OUT,
            TestLogEvent.STANDARD_ERROR,
        )
        exceptionFormat = TestExceptionFormat.FULL
        showCauses = true
        showExceptions = true
        showStackTraces = true
        showStandardStreams = true
    }
}

// ============================================================================
// Static analysis: Detekt + Ktlint
// ============================================================================
detekt {
    buildUponDefaultConfig.set(true)
    allRules.set(false)
    autoCorrect.set(false)
    source.setFrom(files("src"))
    config.setFrom(files("detekt.yml"))
    parallel.set(true)
}

tasks.withType<dev.detekt.gradle.Detekt>().configureEach {
    reports {
        html.required.set(true)
        sarif.required.set(true)
        checkstyle.required.set(false)
    }
}

ktlint {
    version.set(libs.versions.ktlintEngine.get())
    debug.set(false)
    verbose.set(false)
    android.set(false)
    outputToConsole.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
    }
    filter {
        exclude("**/build/**")
        include("**/src/**/kotlin/**")
    }
}

if (benchmarkEnabled) {
    tasks
        .withType<dev.detekt.gradle.Detekt>()
        .matching {
            it.name.contains("BenchmarkBenchmark")
        }.configureEach {
            enabled = false
        }

    tasks
        .matching {
            it.name.startsWith("runKtlintCheckOver") && it.name.endsWith("BenchmarkBenchmarkSourceSet")
        }.configureEach {
            enabled = false
        }
}

tasks.named("check") {
    dependsOn(tasks.withType<dev.detekt.gradle.Detekt>())
    dependsOn(tasks.named("ktlintCheck"))
    dependsOn("test")
}

// ============================================================================
// JS / Wasm toolchain pins
// ============================================================================
val nodeVersion = providers.gradleProperty("node.version").getOrElse("24.15.0")
val wasmNodeVersion = providers.gradleProperty("wasm.node.version").getOrElse(nodeVersion)
val yarnVersion = providers.gradleProperty("yarn.version").getOrElse("1.22.22")
val wasmYarnVersion = providers.gradleProperty("wasm.yarn.version").getOrElse(yarnVersion)

// webpack is pinned in kotlin-js-store/package.json — the single source of truth
// that Dependabot updates natively. Gradle reads the version from there so the
// yarn resolution and the NodeJsRootExtension pin always track the checked-in
// store; a Dependabot bump of package.json/yarn.lock is honored rather than
// overridden. (These two values previously lived in gradle.properties, which
// Dependabot cannot see, so a bump there would silently revert the build.)
@Suppress("UNCHECKED_CAST")
val webpackVersion: String =
    (groovy.json.JsonSlurper().parse(rootProject.file("kotlin-js-store/package.json")) as Map<String, Any>)
        .let { it["dependencies"] as Map<String, Any> }["webpack"] as String

rootProject.extensions.configure<NodeJsEnvSpec>("kotlinNodeJsSpec") { version.set(nodeVersion) }
rootProject.extensions.configure<WasmNodeJsEnvSpec>("kotlinWasmNodeJsSpec") { version.set(wasmNodeVersion) }
rootProject.extensions.configure<YarnRootEnvSpec>("kotlinYarnSpec") {
    version.set(yarnVersion)
    yarnLockMismatchReport.set(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport.WARNING)
    yarnLockAutoReplace.set(true)
}
rootProject.extensions.configure<WasmYarnRootEnvSpec>("kotlinWasmYarnSpec") {
    version.set(wasmYarnVersion)
    yarnLockMismatchReport.set(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport.WARNING)
    yarnLockAutoReplace.set(true)
}

rootProject.extensions.configure<YarnRootExtension>("kotlinYarn") {
    providers
        .gradlePropertiesPrefixedBy("yarn.resolution.")
        .getOrElse(emptyMap())
        .forEach { (key, ver) ->
            val pkg = key.removePrefix("yarn.resolution.")
            resolution(pkg, ver)
            resolution("**/$pkg", ver)
        }
    // webpack resolution sourced from kotlin-js-store/package.json (see above)
    // rather than a yarn.resolution.webpack property, so it can never override a
    // Dependabot bump of the store.
    resolution("webpack", webpackVersion)
    resolution("**/webpack", webpackVersion)
}

val patchedKarmaWebpackPackage =
    rootProject.layout.projectDirectory
        .dir("gradle/npm/karma-webpack")
        .asFile.absolutePath
        .replace("\\", "/")

// TODO: NodeJsRootExtension.versions.* is deprecated and will be removed when the spec-based
//       NodeJsEnvSpec API gains equivalent properties. Track KGP release notes before removing.
rootProject.extensions.configure<NodeJsRootExtension>("kotlinNodeJs") {
    versions.webpack.version = webpackVersion
    versions.webpackCli.version = providers.gradleProperty("node.webpackCli.version").getOrElse("7.0.2")
    versions.karma.version = providers.gradleProperty("node.karma.version").getOrElse("npm:karma-maintained@6.4.7")
    versions.karmaWebpack.version = "file:$patchedKarmaWebpackPackage"
    versions.mocha.version = providers.gradleProperty("node.mocha.version").getOrElse("12.0.0-beta-10")
    versions.kotlinWebHelpers.version = providers.gradleProperty("node.kotlinWebHelpers.version").getOrElse("3.1.0")
}

// Make kotlinUpgradeYarnLock and kotlinWasmUpgradeYarnLock dependencies in the build process
// for KotlinJS and other JavaScript/WASM targets so that yarn.lock is always upgraded automatically.
val jsTasksNeedingYarnLock =
    setOf(
        "compileKotlinJs",
        "compileTestKotlinJs",
        "jsProcessResources",
        "jsTestProcessResources",
        "jsNodeTest",
        "jsBrowserTest",
        "kotlinStoreYarnLock",
    )

tasks
    .matching { it.name in jsTasksNeedingYarnLock }
    .configureEach {
        dependsOn("kotlinUpgradeYarnLock")
    }

val wasmTasksNeedingYarnLock =
    setOf(
        "compileKotlinWasmJs",
        "compileTestKotlinWasmJs",
        "wasmJsProcessResources",
        "wasmJsTestProcessResources",
        "wasmJsNodeTest",
        "wasmJsBrowserTest",
        "compileKotlinWasmWasi",
        "compileTestKotlinWasmWasi",
        "wasmWasiProcessResources",
        "wasmWasiTestProcessResources",
        "wasmWasiNodeTest",
        "kotlinWasmStoreYarnLock",
    )

tasks
    .matching { it.name in wasmTasksNeedingYarnLock }
    .configureEach {
        dependsOn("kotlinWasmUpgradeYarnLock")
    }

// ============================================================================
// Maven Central publishing — Central Portal, first-party + bespoke upload
// ----------------------------------------------------------------------------
// OSSRH was sunset 2025-06-30; the Central Portal is the only path. Sonatype
// ships no first-party Gradle plugin, so we use Gradle's own maven-publish +
// signing (KGP populates the KMP publications) and upload the deployment
// bundle to the Portal API ourselves.
//
// Flow: publish all KMP publications into a local staging Maven layout ->
// zip it -> POST the zip to the Portal upload endpoint with a Bearer token.
// ============================================================================
val publishProjectName = providers.gradleProperty("project.name").getOrElse("unnamed-project")

// Central requires a Javadoc jar per publication; KMP produces none, so attach
// an empty one to every Maven publication.
val emptyJavadocJar =
    tasks.register<Jar>("emptyJavadocJar") {
        archiveClassifier.set("javadoc")
    }

publishing {
    publications.withType<MavenPublication>().configureEach {
        artifact(emptyJavadocJar)
        pom {
            name.set(publishProjectName)
            description.set(providers.gradleProperty("project.pom.description").getOrElse(""))
            inceptionYear.set("2026")
            url.set("https://github.com/KotlinMania/$publishProjectName")
            licenses {
                license {
                    name.set(providers.gradleProperty("project.pom.licenseName").getOrElse("MIT"))
                    url.set(
                        providers
                            .gradleProperty("project.pom.licenseUrl")
                            .getOrElse("https://opensource.org/licenses/MIT"),
                    )
                    distribution.set("repo")
                }
            }
            developers {
                developer {
                    id.set("sydneyrenee")
                    name.set("Sydney Renee")
                    email.set("sydney@solace.ofharmony.ai")
                    url.set("https://github.com/sydneyrenee")
                }
            }
            scm {
                url.set("https://github.com/KotlinMania/$publishProjectName")
                connection.set("scm:git:git://github.com/KotlinMania/$publishProjectName.git")
                developerConnection.set("scm:git:ssh://github.com/KotlinMania/$publishProjectName.git")
            }
        }
    }

    // Stage into a local Maven layout that becomes the Portal deployment bundle.
    // maven-publish auto-generates the md5/sha1/sha256/sha512 checksums Central
    // requires; signing (below) adds the .asc signatures.
    repositories {
        maven {
            name = "centralPortalStaging"
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

signing {
    val signingKey = providers.gradleProperty("signingInMemoryKey").orNull
    val signingKeyId = providers.gradleProperty("signingInMemoryKeyId").orNull
    val signingPassword = providers.gradleProperty("signingInMemoryKeyPassword").orNull
    val signingEnabled = project.findProperty("RELEASE_SIGNING_ENABLED") != "false" && signingKey != null
    if (signingEnabled) {
        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        sign(publishing.publications)
    }
}

val centralPortalPublishTasks =
    tasks.withType<PublishToMavenRepository>().matching {
        it.name.endsWith("ToCentralPortalStagingRepository")
    }

centralPortalPublishTasks.configureEach {
    dependsOn(tasks.withType<Sign>())
}

// Zip the staged Maven layout into a single Central Portal deployment bundle.
val centralPortalBundle =
    tasks.register<Zip>("centralPortalBundle") {
        group = "publishing"
        description = "Bundles the staged Maven artifacts into a Central Portal deployment zip."
        dependsOn(centralPortalPublishTasks)
        from(layout.buildDirectory.dir("staging-deploy"))
        archiveFileName.set("$publishProjectName-$version-bundle.zip")
        destinationDirectory.set(layout.buildDirectory.dir("central-portal"))
    }

// Upload the bundle to the Sonatype Central Portal Publisher API.
// publishingType: USER_MANAGED (default, safe — validates then waits for a
// manual release in the Portal UI) or AUTOMATIC (publishes after validation).
val publishToCentralPortal =
    tasks.register("publishToCentralPortal") {
        group = "publishing"
        description = "Uploads the deployment bundle to the Sonatype Central Portal."
        dependsOn(centralPortalBundle)
        doLast {
            val user =
                providers.gradleProperty("mavenCentralUsername").orNull
                    ?: error("mavenCentralUsername is required to publish to the Central Portal.")
            val password =
                providers.gradleProperty("mavenCentralPassword").orNull
                    ?: error("mavenCentralPassword is required to publish to the Central Portal.")
            val publishingType = providers.gradleProperty("centralPublishingType").getOrElse("USER_MANAGED")
            val token = Base64.getEncoder().encodeToString("$user:$password".toByteArray(Charsets.UTF_8))

            val bundle =
                centralPortalBundle
                    .get()
                    .archiveFile
                    .get()
                    .asFile
            require(bundle.exists()) { "Deployment bundle not found: $bundle" }

            val boundary = "CentralPortalBoundary" + UUID.randomUUID().toString().replace("-", "")
            val crlf = "\r\n"
            val preamble =
                (
                    "--$boundary$crlf" +
                        "Content-Disposition: form-data; name=\"bundle\"; filename=\"${bundle.name}\"$crlf" +
                        "Content-Type: application/octet-stream$crlf$crlf"
                ).toByteArray(Charsets.UTF_8)
            val epilogue = "$crlf--$boundary--$crlf".toByteArray(Charsets.UTF_8)
            val body = preamble + bundle.readBytes() + epilogue

            val deploymentName = "$publishProjectName-$version"
            val uploadUri =
                URI(
                    "https://central.sonatype.com/api/v1/publisher/upload" +
                        "?name=$deploymentName&publishingType=$publishingType",
                )
            val request =
                HttpRequest
                    .newBuilder()
                    .uri(uploadUri)
                    .header("Authorization", "Bearer $token")
                    .header("Content-Type", "multipart/form-data; boundary=$boundary")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                    .build()

            val client = HttpClient.newHttpClient()
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            if (response.statusCode() !in 200..299) {
                error("Central Portal upload failed: HTTP ${response.statusCode()} — ${response.body()}")
            }
            val deploymentId = response.body().trim()
            logger.lifecycle(
                "Central Portal upload accepted (deployment id: $deploymentId). " +
                    "publishingType=$publishingType.",
            )
            val automaticPublishing = publishingType.equals("AUTOMATIC", ignoreCase = true)
            val terminalStates =
                if (automaticPublishing) {
                    setOf("PUBLISHED")
                } else {
                    setOf("VALIDATED", "PUBLISHED")
                }
            val statusUri = URI("https://central.sonatype.com/api/v1/publisher/status?id=$deploymentId")
            val statusAttempts =
                providers.gradleProperty("centralPublishStatusAttempts").map(String::toInt).getOrElse(120)
            val statusDelayMillis =
                providers.gradleProperty("centralPublishStatusDelayMillis").map(String::toLong).getOrElse(10_000L)
            repeat(statusAttempts) { attempt ->
                val statusRequest =
                    HttpRequest
                        .newBuilder()
                        .uri(statusUri)
                        .header("Authorization", "Bearer $token")
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build()
                val statusResponse = client.send(statusRequest, HttpResponse.BodyHandlers.ofString())
                if (statusResponse.statusCode() !in 200..299) {
                    error("Central Portal status check failed: HTTP ${statusResponse.statusCode()} — ${statusResponse.body()}")
                }
                val statusBody = JsonSlurper().parseText(statusResponse.body()) as Map<*, *>
                val deploymentState =
                    statusBody["deploymentState"]?.toString()
                        ?: error("Central Portal status response did not contain deploymentState: ${statusResponse.body()}")
                when (deploymentState) {
                    "FAILED" -> {
                        error("Central Portal deployment failed: ${statusBody["errors"] ?: statusResponse.body()}")
                    }

                    in terminalStates -> {
                        logger.lifecycle("Central Portal deployment $deploymentId reached $deploymentState.")
                        return@doLast
                    }
                }
                logger.lifecycle(
                    "Central Portal deployment $deploymentId is $deploymentState " +
                        "(${attempt + 1}/$statusAttempts).",
                )
                if (attempt + 1 < statusAttempts) {
                    Thread.sleep(statusDelayMillis)
                }
            }
            error(
                "Central Portal deployment $deploymentId did not reach " +
                    "${terminalStates.joinToString("/")} after $statusAttempts checks.",
            )
        }
    }

// ============================================================================
// Tasks
// ============================================================================

// Exact test lifecycle task. Runs commonTest through the KMP allTests lifecycle and host tests.
tasks.register("test") {
    group = "verification"
    description = "Runs the commonTest-backed KMP suite and host tests."
    dependsOn("hostTests")
}

// ============================================================================
// JNI — JVM C bindings. Gated by project.ffi.jvm=true.
// ----------------------------------------------------------------------------
// Compiles src/jvmMain/c/${ffiNameLower}_jni.c into a shared library with
// the host clang and wires the resulting directory into
// -Djava.library.path for jvmTest, so `System.loadLibrary(...)` in the
// internal `<Lib>Jni` bridge resolves. Transliterated from libc-kotlin's
// compileLibcJni with the lib name parameterized on project.ffi.name.
// ============================================================================
if (jvmFfiEnabled) {
    val compileFfiJni =
        tasks.register("compile${ffiName}Jni") {
            group = "build"
            description = "Compiles ${ffiNameLower}_jni.c into a shared library for JVM JNI binding"
            val cSource = file("src/jvmMain/c/${ffiNameLower}_jni.c")
            val outDir =
                layout.buildDirectory
                    .dir("natives")
                    .get()
                    .asFile
            inputs.file(cSource)
            outputs.dir(outDir)

            doLast {
                outDir.mkdirs()
                val javaHome = System.getProperty("java.home") ?: System.getenv("JAVA_HOME") ?: ""
                val isWindows =
                    org.gradle.internal.os.OperatingSystem
                        .current()
                        .isWindows
                val isMac =
                    org.gradle.internal.os.OperatingSystem
                        .current()
                        .isMacOsX

                val (libName, osInclude) =
                    when {
                        isWindows -> "${ffiNameLower}_jni.dll" to "win32"
                        isMac -> "lib${ffiNameLower}_jni.dylib" to "darwin"
                        else -> "lib${ffiNameLower}_jni.so" to "linux"
                    }
                val outFile = File(outDir, libName)
                val javaInclude = File(javaHome, "include")
                val javaOsInclude = File(javaInclude, osInclude)

                if (javaInclude.exists()) {
                    val execOps = project.serviceOf<ExecOperations>()
                    try {
                        val cmd =
                            mutableListOf(
                                "clang",
                                "-shared",
                                "-O2",
                                "-Wall",
                                "-Wextra",
                                "-Werror",
                                "-I${javaInclude.absolutePath}",
                                "-I${javaOsInclude.absolutePath}",
                                cSource.absolutePath,
                                "-o",
                                outFile.absolutePath,
                            )
                        if (!isWindows) {
                            cmd.add(2, "-fPIC")
                        } else {
                            cmd.addAll(listOf("-lws2_32", "-lkernel32", "-luser32", "-loleaut32", "-ladvapi32"))
                        }
                        execOps
                            .exec {
                                commandLine(cmd)
                            }.assertNormalExitValue()
                    } catch (e: Exception) {
                        logger.warn("Could not compile ${ffiNameLower}_jni with host clang: ${e.message}")
                    }
                }
            }
        }

    tasks.withType<Test>().configureEach {
        dependsOn(compileFfiJni)
        systemProperty(
            "java.library.path",
            layout.buildDirectory
                .dir("natives")
                .get()
                .asFile.absolutePath,
        )
    }
}

// ============================================================================
// N-API — JS/WASM C bindings. Gated by project.ffi.napi=true.
// ----------------------------------------------------------------------------
// Builds the native/node-${ffiNameLower}/ addon via node-gyp and copies it
// into the JS test runtime's node_modules so the Kotlin-side @JsModule
// declaration resolves.
// ============================================================================
if (napiFfiEnabled) {
    val buildNodeFfi =
        tasks.register("buildNode$ffiName") {
            group = "build"
            description = "Builds the native node-$ffiNameLower N-API addon via node-gyp"
            val addonFile = file("$ffiNapiDir/build/Release/${ffiNameLower}_native.node")
            inputs.dir("$ffiNapiDir/src")
            inputs.file("$ffiNapiDir/binding.gyp")
            inputs.file("$ffiNapiDir/index.js")
            outputs.file(addonFile)
            doLast {
                val isWindows =
                    org.gradle.internal.os.OperatingSystem
                        .current()
                        .isWindows
                if (isWindows && addonFile.exists()) {
                    return@doLast
                }
                val execOps = project.serviceOf<ExecOperations>()
                if (isWindows) {
                    execOps
                        .exec {
                            workingDir(ffiNapiDir)
                            commandLine("cmd", "/c", "npm install --ignore-scripts && npx node-gyp configure && npx node-gyp build")
                        }.assertNormalExitValue()
                } else {
                    execOps
                        .exec {
                            workingDir(ffiNapiDir)
                            commandLine("sh", "-c", "npm install --ignore-scripts && npx node-gyp configure && npx node-gyp build")
                        }.assertNormalExitValue()
                }
            }
        }

    val copyNodeFfi =
        tasks.register<Copy>("copyNode$ffiName") {
            dependsOn(buildNodeFfi)
            mustRunAfter("kotlinNpmInstall")
            outputs.upToDateWhen { false }
            from(ffiNapiDir)
            into(layout.buildDirectory.dir("js/node_modules/$ffiNpmPackage"))
        }
    val copyNodeFfiWasm =
        tasks.register<Copy>("copyNode${ffiName}Wasm") {
            dependsOn(buildNodeFfi)
            mustRunAfter("kotlinWasmNpmInstall")
            outputs.upToDateWhen { false }
            from(ffiNapiDir)
            into(layout.buildDirectory.dir("wasm/node_modules/$ffiNpmPackage"))
        }
    tasks.matching { it.name.startsWith("jsNodeTest") || it.name.startsWith("wasmJsNodeTest") }.configureEach {
        dependsOn(copyNodeFfi, copyNodeFfiWasm)
    }
}

// Explicit test runner. Named hostTests to avoid shadowing the KMP allTests
// lifecycle task.
tasks.register("hostTests") {
    group = "verification"
    description = "Runs the required real test suite (jvm, js, wasmJs, wasmWasi)."
    dependsOn(
        "jvmTest",
        "kotlinUpgradeYarnLock",
        "jsNodeTest",
        "kotlinWasmUpgradeYarnLock",
        "wasmJsNodeTest",
        "wasmWasiNodeTest",
    )
}

// ============================================================================
// `build` aggregate
// ----------------------------------------------------------------------------
// mingwX64 is the native target for Windows.
// ============================================================================
val nativeTargetNames =
    listOf(
        "mingwX64",
    )

val fullTargetBuildTaskNames =
    buildSet {
        addAll(
            listOf(
                "jvmMainClasses",
                "jvmTestClasses",
                "kotlinUpgradeYarnLock",
                "kotlinWasmUpgradeYarnLock",
                "jsMainClasses",
                "jsTestClasses",
                "wasmJsMainClasses",
                "wasmJsTestClasses",
                "wasmWasiMainClasses",
                "wasmWasiTestClasses",
            ),
        )
        for (target in nativeTargetNames) {
            add("${target}Binaries")
            add("${target}TestBinaries")
        }
        // Native C binding channels, included only when their gate is on.
        // A declared channel that fails to build must fail the build loudly.
        if (jvmFfiEnabled) add("compile${ffiName}Jni")
        if (napiFfiEnabled) add("buildNode$ffiName")
    }

tasks.named("build") {
    dependsOn(fullTargetBuildTaskNames)
}
