// port-lint: source um/handleapi.rs
package io.github.kotlinmania.winapi.um.handleapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.um.winnt.HANDLE

public const val INVALID_HANDLE_VALUE: HANDLE = 0xFFFFFFFFFFFFFFFFuL

public expect fun CloseHandle(hObject: HANDLE): BOOL

public expect fun DuplicateHandle(
    hSourceProcessHandle: HANDLE,
    hSourceHandle: HANDLE,
    hTargetProcessHandle: HANDLE,
    lpTargetHandle: ULongArray,
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwOptions: DWORD,
): BOOL

public expect fun CompareObjectHandles(
    hFirstObjectHandle: HANDLE,
    hSecondObjectHandle: HANDLE,
): BOOL

public expect fun GetHandleInformation(
    hObject: HANDLE,
    lpdwFlags: UIntArray,
): BOOL

public expect fun SetHandleInformation(
    hObject: HANDLE,
    dwMask: DWORD,
    dwFlags: DWORD,
): BOOL
