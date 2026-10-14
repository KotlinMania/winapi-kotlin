// port-lint: source um/minwinbase.rs
package io.github.kotlinmania.winapi.um.minwinbase

import io.github.kotlinmania.winapi.shared.basetsd.ULONG_PTR
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.BYTE
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.FILETIME
import io.github.kotlinmania.winapi.shared.minwindef.HMODULE
import io.github.kotlinmania.winapi.shared.minwindef.LPVOID
import io.github.kotlinmania.winapi.shared.minwindef.MAX_PATH
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.shared.minwindef.WORD
import io.github.kotlinmania.winapi.um.winnt.EXCEPTION_RECORD
import io.github.kotlinmania.winapi.um.winnt.HANDLE
import io.github.kotlinmania.winapi.um.winnt.LPSTR
import io.github.kotlinmania.winapi.um.winnt.LPWSTR
import io.github.kotlinmania.winapi.um.winnt.PCONTEXT
import io.github.kotlinmania.winapi.um.winnt.PRTL_CRITICAL_SECTION
import io.github.kotlinmania.winapi.um.winnt.PRTL_CRITICAL_SECTION_DEBUG
import io.github.kotlinmania.winapi.um.winnt.PVOID
import io.github.kotlinmania.winapi.um.winnt.RTL_CRITICAL_SECTION
import io.github.kotlinmania.winapi.um.winnt.RTL_CRITICAL_SECTION_DEBUG

public data class SECURITY_ATTRIBUTES(
    public var nLength: DWORD = 0u,
    public var lpSecurityDescriptor: LPVOID = 0uL,
    public var bInheritHandle: BOOL = 0,
)

public typealias PSECURITY_ATTRIBUTES = ULong
public typealias LPSECURITY_ATTRIBUTES = ULong

public data class OVERLAPPED_u_s(
    public var Offset: DWORD = 0u,
    public var OffsetHigh: DWORD = 0u,
)

public data class OVERLAPPED_u(
    public var raw: ULong = 0uL,
) {
    public var s: OVERLAPPED_u_s
        get() =
            OVERLAPPED_u_s(
                Offset = (raw and 0xFFFFFFFFuL).toUInt(),
                OffsetHigh = ((raw shr 32) and 0xFFFFFFFFuL).toUInt(),
            )
        set(value) {
            raw = (value.Offset.toULong() and 0xFFFFFFFFuL) or (value.OffsetHigh.toULong() shl 32)
        }

    public var Pointer: PVOID
        get() = raw
        set(value) {
            raw = value
        }
}

public data class OVERLAPPED(
    public var Internal: ULONG_PTR = 0uL,
    public var InternalHigh: ULONG_PTR = 0uL,
    public var u: OVERLAPPED_u = OVERLAPPED_u(),
    public var hEvent: HANDLE = 0uL,
)

public typealias LPOVERLAPPED = ULong

public data class OVERLAPPED_ENTRY(
    public var lpCompletionKey: ULONG_PTR = 0uL,
    public var lpOverlapped: LPOVERLAPPED = 0uL,
    public var Internal: ULONG_PTR = 0uL,
    public var dwNumberOfBytesTransferred: DWORD = 0u,
)

public typealias LPOVERLAPPED_ENTRY = ULong

public data class SYSTEMTIME(
    public var wYear: WORD = 0u,
    public var wMonth: WORD = 0u,
    public var wDayOfWeek: WORD = 0u,
    public var wDay: WORD = 0u,
    public var wHour: WORD = 0u,
    public var wMinute: WORD = 0u,
    public var wSecond: WORD = 0u,
    public var wMilliseconds: WORD = 0u,
)

public typealias PSYSTEMTIME = ULong
public typealias LPSYSTEMTIME = ULong

public class WIN32_FIND_DATAA(
    public var dwFileAttributes: DWORD = 0u,
    public var ftCreationTime: FILETIME = FILETIME(0u, 0u),
    public var ftLastAccessTime: FILETIME = FILETIME(0u, 0u),
    public var ftLastWriteTime: FILETIME = FILETIME(0u, 0u),
    public var nFileSizeHigh: DWORD = 0u,
    public var nFileSizeLow: DWORD = 0u,
    public var dwReserved0: DWORD = 0u,
    public var dwReserved1: DWORD = 0u,
    public var cFileName: ByteArray = ByteArray(MAX_PATH.toInt()),
    public var cAlternateFileName: ByteArray = ByteArray(14),
)

