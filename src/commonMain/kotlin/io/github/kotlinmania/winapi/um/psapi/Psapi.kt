// port-lint: source um/psapi.rs
package io.github.kotlinmania.winapi.um.psapi

import io.github.kotlinmania.winapi.shared.basetsd.SIZE_T
import io.github.kotlinmania.winapi.shared.basetsd.ULONG_PTR
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.LPVOID
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import io.github.kotlinmania.winapi.shared.ntdef.PVOID

public const val LIST_MODULES_DEFAULT: DWORD = 0x0u
public const val LIST_MODULES_32BIT: DWORD = 0x01u
public const val LIST_MODULES_64BIT: DWORD = 0x02u
public const val LIST_MODULES_ALL: DWORD = 0x03u

public typealias LPMODULEINFO = ULong
public typealias PPSAPI_WORKING_SET_INFORMATION = ULong
public typealias PPSAPI_WORKING_SET_EX_INFORMATION = ULong
public typealias PPSAPI_WS_WATCH_INFORMATION = ULong
public typealias PPSAPI_WS_WATCH_INFORMATION_EX = ULong
public typealias PENUM_PAGE_FILE_INFORMATION = ULong
public typealias PPERFORMANCE_INFORMATION = ULong
public typealias PPROCESS_MEMORY_COUNTERS = ULong
public typealias PPROCESS_MEMORY_COUNTERS_EX = ULong
public typealias PENUM_PAGE_FILE_CALLBACKA = ULong
public typealias PENUM_PAGE_FILE_CALLBACKW = ULong

public data class MODULEINFO(
    public var lpBaseOfDll: LPVOID = 0uL,
    public var SizeOfImage: DWORD = 0u,
    public var EntryPoint: LPVOID = 0uL,
)

public data class ENUM_PAGE_FILE_INFORMATION(
    public var cb: DWORD = 0u,
    public var Reserved: DWORD = 0u,
    public var TotalSize: SIZE_T = 0uL,
    public var TotalInUse: SIZE_T = 0uL,
    public var PeakUsage: SIZE_T = 0uL,
)

public data class PERFORMANCE_INFORMATION(
    public var cb: DWORD = 0u,
    public var CommitTotal: SIZE_T = 0uL,
    public var CommitLimit: SIZE_T = 0uL,
    public var CommitPeak: SIZE_T = 0uL,
    public var PhysicalTotal: SIZE_T = 0uL,
    public var PhysicalAvailable: SIZE_T = 0uL,
    public var SystemCache: SIZE_T = 0uL,
    public var KernelTotal: SIZE_T = 0uL,
    public var KernelPaged: SIZE_T = 0uL,
    public var KernelNonpaged: SIZE_T = 0uL,
    public var PageSize: SIZE_T = 0uL,
    public var HandleCount: DWORD = 0u,
    public var ProcessCount: DWORD = 0u,
    public var ThreadCount: DWORD = 0u,
)

public data class PROCESS_MEMORY_COUNTERS(
    public var cb: DWORD = 0u,
    public var PageFaultCount: DWORD = 0u,
    public var PeakWorkingSetSize: SIZE_T = 0uL,
    public var WorkingSetSize: SIZE_T = 0uL,
    public var QuotaPeakPagedPoolUsage: SIZE_T = 0uL,
    public var QuotaPagedPoolUsage: SIZE_T = 0uL,
    public var QuotaPeakNonPagedPoolUsage: SIZE_T = 0uL,
    public var QuotaNonPagedPoolUsage: SIZE_T = 0uL,
    public var PagefileUsage: SIZE_T = 0uL,
    public var PeakPagefileUsage: SIZE_T = 0uL,
)

public data class PROCESS_MEMORY_COUNTERS_EX(
    public var cb: DWORD = 0u,
    public var PageFaultCount: DWORD = 0u,
    public var PeakWorkingSetSize: SIZE_T = 0uL,
    public var WorkingSetSize: SIZE_T = 0uL,
    public var QuotaPeakPagedPoolUsage: SIZE_T = 0uL,
    public var QuotaPagedPoolUsage: SIZE_T = 0uL,
    public var QuotaPeakNonPagedPoolUsage: SIZE_T = 0uL,
    public var QuotaNonPagedPoolUsage: SIZE_T = 0uL,
    public var PagefileUsage: SIZE_T = 0uL,
    public var PeakPagefileUsage: SIZE_T = 0uL,
    public var PrivateUsage: SIZE_T = 0uL,
)

