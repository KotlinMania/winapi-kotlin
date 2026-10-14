package io.github.kotlinmania.winapi.internal

internal object WinapiJni {
    init {
        System.loadLibrary("winapi_jni")
    }

    @JvmStatic
    external fun CloseHandle(hObject: Long): Int

    @JvmStatic
    external fun GetLastError(): Int

    @JvmStatic
    external fun SetLastError(dwErrCode: Int)

    @JvmStatic
    external fun SetLastErrorEx(dwErrCode: Int, dwType: Int)

    @JvmStatic
    external fun LocalFree(hMem: Long): Long

    @JvmStatic
    external fun SetHandleInformation(hObject: Long, dwMask: Int, dwFlags: Int): Int

    @JvmStatic
    external fun GetHandleInformation(hObject: Long, lpdwFlags: IntArray): Int

    @JvmStatic
    external fun DuplicateHandle(
        hSourceProcessHandle: Long,
        hSourceHandle: Long,
        hTargetProcessHandle: Long,
        lpTargetHandle: LongArray,
        dwDesiredAccess: Int,
        bInheritHandle: Int,
        dwOptions: Int,
    ): Int

    @JvmStatic
    external fun CompareObjectHandles(hFirstObjectHandle: Long, hSecondObjectHandle: Long): Int

    @JvmStatic
    external fun FreeLibrary(hLibModule: Long): Int

    @JvmStatic
    external fun GlobalFree(hMem: Long): Long

    @JvmStatic
    external fun RtlNtStatusToDosError(status: Int): Int

    @JvmStatic
    external fun SysAllocString(psz: Long): Long

    @JvmStatic
    external fun SysAllocStringByteLen(psz: Long, len: Int): Long

    @JvmStatic
    external fun SysAllocStringLen(strIn: Long, ui: Int): Long

    @JvmStatic
    external fun SysFreeString(bstrString: Long)

    @JvmStatic
    external fun SysReAllocString(pbstr: LongArray, psz: Long): Int

    @JvmStatic
    external fun SysReAllocStringLen(pbstr: LongArray, psz: Long, len: Int): Int

    @JvmStatic
    external fun SysAddRefString(bstrString: Long): Int

    @JvmStatic
    external fun SysReleaseString(bstrString: Long)

    @JvmStatic
    external fun SysStringByteLen(bstr: Long): Int

    @JvmStatic
    external fun SysStringLen(pbstr: Long): Int

    @JvmStatic
    external fun SetFileAttributesW(lpFileName: Long, dwFileAttributes: Int): Int

    @JvmStatic
    external fun SetFileAttributesA(lpFileName: Long, dwFileAttributes: Int): Int

    @JvmStatic
    external fun GetFileAttributesW(lpFileName: Long): Int

    @JvmStatic
    external fun GetFileAttributesA(lpFileName: Long): Int

    @JvmStatic
    external fun CreateFileW(
        lpFileName: Long,
        dwDesiredAccess: Int,
        dwShareMode: Int,
        lpSecurityAttributes: Long,
        dwCreationDisposition: Int,
        dwFlagsAndAttributes: Int,
        hTemplateFile: Long,
    ): Long

    @JvmStatic
    external fun CreateFileA(
        lpFileName: Long,
        dwDesiredAccess: Int,
        dwShareMode: Int,
        lpSecurityAttributes: Long,
        dwCreationDisposition: Int,
        dwFlagsAndAttributes: Int,
        hTemplateFile: Long,
    ): Long

    @JvmStatic
    external fun DeleteFileW(lpFileName: Long): Int

    @JvmStatic
    external fun DeleteFileA(lpFileName: Long): Int

    @JvmStatic
    external fun FlushFileBuffers(hFile: Long): Int

    @JvmStatic
    external fun AreFileApisANSI(): Int

    @JvmStatic
    external fun GetFileSize(hFile: Long, lpFileSizeHigh: IntArray?): Int

    @JvmStatic
    external fun SetFilePointer(
        hFile: Long,
        lDistanceToMove: Int,
        lpDistanceToMoveHigh: IntArray?,
        dwMoveMethod: Int,
    ): Int

    @JvmStatic
    external fun GetCurrentProcess(): Long

    @JvmStatic
    external fun GetCurrentProcessId(): Int

    @JvmStatic
    external fun GetCurrentThread(): Long

    @JvmStatic
    external fun GetCurrentThreadId(): Int

    @JvmStatic
    external fun TerminateProcess(hProcess: Long, uExitCode: Int): Int

    @JvmStatic
    external fun ExitProcess(uExitCode: Int)

    @JvmStatic
    external fun OpenProcess(dwDesiredAccess: Int, bInheritHandle: Int, dwProcessId: Int): Long

    @JvmStatic
    external fun K32EmptyWorkingSet(hProcess: Long): Int

    @JvmStatic
    external fun GetTimeZoneInformation(lpTimeZoneInformation: Long): Int

    @JvmStatic
    external fun LockWorkStation(): Int

    @JvmStatic
    external fun DisconnectNamedPipe(hNamedPipe: Long): Int
}
