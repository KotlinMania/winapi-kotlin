// port-lint: source shared/guiddef.rs
package io.github.kotlinmania.winapi.shared.guiddef

import io.github.kotlinmania.winapi.CUchar
import io.github.kotlinmania.winapi.CUlong
import io.github.kotlinmania.winapi.CUshort

// GUID definition.
//
// Rust upstream uses the `STRUCT!` macro to derive Debug + Copy + Clone
// on a layout-compatible struct. Kotlin's data class gives equivalent
// equality / debug / clone semantics; the 8-byte Data4 array is modeled
// as a fixed-length UByteArray and equality is implemented manually
// because UByteArray uses reference equality by default.

class GUID(
    val Data1: CUlong,
    val Data2: CUshort,
    val Data3: CUshort,
    val Data4: UByteArray,
) {
    init {
        require(Data4.size == 8) { "GUID.Data4 must be 8 bytes" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GUID) return false
        return Data1 == other.Data1
            && Data2 == other.Data2
            && Data3 == other.Data3
            && Data4.contentEquals(other.Data4)
    }

    override fun hashCode(): Int {
        var h = Data1.hashCode()
        h = 31 * h + Data2.hashCode()
        h = 31 * h + Data3.hashCode()
        h = 31 * h + Data4.contentHashCode()
        return h
    }

    override fun toString(): String =
        "GUID(Data1=$Data1, Data2=$Data2, Data3=$Data3, Data4=[${Data4.joinToString()}])"
}

typealias LPGUID = ULong
typealias LPCGUID = ULong
typealias IID = GUID
typealias LPIID = ULong

/** Re-export of [IsEqualGUID] under the IID alias. */
fun IsEqualIID(g1: IID, g2: IID): Boolean = IsEqualGUID(g1, g2)

typealias CLSID = GUID
typealias LPCLSID = ULong

/** Re-export of [IsEqualGUID] under the CLSID alias. */
fun IsEqualCLSID(g1: CLSID, g2: CLSID): Boolean = IsEqualGUID(g1, g2)

typealias FMTID = GUID
typealias LPFMTID = ULong

/** Re-export of [IsEqualGUID] under the FMTID alias. */
fun IsEqualFMTID(g1: FMTID, g2: FMTID): Boolean = IsEqualGUID(g1, g2)

typealias REFGUID = ULong
typealias REFIID = ULong
typealias REFCLSID = ULong
typealias REFFMTID = ULong

/** Expansion of upstream `DEFINE_GUID!{IID_NULL, 0x00..., 0x0000, ... }`. */
val IID_NULL: GUID = GUID(
    Data1 = 0u,
    Data2 = 0u,
    Data3 = 0u,
    Data4 = ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u),
)

fun IsEqualGUID(g1: GUID, g2: GUID): Boolean = g1 == g2
