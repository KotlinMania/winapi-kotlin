// port-lint: source winapi/src/shared/d3d9types.rs
package io.github.kotlinmania.winapi.shared.d3d9types

import io.github.kotlinmania.winapi.CFloat
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.ntdef.LONG

public typealias D3DCOLOR = DWORD

public fun D3DCOLOR_ARGB(a: DWORD, r: DWORD, g: DWORD, b: DWORD): D3DCOLOR =
    (((a and 0xffu) shl 24) or ((r and 0xffu) shl 16) or ((g and 0xffu) shl 8) or (b and 0xffu))

public fun D3DCOLOR_RGBA(r: DWORD, g: DWORD, b: DWORD, a: DWORD): D3DCOLOR =
    D3DCOLOR_ARGB(a, r, g, b)

public fun D3DCOLOR_XRGB(r: DWORD, g: DWORD, b: DWORD): D3DCOLOR =
    D3DCOLOR_ARGB(0xffu, r, g, b)

public fun D3DCOLOR_XYUV(y: DWORD, u: DWORD, v: DWORD): D3DCOLOR =
    D3DCOLOR_ARGB(0xffu, y, u, v)

public fun D3DCOLOR_AYUV(a: DWORD, y: DWORD, u: DWORD, v: DWORD): D3DCOLOR =
    D3DCOLOR_ARGB(a, y, u, v)

public fun D3DCOLOR_COLORVALUE(r: Float, g: Float, b: Float, a: Float): D3DCOLOR =
    D3DCOLOR_ARGB(
        (r * 255.0f).toUInt(),
        (g * 255.0f).toUInt(),
        (b * 255.0f).toUInt(),
        (a * 255.0f).toUInt(),
    )

public data class D3DVECTOR(
    val x: CFloat,
    val y: CFloat,
    val z: CFloat,
)

public data class D3DCOLORVALUE(
    val r: CFloat,
    val g: CFloat,
    val b: CFloat,
    val a: CFloat,
)

public data class D3DRECT(
    val x1: LONG,
    val y1: LONG,
    val x2: LONG,
    val y2: LONG,
)

public data class D3DMATRIX(
    val m: List<List<CFloat>>,
)
