// port-lint: source shared/ntdef.rs
package io.github.kotlinmania.winapi.shared.ntdef

import io.github.kotlinmania.winapi.CChar
import io.github.kotlinmania.winapi.CDouble
import io.github.kotlinmania.winapi.CInt
import io.github.kotlinmania.winapi.CLong
import io.github.kotlinmania.winapi.CSchar
import io.github.kotlinmania.winapi.CShort
import io.github.kotlinmania.winapi.CUchar
import io.github.kotlinmania.winapi.CUlong
import io.github.kotlinmania.winapi.CUshort
import io.github.kotlinmania.winapi.Int64
import io.github.kotlinmania.winapi.Uint64
import io.github.kotlinmania.winapi.WcharT
import io.github.kotlinmania.winapi.shared.basetsd.KAFFINITY
import io.github.kotlinmania.winapi.shared.basetsd.LONG_PTR
import io.github.kotlinmania.winapi.shared.basetsd.ULONG_PTR
import io.github.kotlinmania.winapi.shared.guiddef.GUID

// Type definitions for the basic types.
//
// Rust `target_arch` cfg branches collapse here to the values that match
// the only Windows native target in the matrix (x86_64 / mingwX64): the
// other branches are documented but not emitted. Pointer types
// (`*mut T`, `*const T`, `*mut *mut T`, etc.) collapse onto ULong since
// the pure-Kotlin port treats pointers as opaque address tokens; the
// pointed-to data is reached through the wrapper struct values rather
// than by dereference.
//
// LARGE_INTEGER / ULARGE_INTEGER upstream are tagged unions overlaying
// an i64/u64 with a `{ LowPart, HighPart }` struct view. The Kotlin port
// keeps them as classes that hold the 64-bit value as the authoritative
// storage; the low/high struct view is exposed as computed properties so
// the union semantics are preserved without raw memory reinterpretation.

// x86_64 branch of #[cfg(target_arch = "x86_64")] (the non-x86_64
// branch's MAX_NATURAL_ALIGNMENT = 4 / MEMORY_ALLOCATION_ALIGNMENT = 8
// is documented here in prose because the workspace native target is
// mingwX64).
const val MAX_NATURAL_ALIGNMENT: ULong = 8u
const val MEMORY_ALLOCATION_ALIGNMENT: ULong = 16u

// x86_64 / x86 branch of SYSTEM_CACHE_ALIGNMENT_SIZE (the non-x86 branch
// uses 128).
const val SYSTEM_CACHE_ALIGNMENT_SIZE: ULong = 64u

typealias PVOID = ULong
typealias PVOID64 = ULong // 64-bit pointer, even when in 32-bit
// VOID in C maps to Unit-equivalent; in Kotlin there is no "VOID" type
// referenced from a typealias chain, so VOID is omitted: the Kotlin port
// uses Unit at function return positions where Rust would use
// `*mut c_void` only when explicitly dereferenced.
typealias CHAR = CChar
typealias SHORT = CShort
typealias LONG = CLong
typealias INT = CInt
typealias WCHAR = WcharT
typealias PWCHAR = ULong
typealias LPWCH = ULong
typealias PWCH = ULong
typealias LPCWCH = ULong
typealias PCWCH = ULong
typealias NWPSTR = ULong
typealias LPWSTR = ULong
typealias LPSTR = ULong
typealias LPTSTR = LPSTR
typealias PWSTR = ULong
typealias PZPWSTR = ULong
typealias PCZPWSTR = ULong
typealias LPUWSTR = ULong // Unaligned pointer
typealias PUWSTR = ULong // Unaligned pointer
typealias LPCWSTR = ULong
typealias PCWSTR = ULong
typealias PZPCWSTR = ULong
typealias PCZPCWSTR = ULong
typealias LPCUWSTR = ULong // Unaligned pointer
typealias PCUWSTR = ULong // Unaligned pointer
typealias PZZWSTR = ULong
typealias PCZZWSTR = ULong
typealias PUZZWSTR = ULong // Unaligned pointer
typealias PCUZZWSTR = ULong // Unaligned pointer
typealias PNZWCH = ULong
typealias PCNZWCH = ULong
typealias PUNZWCH = ULong // Unaligned pointer
typealias PCUNZWCH = ULong // Unaligned pointer
typealias LPCWCHAR = ULong
typealias PCWCHAR = ULong
typealias LPCUWCHAR = ULong // Unaligned pointer
typealias PCUWCHAR = ULong // Unaligned pointer
typealias UCSCHAR = CUlong
const val UCSCHAR_INVALID_CHARACTER: UCSCHAR = 0xffffffffu
const val MIN_UCSCHAR: UCSCHAR = 0u
const val MAX_UCSCHAR: UCSCHAR = 0x0010FFFFu
typealias PUCSCHAR = ULong
typealias PCUCSCHAR = ULong
typealias PUCSSTR = ULong
typealias PUUCSSTR = ULong // Unaligned pointer
typealias PCUCSSTR = ULong
typealias PCUUCSSTR = ULong // Unaligned pointer
typealias PUUCSCHAR = ULong // Unaligned pointer
typealias PCUUCSCHAR = ULong // Unaligned pointer
typealias PCHAR = ULong
typealias LPCH = ULong
typealias PCH = ULong
typealias LPCCH = ULong
typealias PCCH = ULong
typealias NPSTR = ULong
typealias PSTR = ULong
typealias PZPSTR = ULong
typealias PCZPSTR = ULong
typealias LPCSTR = ULong
typealias PCSTR = ULong
typealias PZPCSTR = ULong
typealias PCZPCSTR = ULong
typealias PZZSTR = ULong
typealias PCZZSTR = ULong
typealias PNZCH = ULong
typealias PCNZCH = ULong
// Skipping TCHAR things
typealias DOUBLE = CDouble

data class QUAD(val UseThisFieldToCopy: Int64)

typealias PSHORT = ULong
typealias PLONG = ULong
typealias PQUAD = ULong
typealias UCHAR = CUchar
typealias USHORT = CUshort
typealias ULONG = CUlong
typealias UQUAD = QUAD
typealias PUCHAR = ULong
typealias PUSHORT = ULong
typealias PULONG = ULong
typealias PUQUAD = ULong
typealias PCUCHAR = ULong
typealias PCUSHORT = ULong
typealias PCULONG = ULong
typealias PCUQUAD = ULong
typealias SCHAR = CSchar
typealias PSCHAR = ULong
typealias PCSCHAR = ULong

const val ALL_PROCESSOR_GROUPS: USHORT = 0xffffu

data class PROCESSOR_NUMBER(
    val Group: USHORT,
    val Number: UCHAR,
    val Reserved: UCHAR,
)

typealias PPROCESSOR_NUMBER = ULong

data class GROUP_AFFINITY(
    val Mask: KAFFINITY,
    val Group: USHORT,
    val Reserved: UShortArray,
) {
    init {
        require(Reserved.size == 3) { "GROUP_AFFINITY.Reserved must be 3 entries" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GROUP_AFFINITY) return false
        return Mask == other.Mask
            && Group == other.Group
            && Reserved.contentEquals(other.Reserved)
    }

    override fun hashCode(): Int {
        var h = Mask.hashCode()
        h = 31 * h + Group.hashCode()
        h = 31 * h + Reserved.contentHashCode()
        return h
    }
}

typealias PGROUP_AFFINITY = ULong

// x86_64 branch of MAXIMUM_PROC_PER_GROUP (non-x86_64 = 32).
const val MAXIMUM_PROC_PER_GROUP: UCHAR = 64u
const val MAXIMUM_PROCESSORS: UCHAR = MAXIMUM_PROC_PER_GROUP

typealias HANDLE = ULong
typealias PHANDLE = ULong
typealias FCHAR = UCHAR
typealias FSHORT = USHORT
typealias FLONG = ULONG
typealias HRESULT = CLong
const val OBJ_HANDLE_TAGBITS: ULong = 0x00000003u
typealias CCHAR = CChar
typealias CSHORT = CShort
typealias CLONG = ULONG
typealias PCCHAR = ULong
typealias PCSHORT = ULong
typealias PCLONG = ULong
typealias LCID = ULONG
typealias PLCID = PULONG
typealias LANGID = USHORT

enum class COMPARTMENT_ID(val raw: ULong) {
    UNSPECIFIED_COMPARTMENT_ID(0u),
    DEFAULT_COMPARTMENT_ID(1u),
}

typealias PCOMPARTMENT_ID = ULong
typealias LOGICAL = ULONG
typealias PLOGICAL = ULong
typealias NTSTATUS = LONG
typealias PNTSTATUS = ULong
typealias PCNTSTATUS = ULong

fun NT_SUCCESS(Status: NTSTATUS): Boolean = Status >= 0
fun NT_INFORMATION(Status: NTSTATUS): Boolean = (Status.toUInt() shr 30) == 1u
fun NT_WARNING(Status: NTSTATUS): Boolean = (Status.toUInt() shr 30) == 2u
fun NT_ERROR(Status: NTSTATUS): Boolean = (Status.toUInt() shr 30) == 3u

