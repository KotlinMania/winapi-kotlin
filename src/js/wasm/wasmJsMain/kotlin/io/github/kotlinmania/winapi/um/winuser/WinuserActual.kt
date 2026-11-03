// port-lint: source um/winuser.rs
package io.github.kotlinmania.winapi.um.winuser

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD

public actual fun SetLastErrorEx(dwErrCode: DWORD, dwType: DWORD) {
    WinapiNapiWasm.SetLastErrorEx(dwErrCode, dwType)
}

public actual fun LockWorkStation(): BOOL = WinapiNapiWasm.LockWorkStation()
