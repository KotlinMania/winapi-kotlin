// port-lint: source winapi/src/shared/in6addr.rs
package io.github.kotlinmania.winapi.shared.in6addr

import io.github.kotlinmania.winapi.shared.minwindef.UCHAR
import io.github.kotlinmania.winapi.shared.minwindef.USHORT

public class IN6_ADDR(
    public val bytes: UByteArray = UByteArray(16),
) {
    init {
        require(bytes.size == 16) { "IN6_ADDR must be 16 bytes, got ${bytes.size}" }
    }

    public val words: UShortArray
        get() {
            val result = UShortArray(8)
            for (i in 0 until 8) {
                val b1 = bytes[i * 2].toUInt()
                val b2 = bytes[i * 2 + 1].toUInt()
                result[i] = (b1 or (b2 shl 8)).toUShort()
            }
            return result
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IN6_ADDR) return false
        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int = bytes.contentHashCode()

    override fun toString(): String = "IN6_ADDR(bytes=${bytes.joinToString()})"
}

public typealias in6_addr = IN6_ADDR
public typealias PIN6_ADDR = ULong
public typealias LPIN6_ADDR = ULong
