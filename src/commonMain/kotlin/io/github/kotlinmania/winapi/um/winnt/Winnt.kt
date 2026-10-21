// port-lint: source um/winnt.rs
package io.github.kotlinmania.winapi.um.winnt

import io.github.kotlinmania.winapi.shared.basetsd.DWORD64
import io.github.kotlinmania.winapi.shared.basetsd.SIZE_T
import io.github.kotlinmania.winapi.shared.basetsd.ULONG_PTR
import io.github.kotlinmania.winapi.shared.minwindef.BYTE
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.shared.minwindef.WORD
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.PVOID
import io.github.kotlinmania.winapi.shared.ntdef.ULONGLONG

public const val ANYSIZE_ARRAY: Int = 1

public typealias BOOLEAN = BYTE
public typealias PBOOLEAN = ULong
public typealias PLONG = ULong
public typealias PCONTEXT = ULong

public class CONTEXT(
    public var P1Home: DWORD64 = 0uL,
    public var P2Home: DWORD64 = 0uL,
    public var P3Home: DWORD64 = 0uL,
    public var P4Home: DWORD64 = 0uL,
    public var ContextFlags: DWORD = 0u,
    public var MxCsr: DWORD = 0u,
)

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

public typealias LCID = DWORD
public typealias PLCID = ULong
public typealias LANGID = WORD

public const val NLS_VALID_LOCALE_MASK: DWORD = 0x000FFFFFu

public fun MAKELANGID(p: WORD, s: WORD): LANGID =
    ((s.toUInt() shl 10) or p.toUInt()).toUShort()

public fun PRIMARYLANGID(lgid: LANGID): WORD =
    (lgid.toUInt() and 0x3FFu).toUShort()

public fun SUBLANGID(lgid: LANGID): WORD =
    (lgid.toUInt() shr 10).toUShort()

public fun MAKELCID(lgid: LANGID, srtid: WORD): LCID =
    (srtid.toUInt() shl 16) or lgid.toUInt()

public fun MAKESORTLCID(lgid: LANGID, srtid: WORD, ver: WORD): LCID =
    MAKELCID(lgid, srtid) or (ver.toUInt() shl 20)

public fun LANGIDFROMLCID(lcid: LCID): LANGID =
    lcid.toUShort()

public fun SORTIDFROMLCID(lcid: LCID): WORD =
    ((lcid shr 16) and 0xFu).toUShort()

public fun SORTVERSIONFROMLCID(lcid: LCID): WORD =
    ((lcid shr 16) and 0xFu).toUShort()

public const val EXCEPTION_NONCONTINUABLE: DWORD = 0x1u
public const val EXCEPTION_UNWINDING: DWORD = 0x2u
public const val EXCEPTION_EXIT_UNWIND: DWORD = 0x4u
public const val EXCEPTION_STACK_INVALID: DWORD = 0x8u
public const val EXCEPTION_NESTED_CALL: DWORD = 0x10u
public const val EXCEPTION_TARGET_UNWIND: DWORD = 0x20u
public const val EXCEPTION_COLLIDED_UNWIND: DWORD = 0x40u
public const val EXCEPTION_UNWIND: DWORD = 0x66u

public fun IS_UNWINDING(Flag: DWORD): Boolean =
    (Flag and EXCEPTION_UNWIND) != 0u

public fun IS_DISPATCHING(Flag: DWORD): Boolean =
    (Flag and EXCEPTION_UNWIND) == 0u

public fun IS_TARGET_UNWIND(Flag: DWORD): Boolean =
    (Flag and EXCEPTION_TARGET_UNWIND) != 0u

public const val SECURITY_MANDATORY_LOW_RID: DWORD = 0x00001000u
public const val SECURITY_MANDATORY_MEDIUM_RID: DWORD = 0x00002000u
public const val SECURITY_MANDATORY_MEDIUM_PLUS_RID: DWORD = 0x00002010u
public const val SECURITY_MANDATORY_HIGH_RID: DWORD = 0x00003000u
public const val SECURITY_MANDATORY_SYSTEM_RID: DWORD = 0x00004000u
public const val SECURITY_MANDATORY_MAXIMUM_USER_RID: DWORD = SECURITY_MANDATORY_SYSTEM_RID

public fun MANDATORY_LEVEL_TO_MANDATORY_RID(IL: DWORD): DWORD =
    IL * 0x1000u

public typealias SECURITY_IMPERSONATION_LEVEL = UInt

public const val SecurityAnonymous: SECURITY_IMPERSONATION_LEVEL = 0u
public const val SecurityIdentification: SECURITY_IMPERSONATION_LEVEL = 1u
public const val SecurityImpersonation: SECURITY_IMPERSONATION_LEVEL = 2u
public const val SecurityDelegation: SECURITY_IMPERSONATION_LEVEL = 3u

public typealias PSECURITY_IMPERSONATION_LEVEL = ULong

public const val SECURITY_MAX_IMPERSONATION_LEVEL: SECURITY_IMPERSONATION_LEVEL = SecurityDelegation
public const val SECURITY_MIN_IMPERSONATION_LEVEL: SECURITY_IMPERSONATION_LEVEL = SecurityAnonymous
public const val DEFAULT_IMPERSONATION_LEVEL: SECURITY_IMPERSONATION_LEVEL = SecurityImpersonation

public fun VALID_IMPERSONATION_LEVEL(L: SECURITY_IMPERSONATION_LEVEL): Boolean =
    (L >= SECURITY_MIN_IMPERSONATION_LEVEL) && (L <= SECURITY_MAX_IMPERSONATION_LEVEL)

