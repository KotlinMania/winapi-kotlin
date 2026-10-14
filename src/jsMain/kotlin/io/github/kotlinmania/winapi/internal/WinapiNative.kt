// port-lint: source N/A
package io.github.kotlinmania.winapi.internal

@JsModule("@kotlinmania/winapi-native-bindings")
@JsNonModule
internal external object WinapiNative {
    fun CloseHandle(hObject: Double): Int

    fun GetLastError(): Int

    fun SetLastError(dwErrCode: Int)

    fun SetLastErrorEx(dwErrCode: Int, dwType: Int)

    fun LocalFree(hMem: Double): Double

    fun GlobalFree(hMem: Double): Double

    fun FreeLibrary(hLibModule: Double): Int

    fun SetHandleInformation(hObject: Double, dwMask: Int, dwFlags: Int): Int

    fun GetHandleInformation(hObject: Double): dynamic

    fun CompareObjectHandles(hFirstObjectHandle: Double, hSecondObjectHandle: Double): Int

    fun DuplicateHandle(
        hSourceProcessHandle: Double,
        hSourceHandle: Double,
        hTargetProcessHandle: Double,
        dwDesiredAccess: Int,
        bInheritHandle: Int,
        dwOptions: Int,
    ): dynamic

    fun RtlNtStatusToDosError(status: Int): Int

    fun SysAllocString(psz: Double): Double

    fun SysAllocStringByteLen(psz: Double, len: Int): Double

    fun SysAllocStringLen(strIn: Double, ui: Int): Double

    fun SysFreeString(bstrString: Double)

    fun SysReAllocString(currentVal: Double, psz: Double): dynamic

    fun SysReAllocStringLen(currentVal: Double, psz: Double, len: Int): dynamic

    fun SysAddRefString(bstrString: Double): Int

    fun SysReleaseString(bstrString: Double)

    fun SysStringByteLen(bstr: Double): Int

    fun SysStringLen(pbstr: Double): Int

    fun SetFileAttributesW(lpFileName: Double, dwFileAttributes: Int): Int

    fun SetFileAttributesA(lpFileName: Double, dwFileAttributes: Int): Int

    fun GetFileAttributesW(lpFileName: Double): Int

    fun GetFileAttributesA(lpFileName: Double): Int

    fun CreateFileW(
        lpFileName: Double,
        dwDesiredAccess: Int,
        dwShareMode: Int,
        lpSecurityAttributes: Double,
        dwCreationDisposition: Int,
        dwFlagsAndAttributes: Int,
        hTemplateFile: Double,
    ): Double

    fun CreateFileA(
        lpFileName: Double,
        dwDesiredAccess: Int,
        dwShareMode: Int,
        lpSecurityAttributes: Double,
        dwCreationDisposition: Int,
        dwFlagsAndAttributes: Int,
        hTemplateFile: Double,
    ): Double

    fun DeleteFileW(lpFileName: Double): Int

    fun DeleteFileA(lpFileName: Double): Int

    fun FlushFileBuffers(hFile: Double): Int

    fun AreFileApisANSI(): Int

    fun GetFileSize(hFile: Double, hasHigh: Int): dynamic

    fun SetFilePointer(
        hFile: Double,
        lDistanceToMove: Int,
        hasHigh: Int,
        inHigh: Int,
        dwMoveMethod: Int,
    ): dynamic

    fun GetCurrentProcess(): Double

    fun GetCurrentProcessId(): Int

    fun GetCurrentThread(): Double

    fun GetCurrentThreadId(): Int

    fun TerminateProcess(hProcess: Double, uExitCode: Int): Int

    fun ExitProcess(uExitCode: Int)

    fun OpenProcess(dwDesiredAccess: Int, bInheritHandle: Int, dwProcessId: Int): Double

    fun K32EmptyWorkingSet(hProcess: Double): Int

    fun GetTimeZoneInformation(lpTimeZoneInformation: Double): Int

    fun LockWorkStation(): Int

    fun DisconnectNamedPipe(hNamedPipe: Double): Int

    fun isNativeAvailable(): Boolean
}
