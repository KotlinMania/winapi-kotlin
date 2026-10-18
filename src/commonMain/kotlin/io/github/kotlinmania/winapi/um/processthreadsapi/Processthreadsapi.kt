// port-lint: source um/processthreadsapi.rs
package io.github.kotlinmania.winapi.um.processthreadsapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.LPBYTE
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.minwindef.WORD
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import io.github.kotlinmania.winapi.shared.ntdef.LPSTR
import io.github.kotlinmania.winapi.shared.ntdef.LPWSTR
public data class PROCESS_INFORMATION(
    public var hProcess: HANDLE = 0uL,
    public var hThread: HANDLE = 0uL,
    public var dwProcessId: DWORD = 0u,
    public var dwThreadId: DWORD = 0u,
)

public typealias PPROCESS_INFORMATION = ULong
public typealias LPPROCESS_INFORMATION = ULong

public data class STARTUPINFOA(
    public var cb: DWORD = 0u,
    public var lpReserved: LPSTR = 0uL,
    public var lpDesktop: LPSTR = 0uL,
    public var lpTitle: LPSTR = 0uL,
    public var dwX: DWORD = 0u,
    public var dwY: DWORD = 0u,
    public var dwXSize: DWORD = 0u,
    public var dwYSize: DWORD = 0u,
    public var dwXCountChars: DWORD = 0u,
    public var dwYCountChars: DWORD = 0u,
    public var dwFillAttribute: DWORD = 0u,
    public var dwFlags: DWORD = 0u,
    public var wShowWindow: WORD = 0u,
    public var cbReserved2: WORD = 0u,
    public var lpReserved2: LPBYTE = 0uL,
    public var hStdInput: HANDLE = 0uL,
    public var hStdOutput: HANDLE = 0uL,
    public var hStdError: HANDLE = 0uL,
)

public typealias LPSTARTUPINFOA = ULong

public data class STARTUPINFOW(
    public var cb: DWORD = 0u,
    public var lpReserved: LPWSTR = 0uL,
    public var lpDesktop: LPWSTR = 0uL,
    public var lpTitle: LPWSTR = 0uL,
    public var dwX: DWORD = 0u,
    public var dwY: DWORD = 0u,
    public var dwXSize: DWORD = 0u,
    public var dwYSize: DWORD = 0u,
    public var dwXCountChars: DWORD = 0u,
    public var dwYCountChars: DWORD = 0u,
    public var dwFillAttribute: DWORD = 0u,
    public var dwFlags: DWORD = 0u,
    public var wShowWindow: WORD = 0u,
    public var cbReserved2: WORD = 0u,
    public var lpReserved2: LPBYTE = 0uL,
    public var hStdInput: HANDLE = 0uL,
    public var hStdOutput: HANDLE = 0uL,
    public var hStdError: HANDLE = 0uL,
)

public typealias LPSTARTUPINFOW = ULong

public const val TLS_OUT_OF_INDEXES: DWORD = 0xFFFFFFFFu

public data class PROC_THREAD_ATTRIBUTE_LIST(
    public var dummy: ULong = 0uL,
)

public typealias PPROC_THREAD_ATTRIBUTE_LIST = ULong
public typealias LPPROC_THREAD_ATTRIBUTE_LIST = ULong

public typealias THREAD_INFORMATION_CLASS = UInt

public const val ThreadMemoryPriority: THREAD_INFORMATION_CLASS = 0u
public const val ThreadAbsoluteCpuPriority: THREAD_INFORMATION_CLASS = 1u
public const val ThreadInformationClassMax: THREAD_INFORMATION_CLASS = 2u

public typealias PROCESS_INFORMATION_CLASS = UInt

public const val ProcessMemoryPriority: PROCESS_INFORMATION_CLASS = 0u
public const val ProcessInformationClassMax: PROCESS_INFORMATION_CLASS = 1u

public expect fun GetCurrentProcess(): HANDLE

public expect fun GetCurrentProcessId(): DWORD

public expect fun ExitProcess(uExitCode: UINT)

public expect fun TerminateProcess(hProcess: HANDLE, uExitCode: UINT): BOOL

public expect fun GetCurrentThread(): HANDLE

public expect fun GetCurrentThreadId(): DWORD

public expect fun OpenProcess(
    dwDesiredAccess: DWORD,
    bInheritHandle: BOOL,
    dwProcessId: DWORD,
): HANDLE
