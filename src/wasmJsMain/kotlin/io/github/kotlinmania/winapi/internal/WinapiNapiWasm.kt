// port-lint: source N/A
@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.winapi.internal

@JsFun(
    """() => {
        if (globalThis.__kotlinmania_winapi_native) return 1;
        const proc = globalThis.process;
        const modMod = proc.getBuiltinModule("module");
        const path = proc.getBuiltinModule("path");
        const req = modMod.createRequire(path.join(proc.cwd(), "package.json"));
        globalThis.__kotlinmania_winapi_native = req("@kotlinmania/winapi-native-bindings");
        return 1;
    }""",
)
private external fun ensureWinapiNapiLoaded(): Int

@JsFun("(hObject) => globalThis.__kotlinmania_winapi_native.CloseHandle(hObject) | 0")
private external fun wasmCloseHandle(hObject: Double): Int

@JsFun("() => globalThis.__kotlinmania_winapi_native.GetLastError() | 0")
private external fun wasmGetLastError(): Int

@JsFun("(dwErrCode) => { globalThis.__kotlinmania_winapi_native.SetLastError(dwErrCode >>> 0); }")
private external fun wasmSetLastError(dwErrCode: Int)

@JsFun("(dwErrCode, dwType) => { globalThis.__kotlinmania_winapi_native.SetLastErrorEx(dwErrCode >>> 0, dwType >>> 0); }")
private external fun wasmSetLastErrorEx(dwErrCode: Int, dwType: Int)

@JsFun("(hMem) => +globalThis.__kotlinmania_winapi_native.LocalFree(hMem)")
private external fun wasmLocalFree(hMem: Double): Double

@JsFun("(hMem) => +globalThis.__kotlinmania_winapi_native.GlobalFree(hMem)")
private external fun wasmGlobalFree(hMem: Double): Double

@JsFun("(hLibModule) => globalThis.__kotlinmania_winapi_native.FreeLibrary(hLibModule) | 0")
private external fun wasmFreeLibrary(hLibModule: Double): Int

@JsFun("(hObject, dwMask, dwFlags) => globalThis.__kotlinmania_winapi_native.SetHandleInformation(hObject, dwMask >>> 0, dwFlags >>> 0) | 0")
private external fun wasmSetHandleInformation(hObject: Double, dwMask: Int, dwFlags: Int): Int

@JsFun(
    """(hObject) => {
        const res = globalThis.__kotlinmania_winapi_native.GetHandleInformation(hObject);
        globalThis.__kotlinmania_winapi_last_flags = res.flags | 0;
        return res.success | 0;
    }""",
)
private external fun wasmGetHandleInformation(hObject: Double): Int

@JsFun("() => globalThis.__kotlinmania_winapi_last_flags | 0")
private external fun wasmGetLastHandleFlags(): Int

@JsFun("(h1, h2) => globalThis.__kotlinmania_winapi_native.CompareObjectHandles(h1, h2) | 0")
private external fun wasmCompareObjectHandles(h1: Double, h2: Double): Int

@JsFun(
    """(hSourceProcess, hSource, hTargetProcess, dwDesiredAccess, bInheritHandle, dwOptions) => {
        const res = globalThis.__kotlinmania_winapi_native.DuplicateHandle(
            hSourceProcess,
            hSource,
            hTargetProcess,
            dwDesiredAccess >>> 0,
            bInheritHandle | 0,
            dwOptions >>> 0
        );
        globalThis.__kotlinmania_winapi_last_target_handle = +res.targetHandle;
        return res.success | 0;
    }""",
)
private external fun wasmDuplicateHandle(
    hSourceProcess: Double,
    hSource: Double,
    hTargetProcess: Double,
    dwDesiredAccess: Int,
    bInheritHandle: Int,
    dwOptions: Int,
): Int

@JsFun("() => +globalThis.__kotlinmania_winapi_last_target_handle")
private external fun wasmGetLastTargetHandle(): Double

@JsFun("(status) => globalThis.__kotlinmania_winapi_native.RtlNtStatusToDosError(status | 0) | 0")
private external fun wasmRtlNtStatusToDosError(status: Int): Int

@JsFun("(psz) => +globalThis.__kotlinmania_winapi_native.SysAllocString(psz)")
private external fun wasmSysAllocString(psz: Double): Double

@JsFun("(psz, len) => +globalThis.__kotlinmania_winapi_native.SysAllocStringByteLen(psz, len >>> 0)")
private external fun wasmSysAllocStringByteLen(psz: Double, len: Int): Double

