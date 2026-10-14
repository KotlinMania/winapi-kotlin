// port-lint: source um/handleapi.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.handleapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.um.winnt.HANDLE
import kotlinx.cinterop.LongVar
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import winapi.cinterop.winapi_close_handle
import winapi.cinterop.winapi_compare_object_handles
import winapi.cinterop.winapi_duplicate_handle
import winapi.cinterop.winapi_get_handle_information
import winapi.cinterop.winapi_set_handle_information

public actual fun CloseHandle(hObject: HANDLE): BOOL =
    winapi_close_handle(hObject.toLong())

public actual fun DuplicateHandle(
    hSourceProcessHandle: HANDLE,
    hSourceHandle: HANDLE,
    hTargetProcessHandle: HANDLE,
    lpTargetHandle: ULongArray,
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwOptions: DWORD,
): BOOL =
    memScoped {
        val targetVar = alloc<LongVar>()
        targetVar.value = if (lpTargetHandle.isNotEmpty()) lpTargetHandle[0].toLong() else 0L
        val res =
            winapi_duplicate_handle(
                hSourceProcessHandle.toLong(),
                hSourceHandle.toLong(),
                hTargetProcessHandle.toLong(),
                targetVar.ptr,
                dwDesiredAccess,
                bInheritHandle,
                dwOptions,
            )
        if (lpTargetHandle.isNotEmpty()) {
            lpTargetHandle[0] = targetVar.value.toULong()
        }
        res
    }

public actual fun CompareObjectHandles(
    hFirstObjectHandle: HANDLE,
    hSecondObjectHandle: HANDLE,
): BOOL =
    winapi_compare_object_handles(hFirstObjectHandle.toLong(), hSecondObjectHandle.toLong())

public actual fun GetHandleInformation(
    hObject: HANDLE,
    lpdwFlags: UIntArray,
): BOOL =
    memScoped {
        val flagsVar = alloc<UIntVar>()
        flagsVar.value = if (lpdwFlags.isNotEmpty()) lpdwFlags[0] else 0u
        val res = winapi_get_handle_information(hObject.toLong(), flagsVar.ptr)
        if (lpdwFlags.isNotEmpty()) {
            lpdwFlags[0] = flagsVar.value
        }
        res
    }

public actual fun SetHandleInformation(
    hObject: HANDLE,
    dwMask: DWORD,
    dwFlags: DWORD,
): BOOL =
    winapi_set_handle_information(hObject.toLong(), dwMask, dwFlags)
