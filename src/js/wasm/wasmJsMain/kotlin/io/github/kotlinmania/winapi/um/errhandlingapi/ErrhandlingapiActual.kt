// port-lint: source um/errhandlingapi.rs
package io.github.kotlinmania.winapi.um.errhandlingapi

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
import io.github.kotlinmania.winapi.shared.minwindef.DWORD

public actual fun GetLastError(): DWORD = WinapiNapiWasm.GetLastError()

public actual fun SetLastError(dwErrCode: DWORD) {
    WinapiNapiWasm.SetLastError(dwErrCode)
}
