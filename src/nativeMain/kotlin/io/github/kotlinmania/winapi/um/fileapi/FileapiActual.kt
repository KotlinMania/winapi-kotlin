// port-lint: source um/fileapi.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.fileapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.LPCSTR
import io.github.kotlinmania.winapi.shared.ntdef.LPCWSTR
import io.github.kotlinmania.winapi.um.minwinbase.LPSECURITY_ATTRIBUTES
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import winapi.cinterop.winapi_are_file_apis_ansi
import winapi.cinterop.winapi_create_file_a
import winapi.cinterop.winapi_create_file_w
import winapi.cinterop.winapi_delete_file_a
import winapi.cinterop.winapi_delete_file_w
import winapi.cinterop.winapi_flush_file_buffers
import winapi.cinterop.winapi_get_file_attributes_a
import winapi.cinterop.winapi_get_file_attributes_w
import winapi.cinterop.winapi_get_file_size
import winapi.cinterop.winapi_set_file_attributes_a
import winapi.cinterop.winapi_set_file_attributes_w
import winapi.cinterop.winapi_set_file_pointer

public actual fun CreateFileA(
    lpFileName: LPCSTR,
    dwDesiredAccess: DWORD,
    dwShareMode: DWORD,
    lpSecurityAttributes: LPSECURITY_ATTRIBUTES,
    dwCreationDisposition: DWORD,
    dwFlagsAndAttributes: DWORD,
    hTemplateFile: HANDLE,
): HANDLE =
    winapi_create_file_a(
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
    winapi_create_file_w(
        lpFileName.toLong(),
        dwDesiredAccess,
        dwShareMode,
        lpSecurityAttributes.toLong(),
        dwCreationDisposition,
        dwFlagsAndAttributes,
        hTemplateFile.toLong(),
    ).toULong()

public actual fun DeleteFileA(lpFileName: LPCSTR): BOOL =
    winapi_delete_file_a(lpFileName.toLong())

public actual fun DeleteFileW(lpFileName: LPCWSTR): BOOL =
    winapi_delete_file_w(lpFileName.toLong())

public actual fun FlushFileBuffers(hFile: HANDLE): BOOL =
    winapi_flush_file_buffers(hFile.toLong())

public actual fun GetFileAttributesA(lpFileName: LPCSTR): DWORD =
    winapi_get_file_attributes_a(lpFileName.toLong())

public actual fun GetFileAttributesW(lpFileName: LPCWSTR): DWORD =
    winapi_get_file_attributes_w(lpFileName.toLong())

public actual fun GetFileSize(
    hFile: HANDLE,
    lpFileSizeHigh: UIntArray?,
): DWORD =
    if (lpFileSizeHigh != null && lpFileSizeHigh.isNotEmpty()) {
        memScoped {
            val highVar = alloc<UIntVar>()
            highVar.value = lpFileSizeHigh[0]
            val low = winapi_get_file_size(hFile.toLong(), highVar.ptr)
            lpFileSizeHigh[0] = highVar.value
            low
        }
    } else {
        winapi_get_file_size(hFile.toLong(), null)
    }

public actual fun SetFileAttributesA(
    lpFileName: LPCSTR,
    dwFileAttributes: DWORD,
): BOOL =
    winapi_set_file_attributes_a(lpFileName.toLong(), dwFileAttributes)

public actual fun SetFileAttributesW(
    lpFileName: LPCWSTR,
    dwFileAttributes: DWORD,
): BOOL =
    winapi_set_file_attributes_w(lpFileName.toLong(), dwFileAttributes)

public actual fun SetFilePointer(
    hFile: HANDLE,
    lDistanceToMove: LONG,
    lpDistanceToMoveHigh: IntArray?,
    dwMoveMethod: DWORD,
): DWORD =
    if (lpDistanceToMoveHigh != null && lpDistanceToMoveHigh.isNotEmpty()) {
        memScoped {
            val highVar = alloc<IntVar>()
            highVar.value = lpDistanceToMoveHigh[0]
            val low =
                winapi_set_file_pointer(
                    hFile.toLong(),
                    lDistanceToMove,
                    highVar.ptr,
                    dwMoveMethod,
                )
            lpDistanceToMoveHigh[0] = highVar.value
            low
        }
    } else {
        winapi_set_file_pointer(hFile.toLong(), lDistanceToMove, null, dwMoveMethod)
    }

public actual fun AreFileApisANSI(): BOOL =
    winapi_are_file_apis_ansi()