public typealias PWIN32_FIND_DATAA = ULong
public typealias LPWIN32_FIND_DATAA = ULong

public class WIN32_FIND_DATAW(
    public var dwFileAttributes: DWORD = 0u,
    public var ftCreationTime: FILETIME = FILETIME(0u, 0u),
    public var ftLastAccessTime: FILETIME = FILETIME(0u, 0u),
    public var ftLastWriteTime: FILETIME = FILETIME(0u, 0u),
    public var nFileSizeHigh: DWORD = 0u,
    public var nFileSizeLow: DWORD = 0u,
    public var dwReserved0: DWORD = 0u,
    public var dwReserved1: DWORD = 0u,
    public var cFileName: UShortArray = UShortArray(MAX_PATH.toInt()),
    public var cAlternateFileName: UShortArray = UShortArray(14),
)

public typealias PWIN32_FIND_DATAW = ULong
public typealias LPWIN32_FIND_DATAW = ULong

public typealias FINDEX_INFO_LEVELS = UInt

public const val FindExInfoStandard: FINDEX_INFO_LEVELS = 0u
public const val FindExInfoBasic: FINDEX_INFO_LEVELS = 1u
public const val FindExInfoMaxInfoLevel: FINDEX_INFO_LEVELS = 2u

public const val FIND_FIRST_EX_CASE_SENSITIVE: DWORD = 0x00000001u
public const val FIND_FIRST_EX_LARGE_FETCH: DWORD = 0x00000002u

public typealias FINDEX_SEARCH_OPS = UInt

public const val FindExSearchNameMatch: FINDEX_SEARCH_OPS = 0u
public const val FindExSearchLimitToDirectories: FINDEX_SEARCH_OPS = 1u
public const val FindExSearchLimitToDevices: FINDEX_SEARCH_OPS = 2u
public const val FindExSearchMaxSearchOp: FINDEX_SEARCH_OPS = 3u

public typealias GET_FILEEX_INFO_LEVELS = UInt

public const val GetFileExInfoStandard: GET_FILEEX_INFO_LEVELS = 0u
public const val GetFileExMaxInfoLevel: GET_FILEEX_INFO_LEVELS = 1u

public typealias FILE_INFO_BY_HANDLE_CLASS = UInt

public const val FileBasicInfo: FILE_INFO_BY_HANDLE_CLASS = 0u
public const val FileStandardInfo: FILE_INFO_BY_HANDLE_CLASS = 1u
public const val FileNameInfo: FILE_INFO_BY_HANDLE_CLASS = 2u
public const val FileRenameInfo: FILE_INFO_BY_HANDLE_CLASS = 3u
public const val FileDispositionInfo: FILE_INFO_BY_HANDLE_CLASS = 4u
public const val FileAllocationInfo: FILE_INFO_BY_HANDLE_CLASS = 5u
public const val FileEndOfFileInfo: FILE_INFO_BY_HANDLE_CLASS = 6u
public const val FileStreamInfo: FILE_INFO_BY_HANDLE_CLASS = 7u
public const val FileCompressionInfo: FILE_INFO_BY_HANDLE_CLASS = 8u
public const val FileAttributeTagInfo: FILE_INFO_BY_HANDLE_CLASS = 9u
public const val FileIdBothDirectoryInfo: FILE_INFO_BY_HANDLE_CLASS = 10u
public const val FileIdBothDirectoryRestartInfo: FILE_INFO_BY_HANDLE_CLASS = 11u
public const val FileIoPriorityHintInfo: FILE_INFO_BY_HANDLE_CLASS = 12u
public const val FileRemoteProtocolInfo: FILE_INFO_BY_HANDLE_CLASS = 13u
public const val FileFullDirectoryInfo: FILE_INFO_BY_HANDLE_CLASS = 14u
public const val FileFullDirectoryRestartInfo: FILE_INFO_BY_HANDLE_CLASS = 15u
public const val FileStorageInfo: FILE_INFO_BY_HANDLE_CLASS = 16u
public const val FileAlignmentInfo: FILE_INFO_BY_HANDLE_CLASS = 17u
public const val FileIdInfo: FILE_INFO_BY_HANDLE_CLASS = 18u
public const val FileIdExtdDirectoryInfo: FILE_INFO_BY_HANDLE_CLASS = 19u
public const val FileIdExtdDirectoryRestartInfo: FILE_INFO_BY_HANDLE_CLASS = 20u
public const val FileDispositionInfoEx: FILE_INFO_BY_HANDLE_CLASS = 21u
public const val FileRenameInfoEx: FILE_INFO_BY_HANDLE_CLASS = 22u
public const val MaximumFileInfoByHandleClass: FILE_INFO_BY_HANDLE_CLASS = 23u

