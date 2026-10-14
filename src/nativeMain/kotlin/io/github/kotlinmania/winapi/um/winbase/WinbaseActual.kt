// port-lint: source um/winbase.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.winbase

import io.github.kotlinmania.winapi.shared.minwindef.HGLOBAL
import io.github.kotlinmania.winapi.shared.minwindef.HLOCAL
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.um.winnt.LONG
import winapi.cinterop.winapi_global_free
import winapi.cinterop.winapi_local_free
import winapi.cinterop.winapi_rtl_nt_status_to_dos_error

public actual fun GlobalFree(hMem: HGLOBAL): HGLOBAL =
    winapi_global_free(hMem.toLong()).toULong()

public actual fun LocalFree(hMem: HLOCAL): HLOCAL =
    winapi_local_free(hMem.toLong()).toULong()

public actual fun RtlNtStatusToDosError(status: LONG): ULONG =
    winapi_rtl_nt_status_to_dos_error(status)
