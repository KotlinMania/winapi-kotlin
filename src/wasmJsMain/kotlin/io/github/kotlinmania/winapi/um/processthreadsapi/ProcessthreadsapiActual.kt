// port-lint: source um/processthreadsapi.rs
package io.github.kotlinmania.winapi.um.processthreadsapi

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE

public actual fun GetCurrentProcess(): HANDLE =
    WinapiNapiWasm.GetCurrentProcess().toULong()

public actual fun GetCurrentProcessId(): DWORD =
    WinapiNapiWasm.GetCurrentProcessId()

public actual fun ExitProcess(uExitCode: UINT) {
    WinapiNapiWasm.ExitProcess(uExitCode)
}

public actual fun TerminateProcess(hProcess: HANDLE, uExitCode: UINT): BOOL =
    WinapiNapiWasm.TerminateProcess(hProcess.toLong(), uExitCode)

public actual fun GetCurrentThread(): HANDLE =
    WinapiNapiWasm.GetCurrentThread().toULong()

public actual fun GetCurrentThreadId(): DWORD =
    WinapiNapiWasm.GetCurrentThreadId()

public actual fun OpenProcess(
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwProcessId: DWORD,
): HANDLE =
    WinapiNapiWasm.OpenProcess(dwDesiredAccess, bInheritHandle, dwProcessId).toULong()
