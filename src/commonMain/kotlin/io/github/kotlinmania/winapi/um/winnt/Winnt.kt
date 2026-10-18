// port-lint: source um/winnt.rs
package io.github.kotlinmania.winapi.um.winnt

import io.github.kotlinmania.winapi.shared.basetsd.LONG_PTR
import io.github.kotlinmania.winapi.shared.basetsd.ULONG_PTR
import io.github.kotlinmania.winapi.shared.minwindef.BYTE
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.WORD
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.LPCSTR
import io.github.kotlinmania.winapi.shared.ntdef.PVOID
import io.github.kotlinmania.winapi.shared.ntdef.ULONGLONG

public typealias ANYSIZE_ARRAY_LEN = Int

public const val ANYSIZE_ARRAY: Int = 1

public typealias LPCTSTR = LPCSTR

public typealias BOOLEAN = BYTE
public typealias PBOOLEAN = ULong
public typealias PLONG = ULong
public typealias PCONTEXT = ULong
public typealias CONTEXT = ULong
public typealias PVECTORED_EXCEPTION_HANDLER = ULong
public typealias PAPCFUNC = ULong
public typealias PPROCESSOR_NUMBER = ULong
public typealias PROCESS_MITIGATION_POLICY = UInt

public const val EXCEPTION_MAXIMUM_PARAMETERS: Int = 15

public class EXCEPTION_RECORD(
    public var ExceptionCode: DWORD = 0u,
    public var ExceptionFlags: DWORD = 0u,
    public var ExceptionRecord: ULong = 0uL,
    public var ExceptionAddress: PVOID = 0uL,
    public var NumberParameters: DWORD = 0u,
    public var ExceptionInformation: ULongArray = ULongArray(EXCEPTION_MAXIMUM_PARAMETERS),
)

public typealias PEXCEPTION_RECORD = ULong

public data class EXCEPTION_POINTERS(
    public var ExceptionRecord: PEXCEPTION_RECORD = 0uL,
    public var ContextRecord: PCONTEXT = 0uL,
)

public typealias PEXCEPTION_POINTERS = ULong

public data class LIST_ENTRY(
    public var Flink: ULong = 0uL,
    public var Blink: ULong = 0uL,
)

public data class RTL_CRITICAL_SECTION_DEBUG(
    public var type: WORD = 0u,
    public var CreatorBackTraceIndex: WORD = 0u,
    public var CriticalSection: ULong = 0uL,
    public var ProcessLocksList: LIST_ENTRY = LIST_ENTRY(),
    public var EntryCount: DWORD = 0u,
    public var ContentionCount: DWORD = 0u,
    public var Flags: DWORD = 0u,
    public var CreatorBackTraceIndexHigh: WORD = 0u,
    public var SpareWORD: WORD = 0u,
)

public typealias PRTL_CRITICAL_SECTION_DEBUG = ULong

public data class RTL_CRITICAL_SECTION(
    public var DebugInfo: PRTL_CRITICAL_SECTION_DEBUG = 0uL,
    public var LockCount: LONG = 0,
    public var RecursionCount: LONG = 0,
    public var OwningThread: HANDLE = 0uL,
    public var LockSemaphore: HANDLE = 0uL,
    public var SpinCount: ULONG_PTR = 0uL,
)

public typealias PRTL_CRITICAL_SECTION = ULong

public class FILE_ID_128(
    public var Identifier: UByteArray = UByteArray(16),
)

public data class FILE_SEGMENT_ELEMENT(
    public var Alignment: ULONGLONG = 0uL,
) {
    public var Buffer: PVOID
        get() = Alignment
        set(value) {
            Alignment = value
        }
}

public const val TIME_ZONE_ID_UNKNOWN: DWORD = 0u
public const val TIME_ZONE_ID_STANDARD: DWORD = 1u
public const val TIME_ZONE_ID_DAYLIGHT: DWORD = 2u

public const val DUPLICATE_CLOSE_SOURCE: DWORD = 0x00000001u
public const val DUPLICATE_SAME_ACCESS: DWORD = 0x00000002u

