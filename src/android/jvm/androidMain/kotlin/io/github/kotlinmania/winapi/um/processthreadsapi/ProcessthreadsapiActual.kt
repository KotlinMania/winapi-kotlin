// port-lint: source um/processthreadsapi.rs
package io.github.kotlinmania.winapi.um.processthreadsapi

import io.github.kotlinmania.winapi.internal.WinapiJni
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE

public actual fun GetCurrentProcess(): HANDLE =
    WinapiJni.GetCurrentProcess().toULong()

public actual fun GetCurrentProcessId(): DWORD =
    WinapiJni.GetCurrentProcessId().toUInt()

public actual fun ExitProcess(uExitCode: UINT) {
    WinapiJni.ExitProcess(uExitCode.toInt())
}

public actual fun TerminateProcess(hProcess: HANDLE, uExitCode: UINT): BOOL =
    WinapiJni.TerminateProcess(hProcess.toLong(), uExitCode.toInt())

public actual fun GetCurrentThread(): HANDLE =
    WinapiJni.GetCurrentThread().toULong()

public actual fun GetCurrentThreadId(): DWORD =
    WinapiJni.GetCurrentThreadId().toUInt()

public actual fun OpenProcess(
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwProcessId: DWORD,
): HANDLE =
    WinapiJni.OpenProcess(dwDesiredAccess.toInt(), bInheritHandle, dwProcessId.toInt()).toULong()
