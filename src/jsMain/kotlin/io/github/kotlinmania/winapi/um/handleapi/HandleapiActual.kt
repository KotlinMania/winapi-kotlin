// port-lint: source um/handleapi.rs
package io.github.kotlinmania.winapi.um.handleapi

import io.github.kotlinmania.winapi.internal.WinapiNative
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE

public actual fun CloseHandle(hObject: HANDLE): BOOL =
    WinapiNative.CloseHandle(hObject.toLong().toDouble())

public actual fun DuplicateHandle(
    hSourceProcessHandle: HANDLE,
    hSourceHandle: HANDLE,
    hTargetProcessHandle: HANDLE,
    lpTargetHandle: ULongArray,
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwOptions: DWORD,
): BOOL {
    val res =
        WinapiNative.DuplicateHandle(
            hSourceProcessHandle.toLong().toDouble(),
            hSourceHandle.toLong().toDouble(),
            hTargetProcessHandle.toLong().toDouble(),
            dwDesiredAccess.toInt(),
            bInheritHandle,
            dwOptions.toInt(),
        )
    if (lpTargetHandle.isNotEmpty()) {
        lpTargetHandle[0] = (res.targetHandle as Double).toLong().toULong()
    }
    return res.success as Int
}

public actual fun CompareObjectHandles(
    hFirstObjectHandle: HANDLE,
    hSecondObjectHandle: HANDLE,
): BOOL =
    WinapiNative.CompareObjectHandles(
        hFirstObjectHandle.toLong().toDouble(),
        hSecondObjectHandle.toLong().toDouble(),
    )

public actual fun GetHandleInformation(
    hObject: HANDLE,
    lpdwFlags: UIntArray,
): BOOL {
    val res = WinapiNative.GetHandleInformation(hObject.toLong().toDouble())
    if (lpdwFlags.isNotEmpty()) {
        lpdwFlags[0] = (res.flags as Int).toUInt()
    }
    return res.success as Int
}

public actual fun SetHandleInformation(
    hObject: HANDLE,
    dwMask: DWORD,
    dwFlags: DWORD,
): BOOL =
    WinapiNative.SetHandleInformation(
        hObject.toLong().toDouble(),
        dwMask.toInt(),
        dwFlags.toInt(),
    )
