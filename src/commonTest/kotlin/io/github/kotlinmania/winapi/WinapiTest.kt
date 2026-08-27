// port-lint: tests lib.rs
package io.github.kotlinmania.winapi

import io.github.kotlinmania.winapi.shared.cderr.CDERR_DIALOGFAILURE
import io.github.kotlinmania.winapi.shared.cderr.CDERR_STRUCTSIZE
import io.github.kotlinmania.winapi.shared.cderr.FNERR_FILENAMECODES
import io.github.kotlinmania.winapi.shared.cderr.PDERR_PRINTERCODES
import io.github.kotlinmania.winapi.shared.d3d9types.D3DCOLORVALUE
import io.github.kotlinmania.winapi.shared.d3d9types.D3DCOLOR_ARGB
import io.github.kotlinmania.winapi.shared.d3d9types.D3DCOLOR_RGBA
import io.github.kotlinmania.winapi.shared.d3d9types.D3DCOLOR_XRGB
import io.github.kotlinmania.winapi.shared.dxgiformat.DXGI_FORMAT_R8G8B8A8_UNORM
import io.github.kotlinmania.winapi.shared.dxgiformat.DXGI_FORMAT_UNKNOWN
import io.github.kotlinmania.winapi.shared.dxgitype.DXGI_CPU_ACCESS_NONE
import io.github.kotlinmania.winapi.shared.dxgitype.DXGI_MODE_DESC
import io.github.kotlinmania.winapi.shared.dxgitype.DXGI_MODE_ROTATION_IDENTITY
import io.github.kotlinmania.winapi.shared.dxgitype.DXGI_MODE_SCALING_UNSPECIFIED
import io.github.kotlinmania.winapi.shared.dxgitype.DXGI_MODE_SCANLINE_ORDER_UNSPECIFIED
import io.github.kotlinmania.winapi.shared.dxgitype.DXGI_RATIONAL
import io.github.kotlinmania.winapi.shared.dxgitype.DXGI_RGB
import io.github.kotlinmania.winapi.shared.dxgitype.DXGI_USAGE_SHADER_INPUT
import io.github.kotlinmania.winapi.shared.guiddef.GUID
import io.github.kotlinmania.winapi.shared.guiddef.IID_NULL
import io.github.kotlinmania.winapi.shared.guiddef.IsEqualCLSID
import io.github.kotlinmania.winapi.shared.guiddef.IsEqualFMTID
import io.github.kotlinmania.winapi.shared.guiddef.IsEqualGUID
import io.github.kotlinmania.winapi.shared.guiddef.IsEqualIID
import io.github.kotlinmania.winapi.shared.in6addr.IN6_ADDR
import io.github.kotlinmania.winapi.shared.inaddr.IN_ADDR
import io.github.kotlinmania.winapi.shared.inaddr.InAddrSUnB
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

    @Test
    fun testInAddr() {
        val inAddr = IN_ADDR(s_addr = 0x0100007Fu) // 127.0.0.1 in little-endian
        assertEquals(0x0100007Fu, inAddr.s_addr)
        val bytes = inAddr.s_un_b
        assertEquals(127u.toUByte(), bytes.s_b1)
        assertEquals(0u.toUByte(), bytes.s_b2)
        assertEquals(0u.toUByte(), bytes.s_b3)
        assertEquals(1u.toUByte(), bytes.s_b4)

        inAddr.s_un_b = InAddrSUnB(s_b1 = 192u, s_b2 = 168u, s_b3 = 1u, s_b4 = 100u)
        val b = inAddr.s_un_b
        assertEquals(192u.toUByte(), b.s_b1)
        assertEquals(168u.toUByte(), b.s_b2)
        assertEquals(1u.toUByte(), b.s_b3)
        assertEquals(100u.toUByte(), b.s_b4)
    }

    @Test
    fun testIn6Addr() {
        val bytes = UByteArray(16) { it.toUByte() }
        val in6 = IN6_ADDR(bytes)
        assertEquals(16, in6.bytes.size)
        assertEquals(8, in6.words.size)
        assertEquals(0x0100u.toUShort(), in6.words[0])

        assertFailsWith<IllegalArgumentException> {
            IN6_ADDR(UByteArray(10))
        }
    }

    @Test
    fun testCderrConstants() {
        assertEquals(0xFFFFu, CDERR_DIALOGFAILURE)
        assertEquals(0x0001u, CDERR_STRUCTSIZE)
        assertEquals(0x1000u, PDERR_PRINTERCODES)
        assertEquals(0x3000u, FNERR_FILENAMECODES)
    }

    @Test
    fun testWindefStructures() {
        val rect =
            io.github.kotlinmania.winapi.shared.windef
                .RECT(left = 10, top = 20, right = 100, bottom = 200)
        assertEquals(10, rect.left)
        assertEquals(20, rect.top)
        assertEquals(100, rect.right)
        assertEquals(200, rect.bottom)

        val pt =
            io.github.kotlinmania.winapi.shared.windef
                .POINT(x = 50, y = 75)
        assertEquals(50, pt.x)
        assertEquals(75, pt.y)

        val sz =
            io.github.kotlinmania.winapi.shared.windef
                .SIZE(cx = 800, cy = 600)
        assertEquals(800, sz.cx)
        assertEquals(600, sz.cy)

        assertEquals(0xFFFFFFFFu, io.github.kotlinmania.winapi.shared.windef.DPI_AWARENESS.DPI_AWARENESS_INVALID.value)
        assertEquals(0u, io.github.kotlinmania.winapi.shared.windef.DPI_AWARENESS.DPI_AWARENESS_UNAWARE.value)
        assertEquals(-1L, io.github.kotlinmania.winapi.shared.windef.DPI_AWARENESS_CONTEXT_UNAWARE)
    }

    @Test
    fun testWindowsxMacros() {
        val lp: io.github.kotlinmania.winapi.shared.minwindef.LPARAM = 0x00640032L // x = 0x0032 (50), y = 0x0064 (100)
        assertEquals(
            50,
            io.github.kotlinmania.winapi.shared.windowsx
                .GET_X_LPARAM(lp),
        )
        assertEquals(
            100,
            io.github.kotlinmania.winapi.shared.windowsx
                .GET_Y_LPARAM(lp),
        )
    }

    @Test
    fun testDxgiFormatAndType() {
        assertEquals(0u, DXGI_FORMAT_UNKNOWN)
        assertEquals(28u, DXGI_FORMAT_R8G8B8A8_UNORM)
        assertEquals(0u, DXGI_CPU_ACCESS_NONE)
        assertEquals(16u, DXGI_USAGE_SHADER_INPUT)

        val rgb = DXGI_RGB(red = 1.0f, green = 0.5f, blue = 0.25f)
        assertEquals(1.0f, rgb.red)
        assertEquals(0.5f, rgb.green)
        assertEquals(0.25f, rgb.blue)

        val desc =
            DXGI_MODE_DESC(
                width = 1920u,
                height = 1080u,
                refreshRate = DXGI_RATIONAL(numerator = 60u, denominator = 1u),
                format = DXGI_FORMAT_R8G8B8A8_UNORM,
                scanlineOrdering = DXGI_MODE_SCANLINE_ORDER_UNSPECIFIED,
                scaling = DXGI_MODE_SCALING_UNSPECIFIED,
            )
        assertEquals(1920u, desc.width)
        assertEquals(1080u, desc.height)
        assertEquals(60u, desc.refreshRate.numerator)
        assertEquals(DXGI_MODE_ROTATION_IDENTITY, 1u)
    }

    @Test
    fun testD3d9Colors() {
        val color = D3DCOLOR_ARGB(0xFFu, 0x12u, 0x34u, 0x56u)
        assertEquals(0xFF123456u, color)
        assertEquals(0xFF123456u, D3DCOLOR_RGBA(0x12u, 0x34u, 0x56u, 0xFFu))
        assertEquals(0xFF123456u, D3DCOLOR_XRGB(0x12u, 0x34u, 0x56u))

        val cv = D3DCOLORVALUE(r = 1.0f, g = 0.0f, b = 0.0f, a = 1.0f)
        assertEquals(1.0f, cv.r)
        assertEquals(0.0f, cv.g)
        assertEquals(0.0f, cv.b)
        assertEquals(1.0f, cv.a)
    }

    @Test
    fun testHidUsageConstants() {
        assertEquals(0x00u.toUShort(), io.github.kotlinmania.winapi.shared.hidusage.HID_USAGE_PAGE_UNDEFINED)
        assertEquals(0x01u.toUShort(), io.github.kotlinmania.winapi.shared.hidusage.HID_USAGE_PAGE_GENERIC)
        assertEquals(0x02u.toUShort(), io.github.kotlinmania.winapi.shared.hidusage.HID_USAGE_GENERIC_MOUSE)
        assertEquals(0x06u.toUShort(), io.github.kotlinmania.winapi.shared.hidusage.HID_USAGE_GENERIC_KEYBOARD)
        assertEquals(0x04u.toUShort(), io.github.kotlinmania.winapi.shared.hidusage.HID_USAGE_KEYBOARD_aA)
        assertEquals(0x1Du.toUShort(), io.github.kotlinmania.winapi.shared.hidusage.HID_USAGE_KEYBOARD_zZ)
    }
}
