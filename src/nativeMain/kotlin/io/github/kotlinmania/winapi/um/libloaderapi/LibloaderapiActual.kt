// port-lint: source um/libloaderapi.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.libloaderapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.HMODULE
import winapi.cinterop.winapi_free_library

public actual fun FreeLibrary(hLibModule: HMODULE): BOOL =
    winapi_free_library(hLibModule.toLong())
