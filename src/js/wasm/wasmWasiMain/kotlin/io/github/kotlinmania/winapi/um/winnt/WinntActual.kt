// port-lint: source um/winnt.rs
package io.github.kotlinmania.winapi.um.winnt

import io.github.kotlinmania.winapi.shared.basetsd.SIZE_T
import io.github.kotlinmania.winapi.shared.minwindef.BYTE
import io.github.kotlinmania.winapi.shared.ntdef.PVOID

public actual fun RtlMoveMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
    if (Length != 0uL && Destination != 0uL && Source != 0uL) {
        error("Raw native memory pointers are unavailable on Wasm/WASI")
    }
}

public actual fun RtlCopyMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
    if (Length != 0uL && Destination != 0uL && Source != 0uL) {
        error("Raw native memory pointers are unavailable on Wasm/WASI")
    }
}

public actual fun RtlFillMemory(Destination: PVOID, Length: SIZE_T, Fill: BYTE) {
    if (Length != 0uL && Destination != 0uL) {
        error("Raw native memory pointers are unavailable on Wasm/WASI")
    }
}

public actual fun RtlZeroMemory(Destination: PVOID, Length: SIZE_T) {
    if (Length != 0uL && Destination != 0uL) {
        error("Raw native memory pointers are unavailable on Wasm/WASI")
    }
}