@JsFun("(strIn, ui) => +globalThis.__kotlinmania_winapi_native.SysAllocStringLen(strIn, ui >>> 0)")
private external fun wasmSysAllocStringLen(strIn: Double, ui: Int): Double

@JsFun("(bstrString) => { globalThis.__kotlinmania_winapi_native.SysFreeString(bstrString); }")
private external fun wasmSysFreeString(bstrString: Double)

@JsFun(
    """(currentVal, psz) => {
        const res = globalThis.__kotlinmania_winapi_native.SysReAllocString(currentVal, psz);
        globalThis.__kotlinmania_winapi_last_bstr = +res.bstr;
        return res.result | 0;
    }""",
)
private external fun wasmSysReAllocString(currentVal: Double, psz: Double): Int

@JsFun(
    """(currentVal, psz, len) => {
        const res = globalThis.__kotlinmania_winapi_native.SysReAllocStringLen(currentVal, psz, len >>> 0);
        globalThis.__kotlinmania_winapi_last_bstr = +res.bstr;
        return res.result | 0;
    }""",
)
private external fun wasmSysReAllocStringLen(currentVal: Double, psz: Double, len: Int): Int

@JsFun("() => +globalThis.__kotlinmania_winapi_last_bstr")
private external fun wasmGetLastBstr(): Double

@JsFun("(bstrString) => globalThis.__kotlinmania_winapi_native.SysAddRefString(bstrString) | 0")
private external fun wasmSysAddRefString(bstrString: Double): Int

@JsFun("(bstrString) => { globalThis.__kotlinmania_winapi_native.SysReleaseString(bstrString); }")
private external fun wasmSysReleaseString(bstrString: Double)

@JsFun("(bstr) => globalThis.__kotlinmania_winapi_native.SysStringByteLen(bstr) | 0")
private external fun wasmSysStringByteLen(bstr: Double): Int

@JsFun("(pbstr) => globalThis.__kotlinmania_winapi_native.SysStringLen(pbstr) | 0")
private external fun wasmSysStringLen(pbstr: Double): Int

@JsFun("(lpFileName, dwFileAttributes) => globalThis.__kotlinmania_winapi_native.SetFileAttributesW(lpFileName, dwFileAttributes >>> 0) | 0")
private external fun wasmSetFileAttributesW(lpFileName: Double, dwFileAttributes: Int): Int

@JsFun("(lpFileName, dwFileAttributes) => globalThis.__kotlinmania_winapi_native.SetFileAttributesA(lpFileName, dwFileAttributes >>> 0) | 0")
private external fun wasmSetFileAttributesA(lpFileName: Double, dwFileAttributes: Int): Int

@JsFun("(lpFileName) => globalThis.__kotlinmania_winapi_native.GetFileAttributesW(lpFileName) | 0")
private external fun wasmGetFileAttributesW(lpFileName: Double): Int

@JsFun("(lpFileName) => globalThis.__kotlinmania_winapi_native.GetFileAttributesA(lpFileName) | 0")
private external fun wasmGetFileAttributesA(lpFileName: Double): Int

@JsFun(
    """(lpFileName, dwDesiredAccess, dwShareMode, lpSecurityAttributes, dwCreationDisposition, dwFlagsAndAttributes, hTemplateFile) =>
        +globalThis.__kotlinmania_winapi_native.CreateFileW(
            lpFileName,
            dwDesiredAccess >>> 0,
            dwShareMode >>> 0,
            lpSecurityAttributes,
            dwCreationDisposition >>> 0,
            dwFlagsAndAttributes >>> 0,
            hTemplateFile
        )""",
)
private external fun wasmCreateFileW(
    lpFileName: Double,
    dwDesiredAccess: Int,
    dwShareMode: Int,
    lpSecurityAttributes: Double,
    dwCreationDisposition: Int,
    dwFlagsAndAttributes: Int,
    hTemplateFile: Double,
): Double

@JsFun(
    """(lpFileName, dwDesiredAccess, dwShareMode, lpSecurityAttributes, dwCreationDisposition, dwFlagsAndAttributes, hTemplateFile) =>
        +globalThis.__kotlinmania_winapi_native.CreateFileA(
            lpFileName,
            dwDesiredAccess >>> 0,
            dwShareMode >>> 0,
            lpSecurityAttributes,
            dwCreationDisposition >>> 0,
            dwFlagsAndAttributes >>> 0,
            hTemplateFile
        )""",
)
private external fun wasmCreateFileA(
    lpFileName: Double,
    dwDesiredAccess: Int,
    dwShareMode: Int,
    lpSecurityAttributes: Double,
    dwCreationDisposition: Int,
    dwFlagsAndAttributes: Int,
    hTemplateFile: Double,
): Double

