// port-lint: source um/fileapi.rs
package io.github.kotlinmania.winapi.um.fileapi

import io.github.kotlinmania.winapi.internal.WinapiNative
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
    WinapiNative
        .CreateFileA(
            lpFileName.toLong().toDouble(),
            dwDesiredAccess.toInt(),
            dwShareMode.toInt(),
            lpSecurityAttributes.toLong().toDouble(),
            dwCreationDisposition.toInt(),
            dwFlagsAndAttributes.toInt(),
            hTemplateFile.toLong().toDouble(),
        ).toLong()
        .toULong()

public actual fun CreateFileW(
    lpFileName: LPCWSTR,
    dwDesiredAccess: DWORD,
    dwShareMode: DWORD,
    lpSecurityAttributes: LPSECURITY_ATTRIBUTES,
    dwCreationDisposition: DWORD,
    dwFlagsAndAttributes: DWORD,
    hTemplateFile: HANDLE,
): HANDLE =
    WinapiNative
        .CreateFileW(
            lpFileName.toLong().toDouble(),
            dwDesiredAccess.toInt(),
            dwShareMode.toInt(),
            lpSecurityAttributes.toLong().toDouble(),
            dwCreationDisposition.toInt(),
            dwFlagsAndAttributes.toInt(),
            hTemplateFile.toLong().toDouble(),
        ).toLong()
        .toULong()

public actual fun DeleteFileA(lpFileName: LPCSTR): BOOL =
    WinapiNative.DeleteFileA(lpFileName.toLong().toDouble())

public actual fun DeleteFileW(lpFileName: LPCWSTR): BOOL =
    WinapiNative.DeleteFileW(lpFileName.toLong().toDouble())

public actual fun FlushFileBuffers(hFile: HANDLE): BOOL =
    WinapiNative.FlushFileBuffers(hFile.toLong().toDouble())

public actual fun GetFileAttributesA(lpFileName: LPCSTR): DWORD =
    WinapiNative.GetFileAttributesA(lpFileName.toLong().toDouble()).toUInt()

public actual fun GetFileAttributesW(lpFileName: LPCWSTR): DWORD =
    WinapiNative.GetFileAttributesW(lpFileName.toLong().toDouble()).toUInt()

public actual fun GetFileSize(
    hFile: HANDLE,
    lpFileSizeHigh: UIntArray?,
): DWORD {
    val hasHigh = if (lpFileSizeHigh != null && lpFileSizeHigh.isNotEmpty()) 1 else 0
    val res = WinapiNative.GetFileSize(hFile.toLong().toDouble(), hasHigh)
    if (hasHigh != 0 && lpFileSizeHigh != null) {
        lpFileSizeHigh[0] = (res.high as Int).toUInt()
    }
    return (res.low as Int).toUInt()
}

public actual fun SetFileAttributesA(
    lpFileName: LPCSTR,
    dwFileAttributes: DWORD,
): BOOL =
    WinapiNative.SetFileAttributesA(lpFileName.toLong().toDouble(), dwFileAttributes.toInt())

public actual fun SetFileAttributesW(
    lpFileName: LPCWSTR,
    dwFileAttributes: DWORD,
): BOOL =
    WinapiNative.SetFileAttributesW(lpFileName.toLong().toDouble(), dwFileAttributes.toInt())

public actual fun SetFilePointer(
    hFile: HANDLE,
    lDistanceToMove: LONG,
    lpDistanceToMoveHigh: IntArray?,
    dwMoveMethod: DWORD,
): DWORD {
    val hasHigh = if (lpDistanceToMoveHigh != null && lpDistanceToMoveHigh.isNotEmpty()) 1 else 0
    val inHigh = if (hasHigh != 0 && lpDistanceToMoveHigh != null) lpDistanceToMoveHigh[0] else 0
    val res =
        WinapiNative.SetFilePointer(
            hFile.toLong().toDouble(),
            lDistanceToMove,
            hasHigh,
            inHigh,
            dwMoveMethod.toInt(),
        )
    if (hasHigh != 0 && lpDistanceToMoveHigh != null) {
        lpDistanceToMoveHigh[0] = res.high as Int
    }
    return (res.low as Int).toUInt()
}

public actual fun AreFileApisANSI(): BOOL =
    WinapiNative.AreFileApisANSI()
