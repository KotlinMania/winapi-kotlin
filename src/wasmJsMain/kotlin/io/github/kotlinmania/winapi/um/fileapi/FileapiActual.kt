// port-lint: source um/fileapi.rs
package io.github.kotlinmania.winapi.um.fileapi

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
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
    WinapiNapiWasm
        .CreateFileA(
            lpFileName.toLong(),
            dwDesiredAccess,
            dwShareMode,
            lpSecurityAttributes.toLong(),
            dwCreationDisposition,
            dwFlagsAndAttributes,
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
    WinapiNapiWasm
        .CreateFileW(
            lpFileName.toLong(),
            dwDesiredAccess,
            dwShareMode,
            lpSecurityAttributes.toLong(),
            dwCreationDisposition,
            dwFlagsAndAttributes,
            hTemplateFile.toLong(),
        ).toULong()

public actual fun DeleteFileA(lpFileName: LPCSTR): BOOL =
    WinapiNapiWasm.DeleteFileA(lpFileName.toLong())

public actual fun DeleteFileW(lpFileName: LPCWSTR): BOOL =
    WinapiNapiWasm.DeleteFileW(lpFileName.toLong())

public actual fun FlushFileBuffers(hFile: HANDLE): BOOL =
    WinapiNapiWasm.FlushFileBuffers(hFile.toLong())

public actual fun GetFileAttributesA(lpFileName: LPCSTR): DWORD =
    WinapiNapiWasm.GetFileAttributesA(lpFileName.toLong())

public actual fun GetFileAttributesW(lpFileName: LPCWSTR): DWORD =
    WinapiNapiWasm.GetFileAttributesW(lpFileName.toLong())

public actual fun GetFileSize(
    hFile: HANDLE,
    lpFileSizeHigh: UIntArray?,
): DWORD =
    WinapiNapiWasm.GetFileSize(hFile.toLong(), lpFileSizeHigh)

public actual fun SetFileAttributesA(
    lpFileName: LPCSTR,
    dwFileAttributes: DWORD,
): BOOL =
    WinapiNapiWasm.SetFileAttributesA(lpFileName.toLong(), dwFileAttributes)

public actual fun SetFileAttributesW(
    lpFileName: LPCWSTR,
    dwFileAttributes: DWORD,
): BOOL =
    WinapiNapiWasm.SetFileAttributesW(lpFileName.toLong(), dwFileAttributes)

public actual fun SetFilePointer(
    hFile: HANDLE,
    lDistanceToMove: LONG,
    lpDistanceToMoveHigh: IntArray?,
    dwMoveMethod: DWORD,
): DWORD =
    WinapiNapiWasm.SetFilePointer(
        hFile.toLong(),
        lDistanceToMove,
        lpDistanceToMoveHigh,
        dwMoveMethod,
    )

public actual fun AreFileApisANSI(): BOOL =
    WinapiNapiWasm.AreFileApisANSI()
