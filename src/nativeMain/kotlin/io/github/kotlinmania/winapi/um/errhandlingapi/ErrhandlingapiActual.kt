// port-lint: source um/errhandlingapi.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.errhandlingapi

import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import winapi.cinterop.winapi_get_last_error
import winapi.cinterop.winapi_set_last_error

public actual fun GetLastError(): DWORD = winapi_get_last_error()

public actual fun SetLastError(dwErrCode: DWORD) {
    winapi_set_last_error(dwErrCode)
}
