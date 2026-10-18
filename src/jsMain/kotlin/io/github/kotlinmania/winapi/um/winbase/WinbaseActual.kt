// port-lint: source um/winbase.rs
package io.github.kotlinmania.winapi.um.winbase

import io.github.kotlinmania.winapi.internal.WinapiNative
import io.github.kotlinmania.winapi.shared.minwindef.HGLOBAL
import io.github.kotlinmania.winapi.shared.minwindef.HLOCAL
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.shared.ntdef.LONG
public actual fun GlobalFree(hMem: HGLOBAL): HGLOBAL =
    WinapiNative.GlobalFree(hMem.toLong().toDouble()).toLong().toULong()

public actual fun LocalFree(hMem: HLOCAL): HLOCAL =
    WinapiNative.LocalFree(hMem.toLong().toDouble()).toLong().toULong()

public actual fun RtlNtStatusToDosError(status: LONG): ULONG =
    WinapiNative.RtlNtStatusToDosError(status).toUInt()
