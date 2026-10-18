// port-lint: source um/psapi.rs
package io.github.kotlinmania.winapi.um.psapi

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE

public actual fun K32EmptyWorkingSet(hProcess: HANDLE): BOOL =
    WinapiNapiWasm.K32EmptyWorkingSet(hProcess.toLong())
