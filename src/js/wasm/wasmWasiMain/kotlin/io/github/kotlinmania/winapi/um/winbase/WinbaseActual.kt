// port-lint: source um/winbase.rs
package io.github.kotlinmania.winapi.um.winbase

import io.github.kotlinmania.winapi.shared.minwindef.HGLOBAL
import io.github.kotlinmania.winapi.shared.minwindef.HLOCAL
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.shared.ntdef.LONG

public actual fun GlobalFree(hMem: HGLOBAL): HGLOBAL = 0uL

public actual fun LocalFree(hMem: HLOCAL): HLOCAL = 0uL

public actual fun RtlNtStatusToDosError(status: LONG): ULONG = 0u
