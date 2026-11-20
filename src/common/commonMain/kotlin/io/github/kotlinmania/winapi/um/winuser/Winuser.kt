// port-lint: source um/winuser.rs
package io.github.kotlinmania.winapi.um.winuser

import io.github.kotlinmania.winapi.shared.basetsd.ULONG_PTR
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.HIWORD
import io.github.kotlinmania.winapi.shared.minwindef.LOWORD
import io.github.kotlinmania.winapi.shared.minwindef.LPARAM
import io.github.kotlinmania.winapi.shared.minwindef.WORD
import io.github.kotlinmania.winapi.shared.minwindef.WPARAM
import io.github.kotlinmania.winapi.shared.ntdef.LPSTR
import io.github.kotlinmania.winapi.shared.ntdef.LPWSTR

public const val SLE_ERROR: DWORD = 0x00000001u
public const val SLE_MINORERROR: DWORD = 0x00000002u
public const val SLE_WARNING: DWORD = 0x00000003u
public const val FAPPCOMMAND_MASK: WORD = 0xF000u

public fun IS_INTRESOURCE(r: ULONG_PTR): Boolean = (r shr 16) == 0uL

public fun MAKEINTRESOURCEA(i: WORD): LPSTR = i.toULong()

public fun MAKEINTRESOURCEW(i: WORD): LPWSTR = i.toULong()

public fun GET_APPCOMMAND_LPARAM(lParam: LPARAM): Short =
    (HIWORD(lParam.toUInt()) and FAPPCOMMAND_MASK.inv()).toShort()

public fun GET_DEVICE_LPARAM(lParam: LPARAM): WORD =
    HIWORD(lParam.toUInt()) and FAPPCOMMAND_MASK

public fun GET_WHEEL_DELTA_WPARAM(wParam: WPARAM): Short =
    HIWORD(wParam.toUInt()).toShort()

public fun GET_KEYSTATE_WPARAM(wParam: WPARAM): WORD =
    LOWORD(wParam.toUInt())

public fun GET_NCHITTEST_WPARAM(wParam: WPARAM): Short =
    LOWORD(wParam.toUInt()).toShort()

public fun GET_XBUTTON_WPARAM(wParam: WPARAM): WORD =
    HIWORD(wParam.toUInt())

public fun GET_RAWINPUT_CODE_WPARAM(wParam: WPARAM): WPARAM =
    wParam and 0xFFuL

public expect fun SetLastErrorEx(dwErrCode: DWORD, dwType: DWORD)

public expect fun LockWorkStation(): BOOL
