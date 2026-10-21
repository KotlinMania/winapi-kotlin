// port-lint: source shared/inaddr.rs
package io.github.kotlinmania.winapi.shared.inaddr

import io.github.kotlinmania.winapi.shared.minwindef.UCHAR
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.shared.minwindef.USHORT

public data class InAddrSUnB(
    public var sB1: UCHAR = 0u,
    public var sB2: UCHAR = 0u,
    public var sB3: UCHAR = 0u,
    public var sB4: UCHAR = 0u,
)

public data class InAddrSUnW(
    public var sW1: USHORT = 0u,
    public var sW2: USHORT = 0u,
)

public data class InAddrSUn(
    public var S_UN_B: InAddrSUnB = InAddrSUnB(),
    public var S_UN_W: InAddrSUnW = InAddrSUnW(),
    public var S_ADDR: ULONG = 0u,
)

public data class IN_ADDR(
    public var S_UN: InAddrSUn = InAddrSUn(),
) {
    public constructor(sAddr: ULONG) : this(
        InAddrSUn(
            S_UN_B =
                InAddrSUnB(
                    sB1 = (sAddr and 0xFFu).toUByte(),
                    sB2 = ((sAddr shr 8) and 0xFFu).toUByte(),
                    sB3 = ((sAddr shr 16) and 0xFFu).toUByte(),
                    sB4 = ((sAddr shr 24) and 0xFFu).toUByte(),
                ),
            S_UN_W =
                InAddrSUnW(
                    sW1 = (sAddr and 0xFFFFu).toUShort(),
                    sW2 = ((sAddr shr 16) and 0xFFFFu).toUShort(),
                ),
            S_ADDR = sAddr,
        ),
    )

    public var sAddr: ULONG
        get() = S_UN.S_ADDR
        set(value) {
            S_UN.S_ADDR = value
            S_UN.S_UN_B =
                InAddrSUnB(
                    sB1 = (value and 0xFFu).toUByte(),
                    sB2 = ((value shr 8) and 0xFFu).toUByte(),
                    sB3 = ((value shr 16) and 0xFFu).toUByte(),
                    sB4 = ((value shr 24) and 0xFFu).toUByte(),
                )
            S_UN.S_UN_W =
                InAddrSUnW(
                    sW1 = (value and 0xFFFFu).toUShort(),
                    sW2 = ((value shr 16) and 0xFFFFu).toUShort(),
                )
        }

    public var sUnB: InAddrSUnB
        get() = S_UN.S_UN_B
        set(value) {
            S_UN.S_UN_B = value
            val newAddr =
                value.sB1.toUInt() or
                    (value.sB2.toUInt() shl 8) or
                    (value.sB3.toUInt() shl 16) or
                    (value.sB4.toUInt() shl 24)
            S_UN.S_ADDR = newAddr
            S_UN.S_UN_W =
                InAddrSUnW(
                    sW1 = (newAddr and 0xFFFFu).toUShort(),
                    sW2 = ((newAddr shr 16) and 0xFFFFu).toUShort(),
                )
        }

    public var sUnW: InAddrSUnW
        get() = S_UN.S_UN_W
        set(value) {
            S_UN.S_UN_W = value
            val newAddr = value.sW1.toUInt() or (value.sW2.toUInt() shl 16)
            S_UN.S_ADDR = newAddr
            S_UN.S_UN_B =
                InAddrSUnB(
                    sB1 = (newAddr and 0xFFu).toUByte(),
                    sB2 = ((newAddr shr 8) and 0xFFu).toUByte(),
                    sB3 = ((newAddr shr 16) and 0xFFu).toUByte(),
                    sB4 = ((newAddr shr 24) and 0xFFu).toUByte(),
                )
        }
}

public typealias InAddr = IN_ADDR
public typealias PIN_ADDR = ULong
public typealias LPIN_ADDR = ULong
