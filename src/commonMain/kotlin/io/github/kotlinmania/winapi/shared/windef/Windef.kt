// port-lint: source winapi/src/shared/windef.rs
package io.github.kotlinmania.winapi.shared.windef

import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.HFILE
import io.github.kotlinmania.winapi.shared.minwindef.WORD
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.SHORT

// Basic Windows Type Definitions for windef partition.

sealed class HWND__
typealias HWND = ULong

sealed class HHOOK__
typealias HHOOK = ULong

typealias HGDIOBJ = ULong

sealed class HACCEL__
typealias HACCEL = ULong

sealed class HBITMAP__
typealias HBITMAP = ULong

sealed class HBRUSH__
typealias HBRUSH = ULong

sealed class HCOLORSPACE__
typealias HCOLORSPACE = ULong

sealed class HDC__
typealias HDC = ULong

sealed class HGLRC__
typealias HGLRC = ULong

sealed class HDESK__
typealias HDESK = ULong

sealed class HENHMETAFILE__
typealias HENHMETAFILE = ULong

sealed class HFONT__
typealias HFONT = ULong

sealed class HICON__
typealias HICON = ULong

sealed class HMENU__
typealias HMENU = ULong

sealed class HPALETTE__
typealias HPALETTE = ULong

sealed class HPEN__
typealias HPEN = ULong

sealed class HWINEVENTHOOK__
typealias HWINEVENTHOOK = ULong

sealed class HMONITOR__
typealias HMONITOR = ULong

sealed class HUMPD__
typealias HUMPD = ULong

typealias HCURSOR = HICON
typealias COLORREF = DWORD
typealias LPCOLORREF = ULong

const val HFILE_ERROR: HFILE = -1

data class RECT(
    val left: LONG,
    val top: LONG,
    val right: LONG,
    val bottom: LONG,
)

typealias PRECT = ULong
typealias NPRECT = ULong
typealias LPRECT = ULong
typealias LPCRECT = ULong

data class RECTL(
    val left: LONG,
    val top: LONG,
    val right: LONG,
    val bottom: LONG,
)

typealias PRECTL = ULong
typealias LPRECTL = ULong
typealias LPCRECTL = ULong

data class POINT(
    val x: LONG,
    val y: LONG,
)

typealias PPOINT = ULong
typealias NPPOINT = ULong
typealias LPPOINT = ULong

data class POINTL(
    val x: LONG,
    val y: LONG,
)

typealias PPOINTL = ULong

data class SIZE(
    val cx: LONG,
    val cy: LONG,
)

typealias PSIZE = ULong
typealias LPSIZE = ULong
typealias SIZEL = SIZE
typealias PSIZEL = ULong
typealias LPSIZEL = ULong

data class POINTS(
    val x: SHORT,
    val y: SHORT,
)

typealias PPOINTS = ULong
typealias LPPOINTS = ULong

const val DM_UPDATE: WORD = 1u
const val DM_COPY: WORD = 2u
const val DM_PROMPT: WORD = 4u
const val DM_MODIFY: WORD = 8u
const val DM_IN_BUFFER: WORD = DM_MODIFY
const val DM_IN_PROMPT: WORD = DM_PROMPT
const val DM_OUT_BUFFER: WORD = DM_COPY
const val DM_OUT_DEFAULT: WORD = DM_UPDATE

const val DC_FIELDS: DWORD = 1u
const val DC_PAPERS: DWORD = 2u
const val DC_PAPERSIZE: DWORD = 3u
const val DC_MINEXTENT: DWORD = 4u
const val DC_MAXEXTENT: DWORD = 5u
const val DC_BINS: DWORD = 6u
const val DC_DUPLEX: DWORD = 7u
const val DC_SIZE: DWORD = 8u
const val DC_EXTRA: DWORD = 9u
const val DC_VERSION: DWORD = 10u
const val DC_DRIVER: DWORD = 11u
const val DC_BINNAMES: DWORD = 12u
const val DC_ENUMRESOLUTIONS: DWORD = 13u
const val DC_FILEDEPENDENCIES: DWORD = 14u
const val DC_TRUETYPE: DWORD = 15u
const val DC_PAPERNAMES: DWORD = 16u
const val DC_ORIENTATION: DWORD = 17u
const val DC_COPIES: DWORD = 18u

sealed class DPI_AWARENESS_CONTEXT__
typealias DPI_AWARENESS_CONTEXT = Long

enum class DPI_AWARENESS(
    val value: UInt,
) {
    DPI_AWARENESS_INVALID(0xFFFFFFFFu),
    DPI_AWARENESS_UNAWARE(0u),
    DPI_AWARENESS_SYSTEM_AWARE(1u),
    DPI_AWARENESS_PER_MONITOR_AWARE(2u),
}

const val DPI_AWARENESS_CONTEXT_UNAWARE: DPI_AWARENESS_CONTEXT = -1L
const val DPI_AWARENESS_CONTEXT_SYSTEM_AWARE: DPI_AWARENESS_CONTEXT = -2L
const val DPI_AWARENESS_CONTEXT_PER_MONITOR_AWARE: DPI_AWARENESS_CONTEXT = -3L
const val DPI_AWARENESS_CONTEXT_PER_MONITOR_AWARE_V2: DPI_AWARENESS_CONTEXT = -4L
const val DPI_AWARENESS_CONTEXT_UNAWARE_GDISCALED: DPI_AWARENESS_CONTEXT = -5L

enum class DPI_HOSTING_BEHAVIOR(
    val value: UInt,
) {
    DPI_HOSTING_BEHAVIOR_INVALID(0xFFFFFFFFu),
    DPI_HOSTING_BEHAVIOR_DEFAULT(0u),
    DPI_HOSTING_BEHAVIOR_MIXED(1u),
}
