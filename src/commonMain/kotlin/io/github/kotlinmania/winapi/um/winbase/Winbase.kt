// port-lint: source um/winbase.rs
package io.github.kotlinmania.winapi.um.winbase

import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.HGLOBAL
import io.github.kotlinmania.winapi.shared.minwindef.HLOCAL
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.shared.ntdef.LONG
public const val FILE_BEGIN: DWORD = 0u
public const val FILE_CURRENT: DWORD = 1u
public const val FILE_END: DWORD = 2u

public const val WAIT_FAILED: DWORD = 0xFFFFFFFFu
public const val WAIT_OBJECT_0: DWORD = 0x00000000u
public const val WAIT_ABANDONED: DWORD = 0x00000080u
public const val WAIT_ABANDONED_0: DWORD = 0x00000080u
public const val WAIT_IO_COMPLETION: DWORD = 0x000000C0u
public const val WAIT_TIMEOUT: DWORD = 258u
public const val INFINITE: DWORD = 0xFFFFFFFFu

public const val FILE_FLAG_WRITE_THROUGH: DWORD = 0x80000000u
public const val FILE_FLAG_OVERLAPPED: DWORD = 0x40000000u
public const val FILE_FLAG_NO_BUFFERING: DWORD = 0x20000000u
public const val FILE_FLAG_RANDOM_ACCESS: DWORD = 0x10000000u
public const val FILE_FLAG_SEQUENTIAL_SCAN: DWORD = 0x08000000u
public const val FILE_FLAG_DELETE_ON_CLOSE: DWORD = 0x04000000u
public const val FILE_FLAG_BACKUP_SEMANTICS: DWORD = 0x02000000u
public const val FILE_FLAG_POSIX_SEMANTICS: DWORD = 0x01000000u
public const val FILE_FLAG_SESSION_AWARE: DWORD = 0x00800000u
public const val FILE_FLAG_OPEN_REPARSE_POINT: DWORD = 0x00200000u
public const val FILE_FLAG_OPEN_NO_RECALL: DWORD = 0x00100000u
public const val FILE_FLAG_FIRST_PIPE_INSTANCE: DWORD = 0x00080000u

public const val HANDLE_FLAG_INHERIT: DWORD = 0x00000001u
public const val HANDLE_FLAG_PROTECT_FROM_CLOSE: DWORD = 0x00000002u

public const val GMEM_FIXED: UINT = 0x0000u
public const val GMEM_MOVEABLE: UINT = 0x0002u
public const val GMEM_NOCOMPACT: UINT = 0x0010u
public const val GMEM_NODISCARD: UINT = 0x0020u
public const val GMEM_ZEROINIT: UINT = 0x0040u
public const val GMEM_MODIFY: UINT = 0x0080u
public const val GMEM_DISCARDABLE: UINT = 0x0100u
public const val GMEM_NOT_BANKED: UINT = 0x1000u
public const val GMEM_SHARE: UINT = 0x2000u
public const val GMEM_DDESHARE: UINT = 0x2000u
public const val GMEM_NOTIFY: UINT = 0x4000u
public const val GMEM_LOWER: UINT = GMEM_NOT_BANKED
public const val GMEM_VALID_FLAGS: UINT = 0x7F72u
public const val GMEM_INVALID_HANDLE: UINT = 0x8000u
public const val GHND: UINT = 0x0042u
public const val GPTR: UINT = 0x0040u

public expect fun GlobalFree(hMem: HGLOBAL): HGLOBAL

public expect fun LocalFree(hMem: HLOCAL): HLOCAL

public expect fun RtlNtStatusToDosError(status: LONG): ULONG