@JsFun("(lpFileName) => globalThis.__kotlinmania_winapi_native.DeleteFileW(lpFileName) | 0")
private external fun wasmDeleteFileW(lpFileName: Double): Int

@JsFun("(lpFileName) => globalThis.__kotlinmania_winapi_native.DeleteFileA(lpFileName) | 0")
private external fun wasmDeleteFileA(lpFileName: Double): Int

@JsFun("(hFile) => globalThis.__kotlinmania_winapi_native.FlushFileBuffers(hFile) | 0")
private external fun wasmFlushFileBuffers(hFile: Double): Int

@JsFun("() => globalThis.__kotlinmania_winapi_native.AreFileApisANSI() | 0")
private external fun wasmAreFileApisANSI(): Int

@JsFun(
    """(hFile, hasHigh) => {
        const res = globalThis.__kotlinmania_winapi_native.GetFileSize(hFile, hasHigh | 0);
        globalThis.__kotlinmania_winapi_last_high = res.high | 0;
        return res.low | 0;
    }""",
)
private external fun wasmGetFileSize(hFile: Double, hasHigh: Int): Int

@JsFun(
    """(hFile, lDistanceToMove, hasHigh, inHigh, dwMoveMethod) => {
        const res = globalThis.__kotlinmania_winapi_native.SetFilePointer(
            hFile,
            lDistanceToMove | 0,
            hasHigh | 0,
            inHigh | 0,
            dwMoveMethod >>> 0
        );
        globalThis.__kotlinmania_winapi_last_high = res.high | 0;
        return res.low | 0;
    }""",
)
private external fun wasmSetFilePointer(
    hFile: Double,
    lDistanceToMove: Int,
    hasHigh: Int,
    inHigh: Int,
    dwMoveMethod: Int,
): Int

@JsFun("() => globalThis.__kotlinmania_winapi_last_high | 0")
private external fun wasmGetLastHigh(): Int

@JsFun("() => +globalThis.__kotlinmania_winapi_native.GetCurrentProcess()")
private external fun wasmGetCurrentProcess(): Double

@JsFun("() => globalThis.__kotlinmania_winapi_native.GetCurrentProcessId() | 0")
private external fun wasmGetCurrentProcessId(): Int

@JsFun("() => +globalThis.__kotlinmania_winapi_native.GetCurrentThread()")
private external fun wasmGetCurrentThread(): Double

@JsFun("() => globalThis.__kotlinmania_winapi_native.GetCurrentThreadId() | 0")
private external fun wasmGetCurrentThreadId(): Int

@JsFun("(hProcess, uExitCode) => globalThis.__kotlinmania_winapi_native.TerminateProcess(hProcess, uExitCode >>> 0) | 0")
private external fun wasmTerminateProcess(hProcess: Double, uExitCode: Int): Int

@JsFun("(uExitCode) => { globalThis.__kotlinmania_winapi_native.ExitProcess(uExitCode >>> 0); }")
private external fun wasmExitProcess(uExitCode: Int)

@JsFun("(dwDesiredAccess, bInheritHandle, dwProcessId) => +globalThis.__kotlinmania_winapi_native.OpenProcess(dwDesiredAccess >>> 0, bInheritHandle | 0, dwProcessId >>> 0)")
private external fun wasmOpenProcess(dwDesiredAccess: Int, bInheritHandle: Int, dwProcessId: Int): Double

@JsFun("(hProcess) => globalThis.__kotlinmania_winapi_native.K32EmptyWorkingSet(hProcess) | 0")
private external fun wasmK32EmptyWorkingSet(hProcess: Double): Int

@JsFun("(lpTimeZoneInformation) => globalThis.__kotlinmania_winapi_native.GetTimeZoneInformation(lpTimeZoneInformation) | 0")
private external fun wasmGetTimeZoneInformation(lpTimeZoneInformation: Double): Int

@JsFun("() => globalThis.__kotlinmania_winapi_native.LockWorkStation() | 0")
private external fun wasmLockWorkStation(): Int

@JsFun("(hNamedPipe) => globalThis.__kotlinmania_winapi_native.DisconnectNamedPipe(hNamedPipe) | 0")
private external fun wasmDisconnectNamedPipe(hNamedPipe: Double): Int

internal object WinapiNapiWasm {
    init {
        ensureWinapiNapiLoaded()
    }

    fun CloseHandle(hObject: Long): Int = wasmCloseHandle(hObject.toDouble())

    fun GetLastError(): UInt = wasmGetLastError().toUInt()

    fun SetLastError(dwErrCode: UInt) {
        wasmSetLastError(dwErrCode.toInt())
    }

