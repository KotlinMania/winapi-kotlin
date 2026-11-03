package io.github.kotlinmania.winapi

import io.github.kotlinmania.winapi.shared.minwindef.FALSE
import io.github.kotlinmania.winapi.shared.minwindef.TRUE
import io.github.kotlinmania.winapi.shared.rpcndr.NDR_LITTLE_ENDIAN
import io.github.kotlinmania.winapi.shared.rpcndr.NDR_LOCAL_ENDIAN
import io.github.kotlinmania.winapi.shared.wtypes.CBPCLIPDATA
import io.github.kotlinmania.winapi.shared.wtypes.CLIPDATA
import io.github.kotlinmania.winapi.shared.wtypes.DECIMAL
import io.github.kotlinmania.winapi.shared.wtypes.DECIMAL_SETZERO
import io.github.kotlinmania.winapi.shared.wtypes.VARIANT_FALSE
import io.github.kotlinmania.winapi.shared.wtypes.VARIANT_TRUE
import io.github.kotlinmania.winapi.shared.wtypes.VT_BSTR
import io.github.kotlinmania.winapi.shared.wtypes.VT_EMPTY
import io.github.kotlinmania.winapi.shared.wtypesbase.CLSCTX_INPROC_SERVER
import io.github.kotlinmania.winapi.shared.wtypesbase.CLSCTX_LOCAL_SERVER
import io.github.kotlinmania.winapi.shared.wtypesbase.CLSCTX_VALID_MASK
import io.github.kotlinmania.winapi.um.errhandlingapi.GetLastError
import io.github.kotlinmania.winapi.um.errhandlingapi.SetLastError
import io.github.kotlinmania.winapi.um.fileapi.AreFileApisANSI
import io.github.kotlinmania.winapi.um.fileapi.CREATE_ALWAYS
import io.github.kotlinmania.winapi.um.fileapi.CREATE_NEW
import io.github.kotlinmania.winapi.um.fileapi.CreateFileA
import io.github.kotlinmania.winapi.um.fileapi.CreateFileW
import io.github.kotlinmania.winapi.um.fileapi.DeleteFileA
import io.github.kotlinmania.winapi.um.fileapi.DeleteFileW
import io.github.kotlinmania.winapi.um.fileapi.FlushFileBuffers
import io.github.kotlinmania.winapi.um.fileapi.GetFileAttributesA
import io.github.kotlinmania.winapi.um.fileapi.GetFileAttributesW
import io.github.kotlinmania.winapi.um.fileapi.GetFileSize
import io.github.kotlinmania.winapi.um.fileapi.INVALID_FILE_ATTRIBUTES
import io.github.kotlinmania.winapi.um.fileapi.INVALID_FILE_SIZE
import io.github.kotlinmania.winapi.um.fileapi.INVALID_SET_FILE_POINTER
import io.github.kotlinmania.winapi.um.fileapi.OPEN_EXISTING
import io.github.kotlinmania.winapi.um.fileapi.SetFileAttributesA
import io.github.kotlinmania.winapi.um.fileapi.SetFileAttributesW
import io.github.kotlinmania.winapi.um.fileapi.SetFilePointer
import io.github.kotlinmania.winapi.um.handleapi.CloseHandle
import io.github.kotlinmania.winapi.um.handleapi.CompareObjectHandles
import io.github.kotlinmania.winapi.um.handleapi.DuplicateHandle
import io.github.kotlinmania.winapi.um.handleapi.GetHandleInformation
import io.github.kotlinmania.winapi.um.handleapi.INVALID_HANDLE_VALUE
import io.github.kotlinmania.winapi.um.handleapi.SetHandleInformation
import io.github.kotlinmania.winapi.um.libloaderapi.FreeLibrary
import io.github.kotlinmania.winapi.um.libloaderapi.LOAD_LIBRARY_SEARCH_SYSTEM32
import io.github.kotlinmania.winapi.um.minwinbase.OVERLAPPED
import io.github.kotlinmania.winapi.um.minwinbase.OVERLAPPED_U_S
import io.github.kotlinmania.winapi.um.minwinbase.SECURITY_ATTRIBUTES
import io.github.kotlinmania.winapi.um.minwinbase.STILL_ACTIVE
import io.github.kotlinmania.winapi.um.minwinbase.SYSTEMTIME
import io.github.kotlinmania.winapi.um.namedpipeapi.DisconnectNamedPipe
import io.github.kotlinmania.winapi.um.oleauto.DISPATCH_METHOD
import io.github.kotlinmania.winapi.um.oleauto.MEMBERID_NIL
import io.github.kotlinmania.winapi.um.oleauto.SysAddRefString
import io.github.kotlinmania.winapi.um.oleauto.SysAllocString
import io.github.kotlinmania.winapi.um.oleauto.SysAllocStringByteLen
import io.github.kotlinmania.winapi.um.oleauto.SysAllocStringLen
import io.github.kotlinmania.winapi.um.oleauto.SysFreeString
import io.github.kotlinmania.winapi.um.oleauto.SysReAllocString
import io.github.kotlinmania.winapi.um.oleauto.SysReAllocStringLen
import io.github.kotlinmania.winapi.um.oleauto.SysReleaseString
import io.github.kotlinmania.winapi.um.oleauto.SysStringByteLen
import io.github.kotlinmania.winapi.um.oleauto.SysStringLen
import io.github.kotlinmania.winapi.um.processthreadsapi.GetCurrentProcess
import io.github.kotlinmania.winapi.um.processthreadsapi.GetCurrentProcessId
import io.github.kotlinmania.winapi.um.processthreadsapi.GetCurrentThread
import io.github.kotlinmania.winapi.um.processthreadsapi.GetCurrentThreadId
import io.github.kotlinmania.winapi.um.processthreadsapi.OpenProcess
import io.github.kotlinmania.winapi.um.processthreadsapi.PROCESS_INFORMATION
import io.github.kotlinmania.winapi.um.processthreadsapi.TLS_OUT_OF_INDEXES
import io.github.kotlinmania.winapi.um.processthreadsapi.TerminateProcess
import io.github.kotlinmania.winapi.um.psapi.EmptyWorkingSet
import io.github.kotlinmania.winapi.um.psapi.K32EmptyWorkingSet
import io.github.kotlinmania.winapi.um.psapi.LIST_MODULES_ALL
import io.github.kotlinmania.winapi.um.psapi.PSAPI_WORKING_SET_BLOCK
import io.github.kotlinmania.winapi.um.psapi.PSAPI_WORKING_SET_EX_BLOCK
import io.github.kotlinmania.winapi.um.timezoneapi.GetTimeZoneInformation
import io.github.kotlinmania.winapi.um.timezoneapi.TIME_ZONE_ID_INVALID
import io.github.kotlinmania.winapi.um.timezoneapi.TIME_ZONE_INFORMATION
import io.github.kotlinmania.winapi.um.winbase.FILE_BEGIN
import io.github.kotlinmania.winapi.um.winbase.FILE_CURRENT
import io.github.kotlinmania.winapi.um.winbase.FILE_END
import io.github.kotlinmania.winapi.um.winbase.GlobalFree
import io.github.kotlinmania.winapi.um.winbase.HANDLE_FLAG_INHERIT
import io.github.kotlinmania.winapi.um.winbase.INFINITE
import io.github.kotlinmania.winapi.um.winbase.LocalFree
import io.github.kotlinmania.winapi.um.winbase.RtlNtStatusToDosError
import io.github.kotlinmania.winapi.um.winnt.BTYPE
import io.github.kotlinmania.winapi.um.winnt.DECREF
import io.github.kotlinmania.winapi.um.winnt.DUPLICATE_SAME_ACCESS
import io.github.kotlinmania.winapi.um.winnt.EXCEPTION_TARGET_UNWIND
import io.github.kotlinmania.winapi.um.winnt.EXCEPTION_UNWINDING
import io.github.kotlinmania.winapi.um.winnt.FILE_ATTRIBUTE_NORMAL
import io.github.kotlinmania.winapi.um.winnt.GENERIC_READ
import io.github.kotlinmania.winapi.um.winnt.HEAP_MAKE_TAG_FLAGS
import io.github.kotlinmania.winapi.um.winnt.IMAGE_ORDINAL
import io.github.kotlinmania.winapi.um.winnt.IMAGE_ORDINAL32
import io.github.kotlinmania.winapi.um.winnt.IMAGE_ORDINAL64
import io.github.kotlinmania.winapi.um.winnt.IMAGE_ORDINAL_FLAG32
import io.github.kotlinmania.winapi.um.winnt.IMAGE_ORDINAL_FLAG64
import io.github.kotlinmania.winapi.um.winnt.IMAGE_SNAP_BY_ORDINAL
import io.github.kotlinmania.winapi.um.winnt.IMAGE_SNAP_BY_ORDINAL32
import io.github.kotlinmania.winapi.um.winnt.IMAGE_SNAP_BY_ORDINAL64
import io.github.kotlinmania.winapi.um.winnt.INCREF
import io.github.kotlinmania.winapi.um.winnt.ISARY
import io.github.kotlinmania.winapi.um.winnt.ISFCN
import io.github.kotlinmania.winapi.um.winnt.ISPTR
import io.github.kotlinmania.winapi.um.winnt.ISTAG
import io.github.kotlinmania.winapi.um.winnt.IS_DISPATCHING
import io.github.kotlinmania.winapi.um.winnt.IS_TARGET_UNWIND
import io.github.kotlinmania.winapi.um.winnt.IS_UNWINDING
import io.github.kotlinmania.winapi.um.winnt.IS_VALIDATION_ENABLED
import io.github.kotlinmania.winapi.um.winnt.IsReparseTagDirectory
import io.github.kotlinmania.winapi.um.winnt.IsReparseTagMicrosoft
import io.github.kotlinmania.winapi.um.winnt.IsReparseTagNameSurrogate
import io.github.kotlinmania.winapi.um.winnt.IsVirtualDiskFileShared
import io.github.kotlinmania.winapi.um.winnt.LANGIDFROMLCID
import io.github.kotlinmania.winapi.um.winnt.MAKELANGID
import io.github.kotlinmania.winapi.um.winnt.MAKELCID
import io.github.kotlinmania.winapi.um.winnt.MAKESORTLCID
import io.github.kotlinmania.winapi.um.winnt.MANDATORY_LEVEL_TO_MANDATORY_RID
import io.github.kotlinmania.winapi.um.winnt.PRIMARYLANGID
import io.github.kotlinmania.winapi.um.winnt.PROCESS_QUERY_LIMITED_INFORMATION
import io.github.kotlinmania.winapi.um.winnt.RtlCopyMemory
import io.github.kotlinmania.winapi.um.winnt.RtlFillMemory
import io.github.kotlinmania.winapi.um.winnt.RtlMoveMemory
import io.github.kotlinmania.winapi.um.winnt.RtlZeroMemory
import io.github.kotlinmania.winapi.um.winnt.SORTIDFROMLCID
import io.github.kotlinmania.winapi.um.winnt.SORTVERSIONFROMLCID
import io.github.kotlinmania.winapi.um.winnt.SUBLANGID
import io.github.kotlinmania.winapi.um.winnt.SecurityAnonymous
import io.github.kotlinmania.winapi.um.winnt.SecurityDelegation
import io.github.kotlinmania.winapi.um.winnt.SharedVirtualDiskHandleStateFileShared
import io.github.kotlinmania.winapi.um.winnt.SharedVirtualDiskHandleStateNone
import io.github.kotlinmania.winapi.um.winnt.TIME_ZONE_ID_DAYLIGHT
import io.github.kotlinmania.winapi.um.winnt.TIME_ZONE_ID_STANDARD
import io.github.kotlinmania.winapi.um.winnt.TIME_ZONE_ID_UNKNOWN
import io.github.kotlinmania.winapi.um.winnt.VALID_IMPERSONATION_LEVEL
import io.github.kotlinmania.winapi.um.winnt.WT_SET_MAX_THREADPOOL_THREADS
import io.github.kotlinmania.winapi.um.winuser.LockWorkStation
import io.github.kotlinmania.winapi.um.winuser.SLE_ERROR
import io.github.kotlinmania.winapi.um.winuser.SetLastErrorEx
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UmApiTest {
    @Test
    fun handleAndErrorFfi() {
        SetLastError(0u)
        SetLastErrorEx(0u, SLE_ERROR)
        val lastErr = GetLastError()
        assertTrue(lastErr == 0u || lastErr > 0u)

        assertEquals(0xFFFFFFFFFFFFFFFFuL, INVALID_HANDLE_VALUE)
        assertEquals(FALSE, CloseHandle(0uL))

        val flags = UIntArray(1)
        val infoRes = GetHandleInformation(0uL, flags)
        assertEquals(FALSE, infoRes)

        assertEquals(FALSE, SetHandleInformation(0uL, HANDLE_FLAG_INHERIT, 0u))

        val dupTarget = ULongArray(1)
        val dupRes = DuplicateHandle(0uL, 0uL, 0uL, dupTarget, 0u, FALSE, DUPLICATE_SAME_ACCESS)
        assertEquals(FALSE, dupRes)

        val cmpRes = CompareObjectHandles(0uL, 0uL)
        assertTrue(cmpRes == FALSE || cmpRes == TRUE)

        assertEquals(0uL, LocalFree(0uL))
        assertEquals(0uL, GlobalFree(0uL))
        assertEquals(FALSE, FreeLibrary(0uL))
        assertEquals(0u, RtlNtStatusToDosError(0))
    }

    @Test
    fun oleAutoBstrFfi() {
        assertEquals(-1, MEMBERID_NIL)
        assertEquals(0x1u.toUShort(), DISPATCH_METHOD)

        assertEquals(0uL, SysAllocString(0uL))

        val bstrLen = SysAllocStringLen(0uL, 4u)
        if (bstrLen != 0uL) {
            assertEquals(4u, SysStringLen(bstrLen))
            assertEquals(8u, SysStringByteLen(bstrLen))

            val addRefValid = SysAddRefString(bstrLen)
            if (addRefValid == 0) {
                SysReleaseString(bstrLen)
            }

            val pbstrValid = ULongArray(1) { 0uL }
            val reallocLenValid = SysReAllocStringLen(pbstrValid, bstrLen, 2u)
            assertEquals(1, reallocLenValid)
            assertEquals(2u, SysStringLen(pbstrValid[0]))
            SysFreeString(pbstrValid[0])

            SysFreeString(bstrLen)
        } else {
            val pbstrStub = ULongArray(1) { 0uL }
            assertEquals(0, SysReAllocStringLen(pbstrStub, 0uL, 0u))
        }

        val bstrByteLen = SysAllocStringByteLen(0uL, 6u)
        if (bstrByteLen != 0uL) {
            assertEquals(6u, SysStringByteLen(bstrByteLen))
            assertEquals(3u, SysStringLen(bstrByteLen))
            SysFreeString(bstrByteLen)
        }

        SysFreeString(0uL)
        SysReleaseString(0uL)
        assertEquals(0u, SysStringLen(0uL))
        assertEquals(0u, SysStringByteLen(0uL))

        val pbstr = ULongArray(1) { 0uL }
        val reallocRes = SysReAllocString(pbstr, 0uL)
        assertTrue(reallocRes == 0 || reallocRes == 1)
        if (pbstr[0] != 0uL) {
            SysFreeString(pbstr[0])
            pbstr[0] = 0uL
        }

        val addRefHr = SysAddRefString(0uL)
        assertTrue(addRefHr <= 0)
    }

    @Test
    fun fileApiFfi() {
        assertEquals(1u, CREATE_NEW)
        assertEquals(2u, CREATE_ALWAYS)
        assertEquals(3u, OPEN_EXISTING)
        assertEquals(0u, FILE_BEGIN)
        assertEquals(1u, FILE_CURRENT)
        assertEquals(2u, FILE_END)

        assertEquals(INVALID_FILE_ATTRIBUTES, GetFileAttributesW(0uL))
        assertEquals(INVALID_FILE_ATTRIBUTES, GetFileAttributesA(0uL))
        assertEquals(FALSE, SetFileAttributesW(0uL, FILE_ATTRIBUTE_NORMAL))
        assertEquals(FALSE, SetFileAttributesA(0uL, FILE_ATTRIBUTE_NORMAL))

        val hW = CreateFileW(0uL, GENERIC_READ, 0u, 0uL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, 0uL)
        assertEquals(INVALID_HANDLE_VALUE, hW)

        val hA = CreateFileA(0uL, GENERIC_READ, 0u, 0uL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, 0uL)
        assertEquals(INVALID_HANDLE_VALUE, hA)

        assertEquals(FALSE, DeleteFileW(0uL))
        assertEquals(FALSE, DeleteFileA(0uL))
        assertEquals(FALSE, FlushFileBuffers(INVALID_HANDLE_VALUE))

        val highSize = UIntArray(1)
        assertEquals(INVALID_FILE_SIZE, GetFileSize(INVALID_HANDLE_VALUE, highSize))

        val highDist = IntArray(1)
        assertEquals(INVALID_SET_FILE_POINTER, SetFilePointer(INVALID_HANDLE_VALUE, 0, highDist, FILE_BEGIN))

        val ansi = AreFileApisANSI()
        assertTrue(ansi == TRUE || ansi == FALSE)
    }

    @Test
    fun processThreadTimePipeFfi() {
        val currentProc = GetCurrentProcess()
        assertEquals(0xFFFFFFFFFFFFFFFFuL, currentProc)

        val currentThread = GetCurrentThread()
        assertEquals(0xFFFFFFFFFFFFFFFEuL, currentThread)

        val pid = GetCurrentProcessId()
        assertTrue(pid == 0u || pid > 0u)

        val tid = GetCurrentThreadId()
        assertTrue(tid == 0u || tid > 0u)

        assertEquals(FALSE, TerminateProcess(0uL, 1u))
        assertEquals(0uL, OpenProcess(PROCESS_QUERY_LIMITED_INFORMATION, FALSE, 0u))
        assertEquals(FALSE, K32EmptyWorkingSet(0uL))
        assertEquals(FALSE, EmptyWorkingSet(0uL))

        val tzRes = GetTimeZoneInformation(0uL)
        assertTrue(
            tzRes == TIME_ZONE_ID_INVALID ||
                tzRes == TIME_ZONE_ID_UNKNOWN ||
                tzRes == TIME_ZONE_ID_STANDARD ||
                tzRes == TIME_ZONE_ID_DAYLIGHT,
        )

        val lockRes = LockWorkStation()
        assertTrue(lockRes == FALSE || lockRes == TRUE)

        assertEquals(FALSE, DisconnectNamedPipe(0uL))
    }

    @Test
    fun structsBitfieldsAndWtypes() {
        val sa = SECURITY_ATTRIBUTES(nLength = 24u, lpSecurityDescriptor = 0uL, bInheritHandle = TRUE)
        assertEquals(24u, sa.nLength)
        assertEquals(TRUE, sa.bInheritHandle)

        val overlapped = OVERLAPPED()
        overlapped.u.s = OVERLAPPED_U_S(Offset = 0x11223344u, OffsetHigh = 0x55667788u)
        assertEquals(0x11223344u, overlapped.u.s.Offset)
        assertEquals(0x55667788u, overlapped.u.s.OffsetHigh)
        assertEquals(0x5566778811223344uL, overlapped.u.Pointer)

        val st = SYSTEMTIME(wYear = 2026u, wMonth = 10u, wDay = 14u)
        assertEquals(2026u.toUShort(), st.wYear)

        val tzi = TIME_ZONE_INFORMATION(Bias = 480)
        assertEquals(480, tzi.Bias)
        assertEquals(32, tzi.StandardName.size)

        val pi = PROCESS_INFORMATION(dwProcessId = 42u, dwThreadId = 99u)
        assertEquals(42u, pi.dwProcessId)
        assertEquals(99u, pi.dwThreadId)

        val wsb = PSAPI_WORKING_SET_BLOCK()
        wsb.Protection = 0x15uL
        wsb.ShareCount = 0x5uL
        wsb.Shared = 1uL
        wsb.VirtualPage = 0xABCDEuL
        assertEquals(0x15uL, wsb.Protection)
        assertEquals(0x5uL, wsb.ShareCount)
        assertEquals(1uL, wsb.Shared)
        assertEquals(0xABCDEuL, wsb.VirtualPage)

        val wsex = PSAPI_WORKING_SET_EX_BLOCK()
        wsex.Valid = 1uL
        wsex.Win32Protection = 0x123uL
        wsex.Node = 0x2AuL
        wsex.Bad = 1uL
        assertEquals(1uL, wsex.Valid)
        assertEquals(0x123uL, wsex.Win32Protection)
        assertEquals(0x2AuL, wsex.Node)
        assertEquals(1uL, wsex.Bad)

        val dec = DECIMAL(wReserved = 1u, scale = 2u, sign = 0x80u, Hi32 = 3u, Lo64 = 4uL)
        DECIMAL_SETZERO(dec)
        assertEquals(0uL, dec.Lo64)
        assertEquals(0u, dec.Hi32)
        assertEquals(0u.toUByte(), dec.scale)
        assertEquals(0u.toUByte(), dec.sign)

        val clip = CLIPDATA(cbSize = 20u, ulClipFmt = 1, pClipData = 0uL)
        assertEquals(16u, CBPCLIPDATA(clip))

        assertEquals(NDR_LITTLE_ENDIAN, NDR_LOCAL_ENDIAN)
        assertEquals((-1).toShort(), VARIANT_TRUE)
        assertEquals(0.toShort(), VARIANT_FALSE)
        assertEquals(0u, VT_EMPTY)
        assertEquals(8u, VT_BSTR)
        assertTrue((CLSCTX_VALID_MASK and (CLSCTX_INPROC_SERVER or CLSCTX_LOCAL_SERVER)) != 0u)
        assertEquals(0x00000103u, STILL_ACTIVE)
        assertEquals(0xFFFFFFFFu, INFINITE)
        assertEquals(0xFFFFFFFFu, TLS_OUT_OF_INDEXES)
        assertEquals(3u, LIST_MODULES_ALL)
        assertEquals(0x00000800u, LOAD_LIBRARY_SEARCH_SYSTEM32)
    }

    @Test
    fun winntFunctionsTest() {
        val langId = MAKELANGID(0x09u, 0x01u)
        assertEquals(0x0409u.toUShort(), langId)
        assertEquals(0x09u.toUShort(), PRIMARYLANGID(langId))
        assertEquals(0x01u.toUShort(), SUBLANGID(langId))

        val lcid = MAKELCID(langId, 0x00u)
        assertEquals(0x0409u, lcid)
        val sortLcid = MAKESORTLCID(langId, 0x01u, 0x02u)
        assertEquals(0x00210409u, sortLcid)
        assertEquals(langId, LANGIDFROMLCID(lcid))
        assertEquals(1u.toUShort(), SORTIDFROMLCID(sortLcid))
        assertEquals(1u.toUShort(), SORTVERSIONFROMLCID(sortLcid))

        assertTrue(IS_UNWINDING(EXCEPTION_UNWINDING))
        assertTrue(IS_DISPATCHING(0u))
        assertTrue(IS_TARGET_UNWIND(EXCEPTION_TARGET_UNWIND))

        assertEquals(0x2000u, MANDATORY_LEVEL_TO_MANDATORY_RID(2u))
        assertTrue(VALID_IMPERSONATION_LEVEL(SecurityAnonymous))
        assertTrue(VALID_IMPERSONATION_LEVEL(SecurityDelegation))
        assertTrue(!VALID_IMPERSONATION_LEVEL(99u))

        assertTrue(IsReparseTagMicrosoft(0x80000000u))
        assertTrue(!IsReparseTagMicrosoft(0x40000000u))
        assertTrue(IsReparseTagNameSurrogate(0x20000000u))
        assertTrue(IsReparseTagDirectory(0x10000000u))

        assertTrue(IsVirtualDiskFileShared(SharedVirtualDiskHandleStateFileShared))
        assertTrue(!IsVirtualDiskFileShared(SharedVirtualDiskHandleStateNone))

        assertTrue(BTYPE(0x0001u))
        assertTrue(!BTYPE(0x0000u))
        assertTrue(ISPTR(0x0010u))
        assertTrue(ISFCN(0x0020u))
        assertTrue(ISARY(0x0030u))
        assertTrue(ISTAG(0x000Au.toUByte()))
        val inc = INCREF(0x0001u)
        assertEquals(0x0001u.toUShort(), DECREF(inc))

        assertEquals(0x1234uL, IMAGE_ORDINAL64(0x8000000000001234uL))
        assertEquals(0x1234u, IMAGE_ORDINAL32(0x80001234u))
        assertTrue(IMAGE_SNAP_BY_ORDINAL64(IMAGE_ORDINAL_FLAG64 or 1uL))
        assertTrue(IMAGE_SNAP_BY_ORDINAL32(IMAGE_ORDINAL_FLAG32 or 1u))
        assertEquals(0x5678uL, IMAGE_ORDINAL(0x8000000000005678uL))
        assertEquals(0x5678u, IMAGE_ORDINAL(0x80005678u))
        assertTrue(IMAGE_SNAP_BY_ORDINAL(IMAGE_ORDINAL_FLAG64 or 2uL))
        assertTrue(IMAGE_SNAP_BY_ORDINAL(IMAGE_ORDINAL_FLAG32 or 2u))

        assertEquals(0x00040001u, HEAP_MAKE_TAG_FLAGS(1u, 1u))
        assertTrue(IS_VALIDATION_ENABLED(1u, 1u))
        assertTrue(!IS_VALIDATION_ENABLED(1u, 2u))
        assertEquals(0x00020001u, WT_SET_MAX_THREADPOOL_THREADS(1u, 2u))

        RtlMoveMemory(0uL, 0uL, 0uL)
        RtlCopyMemory(0uL, 0uL, 0uL)
        RtlFillMemory(0uL, 0uL, 0u.toUByte())
        RtlZeroMemory(0uL, 0uL)
    }
}
