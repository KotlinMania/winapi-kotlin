package io.github.kotlinmania.winapi

import io.github.kotlinmania.winapi.shared.guiddef.GUID
import io.github.kotlinmania.winapi.shared.guiddef.IID_NULL
import io.github.kotlinmania.winapi.shared.guiddef.IsEqualCLSID
import io.github.kotlinmania.winapi.shared.guiddef.IsEqualFMTID
import io.github.kotlinmania.winapi.shared.guiddef.IsEqualGUID
import io.github.kotlinmania.winapi.shared.guiddef.IsEqualIID
import io.github.kotlinmania.winapi.shared.minwindef.FALSE
import io.github.kotlinmania.winapi.shared.minwindef.FILETIME
import io.github.kotlinmania.winapi.shared.minwindef.HIBYTE
import io.github.kotlinmania.winapi.shared.minwindef.HIWORD
import io.github.kotlinmania.winapi.shared.minwindef.LOBYTE
import io.github.kotlinmania.winapi.shared.minwindef.LOWORD
import io.github.kotlinmania.winapi.shared.minwindef.MAKELONG
import io.github.kotlinmania.winapi.shared.minwindef.MAKEWORD
import io.github.kotlinmania.winapi.shared.minwindef.MAX_PATH
import io.github.kotlinmania.winapi.shared.minwindef.TRUE
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class WinapiTest {
    @Test
    fun testMinwindefConstants() {
        assertEquals(260u, MAX_PATH)
        assertEquals(0, FALSE)
        assertEquals(1, TRUE)
    }

    @Test
    fun testMakeWordAndBytes() {
        val low: UByte = 0x34u
        val high: UByte = 0x12u
        val word = MAKEWORD(low, high)
        assertEquals(0x1234u.toUShort(), word)
        assertEquals(low, LOBYTE(word))
        assertEquals(high, HIBYTE(word))
    }

    @Test
    fun testMakeLongAndWords() {
        val low: UShort = 0x5678u
        val high: UShort = 0x1234u
        val longVal = MAKELONG(low, high)
        val dwordVal = longVal.toUInt()
        assertEquals(low, LOWORD(dwordVal))
        assertEquals(high, HIWORD(dwordVal))
    }

    @Test
    fun testFiletime() {
        val ft = FILETIME(dwLowDateTime = 100u, dwHighDateTime = 200u)
        assertEquals(100u, ft.dwLowDateTime)
        assertEquals(200u, ft.dwHighDateTime)
    }

    @Test
    fun testGuidNullAndEquality() {
        val g1 =
            GUID(
                Data1 = 0u,
                Data2 = 0u,
                Data3 = 0u,
                Data4 = ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u),
            )
        assertEquals(IID_NULL, g1)
        assertTrue(IsEqualGUID(g1, IID_NULL))
        assertTrue(IsEqualIID(g1, IID_NULL))
        assertTrue(IsEqualCLSID(g1, IID_NULL))
        assertTrue(IsEqualFMTID(g1, IID_NULL))
        assertEquals(g1.hashCode(), IID_NULL.hashCode())

        val g2 =
            GUID(
                Data1 = 1u,
                Data2 = 2u,
                Data3 = 3u,
                Data4 = ubyteArrayOf(1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u),
            )
        assertFalse(IsEqualGUID(g1, g2))
        assertNotEquals(g1, g2)
        assertTrue(g2.toString().contains("Data1=1"))
    }

    @Test
    fun testGuidValidation() {
        assertFailsWith<IllegalArgumentException> {
            GUID(
                Data1 = 0u,
                Data2 = 0u,
                Data3 = 0u,
                Data4 = ubyteArrayOf(1u, 2u, 3u),
            )
        }
    }
}