public data class PSAPI_WORKING_SET_BLOCK(
    public var Flags: ULONG_PTR = 0uL,
) {
    public var Protection: ULONG_PTR
        get() = (Flags shr 0) and 0x1FuL
        set(value) {
            Flags = (Flags and 0x1FuL.inv()) or ((value and 0x1FuL) shl 0)
        }

    public var ShareCount: ULONG_PTR
        get() = (Flags shr 5) and 0x7uL
        set(value) {
            Flags = (Flags and (0x7uL shl 5).inv()) or ((value and 0x7uL) shl 5)
        }

    public var Shared: ULONG_PTR
        get() = (Flags shr 8) and 0x1uL
        set(value) {
            Flags = (Flags and (0x1uL shl 8).inv()) or ((value and 0x1uL) shl 8)
        }

    public var Reserved: ULONG_PTR
        get() = (Flags shr 9) and 0x7uL
        set(value) {
            Flags = (Flags and (0x7uL shl 9).inv()) or ((value and 0x7uL) shl 9)
        }

    public var VirtualPage: ULONG_PTR
        get() = (Flags shr 12) and 0xFFFFFuL
        set(value) {
            Flags = (Flags and (0xFFFFFuL shl 12).inv()) or ((value and 0xFFFFFuL) shl 12)
        }
}

public typealias PPSAPI_WORKING_SET_BLOCK = ULong

public data class PSAPI_WORKING_SET_EX_BLOCK(
    public var Flags: ULONG_PTR = 0uL,
) {
    public var Valid: ULONG_PTR
        get() = Flags and 0x1uL
        set(value) {
            Flags = (Flags and 0x1uL.inv()) or (value and 0x1uL)
        }

    public var ShareCount: ULONG_PTR
        get() = (Flags shr 1) and 0x7uL
        set(value) {
            Flags = (Flags and (0x7uL shl 1).inv()) or ((value and 0x7uL) shl 1)
        }

    public var Win32Protection: ULONG_PTR
        get() = (Flags shr 4) and 0x7FFuL
        set(value) {
            Flags = (Flags and (0x7FFuL shl 4).inv()) or ((value and 0x7FFuL) shl 4)
        }

    public var Shared: ULONG_PTR
        get() = (Flags shr 15) and 0x1uL
        set(value) {
            Flags = (Flags and (0x1uL shl 15).inv()) or ((value and 0x1uL) shl 15)
        }

    public var Node: ULONG_PTR
        get() = (Flags shr 16) and 0x3FuL
        set(value) {
            Flags = (Flags and (0x3FuL shl 16).inv()) or ((value and 0x3FuL) shl 16)
        }

    public var Locked: ULONG_PTR
        get() = (Flags shr 22) and 0x1uL
        set(value) {
            Flags = (Flags and (0x1uL shl 22).inv()) or ((value and 0x1uL) shl 22)
        }

    public var LargePage: ULONG_PTR
        get() = (Flags shr 23) and 0x1uL
        set(value) {
            Flags = (Flags and (0x1uL shl 23).inv()) or ((value and 0x1uL) shl 23)
        }

    public var Reserved: ULONG_PTR
        get() = (Flags shr 24) and 0x7FuL
        set(value) {
            Flags = (Flags and (0x7FuL shl 24).inv()) or ((value and 0x7FuL) shl 24)
        }

    public var Bad: ULONG_PTR
        get() = (Flags shr 31) and 0x1uL
        set(value) {
            Flags = (Flags and (0x1uL shl 31).inv()) or ((value and 0x1uL) shl 31)
        }

    public var ReservedUlong: ULONG_PTR
        get() = (Flags shr 32) and 0xFFFFFFFFuL
        set(value) {
            Flags = (Flags and 0xFFFFFFFFuL) or ((value and 0xFFFFFFFFuL) shl 32)
        }
}

public typealias PPSAPI_WORKING_SET_EX_BLOCK = ULong

public class PSAPI_WORKING_SET_INFORMATION(
    public var NumberOfEntries: ULONG_PTR = 0uL,
    public var WorkingSetInfo: List<PSAPI_WORKING_SET_BLOCK> = listOf(PSAPI_WORKING_SET_BLOCK()),
)

public data class PSAPI_WORKING_SET_EX_INFORMATION(
    public var VirtualAddress: PVOID = 0uL,
    public var VirtualAttributes: PSAPI_WORKING_SET_EX_BLOCK = PSAPI_WORKING_SET_EX_BLOCK(),
)

public data class PSAPI_WS_WATCH_INFORMATION(
    public var FaultingPc: LPVOID = 0uL,
    public var FaultingVa: LPVOID = 0uL,
)

public data class PSAPI_WS_WATCH_INFORMATION_EX(
    public var BasicInfo: PSAPI_WS_WATCH_INFORMATION = PSAPI_WS_WATCH_INFORMATION(),
    public var FaultingThreadId: ULONG_PTR = 0uL,
    public var Flags: ULONG_PTR = 0uL,
)

public expect fun K32EmptyWorkingSet(hProcess: HANDLE): BOOL

public expect fun EmptyWorkingSet(hProcess: HANDLE): BOOL
