// port-lint: source shared/inaddr.rs
package io.github.kotlinmania.winapi.shared.inaddr

import io.github.kotlinmania.winapi.shared.minwindef.UCHAR
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.shared.minwindef.USHORT

public data class InAddrSUnB(
    public var s_b1: UCHAR = 0u,
    public var s_b2: UCHAR = 0u,
    public var s_b3: UCHAR = 0u,
    public var s_b4: UCHAR = 0u,
)

public data class InAddrSUnW(
    public var s_w1: USHORT = 0u,
    public var s_w2: USHORT = 0u,
)

public class IN_ADDR(
    public var s_addr: ULONG = 0u,
) {
    public var s_un_b: InAddrSUnB
        get() = InAddrSUnB(
            s_b1 = (s_addr and 0xFFu).toUByte(),
            s_b2 = ((s_addr shr 8) and 0xFFu).toUByte(),
            s_b3 = ((s_addr shr 16) and 0xFFu).toUByte(),
            s_b4 = ((s_addr shr 24) and 0xFFu).toUByte(),
        )
        set(value) {
            s_addr = value.s_b1.toUInt() or
                (value.s_b2.toUInt() shl 8) or
                (value.s_b3.toUInt() shl 16) or
                (value.s_b4.toUInt() shl 24)
        }

    public var s_un_w: InAddrSUnW
        get() = InAddrSUnW(
            s_w1 = (s_addr and 0xFFFFu).toUShort(),
            s_w2 = ((s_addr shr 16) and 0xFFFFu).toUShort(),
        )
        set(value) {
            s_addr = value.s_w1.toUInt() or (value.s_w2.toUInt() shl 16)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IN_ADDR) return false
        return s_addr == other.s_addr
    }

    override fun hashCode(): Int = s_addr.hashCode()

    override fun toString(): String = "IN_ADDR(s_addr=$s_addr)"
}

public typealias in_addr = IN_ADDR
public typealias PIN_ADDR = ULong
public typealias LPIN_ADDR = ULong