public fun IsReparseTagMicrosoft(_tag: DWORD): Boolean =
    (_tag and 0x80000000u) != 0u

public fun IsReparseTagNameSurrogate(_tag: DWORD): Boolean =
    (_tag and 0x20000000u) != 0u

public fun IsReparseTagDirectory(_tag: DWORD): Boolean =
    (_tag and 0x10000000u) != 0u

public typealias SharedVirtualDiskHandleState = UInt

public const val SharedVirtualDiskHandleStateNone: SharedVirtualDiskHandleState = 0u
public const val SharedVirtualDiskHandleStateFileShared: SharedVirtualDiskHandleState = 1u
public const val SharedVirtualDiskHandleStateHandleShared: SharedVirtualDiskHandleState = 3u

public fun IsVirtualDiskFileShared(HandleState: SharedVirtualDiskHandleState): Boolean =
    (HandleState and SharedVirtualDiskHandleStateFileShared) != 0u

public const val IMAGE_SYM_CLASS_STRUCT_TAG: BYTE = 0x000Au
public const val IMAGE_SYM_CLASS_UNION_TAG: BYTE = 0x000Cu
public const val IMAGE_SYM_CLASS_ENUM_TAG: BYTE = 0x000Fu
public const val IMAGE_SYM_DTYPE_POINTER: WORD = 1u
public const val IMAGE_SYM_DTYPE_FUNCTION: WORD = 2u
public const val IMAGE_SYM_DTYPE_ARRAY: WORD = 3u
public const val N_BTMASK: WORD = 0x000Fu
public const val N_TMASK: WORD = 0x0030u
public const val N_BTSHFT: Int = 4
public const val N_TSHIFT: Int = 2

public fun BTYPE(x: WORD): Boolean =
    (x.toUInt() and N_BTMASK.toUInt()) != 0u

public fun ISPTR(x: WORD): Boolean =
    (x.toUInt() and N_TMASK.toUInt()) == (IMAGE_SYM_DTYPE_POINTER.toUInt() shl N_BTSHFT)

public fun ISFCN(x: WORD): Boolean =
    (x.toUInt() and N_TMASK.toUInt()) == (IMAGE_SYM_DTYPE_FUNCTION.toUInt() shl N_BTSHFT)

public fun ISARY(x: WORD): Boolean =
    (x.toUInt() and N_TMASK.toUInt()) == (IMAGE_SYM_DTYPE_ARRAY.toUInt() shl N_BTSHFT)

public fun ISTAG(x: BYTE): Boolean =
    (x == IMAGE_SYM_CLASS_STRUCT_TAG) || (x == IMAGE_SYM_CLASS_UNION_TAG) || (x == IMAGE_SYM_CLASS_ENUM_TAG)

public fun INCREF(x: WORD): WORD =
    (((x.toUInt() and N_BTMASK.toUInt().inv()) shl N_TSHIFT) or (IMAGE_SYM_DTYPE_POINTER.toUInt() shl N_BTSHFT) or (x.toUInt() and N_BTMASK.toUInt())).toUShort()

public fun DECREF(x: WORD): WORD =
    (((x.toUInt() shr N_TSHIFT) and N_BTMASK.toUInt().inv()) or (x.toUInt() and N_BTMASK.toUInt())).toUShort()

public const val IMAGE_ORDINAL_FLAG64: ULONGLONG = 0x8000000000000000uL
public const val IMAGE_ORDINAL_FLAG32: DWORD = 0x80000000u

public fun IMAGE_ORDINAL64(Ordinal: ULONGLONG): ULONGLONG =
    Ordinal and 0xFFFFuL

public fun IMAGE_ORDINAL32(Ordinal: DWORD): DWORD =
    Ordinal and 0xFFFFu

public fun IMAGE_SNAP_BY_ORDINAL64(Ordinal: ULONGLONG): Boolean =
    (Ordinal and IMAGE_ORDINAL_FLAG64) != 0uL

public fun IMAGE_SNAP_BY_ORDINAL32(Ordinal: DWORD): Boolean =
    (Ordinal and IMAGE_ORDINAL_FLAG32) != 0u

public fun IMAGE_ORDINAL(Ordinal: ULONGLONG): ULONGLONG =
    IMAGE_ORDINAL64(Ordinal)

public fun IMAGE_SNAP_BY_ORDINAL(Ordinal: ULONGLONG): Boolean =
    IMAGE_SNAP_BY_ORDINAL64(Ordinal)

public fun IMAGE_ORDINAL(Ordinal: DWORD): DWORD =
    IMAGE_ORDINAL32(Ordinal)

public fun IMAGE_SNAP_BY_ORDINAL(Ordinal: DWORD): Boolean =
    IMAGE_SNAP_BY_ORDINAL32(Ordinal)

public const val HEAP_TAG_SHIFT: Int = 18

public fun HEAP_MAKE_TAG_FLAGS(TagBase: DWORD, Tag: DWORD): DWORD =
    TagBase + (Tag shl HEAP_TAG_SHIFT)

public fun RtlMoveMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
}

public fun RtlCopyMemory(Destination: PVOID, Source: PVOID, Length: SIZE_T) {
}

public fun RtlFillMemory(Destination: PVOID, Length: SIZE_T, Fill: BYTE) {
}

public fun RtlZeroMemory(Destination: PVOID, Length: SIZE_T) {
}

public fun IS_VALIDATION_ENABLED(C: DWORD, L: DWORD): Boolean =
    (L and C) != 0u

public fun WT_SET_MAX_THREADPOOL_THREADS(Flags: ULONG, Limit: ULONG): ULONG =
    Flags or (Limit shl 16)
