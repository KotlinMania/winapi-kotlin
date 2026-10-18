// port-lint: source um/fileapi.rs
package io.github.kotlinmania.winapi.um.fileapi

import io.github.kotlinmania.winapi.internal.WinapiJni
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.um.minwinbase.LPSECURITY_ATTRIBUTES
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.LPCSTR
import io.github.kotlinmania.winapi.shared.ntdef.LPCWSTR
public actual fun CreateFileA(
    lpFileName: LPCSTR,
    dwDesiredAccess: DWORD,
    dwShareMode: DWORD,
    lpSecurityAttributes: LPSECURITY_ATTRIBUTES,
    dwCreationDisposition: DWORD,
    dwFlagsAndAttributes: DWORD,
    hTemplateFile: HANDLE,
): HANDLE =
    WinapiJni
        .CreateFileA(
            lpFileName.toLong(),
            dwDesiredAccess.toInt(),
            dwShareMode.toInt(),
            lpSecurityAttributes.toLong(),
            dwCreationDisposition.toInt(),
            dwFlagsAndAttributes.toInt(),
            hTemplateFile.toLong(),
        ).toULong()

public actual fun CreateFileW(
    lpFileName: LPCWSTR,
    dwDesiredAccess: DWORD,
    dwShareMode: DWORD,
    lpSecurityAttributes: LPSECURITY_ATTRIBUTES,
    dwCreationDisposition: DWORD,
    dwFlagsAndAttributes: DWORD,
    hTemplateFile: HANDLE,
): HANDLE =
    WinapiJni
        .CreateFileW(
            lpFileName.toLong(),
            dwDesiredAccess.toInt(),
            dwShareMode.toInt(),
            lpSecurityAttributes.toLong(),
            dwCreationDisposition.toInt(),
            dwFlagsAndAttributes.toInt(),
            hTemplateFile.toLong(),
        ).toULong()

public actual fun DeleteFileA(lpFileName: LPCSTR): BOOL =
    WinapiJni.DeleteFileA(lpFileName.toLong())

public actual fun DeleteFileW(lpFileName: LPCWSTR): BOOL =
    WinapiJni.DeleteFileW(lpFileName.toLong())

public actual fun FlushFileBuffers(hFile: HANDLE): BOOL =
    WinapiJni.FlushFileBuffers(hFile.toLong())

public actual fun GetFileAttributesA(lpFileName: LPCSTR): DWORD =
    WinapiJni.GetFileAttributesA(lpFileName.toLong()).toUInt()

public actual fun GetFileAttributesW(lpFileName: LPCWSTR): DWORD =
    WinapiJni.GetFileAttributesW(lpFileName.toLong()).toUInt()

public actual fun GetFileSize(
    hFile: HANDLE,
    lpFileSizeHigh: UIntArray?,
): DWORD {
    if (lpFileSizeHigh != null && lpFileSizeHigh.isNotEmpty()) {
        val arr = IntArray(1) { lpFileSizeHigh[0].toInt() }
        val low = WinapiJni.GetFileSize(hFile.toLong(), arr).toUInt()
        lpFileSizeHigh[0] = arr[0].toUInt()
        return low
    }
    return WinapiJni.GetFileSize(hFile.toLong(), null).toUInt()
}

public actual fun SetFileAttributesA(
    lpFileName: LPCSTR,
    dwFileAttributes: DWORD,
): BOOL =
    WinapiJni.SetFileAttributesA(lpFileName.toLong(), dwFileAttributes.toInt())

public actual fun SetFileAttributesW(
    lpFileName: LPCWSTR,
    dwFileAttributes: DWORD,
): BOOL =
    WinapiJni.SetFileAttributesW(lpFileName.toLong(), dwFileAttributes.toInt())

public actual fun SetFilePointer(
    hFile: HANDLE,
    lDistanceToMove: LONG,
    lpDistanceToMoveHigh: IntArray?,
    dwMoveMethod: DWORD,
): DWORD =
    WinapiJni
        .SetFilePointer(
            hFile.toLong(),
            lDistanceToMove,
            lpDistanceToMoveHigh,
            dwMoveMethod.toInt(),
        ).toUInt()

public actual fun AreFileApisANSI(): BOOL =
    WinapiJni.AreFileApisANSI()
