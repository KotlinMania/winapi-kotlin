// port-lint: source um/processthreadsapi.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.processthreadsapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.um.winnt.HANDLE
import winapi.cinterop.winapi_exit_process
import winapi.cinterop.winapi_get_current_process
import winapi.cinterop.winapi_get_current_process_id
import winapi.cinterop.winapi_get_current_thread
import winapi.cinterop.winapi_get_current_thread_id
import winapi.cinterop.winapi_open_process
import winapi.cinterop.winapi_terminate_process

public actual fun GetCurrentProcess(): HANDLE =
    winapi_get_current_process().toULong()

public actual fun GetCurrentProcessId(): DWORD =
    winapi_get_current_process_id()

public actual fun ExitProcess(uExitCode: UINT) {
    winapi_exit_process(uExitCode)
}

public actual fun TerminateProcess(hProcess: HANDLE, uExitCode: UINT): BOOL =
    winapi_terminate_process(hProcess.toLong(), uExitCode)

public actual fun GetCurrentThread(): HANDLE =
    winapi_get_current_thread().toULong()

public actual fun GetCurrentThreadId(): DWORD =
    winapi_get_current_thread_id()

public actual fun OpenProcess(
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwProcessId: DWORD,
): HANDLE =
    winapi_open_process(dwDesiredAccess, bInheritHandle, dwProcessId).toULong()
