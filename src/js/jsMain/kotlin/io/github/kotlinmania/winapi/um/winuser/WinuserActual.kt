// port-lint: source um/winuser.rs
package io.github.kotlinmania.winapi.um.winuser

import io.github.kotlinmania.winapi.internal.WinapiNative
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD

public actual fun SetLastErrorEx(dwErrCode: DWORD, dwType: DWORD) {
    WinapiNative.SetLastErrorEx(dwErrCode.toInt(), dwType.toInt())
}

public actual fun LockWorkStation(): BOOL = WinapiNative.LockWorkStation()
