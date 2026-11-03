// port-lint: source um/errhandlingapi.rs
package io.github.kotlinmania.winapi.um.errhandlingapi

import io.github.kotlinmania.winapi.internal.WinapiNative
import io.github.kotlinmania.winapi.shared.minwindef.DWORD

public actual fun GetLastError(): DWORD = WinapiNative.GetLastError().toUInt()

public actual fun SetLastError(dwErrCode: DWORD) {
    WinapiNative.SetLastError(dwErrCode.toInt())
}