const val APPLICATION_ERROR_MASK: ULONG = 0x20000000u
const val ERROR_SEVERITY_SUCCESS: ULONG = 0x00000000u
const val ERROR_SEVERITY_INFORMATIONAL: ULONG = 0x40000000u
const val ERROR_SEVERITY_WARNING: ULONG = 0x80000000u
const val ERROR_SEVERITY_ERROR: ULONG = 0xC0000000u
typealias SECURITY_STATUS = CLong
typealias TIME = LARGE_INTEGER
typealias PTIME = ULong

data class FLOAT128(val LowPart: Int64, val HighPart: Int64)

typealias PFLOAT128 = ULong
typealias LONGLONG = Int64
typealias ULONGLONG = Uint64
const val MAXLONGLONG: LONGLONG = 0x7fffffffffffffffL
typealias PLONGLONG = ULong
typealias PULONGLONG = ULong
typealias USN = LONGLONG

/**
 * Tagged-union LARGE_INTEGER: 64-bit signed integer with a low/high
 * 32-bit view. Upstream Rust uses UNION! to overlap the i64 view with
 * a `{ LowPart: ULONG, HighPart: LONG }` struct; the Kotlin port keeps
 * the 64-bit value as authoritative storage and exposes the struct view
 * through computed accessors that bit-slice it.
 */
class LARGE_INTEGER(var QuadPart: LONGLONG) {
    var LowPart: ULONG
        get() = (QuadPart and 0xFFFFFFFFL).toUInt()
        set(value) {
            QuadPart = (QuadPart and 0xFFFFFFFFL.inv()) or value.toLong()
        }
    var HighPart: LONG
        get() = (QuadPart shr 32).toInt()
        set(value) {
            QuadPart = (QuadPart and 0xFFFFFFFFL) or (value.toLong() shl 32)
        }

    override fun equals(other: Any?): Boolean = other is LARGE_INTEGER && QuadPart == other.QuadPart
    override fun hashCode(): Int = QuadPart.hashCode()
    override fun toString(): String = "LARGE_INTEGER(QuadPart=$QuadPart)"
}

typealias PLARGE_INTEGER = ULong

/**
 * Unsigned tagged-union ULARGE_INTEGER: 64-bit unsigned integer with a
 * pair of 32-bit low/high views. See [LARGE_INTEGER] for the rationale
 * on the storage model.
 */
class ULARGE_INTEGER(var QuadPart: ULONGLONG) {
    var LowPart: ULONG
        get() = (QuadPart and 0xFFFFFFFFu).toUInt()
        set(value) {
            QuadPart = (QuadPart and 0xFFFFFFFFu.toULong().inv()) or value.toULong()
        }
    var HighPart: ULONG
        get() = (QuadPart shr 32).toUInt()
        set(value) {
            QuadPart = (QuadPart and 0xFFFFFFFFu) or (value.toULong() shl 32)
        }

    override fun equals(other: Any?): Boolean = other is ULARGE_INTEGER && QuadPart == other.QuadPart
    override fun hashCode(): Int = QuadPart.hashCode()
    override fun toString(): String = "ULARGE_INTEGER(QuadPart=$QuadPart)"
}

typealias PULARGE_INTEGER = ULong
typealias RTL_REFERENCE_COUNT = LONG_PTR
typealias PRTL_REFERENCE_COUNT = ULong

data class LUID(val LowPart: ULONG, val HighPart: LONG)

typealias PLUID = ULong
typealias DWORDLONG = ULONGLONG
typealias PDWORDLONG = ULong
typealias PHYSICAL_ADDRESS = LARGE_INTEGER
typealias PPHYSICAL_ADDRESS = ULong

enum class EVENT_TYPE(val raw: ULong) {
    NotificationEvent(0u),
    SynchronizationEvent(1u),
}

enum class TIMER_TYPE(val raw: ULong) {
    NotificationTimer(0u),
    SynchronizationTimer(1u),
}

enum class WAIT_TYPE(val raw: ULong) {
    WaitAll(0u),
    WaitAny(1u),
    WaitNotification(2u),
    WaitDequeue(3u),
}

typealias PSZ = ULong
typealias PCSZ = ULong
typealias RTL_STRING_LENGTH_TYPE = USHORT

data class STRING(
    val Length: USHORT,
    val MaximumLength: USHORT,
    val Buffer: PCHAR,
)

typealias PSTRING = ULong
typealias ANSI_STRING = STRING
typealias PANSI_STRING = PSTRING
typealias OEM_STRING = STRING
typealias POEM_STRING = PSTRING
typealias PCOEM_STRING = ULong

data class CSTRING(
    val Length: USHORT,
    val MaximumLength: USHORT,
    val Buffer: ULong,
)

typealias PCSTRING = ULong
const val ANSI_NULL: CHAR = 0
typealias CANSI_STRING = STRING
typealias PCANSI_STRING = PSTRING

data class UNICODE_STRING(
    val Length: USHORT,
    val MaximumLength: USHORT,
    val Buffer: PWCH,
)

typealias PUNICODE_STRING = ULong
typealias PCUNICODE_STRING = ULong
const val UNICODE_NULL: WCHAR = 0u
const val UNICODE_STRING_MAX_BYTES: USHORT = 65534u
const val UNICODE_STRING_MAX_CHARS: ULong = 32767u
typealias BOOLEAN = UCHAR
typealias PBOOLEAN = ULong

data class LIST_ENTRY(
    val Flink: ULong, // *mut LIST_ENTRY
    val Blink: ULong, // *mut LIST_ENTRY
)

typealias PLIST_ENTRY = ULong
typealias PRLIST_ENTRY = ULong // Restricted pointer

data class SINGLE_LIST_ENTRY(val Next: ULong) // *mut SINGLE_LIST_ENTRY

typealias PSINGLE_LIST_ENTRY = ULong

/**
 * Red-black tree node. Upstream Rust overlays a `{ Children: [*mut node; 2] }`
 * view with a `{ Left, Right }` view via UNION!. The Kotlin port models
 * children as a two-element ULong array (the authoritative storage) and
 * exposes Left/Right as indexed accessors.
 */
class RTL_BALANCED_NODE(
    val Children: ULongArray = ULongArray(2),
    var ParentValue: ULONG_PTR = 0u,
) {
    var Left: ULong
        get() = Children[0]
        set(value) { Children[0] = value }
    var Right: ULong
        get() = Children[1]
        set(value) { Children[1] = value }
}

const val RTL_BALANCED_NODE_RESERVED_PARENT_MASK: ULONG_PTR = 3u
typealias PRTL_BALANCED_NODE = ULong

// Upstream `RTL_BALANCED_NODE_GET_PARENT_POINTER` dereferences a raw
// pointer and masks the low bits off the encoded parent address. The
// pure-Kotlin port reproduces the mask on the encoded value directly;
// callers pass the ParentValue field they already read.
fun RTL_BALANCED_NODE_GET_PARENT_POINTER(parentValue: ULONG_PTR): PRTL_BALANCED_NODE =
    parentValue and RTL_BALANCED_NODE_RESERVED_PARENT_MASK.inv()

data class LIST_ENTRY32(val Flink: ULONG, val Blink: ULONG)
typealias PLIST_ENTRY32 = ULong

data class LIST_ENTRY64(val Flink: ULONGLONG, val Blink: ULONGLONG)
typealias PLIST_ENTRY64 = ULong

data class SINGLE_LIST_ENTRY32(val Next: ULONG)
typealias PSINGLE_LIST_ENTRY32 = ULong

// Upstream takes raw pointers; the pure-Kotlin port copies field-by-field
// between mutable wrappers.
fun ListEntry32To64(l32: LIST_ENTRY32, l64: LIST_ENTRY64): LIST_ENTRY64 =
    LIST_ENTRY64(Flink = l32.Flink.toULong(), Blink = l32.Blink.toULong())

fun ListEntry64To32(l64: LIST_ENTRY64): LIST_ENTRY32 =
    LIST_ENTRY32(Flink = l64.Flink.toUInt(), Blink = l64.Blink.toUInt())

class WNF_STATE_NAME(val Data: UIntArray = UIntArray(2)) {
    init {
        require(Data.size == 2) { "WNF_STATE_NAME.Data must be 2 entries" }
    }
}

typealias PWNF_STATE_NAME = ULong
typealias PCWNF_STATE_NAME = ULong

data class STRING32(val Length: USHORT, val MaximumLength: USHORT, val Buffer: ULONG)
typealias PSTRING32 = ULong
typealias UNICODE_STRING32 = STRING32
typealias PUNICODE_STRING32 = ULong
typealias ANSI_STRING32 = STRING32
typealias PANSI_STRING32 = ULong

data class STRING64(val Length: USHORT, val MaximumLength: USHORT, val Buffer: ULONGLONG)
typealias PSTRING64 = ULong
typealias UNICODE_STRING64 = STRING64
typealias PUNICODE_STRING64 = ULong
typealias ANSI_STRING64 = STRING64
typealias PANSI_STRING64 = ULong

