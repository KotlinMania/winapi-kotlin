package io.github.kotlinmania.winapi.internal

private val winapiNapiAddon: dynamic =
    js(
        "(function() { " +
            "if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_winapi !== 'undefined') return globalThis.__km_napi_winapi; " +
            "if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; " +
            "var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); " +
            "var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); " +
            "if (!req) return null; " +
            "try { var pkg = req('@kotlinmania/winapi-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_winapi = pkg; return pkg; } catch (_) {} " +
            "var fs = req('fs'); " +
            "var path = req('path'); " +
            "var dir = process.cwd ? process.cwd() : '.'; " +
            "var rels = [" +
            "'winapi_native.node', " +
            "'build/node-ffi/winapi_native.node', " +
            "'src/js/node-winapi/dist/build/Release/winapi_native.node', " +
            "'src/js/node-winapi/dist/build/Debug/winapi_native.node'" +
            "]; " +
            "for (var d = 0; d < 8; d++) { " +
            "for (var i = 0; i < rels.length; i++) { " +
            "var c = path.resolve(dir, rels[i]); " +
            "if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_winapi = loaded; return loaded; } " +
            "} " +
            "var parent = path.dirname(dir); " +
            "if (parent === dir) break; " +
            "dir = parent; " +
            "} " +
            "if (typeof globalThis !== 'undefined') globalThis.__km_napi_winapi = null; " +
            "return null; " +
            "})()",
    )

internal object WinapiNative {
    private fun requireAddon(): dynamic =
        winapiNapiAddon ?: throw UnsupportedOperationException("Winapi native FFI addon is not available")

    fun CloseHandle(hObject: Double): Int = requireAddon().CloseHandle(hObject) as Int

    fun GetLastError(): Int = requireAddon().GetLastError() as Int

    fun SetLastError(dwErrCode: Int) {
        requireAddon().SetLastError(dwErrCode)
    }

    fun SetLastErrorEx(dwErrCode: Int, dwType: Int) {
        requireAddon().SetLastErrorEx(dwErrCode, dwType)
    }

    fun LocalFree(hMem: Double): Double = requireAddon().LocalFree(hMem) as Double

    fun GlobalFree(hMem: Double): Double = requireAddon().GlobalFree(hMem) as Double

    fun FreeLibrary(hLibModule: Double): Int = requireAddon().FreeLibrary(hLibModule) as Int

    fun SetHandleInformation(hObject: Double, dwMask: Int, dwFlags: Int): Int =
        requireAddon().SetHandleInformation(hObject, dwMask, dwFlags) as Int

    fun GetHandleInformation(hObject: Double): dynamic = requireAddon().GetHandleInformation(hObject)

    fun CompareObjectHandles(hFirstObjectHandle: Double, hSecondObjectHandle: Double): Int =
        requireAddon().CompareObjectHandles(hFirstObjectHandle, hSecondObjectHandle) as Int

    fun DuplicateHandle(
        hSourceProcessHandle: Double,
        hSourceHandle: Double,
        hTargetProcessHandle: Double,
        dwDesiredAccess: Int,
        bInheritHandle: Int,
        dwOptions: Int,
    ): dynamic =
        requireAddon().DuplicateHandle(
            hSourceProcessHandle,
            hSourceHandle,
            hTargetProcessHandle,
            dwDesiredAccess,
            bInheritHandle,
            dwOptions,
        )

    fun RtlNtStatusToDosError(status: Int): Int = requireAddon().RtlNtStatusToDosError(status) as Int

    fun SysAllocString(psz: Double): Double = requireAddon().SysAllocString(psz) as Double

    fun SysAllocStringByteLen(psz: Double, len: Int): Double = requireAddon().SysAllocStringByteLen(psz, len) as Double

    fun SysAllocStringLen(strIn: Double, ui: Int): Double = requireAddon().SysAllocStringLen(strIn, ui) as Double

    fun SysFreeString(bstrString: Double) {
        requireAddon().SysFreeString(bstrString)
    }

    fun SysReAllocString(currentVal: Double, psz: Double): dynamic = requireAddon().SysReAllocString(currentVal, psz)

    fun SysReAllocStringLen(currentVal: Double, psz: Double, len: Int): dynamic =
        requireAddon().SysReAllocStringLen(currentVal, psz, len)

    fun SysAddRefString(bstrString: Double): Int = requireAddon().SysAddRefString(bstrString) as Int

    fun SysReleaseString(bstrString: Double) {
        requireAddon().SysReleaseString(bstrString)
    }

