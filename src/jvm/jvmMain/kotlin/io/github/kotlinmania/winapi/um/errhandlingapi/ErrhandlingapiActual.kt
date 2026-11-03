// port-lint: source um/errhandlingapi.rs
package io.github.kotlinmania.winapi.um.errhandlingapi

import io.github.kotlinmania.winapi.internal.WinapiJni
import io.github.kotlinmania.winapi.shared.minwindef.DWORD

public actual fun GetLastError(): DWORD = WinapiJni.GetLastError().toUInt()

public actual fun SetLastError(dwErrCode: DWORD) {
    WinapiJni.SetLastError(dwErrCode.toInt())
}
