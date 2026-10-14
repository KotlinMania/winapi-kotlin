// port-lint: source um/handleapi.rs
package io.github.kotlinmania.winapi.um.handleapi

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.um.winnt.HANDLE

public actual fun CloseHandle(hObject: HANDLE): BOOL =
    WinapiNapiWasm.CloseHandle(hObject.toLong())

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
        WinapiNapiWasm.DuplicateHandle(
            hSourceProcessHandle.toLong(),
            hSourceHandle.toLong(),
            hTargetProcessHandle.toLong(),
            arr,
            dwDesiredAccess,
            bInheritHandle,
            dwOptions,
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
    WinapiNapiWasm.CompareObjectHandles(hFirstObjectHandle.toLong(), hSecondObjectHandle.toLong())

public actual fun GetHandleInformation(
    hObject: HANDLE,
    lpdwFlags: UIntArray,
): BOOL =
    WinapiNapiWasm.GetHandleInformation(hObject.toLong(), lpdwFlags)

public actual fun SetHandleInformation(
    hObject: HANDLE,
    dwMask: DWORD,
    dwFlags: DWORD,
): BOOL =
    WinapiNapiWasm.SetHandleInformation(hObject.toLong(), dwMask, dwFlags)
