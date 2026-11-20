// port-lint: source um/winnt.rs
package io.github.kotlinmania.winapi.um.winnt

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
import io.github.kotlinmania.winapi.shared.basetsd.SIZE_T
import io.github.kotlinmania.winapi.shared.minwindef.BYTE
import io.github.kotlinmania.winapi.shared.ntdef.PVOID

public actual fun RtlMoveMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
    if (Length != 0uL && Destination != 0uL && Source != 0uL) {
        WinapiNapiWasm.RtlMoveMemory(Destination.toLong(), Source.toLong(), Length.toLong())
    }
}

public actual fun RtlCopyMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
    if (Length != 0uL && Destination != 0uL && Source != 0uL) {
        WinapiNapiWasm.RtlCopyMemory(Destination.toLong(), Source.toLong(), Length.toLong())
    }
}

public actual fun RtlFillMemory(Destination: PVOID, Length: SIZE_T, Fill: BYTE) {
    if (Length != 0uL && Destination != 0uL) {
        WinapiNapiWasm.RtlFillMemory(Destination.toLong(), Length.toLong(), Fill.toInt())
    }
}

public actual fun RtlZeroMemory(Destination: PVOID, Length: SIZE_T) {
    if (Length != 0uL && Destination != 0uL) {
        WinapiNapiWasm.RtlZeroMemory(Destination.toLong(), Length.toLong())
    }
}