const val OBJ_INHERIT: ULONG = 0x00000002u
const val OBJ_PERMANENT: ULONG = 0x00000010u
const val OBJ_EXCLUSIVE: ULONG = 0x00000020u
const val OBJ_CASE_INSENSITIVE: ULONG = 0x00000040u
const val OBJ_OPENIF: ULONG = 0x00000080u
const val OBJ_OPENLINK: ULONG = 0x00000100u
const val OBJ_KERNEL_HANDLE: ULONG = 0x00000200u
const val OBJ_FORCE_ACCESS_CHECK: ULONG = 0x00000400u
const val OBJ_IGNORE_IMPERSONATED_DEVICEMAP: ULONG = 0x00000800u
const val OBJ_DONT_REPARSE: ULONG = 0x00001000u
const val OBJ_VALID_ATTRIBUTES: ULONG = 0x00001FF2u

data class OBJECT_ATTRIBUTES64(
    val Length: ULONG,
    val RootDirectory: ULong, // ULONG64
    val ObjectName: ULong, // ULONG64
    val Attributes: ULONG,
    val SecurityDescriptor: ULong, // ULONG64
    val SecurityQualityOfService: ULong, // ULONG64
)

typealias POBJECT_ATTRIBUTES64 = ULong
typealias PCOBJECT_ATTRIBUTES64 = ULong

data class OBJECT_ATTRIBUTES32(
    val Length: ULONG,
    val RootDirectory: ULONG,
    val ObjectName: ULONG,
    val Attributes: ULONG,
    val SecurityDescriptor: ULONG,
    val SecurityQualityOfService: ULONG,
)

typealias POBJECT_ATTRIBUTES32 = ULong
typealias PCOBJECT_ATTRIBUTES32 = ULong

data class OBJECT_ATTRIBUTES(
    val Length: ULONG,
    val RootDirectory: HANDLE,
    val ObjectName: PUNICODE_STRING,
    val Attributes: ULONG,
    val SecurityDescriptor: PVOID,
    val SecurityQualityOfService: PVOID,
)

typealias POBJECT_ATTRIBUTES = ULong
typealias PCOBJECT_ATTRIBUTES = ULong

/**
 * Construct an OBJECT_ATTRIBUTES with the canonical Length and the supplied
 * fields. Upstream this is an in-place initializer writing through a
 * POBJECT_ATTRIBUTES; the pure-Kotlin port returns the struct value since
 * the port models pointers as opaque tokens that can't be dereferenced.
 */
fun InitializeObjectAttributes(
    n: PUNICODE_STRING,
    a: ULONG,
    r: HANDLE,
    s: PVOID,
): OBJECT_ATTRIBUTES = OBJECT_ATTRIBUTES(
    // sizeof(OBJECT_ATTRIBUTES) on the 64-bit ABI: 6 fields, all 8 bytes wide
    // after the Length / Attributes 32-bit fields pad to 8 → 48 bytes total.
    Length = 48u,
    RootDirectory = r,
    ObjectName = n,
    Attributes = a,
    SecurityDescriptor = s,
    SecurityQualityOfService = NULL,
)

const val FALSE: BOOLEAN = 0u
const val TRUE: BOOLEAN = 1u
const val NULL: PVOID = 0u
const val NULL64: PVOID64 = 0u

data class OBJECTID(val Lineage: GUID, val Uniquifier: ULONG)

// CHAR is a signed byte; the byte literals encode the high-bit MIN/MAX
// pair that C/Rust spell as 0x80 (-128) and 0x7f (127). Kotlin Byte
// literals don't accept hex past 0x7F, so cast explicitly.
const val MINCHAR: CHAR = (-128).toByte()
const val MAXCHAR: CHAR = 0x7f
const val MINSHORT: SHORT = (-0x8000).toShort()
const val MAXSHORT: SHORT = 0x7fff
const val MINLONG: LONG = -0x80000000
const val MAXLONG: LONG = 0x7fffffff
const val MAXUCHAR: UCHAR = 0xffu
const val MAXUSHORT: USHORT = 0xffffu
const val MAXULONG: ULONG = 0xffffffffu
// PEXCEPTION_ROUTINE: Can't define here, because it needs EXCEPTION_RECORD and CONTEXT.
typealias KIRQL = UCHAR
typealias PKIRQL = ULong

enum class NT_PRODUCT_TYPE(val raw: ULong) {
    NtProductWinNt(1u),
    NtProductLanManNt(2u),
    NtProductServer(3u),
}

typealias PNT_PRODUCT_TYPE = ULong

enum class SUITE_TYPE(val raw: ULong) {
    SmallBusiness(0u),
    Enterprise(1u),
    BackOffice(2u),
    CommunicationServer(3u),
    TerminalServer(4u),
    SmallBusinessRestricted(5u),
    EmbeddedNT(6u),
    DataCenter(7u),
    SingleUserTS(8u),
    Personal(9u),
    Blade(10u),
    EmbeddedRestricted(11u),
    SecurityAppliance(12u),
    StorageServer(13u),
    ComputeServer(14u),
    WHServer(15u),
    PhoneNT(16u),
    MaxSuiteType(17u),
}

const val VER_SERVER_NT: ULONG = 0x80000000u
const val VER_WORKSTATION_NT: ULONG = 0x40000000u
const val VER_SUITE_SMALLBUSINESS: ULONG = 0x00000001u
const val VER_SUITE_ENTERPRISE: ULONG = 0x00000002u
const val VER_SUITE_BACKOFFICE: ULONG = 0x00000004u
const val VER_SUITE_COMMUNICATIONS: ULONG = 0x00000008u
const val VER_SUITE_TERMINAL: ULONG = 0x00000010u
const val VER_SUITE_SMALLBUSINESS_RESTRICTED: ULONG = 0x00000020u
const val VER_SUITE_EMBEDDEDNT: ULONG = 0x00000040u
const val VER_SUITE_DATACENTER: ULONG = 0x00000080u
const val VER_SUITE_SINGLEUSERTS: ULONG = 0x00000100u
const val VER_SUITE_PERSONAL: ULONG = 0x00000200u
const val VER_SUITE_BLADE: ULONG = 0x00000400u
const val VER_SUITE_EMBEDDED_RESTRICTED: ULONG = 0x00000800u
const val VER_SUITE_SECURITY_APPLIANCE: ULONG = 0x00001000u
const val VER_SUITE_STORAGE_SERVER: ULONG = 0x00002000u
const val VER_SUITE_COMPUTE_SERVER: ULONG = 0x00004000u
const val VER_SUITE_WH_SERVER: ULONG = 0x00008000u

