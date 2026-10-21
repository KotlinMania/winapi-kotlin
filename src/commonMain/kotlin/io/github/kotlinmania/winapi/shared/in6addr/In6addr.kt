// port-lint: source shared/in6addr.rs
package io.github.kotlinmania.winapi.shared.in6addr

public class In6AddrU(
    public var Byte: UByteArray = UByteArray(16),
) {
    init {
        require(Byte.size == 16) { "IN6_ADDR must be 16 bytes, got ${Byte.size}" }
    }

    public var Word: UShortArray
        get() {
            val result = UShortArray(8)
            for (i in 0 until 8) {
                val b1 = Byte[i * 2].toUInt()
                val b2 = Byte[i * 2 + 1].toUInt()
                result[i] = (b1 or (b2 shl 8)).toUShort()
            }
            return result
        }
        set(value) {
            for (i in 0 until minOf(8, value.size)) {
                Byte[i * 2] = (value[i].toUInt() and 0xFFu).toUByte()
                Byte[i * 2 + 1] = ((value[i].toUInt() shr 8) and 0xFFu).toUByte()
            }
        }
}

public data class IN6_ADDR(
    public var u: In6AddrU = In6AddrU(),
) {
    public constructor(bytes: UByteArray) : this(In6AddrU(bytes))

    public val bytes: UByteArray
        get() = u.Byte

    public val words: UShortArray
        get() = u.Word
}

public typealias In6Addr = IN6_ADDR
public typealias PIN6_ADDR = ULong
public typealias LPIN6_ADDR = ULong