public typealias PFILE_INFO_BY_HANDLE_CLASS = ULong
public typealias CRITICAL_SECTION = RTL_CRITICAL_SECTION
public typealias PCRITICAL_SECTION = PRTL_CRITICAL_SECTION
public typealias LPCRITICAL_SECTION = PRTL_CRITICAL_SECTION
public typealias CRITICAL_SECTION_DEBUG = RTL_CRITICAL_SECTION_DEBUG
public typealias PCRITICAL_SECTION_DEBUG = PRTL_CRITICAL_SECTION_DEBUG
public typealias LPCRITICAL_SECTION_DEBUG = PRTL_CRITICAL_SECTION_DEBUG
public typealias LPOVERLAPPED_COMPLETION_ROUTINE = ULong

public const val LOCKFILE_FAIL_IMMEDIATELY: DWORD = 0x00000001u
public const val LOCKFILE_EXCLUSIVE_LOCK: DWORD = 0x00000002u

public class PROCESS_HEAP_ENTRY_Block(
    public var hMem: HANDLE = 0uL,
    public var dwReserved: UIntArray = UIntArray(3),
)

public data class PROCESS_HEAP_ENTRY_Region(
    public var dwCommittedSize: DWORD = 0u,
    public var dwUnCommittedSize: DWORD = 0u,
    public var lpFirstBlock: LPVOID = 0uL,
    public var lpLastBlock: LPVOID = 0uL,
)

public class PROCESS_HEAP_ENTRY_u(
    public var Block: PROCESS_HEAP_ENTRY_Block = PROCESS_HEAP_ENTRY_Block(),
    public var Region: PROCESS_HEAP_ENTRY_Region = PROCESS_HEAP_ENTRY_Region(),
)

public class PROCESS_HEAP_ENTRY(
    public var lpData: PVOID = 0uL,
    public var cbData: DWORD = 0u,
    public var cbOverhead: BYTE = 0u,
    public var iRegionIndex: BYTE = 0u,
    public var wFlags: WORD = 0u,
    public var u: PROCESS_HEAP_ENTRY_u = PROCESS_HEAP_ENTRY_u(),
)

public typealias LPPROCESS_HEAP_ENTRY = ULong
public typealias PPROCESS_HEAP_ENTRY = ULong

public const val PROCESS_HEAP_REGION: WORD = 0x0001u
public const val PROCESS_HEAP_UNCOMMITTED_RANGE: WORD = 0x0002u
public const val PROCESS_HEAP_ENTRY_BUSY: WORD = 0x0004u
public const val PROCESS_HEAP_SEG_ALLOC: WORD = 0x0008u
public const val PROCESS_HEAP_ENTRY_MOVEABLE: WORD = 0x0010u
public const val PROCESS_HEAP_ENTRY_DDESHARE: WORD = 0x0020u

public data class REASON_CONTEXT_Detailed(
    public var LocalizedReasonModule: HMODULE = 0uL,
    public var LocalizedReasonId: ULONG = 0u,
    public var ReasonStringCount: ULONG = 0u,
    public var ReasonStrings: ULong = 0uL,
)

public class REASON_CONTEXT_Reason(
    public var Detailed: REASON_CONTEXT_Detailed = REASON_CONTEXT_Detailed(),
    public var SimpleReasonString: LPWSTR = 0uL,
)

public class REASON_CONTEXT(
    public var Version: ULONG = 0u,
    public var Flags: DWORD = 0u,
    public var Reason: REASON_CONTEXT_Reason = REASON_CONTEXT_Reason(),
)

public typealias PREASON_CONTEXT = ULong

