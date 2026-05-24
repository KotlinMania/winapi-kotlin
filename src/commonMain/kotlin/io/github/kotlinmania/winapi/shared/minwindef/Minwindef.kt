// port-lint: source shared/minwindef.rs
package io.github.kotlinmania.winapi.shared.minwindef

import io.github.kotlinmania.winapi.CFloat
import io.github.kotlinmania.winapi.CInt
import io.github.kotlinmania.winapi.CUchar
import io.github.kotlinmania.winapi.CUint
import io.github.kotlinmania.winapi.CUlong
import io.github.kotlinmania.winapi.CUshort
import io.github.kotlinmania.winapi.shared.basetsd.LONG_PTR
import io.github.kotlinmania.winapi.shared.basetsd.UINT_PTR
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import io.github.kotlinmania.winapi.shared.ntdef.LONG

// Basic Windows Type Definitions for minwin partition.

typealias ULONG = CUlong
typealias PULONG = ULong // *mut ULONG
typealias USHORT = CUshort
typealias PUSHORT = ULong
typealias UCHAR = CUchar
typealias PUCHAR = ULong
typealias PSZ = ULong // *mut c_char

const val MAX_PATH: ULong = 260u
const val FALSE: BOOL = 0
const val TRUE: BOOL = 1

typealias DWORD = CUlong
typealias BOOL = CInt
typealias BYTE = CUchar
typealias WORD = CUshort
typealias FLOAT = CFloat
typealias PFLOAT = ULong
typealias PBOOL = ULong
typealias LPBOOL = ULong
typealias PBYTE = ULong
typealias LPBYTE = ULong
typealias PINT = ULong
typealias LPINT = ULong
typealias PWORD = ULong
typealias LPWORD = ULong
typealias LPLONG = ULong
typealias PDWORD = ULong
typealias LPDWORD = ULong
typealias LPVOID = ULong
typealias LPCVOID = ULong
typealias INT = CInt
typealias UINT = CUint
typealias PUINT = ULong
typealias WPARAM = UINT_PTR
typealias LPARAM = LONG_PTR
typealias LRESULT = LONG_PTR

fun MAKEWORD(a: BYTE, b: BYTE): WORD =
    (a.toUInt() or (b.toUInt() shl 8)).toUShort()

fun MAKELONG(a: WORD, b: WORD): LONG =
    ((a.toUInt() or (b.toUInt() shl 16))).toInt()

fun LOWORD(l: DWORD): WORD = (l and 0xffffu).toUShort()
fun HIWORD(l: DWORD): WORD = ((l shr 16) and 0xffffu).toUShort()
fun LOBYTE(l: WORD): BYTE = (l.toUInt() and 0xffu).toUByte()
fun HIBYTE(l: WORD): BYTE = ((l.toUInt() shr 8) and 0xffu).toUByte()

typealias SPHANDLE = ULong
typealias LPHANDLE = ULong
typealias HGLOBAL = HANDLE
typealias HLOCAL = HANDLE
typealias GLOBALHANDLE = HANDLE
typealias LOCALHANDLE = HANDLE

/** Upstream `pub enum __some_function {}` — uninhabited tag type used
 *  to give FARPROC / NEARPROC / PROC distinct nominal pointer aliases. */
sealed class SomeFunction

typealias FARPROC = ULong // *mut __some_function
typealias NEARPROC = ULong // *mut __some_function
typealias PROC = ULong // *mut __some_function

typealias ATOM = WORD

// DECLARE_HANDLE!{HKEY, HKEY__} expands to `pub enum HKEY__ {}; pub
// type HKEY = *mut HKEY__;`. Each handle gets its own uninhabited tag
// class for nominal distinctness and a ULong typealias for the
// pointer-value form. The same pattern repeats for HMETAFILE,
// HINSTANCE, HRGN, HRSRC, HSPRITE, HLSURF, HSTR, HTASK, HWINSTA, HKL.
sealed class HKEY__
typealias HKEY = ULong
typealias PHKEY = ULong

sealed class HMETAFILE__
typealias HMETAFILE = ULong

sealed class HINSTANCE__
typealias HINSTANCE = ULong
typealias HMODULE = HINSTANCE

sealed class HRGN__
typealias HRGN = ULong

sealed class HRSRC__
typealias HRSRC = ULong

sealed class HSPRITE__
typealias HSPRITE = ULong

sealed class HLSURF__
typealias HLSURF = ULong

sealed class HSTR__
typealias HSTR = ULong

sealed class HTASK__
typealias HTASK = ULong

sealed class HWINSTA__
typealias HWINSTA = ULong

sealed class HKL__
typealias HKL = ULong

typealias HFILE = CInt

data class FILETIME(
    val dwLowDateTime: DWORD,
    val dwHighDateTime: DWORD,
)

typealias PFILETIME = ULong
typealias LPFILETIME = ULong
