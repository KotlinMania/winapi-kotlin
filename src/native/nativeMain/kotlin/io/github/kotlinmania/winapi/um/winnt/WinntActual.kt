// port-lint: source um/winnt.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.winnt

import io.github.kotlinmania.winapi.shared.basetsd.SIZE_T
import io.github.kotlinmania.winapi.shared.minwindef.BYTE
import io.github.kotlinmania.winapi.shared.ntdef.PVOID
import winapi.cinterop.winapi_rtl_copy_memory
import winapi.cinterop.winapi_rtl_fill_memory
import winapi.cinterop.winapi_rtl_move_memory
import winapi.cinterop.winapi_rtl_zero_memory

public actual fun RtlMoveMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
    winapi_rtl_move_memory(Destination.toLong(), Source.toLong(), Length)
}

public actual fun RtlCopyMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
    winapi_rtl_copy_memory(Destination.toLong(), Source.toLong(), Length)
}

public actual fun RtlFillMemory(Destination: PVOID, Length: SIZE_T, Fill: BYTE) {
    winapi_rtl_fill_memory(Destination.toLong(), Length, Fill)
}

public actual fun RtlZeroMemory(Destination: PVOID, Length: SIZE_T) {
    winapi_rtl_zero_memory(Destination.toLong(), Length)
}