public const val EXCEPTION_DEBUG_EVENT: DWORD = 1u
public const val CREATE_THREAD_DEBUG_EVENT: DWORD = 2u
public const val CREATE_PROCESS_DEBUG_EVENT: DWORD = 3u
public const val EXIT_THREAD_DEBUG_EVENT: DWORD = 4u
public const val EXIT_PROCESS_DEBUG_EVENT: DWORD = 5u
public const val LOAD_DLL_DEBUG_EVENT: DWORD = 6u
public const val UNLOAD_DLL_DEBUG_EVENT: DWORD = 7u
public const val OUTPUT_DEBUG_STRING_EVENT: DWORD = 8u
public const val RIP_EVENT: DWORD = 9u

public typealias PTHREAD_START_ROUTINE = ULong
public typealias LPTHREAD_START_ROUTINE = PTHREAD_START_ROUTINE
public typealias PENCLAVE_ROUTINE = ULong
public typealias LPENCLAVE_ROUTINE = PENCLAVE_ROUTINE

public class EXCEPTION_DEBUG_INFO(
    public var ExceptionRecord: EXCEPTION_RECORD = EXCEPTION_RECORD(),
    public var dwFirstChance: DWORD = 0u,
)

public typealias LPEXCEPTION_DEBUG_INFO = ULong

public data class CREATE_THREAD_DEBUG_INFO(
    public var hThread: HANDLE = 0uL,
    public var lpThreadLocalBase: LPVOID = 0uL,
    public var lpStartAddress: LPTHREAD_START_ROUTINE = 0uL,
)

public typealias LPCREATE_THREAD_DEBUG_INFO = ULong

public data class CREATE_PROCESS_DEBUG_INFO(
    public var hFile: HANDLE = 0uL,
    public var hProcess: HANDLE = 0uL,
    public var hThread: HANDLE = 0uL,
    public var lpBaseOfImage: LPVOID = 0uL,
    public var dwDebugInfoFileOffset: DWORD = 0u,
    public var nDebugInfoSize: DWORD = 0u,
    public var lpThreadLocalBase: LPVOID = 0uL,
    public var lpStartAddress: LPTHREAD_START_ROUTINE = 0uL,
    public var lpImageName: LPVOID = 0uL,
    public var fUnicode: WORD = 0u,
)

public typealias LPCREATE_PROCESS_DEBUG_INFO = ULong

public data class EXIT_THREAD_DEBUG_INFO(
    public var dwExitCode: DWORD = 0u,
)

public typealias LPEXIT_THREAD_DEBUG_INFO = ULong

public data class EXIT_PROCESS_DEBUG_INFO(
    public var dwExitCode: DWORD = 0u,
)

public typealias LPEXIT_PROCESS_DEBUG_INFO = ULong

public data class LOAD_DLL_DEBUG_INFO(
    public var hFile: HANDLE = 0uL,
    public var lpBaseOfDll: LPVOID = 0uL,
    public var dwDebugInfoFileOffset: DWORD = 0u,
    public var nDebugInfoSize: DWORD = 0u,
    public var lpImageName: LPVOID = 0uL,
    public var fUnicode: WORD = 0u,
)

public typealias LPLOAD_DLL_DEBUG_INFO = ULong

public data class UNLOAD_DLL_DEBUG_INFO(
    public var lpBaseOfDll: LPVOID = 0uL,
)

public typealias LPUNLOAD_DLL_DEBUG_INFO = ULong

public data class OUTPUT_DEBUG_STRING_INFO(
    public var lpDebugStringData: LPSTR = 0uL,
    public var fUnicode: WORD = 0u,
    public var nDebugStringLength: WORD = 0u,
)

public typealias LPOUTPUT_DEBUG_STRING_INFO = ULong

public data class RIP_INFO(
    public var dwError: DWORD = 0u,
    public var dwType: DWORD = 0u,
)

public typealias LPRIP_INFO = ULong

public class DEBUG_EVENT_u(
    public var Exception: EXCEPTION_DEBUG_INFO = EXCEPTION_DEBUG_INFO(),
    public var CreateThread: CREATE_THREAD_DEBUG_INFO = CREATE_THREAD_DEBUG_INFO(),
    public var CreateProcessInfo: CREATE_PROCESS_DEBUG_INFO = CREATE_PROCESS_DEBUG_INFO(),
    public var ExitThread: EXIT_THREAD_DEBUG_INFO = EXIT_THREAD_DEBUG_INFO(),
    public var ExitProcess: EXIT_PROCESS_DEBUG_INFO = EXIT_PROCESS_DEBUG_INFO(),
    public var LoadDll: LOAD_DLL_DEBUG_INFO = LOAD_DLL_DEBUG_INFO(),
    public var UnloadDll: UNLOAD_DLL_DEBUG_INFO = UNLOAD_DLL_DEBUG_INFO(),
    public var DebugString: OUTPUT_DEBUG_STRING_INFO = OUTPUT_DEBUG_STRING_INFO(),
    public var RipInfo: RIP_INFO = RIP_INFO(),
)