    fun SetLastErrorEx(dwErrCode: UInt, dwType: UInt) {
        wasmSetLastErrorEx(dwErrCode.toInt(), dwType.toInt())
    }

    fun LocalFree(hMem: Long): Long = wasmLocalFree(hMem.toDouble()).toLong()

    fun GlobalFree(hMem: Long): Long = wasmGlobalFree(hMem.toDouble()).toLong()

    fun FreeLibrary(hLibModule: Long): Int = wasmFreeLibrary(hLibModule.toDouble())

    fun SetHandleInformation(hObject: Long, dwMask: UInt, dwFlags: UInt): Int =
        wasmSetHandleInformation(hObject.toDouble(), dwMask.toInt(), dwFlags.toInt())

    fun GetHandleInformation(hObject: Long, lpdwFlags: UIntArray): Int {
        val success = wasmGetHandleInformation(hObject.toDouble())
        if (lpdwFlags.isNotEmpty()) {
            lpdwFlags[0] = wasmGetLastHandleFlags().toUInt()
        }
        return success
    }

    fun CompareObjectHandles(h1: Long, h2: Long): Int =
        wasmCompareObjectHandles(h1.toDouble(), h2.toDouble())

    fun DuplicateHandle(
        hSourceProcessHandle: Long,
        hSourceHandle: Long,
        hTargetProcessHandle: Long,
        lpTargetHandle: LongArray,
        dwDesiredAccess: UInt,
        bInheritHandle: Int,
        dwOptions: UInt,
    ): Int {
        val success =
            wasmDuplicateHandle(
                hSourceProcessHandle.toDouble(),
                hSourceHandle.toDouble(),
                hTargetProcessHandle.toDouble(),
                dwDesiredAccess.toInt(),
                bInheritHandle,
                dwOptions.toInt(),
            )
        if (lpTargetHandle.isNotEmpty()) {
            lpTargetHandle[0] = wasmGetLastTargetHandle().toLong()
        }
        return success
    }

    fun RtlNtStatusToDosError(status: Int): UInt = wasmRtlNtStatusToDosError(status).toUInt()

    fun SysAllocString(psz: Long): Long = wasmSysAllocString(psz.toDouble()).toLong()

    fun SysAllocStringByteLen(psz: Long, len: UInt): Long =
        wasmSysAllocStringByteLen(psz.toDouble(), len.toInt()).toLong()

    fun SysAllocStringLen(strIn: Long, ui: UInt): Long =
        wasmSysAllocStringLen(strIn.toDouble(), ui.toInt()).toLong()

    fun SysFreeString(bstrString: Long) {
        wasmSysFreeString(bstrString.toDouble())
    }

    fun SysReAllocString(pbstr: LongArray, psz: Long): Int {
        val current = if (pbstr.isNotEmpty()) pbstr[0] else 0L
        val res = wasmSysReAllocString(current.toDouble(), psz.toDouble())
        if (pbstr.isNotEmpty()) {
            pbstr[0] = wasmGetLastBstr().toLong()
        }
        return res
    }

    fun SysReAllocStringLen(pbstr: LongArray, psz: Long, len: UInt): Int {
        val current = if (pbstr.isNotEmpty()) pbstr[0] else 0L
        val res = wasmSysReAllocStringLen(current.toDouble(), psz.toDouble(), len.toInt())
        if (pbstr.isNotEmpty()) {
            pbstr[0] = wasmGetLastBstr().toLong()
        }
        return res
    }

    fun SysAddRefString(bstrString: Long): Int = wasmSysAddRefString(bstrString.toDouble())

    fun SysReleaseString(bstrString: Long) {
        wasmSysReleaseString(bstrString.toDouble())
    }

    fun SysStringByteLen(bstr: Long): UInt = wasmSysStringByteLen(bstr.toDouble()).toUInt()

    fun SysStringLen(pbstr: Long): UInt = wasmSysStringLen(pbstr.toDouble()).toUInt()

    fun SetFileAttributesW(lpFileName: Long, dwFileAttributes: UInt): Int =
        wasmSetFileAttributesW(lpFileName.toDouble(), dwFileAttributes.toInt())

    fun SetFileAttributesA(lpFileName: Long, dwFileAttributes: UInt): Int =
        wasmSetFileAttributesA(lpFileName.toDouble(), dwFileAttributes.toInt())

    fun GetFileAttributesW(lpFileName: Long): UInt =
        wasmGetFileAttributesW(lpFileName.toDouble()).toUInt()

    fun GetFileAttributesA(lpFileName: Long): UInt =
        wasmGetFileAttributesA(lpFileName.toDouble()).toUInt()