    fun SysStringByteLen(bstr: Double): Int = requireAddon().SysStringByteLen(bstr) as Int

    fun SysStringLen(pbstr: Double): Int = requireAddon().SysStringLen(pbstr) as Int

    fun SetFileAttributesW(lpFileName: Double, dwFileAttributes: Int): Int =
        requireAddon().SetFileAttributesW(lpFileName, dwFileAttributes) as Int

    fun SetFileAttributesA(lpFileName: Double, dwFileAttributes: Int): Int =
        requireAddon().SetFileAttributesA(lpFileName, dwFileAttributes) as Int

    fun GetFileAttributesW(lpFileName: Double): Int = requireAddon().GetFileAttributesW(lpFileName) as Int

    fun GetFileAttributesA(lpFileName: Double): Int = requireAddon().GetFileAttributesA(lpFileName) as Int

    fun CreateFileW(
        lpFileName: Double,
        dwDesiredAccess: Int,
        dwShareMode: Int,
        lpSecurityAttributes: Double,
        dwCreationDisposition: Int,
        dwFlagsAndAttributes: Int,
        hTemplateFile: Double,
    ): Double =
        requireAddon().CreateFileW(
            lpFileName,
            dwDesiredAccess,
            dwShareMode,
            lpSecurityAttributes,
            dwCreationDisposition,
            dwFlagsAndAttributes,
            hTemplateFile,
        ) as Double

    fun CreateFileA(
        lpFileName: Double,
        dwDesiredAccess: Int,
        dwShareMode: Int,
        lpSecurityAttributes: Double,
        dwCreationDisposition: Int,
        dwFlagsAndAttributes: Int,
        hTemplateFile: Double,
    ): Double =
        requireAddon().CreateFileA(
            lpFileName,
            dwDesiredAccess,
            dwShareMode,
            lpSecurityAttributes,
            dwCreationDisposition,
            dwFlagsAndAttributes,
            hTemplateFile,
        ) as Double

    fun DeleteFileW(lpFileName: Double): Int = requireAddon().DeleteFileW(lpFileName) as Int

    fun DeleteFileA(lpFileName: Double): Int = requireAddon().DeleteFileA(lpFileName) as Int

    fun FlushFileBuffers(hFile: Double): Int = requireAddon().FlushFileBuffers(hFile) as Int

    fun AreFileApisANSI(): Int = requireAddon().AreFileApisANSI() as Int

    fun GetFileSize(hFile: Double, hasHigh: Int): dynamic = requireAddon().GetFileSize(hFile, hasHigh)

    fun SetFilePointer(
        hFile: Double,
        lDistanceToMove: Int,
        hasHigh: Int,
        inHigh: Int,
        dwMoveMethod: Int,
    ): dynamic = requireAddon().SetFilePointer(hFile, lDistanceToMove, hasHigh, inHigh, dwMoveMethod)

    fun GetCurrentProcess(): Double = requireAddon().GetCurrentProcess() as Double

    fun GetCurrentProcessId(): Int = requireAddon().GetCurrentProcessId() as Int

    fun GetCurrentThread(): Double = requireAddon().GetCurrentThread() as Double

    fun GetCurrentThreadId(): Int = requireAddon().GetCurrentThreadId() as Int

    fun TerminateProcess(hProcess: Double, uExitCode: Int): Int = requireAddon().TerminateProcess(hProcess, uExitCode) as Int

    fun ExitProcess(uExitCode: Int) {
        requireAddon().ExitProcess(uExitCode)
    }

    fun OpenProcess(dwDesiredAccess: Int, bInheritHandle: Int, dwProcessId: Int): Double =
        requireAddon().OpenProcess(dwDesiredAccess, bInheritHandle, dwProcessId) as Double

    fun K32EmptyWorkingSet(hProcess: Double): Int = requireAddon().K32EmptyWorkingSet(hProcess) as Int

    fun GetTimeZoneInformation(lpTimeZoneInformation: Double): Int =
        requireAddon().GetTimeZoneInformation(lpTimeZoneInformation) as Int

    fun LockWorkStation(): Int = requireAddon().LockWorkStation() as Int

    fun DisconnectNamedPipe(hNamedPipe: Double): Int = requireAddon().DisconnectNamedPipe(hNamedPipe) as Int

    fun isNativeAvailable(): Boolean = winapiNapiAddon != null && (winapiNapiAddon.isNativeAvailable() as Boolean)
}
