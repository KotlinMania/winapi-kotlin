// port-lint: source um/processthreadsapi.rs
package io.github.kotlinmania.winapi.um.processthreadsapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.um.winnt.HANDLE

public actual fun GetCurrentProcess(): HANDLE = (-1L).toULong()

public actual fun GetCurrentProcessId(): DWORD = 0u

public actual fun ExitProcess(uExitCode: UINT) {}

public actual fun TerminateProcess(hProcess: HANDLE, uExitCode: UINT): BOOL = 0

public actual fun GetCurrentThread(): HANDLE = (-2L).toULong()

public actual fun GetCurrentThreadId(): DWORD = 0u

public actual fun OpenProcess(
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwProcessId: DWORD,
): HANDLE = 0uL
