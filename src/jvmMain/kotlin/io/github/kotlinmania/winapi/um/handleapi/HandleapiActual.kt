// port-lint: source um/handleapi.rs
package io.github.kotlinmania.winapi.um.handleapi

import io.github.kotlinmania.winapi.internal.WinapiJni
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
public actual fun CloseHandle(hObject: HANDLE): BOOL =
    WinapiJni.CloseHandle(hObject.toLong())

public actual fun DuplicateHandle(
    hSourceProcessHandle: HANDLE,
    hSourceHandle: HANDLE,
    hTargetProcessHandle: HANDLE,
    lpTargetHandle: ULongArray,
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwOptions: DWORD,
): BOOL {
    val arr = LongArray(1) { if (lpTargetHandle.isNotEmpty()) lpTargetHandle[0].toLong() else 0L }
    val res =
        WinapiJni.DuplicateHandle(
            hSourceProcessHandle.toLong(),
            hSourceHandle.toLong(),
            hTargetProcessHandle.toLong(),
            arr,
            dwDesiredAccess.toInt(),
            bInheritHandle,
            dwOptions.toInt(),
        )
    if (lpTargetHandle.isNotEmpty()) {
        lpTargetHandle[0] = arr[0].toULong()
    }
    return res
}

public actual fun CompareObjectHandles(
    hFirstObjectHandle: HANDLE,
    hSecondObjectHandle: HANDLE,
): BOOL =
    WinapiJni.CompareObjectHandles(hFirstObjectHandle.toLong(), hSecondObjectHandle.toLong())

public actual fun GetHandleInformation(
    hObject: HANDLE,
    lpdwFlags: UIntArray,
): BOOL {
    val arr = IntArray(1) { if (lpdwFlags.isNotEmpty()) lpdwFlags[0].toInt() else 0 }
    val res = WinapiJni.GetHandleInformation(hObject.toLong(), arr)
    if (lpdwFlags.isNotEmpty()) {
        lpdwFlags[0] = arr[0].toUInt()
    }
    return res
}

public actual fun SetHandleInformation(
    hObject: HANDLE,
    dwMask: DWORD,
    dwFlags: DWORD,
): BOOL =
    WinapiJni.SetHandleInformation(hObject.toLong(), dwMask.toInt(), dwFlags.toInt())
