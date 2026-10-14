// port-lint: source um/winuser.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.winuser

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import winapi.cinterop.winapi_lock_work_station
import winapi.cinterop.winapi_set_last_error_ex

public actual fun SetLastErrorEx(dwErrCode: DWORD, dwType: DWORD) {
    winapi_set_last_error_ex(dwErrCode, dwType)
}

public actual fun LockWorkStation(): BOOL = winapi_lock_work_station()
