// port-lint: source um/processthreadsapi.rs
package io.github.kotlinmania.winapi.um.processthreadsapi

import io.github.kotlinmania.winapi.internal.WinapiNative
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE

public actual fun GetCurrentProcess(): HANDLE =
    WinapiNative.GetCurrentProcess().toLong().toULong()

public actual fun GetCurrentProcessId(): DWORD =
    WinapiNative.GetCurrentProcessId().toUInt()

public actual fun ExitProcess(uExitCode: UINT) {
    WinapiNative.ExitProcess(uExitCode.toInt())
}

public actual fun TerminateProcess(hProcess: HANDLE, uExitCode: UINT): BOOL =
    WinapiNative.TerminateProcess(hProcess.toLong().toDouble(), uExitCode.toInt())

public actual fun GetCurrentThread(): HANDLE =
    WinapiNative.GetCurrentThread().toLong().toULong()

public actual fun GetCurrentThreadId(): DWORD =
    WinapiNative.GetCurrentThreadId().toUInt()

public actual fun OpenProcess(
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwProcessId: DWORD,
): HANDLE =
    WinapiNative
        .OpenProcess(
            dwDesiredAccess.toInt(),
            bInheritHandle,
            dwProcessId.toInt(),
        ).toLong()
        .toULong()