const val PRODUCT_UNDEFINED: ULONG = 0x00000000u
const val PRODUCT_ULTIMATE: ULONG = 0x00000001u
const val PRODUCT_HOME_BASIC: ULONG = 0x00000002u
const val PRODUCT_HOME_PREMIUM: ULONG = 0x00000003u
const val PRODUCT_ENTERPRISE: ULONG = 0x00000004u
const val PRODUCT_HOME_BASIC_N: ULONG = 0x00000005u
const val PRODUCT_BUSINESS: ULONG = 0x00000006u
const val PRODUCT_STANDARD_SERVER: ULONG = 0x00000007u
const val PRODUCT_DATACENTER_SERVER: ULONG = 0x00000008u
const val PRODUCT_SMALLBUSINESS_SERVER: ULONG = 0x00000009u
const val PRODUCT_ENTERPRISE_SERVER: ULONG = 0x0000000Au
const val PRODUCT_STARTER: ULONG = 0x0000000Bu
const val PRODUCT_DATACENTER_SERVER_CORE: ULONG = 0x0000000Cu
const val PRODUCT_STANDARD_SERVER_CORE: ULONG = 0x0000000Du
const val PRODUCT_ENTERPRISE_SERVER_CORE: ULONG = 0x0000000Eu
const val PRODUCT_ENTERPRISE_SERVER_IA64: ULONG = 0x0000000Fu
const val PRODUCT_BUSINESS_N: ULONG = 0x00000010u
const val PRODUCT_WEB_SERVER: ULONG = 0x00000011u
const val PRODUCT_CLUSTER_SERVER: ULONG = 0x00000012u
const val PRODUCT_HOME_SERVER: ULONG = 0x00000013u
const val PRODUCT_STORAGE_EXPRESS_SERVER: ULONG = 0x00000014u
const val PRODUCT_STORAGE_STANDARD_SERVER: ULONG = 0x00000015u
const val PRODUCT_STORAGE_WORKGROUP_SERVER: ULONG = 0x00000016u
const val PRODUCT_STORAGE_ENTERPRISE_SERVER: ULONG = 0x00000017u
const val PRODUCT_SERVER_FOR_SMALLBUSINESS: ULONG = 0x00000018u
const val PRODUCT_SMALLBUSINESS_SERVER_PREMIUM: ULONG = 0x00000019u
const val PRODUCT_HOME_PREMIUM_N: ULONG = 0x0000001Au
const val PRODUCT_ENTERPRISE_N: ULONG = 0x0000001Bu
const val PRODUCT_ULTIMATE_N: ULONG = 0x0000001Cu
const val PRODUCT_WEB_SERVER_CORE: ULONG = 0x0000001Du
const val PRODUCT_MEDIUMBUSINESS_SERVER_MANAGEMENT: ULONG = 0x0000001Eu
const val PRODUCT_MEDIUMBUSINESS_SERVER_SECURITY: ULONG = 0x0000001Fu
const val PRODUCT_MEDIUMBUSINESS_SERVER_MESSAGING: ULONG = 0x00000020u
const val PRODUCT_SERVER_FOUNDATION: ULONG = 0x00000021u
const val PRODUCT_HOME_PREMIUM_SERVER: ULONG = 0x00000022u
const val PRODUCT_SERVER_FOR_SMALLBUSINESS_V: ULONG = 0x00000023u
const val PRODUCT_STANDARD_SERVER_V: ULONG = 0x00000024u
const val PRODUCT_DATACENTER_SERVER_V: ULONG = 0x00000025u
const val PRODUCT_ENTERPRISE_SERVER_V: ULONG = 0x00000026u
const val PRODUCT_DATACENTER_SERVER_CORE_V: ULONG = 0x00000027u
const val PRODUCT_STANDARD_SERVER_CORE_V: ULONG = 0x00000028u
const val PRODUCT_ENTERPRISE_SERVER_CORE_V: ULONG = 0x00000029u
const val PRODUCT_HYPERV: ULONG = 0x0000002Au
const val PRODUCT_STORAGE_EXPRESS_SERVER_CORE: ULONG = 0x0000002Bu
const val PRODUCT_STORAGE_STANDARD_SERVER_CORE: ULONG = 0x0000002Cu
const val PRODUCT_STORAGE_WORKGROUP_SERVER_CORE: ULONG = 0x0000002Du
const val PRODUCT_STORAGE_ENTERPRISE_SERVER_CORE: ULONG = 0x0000002Eu
const val PRODUCT_STARTER_N: ULONG = 0x0000002Fu
const val PRODUCT_PROFESSIONAL: ULONG = 0x00000030u
const val PRODUCT_PROFESSIONAL_N: ULONG = 0x00000031u
const val PRODUCT_SB_SOLUTION_SERVER: ULONG = 0x00000032u
const val PRODUCT_SERVER_FOR_SB_SOLUTIONS: ULONG = 0x00000033u
const val PRODUCT_STANDARD_SERVER_SOLUTIONS: ULONG = 0x00000034u
const val PRODUCT_STANDARD_SERVER_SOLUTIONS_CORE: ULONG = 0x00000035u
const val PRODUCT_SB_SOLUTION_SERVER_EM: ULONG = 0x00000036u
const val PRODUCT_SERVER_FOR_SB_SOLUTIONS_EM: ULONG = 0x00000037u
const val PRODUCT_SOLUTION_EMBEDDEDSERVER: ULONG = 0x00000038u
const val PRODUCT_SOLUTION_EMBEDDEDSERVER_CORE: ULONG = 0x00000039u
const val PRODUCT_PROFESSIONAL_EMBEDDED: ULONG = 0x0000003Au
const val PRODUCT_ESSENTIALBUSINESS_SERVER_MGMT: ULONG = 0x0000003Bu
const val PRODUCT_ESSENTIALBUSINESS_SERVER_ADDL: ULONG = 0x0000003Cu
const val PRODUCT_ESSENTIALBUSINESS_SERVER_MGMTSVC: ULONG = 0x0000003Du
const val PRODUCT_ESSENTIALBUSINESS_SERVER_ADDLSVC: ULONG = 0x0000003Eu
const val PRODUCT_SMALLBUSINESS_SERVER_PREMIUM_CORE: ULONG = 0x0000003Fu
const val PRODUCT_CLUSTER_SERVER_V: ULONG = 0x00000040u
const val PRODUCT_EMBEDDED: ULONG = 0x00000041u
const val PRODUCT_STARTER_E: ULONG = 0x00000042u
const val PRODUCT_HOME_BASIC_E: ULONG = 0x00000043u
const val PRODUCT_HOME_PREMIUM_E: ULONG = 0x00000044u
const val PRODUCT_PROFESSIONAL_E: ULONG = 0x00000045u
const val PRODUCT_ENTERPRISE_E: ULONG = 0x00000046u
const val PRODUCT_ULTIMATE_E: ULONG = 0x00000047u
const val PRODUCT_ENTERPRISE_EVALUATION: ULONG = 0x00000048u
const val PRODUCT_MULTIPOINT_STANDARD_SERVER: ULONG = 0x0000004Cu
const val PRODUCT_MULTIPOINT_PREMIUM_SERVER: ULONG = 0x0000004Du
const val PRODUCT_STANDARD_EVALUATION_SERVER: ULONG = 0x0000004Fu
const val PRODUCT_DATACENTER_EVALUATION_SERVER: ULONG = 0x00000050u
const val PRODUCT_ENTERPRISE_N_EVALUATION: ULONG = 0x00000054u
const val PRODUCT_EMBEDDED_AUTOMOTIVE: ULONG = 0x00000055u
const val PRODUCT_EMBEDDED_INDUSTRY_A: ULONG = 0x00000056u
const val PRODUCT_THINPC: ULONG = 0x00000057u
const val PRODUCT_EMBEDDED_A: ULONG = 0x00000058u
const val PRODUCT_EMBEDDED_INDUSTRY: ULONG = 0x00000059u
const val PRODUCT_EMBEDDED_E: ULONG = 0x0000005Au
const val PRODUCT_EMBEDDED_INDUSTRY_E: ULONG = 0x0000005Bu
const val PRODUCT_EMBEDDED_INDUSTRY_A_E: ULONG = 0x0000005Cu
const val PRODUCT_STORAGE_WORKGROUP_EVALUATION_SERVER: ULONG = 0x0000005Fu
const val PRODUCT_STORAGE_STANDARD_EVALUATION_SERVER: ULONG = 0x00000060u
const val PRODUCT_CORE_ARM: ULONG = 0x00000061u
const val PRODUCT_CORE_N: ULONG = 0x00000062u
const val PRODUCT_CORE_COUNTRYSPECIFIC: ULONG = 0x00000063u
const val PRODUCT_CORE_SINGLELANGUAGE: ULONG = 0x00000064u
const val PRODUCT_CORE: ULONG = 0x00000065u
const val PRODUCT_PROFESSIONAL_WMC: ULONG = 0x00000067u
const val PRODUCT_MOBILE_CORE: ULONG = 0x00000068u
const val PRODUCT_EMBEDDED_INDUSTRY_EVAL: ULONG = 0x00000069u
const val PRODUCT_EMBEDDED_INDUSTRY_E_EVAL: ULONG = 0x0000006Au
const val PRODUCT_EMBEDDED_EVAL: ULONG = 0x0000006Bu
const val PRODUCT_EMBEDDED_E_EVAL: ULONG = 0x0000006Cu
const val PRODUCT_NANO_SERVER: ULONG = 0x0000006Du
const val PRODUCT_CLOUD_STORAGE_SERVER: ULONG = 0x0000006Eu
const val PRODUCT_CORE_CONNECTED: ULONG = 0x0000006Fu
const val PRODUCT_PROFESSIONAL_STUDENT: ULONG = 0x00000070u
const val PRODUCT_CORE_CONNECTED_N: ULONG = 0x00000071u
const val PRODUCT_PROFESSIONAL_STUDENT_N: ULONG = 0x00000072u
const val PRODUCT_CORE_CONNECTED_SINGLELANGUAGE: ULONG = 0x00000073u
const val PRODUCT_CORE_CONNECTED_COUNTRYSPECIFIC: ULONG = 0x00000074u
const val PRODUCT_CONNECTED_CAR: ULONG = 0x00000075u
const val PRODUCT_INDUSTRY_HANDHELD: ULONG = 0x00000076u
const val PRODUCT_PPI_PRO: ULONG = 0x00000077u
const val PRODUCT_ARM64_SERVER: ULONG = 0x00000078u
const val PRODUCT_EDUCATION: ULONG = 0x00000079u
const val PRODUCT_EDUCATION_N: ULONG = 0x0000007Au
const val PRODUCT_IOTUAP: ULONG = 0x0000007Bu
const val PRODUCT_CLOUD_HOST_INFRASTRUCTURE_SERVER: ULONG = 0x0000007Cu
const val PRODUCT_ENTERPRISE_S: ULONG = 0x0000007Du
const val PRODUCT_ENTERPRISE_S_N: ULONG = 0x0000007Eu
const val PRODUCT_PROFESSIONAL_S: ULONG = 0x0000007Fu
const val PRODUCT_PROFESSIONAL_S_N: ULONG = 0x00000080u
const val PRODUCT_ENTERPRISE_S_EVALUATION: ULONG = 0x00000081u
const val PRODUCT_ENTERPRISE_S_N_EVALUATION: ULONG = 0x00000082u
const val PRODUCT_HOLOGRAPHIC: ULONG = 0x00000087u
const val PRODUCT_PRO_SINGLE_LANGUAGE: ULONG = 0x0000008Au
const val PRODUCT_PRO_CHINA: ULONG = 0x0000008Bu
const val PRODUCT_ENTERPRISE_SUBSCRIPTION: ULONG = 0x0000008Cu
const val PRODUCT_ENTERPRISE_SUBSCRIPTION_N: ULONG = 0x0000008Du
const val PRODUCT_DATACENTER_NANO_SERVER: ULONG = 0x0000008Fu
const val PRODUCT_STANDARD_NANO_SERVER: ULONG = 0x00000090u
const val PRODUCT_DATACENTER_A_SERVER_CORE: ULONG = 0x00000091u
const val PRODUCT_STANDARD_A_SERVER_CORE: ULONG = 0x00000092u
const val PRODUCT_DATACENTER_WS_SERVER_CORE: ULONG = 0x00000093u
const val PRODUCT_STANDARD_WS_SERVER_CORE: ULONG = 0x00000094u
const val PRODUCT_UTILITY_VM: ULONG = 0x00000095u
const val PRODUCT_DATACENTER_EVALUATION_SERVER_CORE: ULONG = 0x0000009Fu
const val PRODUCT_STANDARD_EVALUATION_SERVER_CORE: ULONG = 0x000000A0u
const val PRODUCT_PRO_WORKSTATION: ULONG = 0x000000A1u
const val PRODUCT_PRO_WORKSTATION_N: ULONG = 0x000000A2u
const val PRODUCT_PRO_FOR_EDUCATION: ULONG = 0x000000A4u
const val PRODUCT_PRO_FOR_EDUCATION_N: ULONG = 0x000000A5u
const val PRODUCT_AZURE_SERVER_CORE: ULONG = 0x000000A8u
const val PRODUCT_AZURE_NANO_SERVER: ULONG = 0x000000A9u
const val PRODUCT_UNLICENSED: ULONG = 0xABCDABCDu

