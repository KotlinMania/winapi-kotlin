// port-lint: source um/fileapi.rs
package io.github.kotlinmania.winapi.um.fileapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.LPCSTR
import io.github.kotlinmania.winapi.shared.ntdef.LPCWSTR
import io.github.kotlinmania.winapi.um.handleapi.INVALID_HANDLE_VALUE
import io.github.kotlinmania.winapi.um.minwinbase.LPSECURITY_ATTRIBUTES

public actual fun CreateFileA(
    lpFileName: LPCSTR,
    dwDesiredAccess: DWORD,
    dwShareMode: DWORD,
    lpSecurityAttributes: LPSECURITY_ATTRIBUTES,
    dwCreationDisposition: DWORD,
    dwFlagsAndAttributes: DWORD,
    hTemplateFile: HANDLE,
): HANDLE = INVALID_HANDLE_VALUE

public actual fun CreateFileW(
    lpFileName: LPCWSTR,
    dwDesiredAccess: DWORD,
    dwShareMode: DWORD,
    lpSecurityAttributes: LPSECURITY_ATTRIBUTES,
    dwCreationDisposition: DWORD,
    dwFlagsAndAttributes: DWORD,
    hTemplateFile: HANDLE,
): HANDLE = INVALID_HANDLE_VALUE

public actual fun DeleteFileA(lpFileName: LPCSTR): BOOL = 0

public actual fun DeleteFileW(lpFileName: LPCWSTR): BOOL = 0

public actual fun FlushFileBuffers(hFile: HANDLE): BOOL = 0

public actual fun GetFileAttributesA(lpFileName: LPCSTR): DWORD = INVALID_FILE_ATTRIBUTES

public actual fun GetFileAttributesW(lpFileName: LPCWSTR): DWORD = INVALID_FILE_ATTRIBUTES

public actual fun GetFileSize(
    hFile: HANDLE,
    lpFileSizeHigh: UIntArray?,
): DWORD {
    if (lpFileSizeHigh != null && lpFileSizeHigh.isNotEmpty()) {
        lpFileSizeHigh[0] = 0u
    }
    return INVALID_FILE_SIZE
}

public actual fun SetFileAttributesA(
    lpFileName: LPCSTR,
    dwFileAttributes: DWORD,
): BOOL = 0

public actual fun SetFileAttributesW(
    lpFileName: LPCWSTR,
    dwFileAttributes: DWORD,
): BOOL = 0

public actual fun SetFilePointer(
    hFile: HANDLE,
    lDistanceToMove: LONG,
    lpDistanceToMoveHigh: IntArray?,
    dwMoveMethod: DWORD,
): DWORD = INVALID_SET_FILE_POINTER

public actual fun AreFileApisANSI(): BOOL = 1