    fun CreateFileW(
        lpFileName: Long,
        dwDesiredAccess: UInt,
        dwShareMode: UInt,
        lpSecurityAttributes: Long,
        dwCreationDisposition: UInt,
        dwFlagsAndAttributes: UInt,
        hTemplateFile: Long,
    ): Long =
        wasmCreateFileW(
            lpFileName.toDouble(),
            dwDesiredAccess.toInt(),
            dwShareMode.toInt(),
            lpSecurityAttributes.toDouble(),
            dwCreationDisposition.toInt(),
            dwFlagsAndAttributes.toInt(),
            hTemplateFile.toDouble(),
        ).toLong()

    fun CreateFileA(
        lpFileName: Long,
        dwDesiredAccess: UInt,
        dwShareMode: UInt,
        lpSecurityAttributes: Long,
        dwCreationDisposition: UInt,
        dwFlagsAndAttributes: UInt,
        hTemplateFile: Long,
    ): Long =
        wasmCreateFileA(
            lpFileName.toDouble(),
            dwDesiredAccess.toInt(),
            dwShareMode.toInt(),
            lpSecurityAttributes.toDouble(),
            dwCreationDisposition.toInt(),
            dwFlagsAndAttributes.toInt(),
            hTemplateFile.toDouble(),
        ).toLong()

    fun DeleteFileW(lpFileName: Long): Int = wasmDeleteFileW(lpFileName.toDouble())

    fun DeleteFileA(lpFileName: Long): Int = wasmDeleteFileA(lpFileName.toDouble())

    fun FlushFileBuffers(hFile: Long): Int = wasmFlushFileBuffers(hFile.toDouble())

    fun AreFileApisANSI(): Int = wasmAreFileApisANSI()

    fun GetFileSize(hFile: Long, lpFileSizeHigh: UIntArray?): UInt {
        val hasHigh = if (lpFileSizeHigh != null && lpFileSizeHigh.isNotEmpty()) 1 else 0
        val low = wasmGetFileSize(hFile.toDouble(), hasHigh)
        if (hasHigh != 0 && lpFileSizeHigh != null) {
            lpFileSizeHigh[0] = wasmGetLastHigh().toUInt()
        }
        return low.toUInt()
    }

    fun SetFilePointer(
        hFile: Long,
        lDistanceToMove: Int,
        lpDistanceToMoveHigh: IntArray?,
        dwMoveMethod: UInt,
    ): UInt {
        val hasHigh = if (lpDistanceToMoveHigh != null && lpDistanceToMoveHigh.isNotEmpty()) 1 else 0
        val inHigh = if (hasHigh != 0 && lpDistanceToMoveHigh != null) lpDistanceToMoveHigh[0] else 0
        val low =
            wasmSetFilePointer(
                hFile.toDouble(),
                lDistanceToMove,
                hasHigh,
                inHigh,
                dwMoveMethod.toInt(),
            )
        if (hasHigh != 0 && lpDistanceToMoveHigh != null) {
            lpDistanceToMoveHigh[0] = wasmGetLastHigh()
        }
        return low.toUInt()
    }

    fun GetCurrentProcess(): Long = wasmGetCurrentProcess().toLong()

    fun GetCurrentProcessId(): UInt = wasmGetCurrentProcessId().toUInt()

    fun GetCurrentThread(): Long = wasmGetCurrentThread().toLong()

    fun GetCurrentThreadId(): UInt = wasmGetCurrentThreadId().toUInt()

    fun TerminateProcess(hProcess: Long, uExitCode: UInt): Int =
        wasmTerminateProcess(hProcess.toDouble(), uExitCode.toInt())

    fun ExitProcess(uExitCode: UInt) {
        wasmExitProcess(uExitCode.toInt())
    }

    fun OpenProcess(dwDesiredAccess: UInt, bInheritHandle: Int, dwProcessId: UInt): Long =
        wasmOpenProcess(dwDesiredAccess.toInt(), bInheritHandle, dwProcessId.toInt()).toLong()

    fun K32EmptyWorkingSet(hProcess: Long): Int = wasmK32EmptyWorkingSet(hProcess.toDouble())

    fun GetTimeZoneInformation(lpTimeZoneInformation: Long): UInt =
        wasmGetTimeZoneInformation(lpTimeZoneInformation.toDouble()).toUInt()

    fun LockWorkStation(): Int = wasmLockWorkStation()

    fun DisconnectNamedPipe(hNamedPipe: Long): Int = wasmDisconnectNamedPipe(hNamedPipe.toDouble())
}