const val LANG_NEUTRAL: USHORT = 0x00u
const val LANG_INVARIANT: USHORT = 0x7fu
const val LANG_AFRIKAANS: USHORT = 0x36u
const val LANG_ALBANIAN: USHORT = 0x1cu
const val LANG_ALSATIAN: USHORT = 0x84u
const val LANG_AMHARIC: USHORT = 0x5eu
const val LANG_ARABIC: USHORT = 0x01u
const val LANG_ARMENIAN: USHORT = 0x2bu
const val LANG_ASSAMESE: USHORT = 0x4du
const val LANG_AZERI: USHORT = 0x2cu
const val LANG_AZERBAIJANI: USHORT = 0x2cu
const val LANG_BANGLA: USHORT = 0x45u
const val LANG_BASHKIR: USHORT = 0x6du
const val LANG_BASQUE: USHORT = 0x2du
const val LANG_BELARUSIAN: USHORT = 0x23u
const val LANG_BENGALI: USHORT = 0x45u
const val LANG_BRETON: USHORT = 0x7eu
const val LANG_BOSNIAN: USHORT = 0x1au
const val LANG_BOSNIAN_NEUTRAL: USHORT = 0x781au
const val LANG_BULGARIAN: USHORT = 0x02u
const val LANG_CATALAN: USHORT = 0x03u
const val LANG_CENTRAL_KURDISH: USHORT = 0x92u
const val LANG_CHEROKEE: USHORT = 0x5cu
const val LANG_CHINESE: USHORT = 0x04u
const val LANG_CHINESE_SIMPLIFIED: USHORT = 0x04u
const val LANG_CHINESE_TRADITIONAL: USHORT = 0x7c04u
const val LANG_CORSICAN: USHORT = 0x83u
const val LANG_CROATIAN: USHORT = 0x1au
const val LANG_CZECH: USHORT = 0x05u
const val LANG_DANISH: USHORT = 0x06u
const val LANG_DARI: USHORT = 0x8cu
const val LANG_DIVEHI: USHORT = 0x65u
const val LANG_DUTCH: USHORT = 0x13u
const val LANG_ENGLISH: USHORT = 0x09u
const val LANG_ESTONIAN: USHORT = 0x25u
const val LANG_FAEROESE: USHORT = 0x38u
const val LANG_FARSI: USHORT = 0x29u
const val LANG_FILIPINO: USHORT = 0x64u
const val LANG_FINNISH: USHORT = 0x0bu
const val LANG_FRENCH: USHORT = 0x0cu
const val LANG_FRISIAN: USHORT = 0x62u
const val LANG_FULAH: USHORT = 0x67u
const val LANG_GALICIAN: USHORT = 0x56u
const val LANG_GEORGIAN: USHORT = 0x37u
const val LANG_GERMAN: USHORT = 0x07u
const val LANG_GREEK: USHORT = 0x08u
const val LANG_GREENLANDIC: USHORT = 0x6fu
const val LANG_GUJARATI: USHORT = 0x47u
const val LANG_HAUSA: USHORT = 0x68u
const val LANG_HAWAIIAN: USHORT = 0x75u
const val LANG_HEBREW: USHORT = 0x0du
const val LANG_HINDI: USHORT = 0x39u
const val LANG_HUNGARIAN: USHORT = 0x0eu
const val LANG_ICELANDIC: USHORT = 0x0fu
const val LANG_IGBO: USHORT = 0x70u
const val LANG_INDONESIAN: USHORT = 0x21u
const val LANG_INUKTITUT: USHORT = 0x5du
const val LANG_IRISH: USHORT = 0x3cu
const val LANG_ITALIAN: USHORT = 0x10u
const val LANG_JAPANESE: USHORT = 0x11u
const val LANG_KANNADA: USHORT = 0x4bu
const val LANG_KASHMIRI: USHORT = 0x60u
const val LANG_KAZAK: USHORT = 0x3fu
const val LANG_KHMER: USHORT = 0x53u
const val LANG_KICHE: USHORT = 0x86u
const val LANG_KINYARWANDA: USHORT = 0x87u
const val LANG_KONKANI: USHORT = 0x57u
const val LANG_KOREAN: USHORT = 0x12u
const val LANG_KYRGYZ: USHORT = 0x40u
const val LANG_LAO: USHORT = 0x54u
const val LANG_LATVIAN: USHORT = 0x26u
const val LANG_LITHUANIAN: USHORT = 0x27u
const val LANG_LOWER_SORBIAN: USHORT = 0x2eu
const val LANG_LUXEMBOURGISH: USHORT = 0x6eu
const val LANG_MACEDONIAN: USHORT = 0x2fu
const val LANG_MALAY: USHORT = 0x3eu
const val LANG_MALAYALAM: USHORT = 0x4cu
const val LANG_MALTESE: USHORT = 0x3au
const val LANG_MANIPURI: USHORT = 0x58u
const val LANG_MAORI: USHORT = 0x81u
const val LANG_MAPUDUNGUN: USHORT = 0x7au
const val LANG_MARATHI: USHORT = 0x4eu
const val LANG_MOHAWK: USHORT = 0x7cu
const val LANG_MONGOLIAN: USHORT = 0x50u
const val LANG_NEPALI: USHORT = 0x61u
const val LANG_NORWEGIAN: USHORT = 0x14u
const val LANG_OCCITAN: USHORT = 0x82u
const val LANG_ODIA: USHORT = 0x48u
const val LANG_ORIYA: USHORT = 0x48u
const val LANG_PASHTO: USHORT = 0x63u
const val LANG_PERSIAN: USHORT = 0x29u
const val LANG_POLISH: USHORT = 0x15u
const val LANG_PORTUGUESE: USHORT = 0x16u
const val LANG_PULAR: USHORT = 0x67u
const val LANG_PUNJABI: USHORT = 0x46u
const val LANG_QUECHUA: USHORT = 0x6bu
const val LANG_ROMANIAN: USHORT = 0x18u
const val LANG_ROMANSH: USHORT = 0x17u
const val LANG_RUSSIAN: USHORT = 0x19u
const val LANG_SAKHA: USHORT = 0x85u
const val LANG_SAMI: USHORT = 0x3bu
const val LANG_SANSKRIT: USHORT = 0x4fu
const val LANG_SCOTTISH_GAELIC: USHORT = 0x91u
const val LANG_SERBIAN: USHORT = 0x1au
const val LANG_SERBIAN_NEUTRAL: USHORT = 0x7c1au
const val LANG_SINDHI: USHORT = 0x59u
const val LANG_SINHALESE: USHORT = 0x5bu
const val LANG_SLOVAK: USHORT = 0x1bu
const val LANG_SLOVENIAN: USHORT = 0x24u
const val LANG_SOTHO: USHORT = 0x6cu
const val LANG_SPANISH: USHORT = 0x0au
const val LANG_SWAHILI: USHORT = 0x41u
const val LANG_SWEDISH: USHORT = 0x1du
const val LANG_SYRIAC: USHORT = 0x5au
const val LANG_TAJIK: USHORT = 0x28u
const val LANG_TAMAZIGHT: USHORT = 0x5fu
const val LANG_TAMIL: USHORT = 0x49u
const val LANG_TATAR: USHORT = 0x44u
const val LANG_TELUGU: USHORT = 0x4au
const val LANG_THAI: USHORT = 0x1eu
const val LANG_TIBETAN: USHORT = 0x51u
const val LANG_TIGRIGNA: USHORT = 0x73u
const val LANG_TIGRINYA: USHORT = 0x73u
const val LANG_TSWANA: USHORT = 0x32u
const val LANG_TURKISH: USHORT = 0x1fu
const val LANG_TURKMEN: USHORT = 0x42u
const val LANG_UIGHUR: USHORT = 0x80u
const val LANG_UKRAINIAN: USHORT = 0x22u
const val LANG_UPPER_SORBIAN: USHORT = 0x2eu
const val LANG_URDU: USHORT = 0x20u
const val LANG_UZBEK: USHORT = 0x43u
const val LANG_VALENCIAN: USHORT = 0x03u
const val LANG_VIETNAMESE: USHORT = 0x2au
const val LANG_WELSH: USHORT = 0x52u
const val LANG_WOLOF: USHORT = 0x88u
const val LANG_XHOSA: USHORT = 0x34u
const val LANG_YAKUT: USHORT = 0x85u
const val LANG_YI: USHORT = 0x78u
const val LANG_YORUBA: USHORT = 0x6au
const val LANG_ZULU: USHORT = 0x35u

