@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.winnt

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.set
import kotlinx.cinterop.toLong
import kotlin.test.Test
import kotlin.test.assertEquals

class WinntNativeMemoryTest {
    @Test
    fun rtlMemoryOperationsWorkOnNativeBuffers() {
        memScoped {
            val src = allocArray<ByteVar>(6)
            val dst = allocArray<ByteVar>(6)
            val srcAddr = src.rawValue.toLong().toULong()
            val dstAddr = dst.rawValue.toLong().toULong()

            RtlFillMemory(srcAddr, 6uL, 0x5Au.toUByte())
            for (i in 0 until 6) {
                assertEquals(0x5A.toByte(), src[i])
            }

            for (i in 0 until 6) {
                src[i] = (i + 1).toByte()
            }
            RtlCopyMemory(dstAddr, srcAddr, 6uL)
            for (i in 0 until 6) {
                assertEquals((i + 1).toByte(), dst[i])
            }

            // Overlapping move within dst[0..4) -> dst[2..6)
            val dstShiftAddr = (dstAddr + 2uL)
            RtlMoveMemory(dstShiftAddr, dstAddr, 4uL)
            assertEquals(1.toByte(), dst[2])
            assertEquals(2.toByte(), dst[3])
            assertEquals(3.toByte(), dst[4])
            assertEquals(4.toByte(), dst[5])

            RtlZeroMemory(dstAddr, 6uL)
            for (i in 0 until 6) {
                assertEquals(0.toByte(), dst[i])
            }
        }
    }
}
