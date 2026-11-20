// port-lint: source um/winnt.rs
package io.github.kotlinmania.winapi.um.winnt

import io.github.kotlinmania.winapi.internal.WinapiNative
import io.github.kotlinmania.winapi.shared.basetsd.SIZE_T
import io.github.kotlinmania.winapi.shared.minwindef.BYTE
import io.github.kotlinmania.winapi.shared.ntdef.PVOID

public actual fun RtlMoveMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
    if (Length != 0uL && Destination != 0uL && Source != 0uL) {
        WinapiNative.RtlMoveMemory(Destination.toLong().toDouble(), Source.toLong().toDouble(), Length.toLong().toDouble())
    }
}

public actual fun RtlCopyMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
    if (Length != 0uL && Destination != 0uL && Source != 0uL) {
        WinapiNative.RtlCopyMemory(Destination.toLong().toDouble(), Source.toLong().toDouble(), Length.toLong().toDouble())
    }
}

public actual fun RtlFillMemory(Destination: PVOID, Length: SIZE_T, Fill: BYTE) {
    if (Length != 0uL && Destination != 0uL) {
        WinapiNative.RtlFillMemory(Destination.toLong().toDouble(), Length.toLong().toDouble(), Fill.toInt())
    }
}

public actual fun RtlZeroMemory(Destination: PVOID, Length: SIZE_T) {
    if (Length != 0uL && Destination != 0uL) {
        WinapiNative.RtlZeroMemory(Destination.toLong().toDouble(), Length.toLong().toDouble())
    }
}