const val SUBLANG_NEUTRAL: USHORT = 0x00u
const val SUBLANG_DEFAULT: USHORT = 0x01u
const val SUBLANG_SYS_DEFAULT: USHORT = 0x02u
const val SUBLANG_CUSTOM_DEFAULT: USHORT = 0x03u
const val SUBLANG_CUSTOM_UNSPECIFIED: USHORT = 0x04u
const val SUBLANG_UI_CUSTOM_DEFAULT: USHORT = 0x05u
const val SUBLANG_AFRIKAANS_SOUTH_AFRICA: USHORT = 0x01u
const val SUBLANG_ALBANIAN_ALBANIA: USHORT = 0x01u
const val SUBLANG_ALSATIAN_FRANCE: USHORT = 0x01u
const val SUBLANG_AMHARIC_ETHIOPIA: USHORT = 0x01u
const val SUBLANG_ARABIC_SAUDI_ARABIA: USHORT = 0x01u
const val SUBLANG_ARABIC_IRAQ: USHORT = 0x02u
const val SUBLANG_ARABIC_EGYPT: USHORT = 0x03u
const val SUBLANG_ARABIC_LIBYA: USHORT = 0x04u
const val SUBLANG_ARABIC_ALGERIA: USHORT = 0x05u
const val SUBLANG_ARABIC_MOROCCO: USHORT = 0x06u
const val SUBLANG_ARABIC_TUNISIA: USHORT = 0x07u
const val SUBLANG_ARABIC_OMAN: USHORT = 0x08u
const val SUBLANG_ARABIC_YEMEN: USHORT = 0x09u
const val SUBLANG_ARABIC_SYRIA: USHORT = 0x0au
const val SUBLANG_ARABIC_JORDAN: USHORT = 0x0bu
const val SUBLANG_ARABIC_LEBANON: USHORT = 0x0cu
const val SUBLANG_ARABIC_KUWAIT: USHORT = 0x0du
const val SUBLANG_ARABIC_UAE: USHORT = 0x0eu
const val SUBLANG_ARABIC_BAHRAIN: USHORT = 0x0fu
const val SUBLANG_ARABIC_QATAR: USHORT = 0x10u
const val SUBLANG_ARMENIAN_ARMENIA: USHORT = 0x01u
const val SUBLANG_ASSAMESE_INDIA: USHORT = 0x01u
const val SUBLANG_AZERI_LATIN: USHORT = 0x01u
const val SUBLANG_AZERI_CYRILLIC: USHORT = 0x02u
const val SUBLANG_AZERBAIJANI_AZERBAIJAN_LATIN: USHORT = 0x01u
const val SUBLANG_AZERBAIJANI_AZERBAIJAN_CYRILLIC: USHORT = 0x02u
const val SUBLANG_BANGLA_INDIA: USHORT = 0x01u
const val SUBLANG_BANGLA_BANGLADESH: USHORT = 0x02u
const val SUBLANG_BASHKIR_RUSSIA: USHORT = 0x01u
const val SUBLANG_BASQUE_BASQUE: USHORT = 0x01u
const val SUBLANG_BELARUSIAN_BELARUS: USHORT = 0x01u
const val SUBLANG_BENGALI_INDIA: USHORT = 0x01u
const val SUBLANG_BENGALI_BANGLADESH: USHORT = 0x02u
const val SUBLANG_BOSNIAN_BOSNIA_HERZEGOVINA_LATIN: USHORT = 0x05u
const val SUBLANG_BOSNIAN_BOSNIA_HERZEGOVINA_CYRILLIC: USHORT = 0x08u
const val SUBLANG_BRETON_FRANCE: USHORT = 0x01u
const val SUBLANG_BULGARIAN_BULGARIA: USHORT = 0x01u
const val SUBLANG_CATALAN_CATALAN: USHORT = 0x01u
const val SUBLANG_CENTRAL_KURDISH_IRAQ: USHORT = 0x01u
const val SUBLANG_CHEROKEE_CHEROKEE: USHORT = 0x01u
const val SUBLANG_CHINESE_TRADITIONAL: USHORT = 0x01u
const val SUBLANG_CHINESE_SIMPLIFIED: USHORT = 0x02u
const val SUBLANG_CHINESE_HONGKONG: USHORT = 0x03u
const val SUBLANG_CHINESE_SINGAPORE: USHORT = 0x04u
const val SUBLANG_CHINESE_MACAU: USHORT = 0x05u
const val SUBLANG_CORSICAN_FRANCE: USHORT = 0x01u
const val SUBLANG_CZECH_CZECH_REPUBLIC: USHORT = 0x01u
const val SUBLANG_CROATIAN_CROATIA: USHORT = 0x01u
const val SUBLANG_CROATIAN_BOSNIA_HERZEGOVINA_LATIN: USHORT = 0x04u
const val SUBLANG_DANISH_DENMARK: USHORT = 0x01u
const val SUBLANG_DARI_AFGHANISTAN: USHORT = 0x01u
const val SUBLANG_DIVEHI_MALDIVES: USHORT = 0x01u
const val SUBLANG_DUTCH: USHORT = 0x01u
const val SUBLANG_DUTCH_BELGIAN: USHORT = 0x02u
const val SUBLANG_ENGLISH_US: USHORT = 0x01u
const val SUBLANG_ENGLISH_UK: USHORT = 0x02u
const val SUBLANG_ENGLISH_AUS: USHORT = 0x03u
const val SUBLANG_ENGLISH_CAN: USHORT = 0x04u
const val SUBLANG_ENGLISH_NZ: USHORT = 0x05u
const val SUBLANG_ENGLISH_EIRE: USHORT = 0x06u
const val SUBLANG_ENGLISH_SOUTH_AFRICA: USHORT = 0x07u
const val SUBLANG_ENGLISH_JAMAICA: USHORT = 0x08u
const val SUBLANG_ENGLISH_CARIBBEAN: USHORT = 0x09u
const val SUBLANG_ENGLISH_BELIZE: USHORT = 0x0au
const val SUBLANG_ENGLISH_TRINIDAD: USHORT = 0x0bu
const val SUBLANG_ENGLISH_ZIMBABWE: USHORT = 0x0cu
const val SUBLANG_ENGLISH_PHILIPPINES: USHORT = 0x0du
const val SUBLANG_ENGLISH_INDIA: USHORT = 0x10u
const val SUBLANG_ENGLISH_MALAYSIA: USHORT = 0x11u
const val SUBLANG_ENGLISH_SINGAPORE: USHORT = 0x12u
const val SUBLANG_ESTONIAN_ESTONIA: USHORT = 0x01u
const val SUBLANG_FAEROESE_FAROE_ISLANDS: USHORT = 0x01u
const val SUBLANG_FILIPINO_PHILIPPINES: USHORT = 0x01u
const val SUBLANG_FINNISH_FINLAND: USHORT = 0x01u
const val SUBLANG_FRENCH: USHORT = 0x01u
const val SUBLANG_FRENCH_BELGIAN: USHORT = 0x02u
const val SUBLANG_FRENCH_CANADIAN: USHORT = 0x03u
const val SUBLANG_FRENCH_SWISS: USHORT = 0x04u
const val SUBLANG_FRENCH_LUXEMBOURG: USHORT = 0x05u
const val SUBLANG_FRENCH_MONACO: USHORT = 0x06u
const val SUBLANG_FRISIAN_NETHERLANDS: USHORT = 0x01u
const val SUBLANG_FULAH_SENEGAL: USHORT = 0x02u
const val SUBLANG_GALICIAN_GALICIAN: USHORT = 0x01u
const val SUBLANG_GEORGIAN_GEORGIA: USHORT = 0x01u
const val SUBLANG_GERMAN: USHORT = 0x01u
const val SUBLANG_GERMAN_SWISS: USHORT = 0x02u
const val SUBLANG_GERMAN_AUSTRIAN: USHORT = 0x03u
const val SUBLANG_GERMAN_LUXEMBOURG: USHORT = 0x04u
const val SUBLANG_GERMAN_LIECHTENSTEIN: USHORT = 0x05u
const val SUBLANG_GREEK_GREECE: USHORT = 0x01u
const val SUBLANG_GREENLANDIC_GREENLAND: USHORT = 0x01u
const val SUBLANG_GUJARATI_INDIA: USHORT = 0x01u
const val SUBLANG_HAUSA_NIGERIA_LATIN: USHORT = 0x01u
const val SUBLANG_HAWAIIAN_US: USHORT = 0x01u
const val SUBLANG_HEBREW_ISRAEL: USHORT = 0x01u
const val SUBLANG_HINDI_INDIA: USHORT = 0x01u
const val SUBLANG_HUNGARIAN_HUNGARY: USHORT = 0x01u
const val SUBLANG_ICELANDIC_ICELAND: USHORT = 0x01u
const val SUBLANG_IGBO_NIGERIA: USHORT = 0x01u
const val SUBLANG_INDONESIAN_INDONESIA: USHORT = 0x01u
const val SUBLANG_INUKTITUT_CANADA: USHORT = 0x01u
const val SUBLANG_INUKTITUT_CANADA_LATIN: USHORT = 0x02u
const val SUBLANG_IRISH_IRELAND: USHORT = 0x02u
const val SUBLANG_ITALIAN: USHORT = 0x01u
const val SUBLANG_ITALIAN_SWISS: USHORT = 0x02u
const val SUBLANG_JAPANESE_JAPAN: USHORT = 0x01u
const val SUBLANG_KANNADA_INDIA: USHORT = 0x01u
const val SUBLANG_KASHMIRI_SASIA: USHORT = 0x02u
const val SUBLANG_KASHMIRI_INDIA: USHORT = 0x02u
const val SUBLANG_KAZAK_KAZAKHSTAN: USHORT = 0x01u
const val SUBLANG_KHMER_CAMBODIA: USHORT = 0x01u
const val SUBLANG_KICHE_GUATEMALA: USHORT = 0x01u
const val SUBLANG_KINYARWANDA_RWANDA: USHORT = 0x01u
const val SUBLANG_KONKANI_INDIA: USHORT = 0x01u
const val SUBLANG_KOREAN: USHORT = 0x01u
const val SUBLANG_KYRGYZ_KYRGYZSTAN: USHORT = 0x01u
const val SUBLANG_LAO_LAO: USHORT = 0x01u
const val SUBLANG_LATVIAN_LATVIA: USHORT = 0x01u
const val SUBLANG_LITHUANIAN: USHORT = 0x01u
const val SUBLANG_LOWER_SORBIAN_GERMANY: USHORT = 0x02u
const val SUBLANG_LUXEMBOURGISH_LUXEMBOURG: USHORT = 0x01u
const val SUBLANG_MACEDONIAN_MACEDONIA: USHORT = 0x01u
const val SUBLANG_MALAY_MALAYSIA: USHORT = 0x01u
const val SUBLANG_MALAY_BRUNEI_DARUSSALAM: USHORT = 0x02u
const val SUBLANG_MALAYALAM_INDIA: USHORT = 0x01u
const val SUBLANG_MALTESE_MALTA: USHORT = 0x01u
const val SUBLANG_MAORI_NEW_ZEALAND: USHORT = 0x01u
const val SUBLANG_MAPUDUNGUN_CHILE: USHORT = 0x01u
const val SUBLANG_MARATHI_INDIA: USHORT = 0x01u
const val SUBLANG_MOHAWK_MOHAWK: USHORT = 0x01u
const val SUBLANG_MONGOLIAN_CYRILLIC_MONGOLIA: USHORT = 0x01u
const val SUBLANG_MONGOLIAN_PRC: USHORT = 0x02u
const val SUBLANG_NEPALI_INDIA: USHORT = 0x02u
const val SUBLANG_NEPALI_NEPAL: USHORT = 0x01u
const val SUBLANG_NORWEGIAN_BOKMAL: USHORT = 0x01u
const val SUBLANG_NORWEGIAN_NYNORSK: USHORT = 0x02u
const val SUBLANG_OCCITAN_FRANCE: USHORT = 0x01u
const val SUBLANG_ODIA_INDIA: USHORT = 0x01u
const val SUBLANG_ORIYA_INDIA: USHORT = 0x01u
const val SUBLANG_PASHTO_AFGHANISTAN: USHORT = 0x01u
const val SUBLANG_PERSIAN_IRAN: USHORT = 0x01u
const val SUBLANG_POLISH_POLAND: USHORT = 0x01u
const val SUBLANG_PORTUGUESE: USHORT = 0x02u
const val SUBLANG_PORTUGUESE_BRAZILIAN: USHORT = 0x01u
const val SUBLANG_PULAR_SENEGAL: USHORT = 0x02u
const val SUBLANG_PUNJABI_INDIA: USHORT = 0x01u
const val SUBLANG_PUNJABI_PAKISTAN: USHORT = 0x02u
const val SUBLANG_QUECHUA_BOLIVIA: USHORT = 0x01u
const val SUBLANG_QUECHUA_ECUADOR: USHORT = 0x02u
const val SUBLANG_QUECHUA_PERU: USHORT = 0x03u
const val SUBLANG_ROMANIAN_ROMANIA: USHORT = 0x01u
const val SUBLANG_ROMANSH_SWITZERLAND: USHORT = 0x01u
const val SUBLANG_RUSSIAN_RUSSIA: USHORT = 0x01u
const val SUBLANG_SAKHA_RUSSIA: USHORT = 0x01u
const val SUBLANG_SAMI_NORTHERN_NORWAY: USHORT = 0x01u
const val SUBLANG_SAMI_NORTHERN_SWEDEN: USHORT = 0x02u
const val SUBLANG_SAMI_NORTHERN_FINLAND: USHORT = 0x03u
const val SUBLANG_SAMI_LULE_NORWAY: USHORT = 0x04u
const val SUBLANG_SAMI_LULE_SWEDEN: USHORT = 0x05u
const val SUBLANG_SAMI_SOUTHERN_NORWAY: USHORT = 0x06u
const val SUBLANG_SAMI_SOUTHERN_SWEDEN: USHORT = 0x07u
const val SUBLANG_SAMI_SKOLT_FINLAND: USHORT = 0x08u
const val SUBLANG_SAMI_INARI_FINLAND: USHORT = 0x09u
const val SUBLANG_SANSKRIT_INDIA: USHORT = 0x01u
const val SUBLANG_SCOTTISH_GAELIC: USHORT = 0x01u
const val SUBLANG_SERBIAN_BOSNIA_HERZEGOVINA_LATIN: USHORT = 0x06u
const val SUBLANG_SERBIAN_BOSNIA_HERZEGOVINA_CYRILLIC: USHORT = 0x07u
const val SUBLANG_SERBIAN_MONTENEGRO_LATIN: USHORT = 0x0bu
const val SUBLANG_SERBIAN_MONTENEGRO_CYRILLIC: USHORT = 0x0cu
const val SUBLANG_SERBIAN_SERBIA_LATIN: USHORT = 0x09u
const val SUBLANG_SERBIAN_SERBIA_CYRILLIC: USHORT = 0x0au
const val SUBLANG_SERBIAN_CROATIA: USHORT = 0x01u
const val SUBLANG_SERBIAN_LATIN: USHORT = 0x02u
const val SUBLANG_SERBIAN_CYRILLIC: USHORT = 0x03u
const val SUBLANG_SINDHI_INDIA: USHORT = 0x01u
const val SUBLANG_SINDHI_PAKISTAN: USHORT = 0x02u
const val SUBLANG_SINDHI_AFGHANISTAN: USHORT = 0x02u
const val SUBLANG_SINHALESE_SRI_LANKA: USHORT = 0x01u
const val SUBLANG_SOTHO_NORTHERN_SOUTH_AFRICA: USHORT = 0x01u
const val SUBLANG_SLOVAK_SLOVAKIA: USHORT = 0x01u
const val SUBLANG_SLOVENIAN_SLOVENIA: USHORT = 0x01u
const val SUBLANG_SPANISH: USHORT = 0x01u
const val SUBLANG_SPANISH_MEXICAN: USHORT = 0x02u
const val SUBLANG_SPANISH_MODERN: USHORT = 0x03u
const val SUBLANG_SPANISH_GUATEMALA: USHORT = 0x04u
const val SUBLANG_SPANISH_COSTA_RICA: USHORT = 0x05u
const val SUBLANG_SPANISH_PANAMA: USHORT = 0x06u
const val SUBLANG_SPANISH_DOMINICAN_REPUBLIC: USHORT = 0x07u
const val SUBLANG_SPANISH_VENEZUELA: USHORT = 0x08u
const val SUBLANG_SPANISH_COLOMBIA: USHORT = 0x09u
const val SUBLANG_SPANISH_PERU: USHORT = 0x0au
const val SUBLANG_SPANISH_ARGENTINA: USHORT = 0x0bu
const val SUBLANG_SPANISH_ECUADOR: USHORT = 0x0cu
const val SUBLANG_SPANISH_CHILE: USHORT = 0x0du
const val SUBLANG_SPANISH_URUGUAY: USHORT = 0x0eu
const val SUBLANG_SPANISH_PARAGUAY: USHORT = 0x0fu
const val SUBLANG_SPANISH_BOLIVIA: USHORT = 0x10u
const val SUBLANG_SPANISH_EL_SALVADOR: USHORT = 0x11u
const val SUBLANG_SPANISH_HONDURAS: USHORT = 0x12u
const val SUBLANG_SPANISH_NICARAGUA: USHORT = 0x13u
const val SUBLANG_SPANISH_PUERTO_RICO: USHORT = 0x14u
const val SUBLANG_SPANISH_US: USHORT = 0x15u
const val SUBLANG_SWAHILI_KENYA: USHORT = 0x01u
const val SUBLANG_SWEDISH: USHORT = 0x01u
const val SUBLANG_SWEDISH_FINLAND: USHORT = 0x02u
const val SUBLANG_SYRIAC_SYRIA: USHORT = 0x01u
const val SUBLANG_TAJIK_TAJIKISTAN: USHORT = 0x01u
const val SUBLANG_TAMAZIGHT_ALGERIA_LATIN: USHORT = 0x02u
const val SUBLANG_TAMAZIGHT_MOROCCO_TIFINAGH: USHORT = 0x04u
const val SUBLANG_TAMIL_INDIA: USHORT = 0x01u
const val SUBLANG_TAMIL_SRI_LANKA: USHORT = 0x02u
const val SUBLANG_TATAR_RUSSIA: USHORT = 0x01u
const val SUBLANG_TELUGU_INDIA: USHORT = 0x01u
const val SUBLANG_THAI_THAILAND: USHORT = 0x01u
const val SUBLANG_TIBETAN_PRC: USHORT = 0x01u
const val SUBLANG_TIGRIGNA_ERITREA: USHORT = 0x02u
const val SUBLANG_TIGRINYA_ERITREA: USHORT = 0x02u
const val SUBLANG_TIGRINYA_ETHIOPIA: USHORT = 0x01u
const val SUBLANG_TSWANA_BOTSWANA: USHORT = 0x02u
const val SUBLANG_TSWANA_SOUTH_AFRICA: USHORT = 0x01u
const val SUBLANG_TURKISH_TURKEY: USHORT = 0x01u
const val SUBLANG_TURKMEN_TURKMENISTAN: USHORT = 0x01u
const val SUBLANG_UIGHUR_PRC: USHORT = 0x01u
const val SUBLANG_UKRAINIAN_UKRAINE: USHORT = 0x01u
const val SUBLANG_UPPER_SORBIAN_GERMANY: USHORT = 0x01u
const val SUBLANG_URDU_PAKISTAN: USHORT = 0x01u
const val SUBLANG_URDU_INDIA: USHORT = 0x02u
const val SUBLANG_UZBEK_LATIN: USHORT = 0x01u
const val SUBLANG_UZBEK_CYRILLIC: USHORT = 0x02u
const val SUBLANG_VALENCIAN_VALENCIA: USHORT = 0x02u
const val SUBLANG_VIETNAMESE_VIETNAM: USHORT = 0x01u
const val SUBLANG_WELSH_UNITED_KINGDOM: USHORT = 0x01u
const val SUBLANG_WOLOF_SENEGAL: USHORT = 0x01u
const val SUBLANG_XHOSA_SOUTH_AFRICA: USHORT = 0x01u
const val SUBLANG_YAKUT_RUSSIA: USHORT = 0x01u
const val SUBLANG_YI_PRC: USHORT = 0x01u
const val SUBLANG_YORUBA_NIGERIA: USHORT = 0x01u
const val SUBLANG_ZULU_SOUTH_AFRICA: USHORT = 0x01u

