// port-lint: source um/winbase.rs
package io.github.kotlinmania.winapi.um.winbase

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
import io.github.kotlinmania.winapi.shared.minwindef.HGLOBAL
import io.github.kotlinmania.winapi.shared.minwindef.HLOCAL
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.um.winnt.LONG

public actual fun GlobalFree(hMem: HGLOBAL): HGLOBAL =
    WinapiNapiWasm.GlobalFree(hMem.toLong()).toULong()

public actual fun LocalFree(hMem: HLOCAL): HLOCAL =
    WinapiNapiWasm.LocalFree(hMem.toLong()).toULong()

public actual fun RtlNtStatusToDosError(status: LONG): ULONG =
    WinapiNapiWasm.RtlNtStatusToDosError(status)