public const val DELETE: DWORD = 0x00010000u
public const val READ_CONTROL: DWORD = 0x00020000u
public const val WRITE_DAC: DWORD = 0x00040000u
public const val WRITE_OWNER: DWORD = 0x00080000u
public const val SYNCHRONIZE: DWORD = 0x00100000u
public const val STANDARD_RIGHTS_REQUIRED: DWORD = 0x000F0000u
public const val STANDARD_RIGHTS_READ: DWORD = READ_CONTROL
public const val STANDARD_RIGHTS_WRITE: DWORD = READ_CONTROL
public const val STANDARD_RIGHTS_EXECUTE: DWORD = READ_CONTROL
public const val STANDARD_RIGHTS_ALL: DWORD = 0x001F0000u
public const val SPECIFIC_RIGHTS_ALL: DWORD = 0x0000FFFFu

public const val GENERIC_READ: DWORD = 0x80000000u
public const val GENERIC_WRITE: DWORD = 0x40000000u
public const val GENERIC_EXECUTE: DWORD = 0x20000000u
public const val GENERIC_ALL: DWORD = 0x10000000u

public const val FILE_SHARE_READ: DWORD = 0x00000001u
public const val FILE_SHARE_WRITE: DWORD = 0x00000002u
public const val FILE_SHARE_DELETE: DWORD = 0x00000004u

public const val FILE_ATTRIBUTE_READONLY: DWORD = 0x00000001u
public const val FILE_ATTRIBUTE_HIDDEN: DWORD = 0x00000002u
public const val FILE_ATTRIBUTE_SYSTEM: DWORD = 0x00000004u
public const val FILE_ATTRIBUTE_DIRECTORY: DWORD = 0x00000010u
public const val FILE_ATTRIBUTE_ARCHIVE: DWORD = 0x00000020u
public const val FILE_ATTRIBUTE_DEVICE: DWORD = 0x00000040u
public const val FILE_ATTRIBUTE_NORMAL: DWORD = 0x00000080u
public const val FILE_ATTRIBUTE_TEMPORARY: DWORD = 0x00000100u
public const val FILE_ATTRIBUTE_SPARSE_FILE: DWORD = 0x00000200u
public const val FILE_ATTRIBUTE_REPARSE_POINT: DWORD = 0x00000400u
public const val FILE_ATTRIBUTE_COMPRESSED: DWORD = 0x00000800u
public const val FILE_ATTRIBUTE_OFFLINE: DWORD = 0x00001000u
public const val FILE_ATTRIBUTE_NOT_CONTENT_INDEXED: DWORD = 0x00002000u
public const val FILE_ATTRIBUTE_ENCRYPTED: DWORD = 0x00004000u
public const val FILE_ATTRIBUTE_INTEGRITY_STREAM: DWORD = 0x00008000u
public const val FILE_ATTRIBUTE_VIRTUAL: DWORD = 0x00010000u
public const val FILE_ATTRIBUTE_NO_SCRUB_DATA: DWORD = 0x00020000u

public const val PROCESS_TERMINATE: DWORD = 0x0001u
public const val PROCESS_CREATE_THREAD: DWORD = 0x0002u
public const val PROCESS_SET_SESSIONID: DWORD = 0x0004u
public const val PROCESS_VM_OPERATION: DWORD = 0x0008u
public const val PROCESS_VM_READ: DWORD = 0x0010u
public const val PROCESS_VM_WRITE: DWORD = 0x0020u
public const val PROCESS_DUP_HANDLE: DWORD = 0x0040u
public const val PROCESS_CREATE_PROCESS: DWORD = 0x0080u
public const val PROCESS_SET_QUOTA: DWORD = 0x0100u
public const val PROCESS_SET_INFORMATION: DWORD = 0x0200u
public const val PROCESS_QUERY_INFORMATION: DWORD = 0x0400u
public const val PROCESS_SUSPEND_RESUME: DWORD = 0x0800u
public const val PROCESS_QUERY_LIMITED_INFORMATION: DWORD = 0x1000u
public const val PROCESS_SET_LIMITED_INFORMATION: DWORD = 0x2000u
public const val PROCESS_ALL_ACCESS: DWORD = 0x001FFFFFu

public typealias PVOID_FUNC = LONG_PTR