const val SORT_DEFAULT: USHORT = 0x0u
const val SORT_INVARIANT_MATH: USHORT = 0x1u
const val SORT_JAPANESE_XJIS: USHORT = 0x0u
const val SORT_JAPANESE_UNICODE: USHORT = 0x1u
const val SORT_JAPANESE_RADICALSTROKE: USHORT = 0x4u
const val SORT_CHINESE_BIG5: USHORT = 0x0u
const val SORT_CHINESE_PRCP: USHORT = 0x0u
const val SORT_CHINESE_UNICODE: USHORT = 0x1u
const val SORT_CHINESE_PRC: USHORT = 0x2u
const val SORT_CHINESE_BOPOMOFO: USHORT = 0x3u
const val SORT_CHINESE_RADICALSTROKE: USHORT = 0x4u
const val SORT_KOREAN_KSC: USHORT = 0x0u
const val SORT_KOREAN_UNICODE: USHORT = 0x1u
const val SORT_GERMAN_PHONE_BOOK: USHORT = 0x1u
const val SORT_HUNGARIAN_DEFAULT: USHORT = 0x0u
const val SORT_HUNGARIAN_TECHNICAL: USHORT = 0x1u
const val SORT_GEORGIAN_TRADITIONAL: USHORT = 0x0u
const val SORT_GEORGIAN_MODERN: USHORT = 0x1u

