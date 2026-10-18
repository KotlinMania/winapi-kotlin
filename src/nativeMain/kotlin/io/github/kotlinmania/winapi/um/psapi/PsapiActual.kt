// port-lint: source um/psapi.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.psapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import winapi.cinterop.winapi_k32_empty_working_set

public actual fun K32EmptyWorkingSet(hProcess: HANDLE): BOOL =
    winapi_k32_empty_working_set(hProcess.toLong())