public class DEBUG_EVENT(
    public var dwDebugEventCode: DWORD = 0u,
    public var dwProcessId: DWORD = 0u,
    public var dwThreadId: DWORD = 0u,
    public var u: DEBUG_EVENT_u = DEBUG_EVENT_u(),
)

public typealias LPDEBUG_EVENT = ULong
public typealias LPCONTEXT = PCONTEXT

public const val STILL_ACTIVE: DWORD = 0x00000103u
public const val EXCEPTION_ACCESS_VIOLATION: DWORD = 0xC0000005u
public const val EXCEPTION_DATATYPE_MISALIGNMENT: DWORD = 0x80000002u
public const val EXCEPTION_BREAKPOINT: DWORD = 0x80000003u
public const val EXCEPTION_SINGLE_STEP: DWORD = 0x80000004u
public const val EXCEPTION_ARRAY_BOUNDS_EXCEEDED: DWORD = 0xC000008Cu
public const val EXCEPTION_FLT_DENORMAL_OPERAND: DWORD = 0xC000008Du
public const val EXCEPTION_FLT_DIVIDE_BY_ZERO: DWORD = 0xC000008Eu
public const val EXCEPTION_FLT_INEXACT_RESULT: DWORD = 0xC000008Fu
public const val EXCEPTION_FLT_INVALID_OPERATION: DWORD = 0xC0000090u
public const val EXCEPTION_FLT_OVERFLOW: DWORD = 0xC0000091u
public const val EXCEPTION_FLT_STACK_CHECK: DWORD = 0xC0000092u
public const val EXCEPTION_FLT_UNDERFLOW: DWORD = 0xC0000093u
public const val EXCEPTION_INT_DIVIDE_BY_ZERO: DWORD = 0xC0000094u
public const val EXCEPTION_INT_OVERFLOW: DWORD = 0xC0000095u
public const val EXCEPTION_PRIV_INSTRUCTION: DWORD = 0xC0000096u
public const val EXCEPTION_IN_PAGE_ERROR: DWORD = 0xC0000006u
public const val EXCEPTION_ILLEGAL_INSTRUCTION: DWORD = 0xC000001Du
public const val EXCEPTION_NONCONTINUABLE_EXCEPTION: DWORD = 0xC0000025u
public const val EXCEPTION_STACK_OVERFLOW: DWORD = 0xC00000FDu
public const val EXCEPTION_INVALID_DISPOSITION: DWORD = 0xC0000026u
public const val EXCEPTION_GUARD_PAGE: DWORD = 0x80000001u
public const val EXCEPTION_INVALID_HANDLE: DWORD = 0xC0000008u
public const val EXCEPTION_POSSIBLE_DEADLOCK: DWORD = 0xC0000194u
public const val CONTROL_C_EXIT: DWORD = 0xC000013Au

public const val LMEM_FIXED: UINT = 0x0000u
public const val LMEM_MOVEABLE: UINT = 0x0002u
public const val LMEM_NOCOMPACT: UINT = 0x0010u
public const val LMEM_NODISCARD: UINT = 0x0020u
public const val LMEM_ZEROINIT: UINT = 0x0040u
public const val LMEM_MODIFY: UINT = 0x0080u
public const val LMEM_DISCARDABLE: UINT = 0x0F00u
public const val LMEM_VALID_FLAGS: UINT = 0x0F72u
public const val LMEM_INVALID_HANDLE: UINT = 0x8000u
public const val LHND: UINT = 0x0042u
public const val LPTR: UINT = 0x0040u
public const val NONZEROLHND: UINT = LMEM_MOVEABLE
public const val NONZEROLPTR: UINT = LMEM_FIXED
public const val LMEM_DISCARDED: UINT = 0x4000u
public const val LMEM_LOCKCOUNT: UINT = 0x00FFu
public const val NUMA_NO_PREFERRED_NODE: DWORD = 0xFFFFFFFFu