// Upstream defines `MAKELANGID` twice — first as `macro_rules!`, then
// as `#[inline] pub fn`. Kotlin only needs the function form; const
// initializers below use it directly.
fun MAKELANGID(p: USHORT, s: USHORT): LANGID = ((s.toUInt() shl 10) or p.toUInt()).toUShort()
fun PRIMARYLANGID(lgid: LANGID): USHORT = (lgid.toUInt() and 0x3ffu).toUShort()
fun SUBLANGID(lgid: LANGID): USHORT = (lgid.toUInt() shr 10).toUShort()

const val NLS_VALID_LOCALE_MASK: ULONG = 0x000fffffu

fun MAKELCID(lgid: LANGID, srtid: USHORT): LCID =
    (srtid.toULong() shl 16).toUInt() or lgid.toUInt()

fun MAKESORTLCID(lgid: LANGID, srtid: USHORT, ver: USHORT): LCID =
    MAKELCID(lgid, srtid) or (ver.toULong() shl 20).toUInt()

fun LANGIDFROMLCID(lcid: LCID): LANGID = lcid.toUShort()
fun SORTIDFROMLCID(lcid: LCID): USHORT = ((lcid shr 16) and 0xfu).toUShort()
fun SORTVERSIONFROMLCID(lcid: LCID): USHORT = ((lcid shr 16) and 0xfu).toUShort()

const val LOCALE_NAME_MAX_LENGTH: ULong = 85u

val LANG_SYSTEM_DEFAULT: LANGID = MAKELANGID(LANG_NEUTRAL, SUBLANG_SYS_DEFAULT)
val LANG_USER_DEFAULT: LANGID = MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT)
val LOCALE_SYSTEM_DEFAULT: LCID = MAKELCID(LANG_SYSTEM_DEFAULT, SORT_DEFAULT)
val LOCALE_USER_DEFAULT: LCID = MAKELCID(LANG_USER_DEFAULT, SORT_DEFAULT)
val LOCALE_CUSTOM_DEFAULT: LCID = MAKELCID(MAKELANGID(LANG_NEUTRAL, SUBLANG_CUSTOM_DEFAULT), SORT_DEFAULT)
val LOCALE_CUSTOM_UNSPECIFIED: LCID = MAKELCID(MAKELANGID(LANG_NEUTRAL, SUBLANG_CUSTOM_UNSPECIFIED), SORT_DEFAULT)
val LOCALE_CUSTOM_UI_DEFAULT: LCID = MAKELCID(MAKELANGID(LANG_NEUTRAL, SUBLANG_UI_CUSTOM_DEFAULT), SORT_DEFAULT)
val LOCALE_NEUTRAL: LCID = MAKELCID(MAKELANGID(LANG_NEUTRAL, SUBLANG_NEUTRAL), SORT_DEFAULT)
val LOCALE_INVARIANT: LCID = MAKELCID(MAKELANGID(LANG_INVARIANT, SUBLANG_NEUTRAL), SORT_DEFAULT)
const val LOCALE_TRANSIENT_KEYBOARD1: LCID = 0x2000u
const val LOCALE_TRANSIENT_KEYBOARD2: LCID = 0x2400u
const val LOCALE_TRANSIENT_KEYBOARD3: LCID = 0x2800u
const val LOCALE_TRANSIENT_KEYBOARD4: LCID = 0x2c00u
val LOCALE_UNASSIGNED_LCID: LCID = LOCALE_CUSTOM_UNSPECIFIED
