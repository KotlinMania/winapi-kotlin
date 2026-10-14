#include "winapi_wrapper.h"

#ifdef _WIN32
#define WIN32_LEAN_AND_MEAN
#include <windows.h>
#include <oleauto.h>

typedef BOOL (WINAPI *PFN_CompareObjectHandles)(HANDLE, HANDLE);
static BOOL SafeCompareObjectHandles(HANDLE h1, HANDLE h2) {
    static PFN_CompareObjectHandles pfn = NULL;
    static int initialized = 0;
    if (!initialized) {
        HMODULE hModule = GetModuleHandleA("kernelbase.dll");
        if (!hModule) hModule = GetModuleHandleA("kernel32.dll");
        if (hModule) {
            pfn = (PFN_CompareObjectHandles)(void(*)(void))GetProcAddress(hModule, "CompareObjectHandles");
        }
        initialized = 1;
    }
    if (pfn) {
        return pfn(h1, h2);
    }
    return (h1 == h2) ? TRUE : FALSE;
}

typedef ULONG (NTAPI *PFN_RtlNtStatusToDosError)(LONG);
static ULONG SafeRtlNtStatusToDosError(LONG status) {
    static PFN_RtlNtStatusToDosError pfn = NULL;
    static int initialized = 0;
    if (!initialized) {
        HMODULE hModule = GetModuleHandleA("ntdll.dll");
        if (hModule) {
            pfn = (PFN_RtlNtStatusToDosError)(void(*)(void))GetProcAddress(hModule, "RtlNtStatusToDosError");
        }
        initialized = 1;
    }
    if (pfn) {
        return pfn(status);
    }
    return 0;
}

typedef void (WINAPI *PFN_SetLastErrorEx)(DWORD, DWORD);
static void SafeSetLastErrorEx(DWORD dwErrCode, DWORD dwType) {
    static PFN_SetLastErrorEx pfn = NULL;
    static int initialized = 0;
    if (!initialized) {
        HMODULE hModule = GetModuleHandleA("user32.dll");
        if (!hModule) hModule = LoadLibraryA("user32.dll");
        if (hModule) {
            pfn = (PFN_SetLastErrorEx)(void(*)(void))GetProcAddress(hModule, "SetLastErrorEx");
        }
        initialized = 1;
    }
    if (pfn) {
        pfn(dwErrCode, dwType);
    } else {
        SetLastError(dwErrCode);
    }
}

typedef BOOL (WINAPI *PFN_K32EmptyWorkingSet)(HANDLE);
static BOOL SafeK32EmptyWorkingSet(HANDLE hProcess) {
    static PFN_K32EmptyWorkingSet pfn = NULL;
    static int initialized = 0;
    if (!initialized) {
        HMODULE hModule = GetModuleHandleA("kernel32.dll");
        if (hModule) {
            pfn = (PFN_K32EmptyWorkingSet)(void(*)(void))GetProcAddress(hModule, "K32EmptyWorkingSet");
        }
        if (!pfn) {
            HMODULE hPsapi = LoadLibraryA("psapi.dll");
            if (hPsapi) {
                pfn = (PFN_K32EmptyWorkingSet)(void(*)(void))GetProcAddress(hPsapi, "EmptyWorkingSet");
            }
        }
        initialized = 1;
    }
    if (pfn) {
        return pfn(hProcess);
    }
    return FALSE;
}

typedef BOOL (WINAPI *PFN_LockWorkStation)(void);
static BOOL SafeLockWorkStation(void) {
    static PFN_LockWorkStation pfn = NULL;
    static int initialized = 0;
    if (!initialized) {
        HMODULE hModule = GetModuleHandleA("user32.dll");
        if (!hModule) hModule = LoadLibraryA("user32.dll");
        if (hModule) {
            pfn = (PFN_LockWorkStation)(void(*)(void))GetProcAddress(hModule, "LockWorkStation");
        }
        initialized = 1;
    }
    if (pfn) {
        return pfn();
    }
    return FALSE;
}

typedef HRESULT (WINAPI *PFN_SysAddRefString)(BSTR);
static HRESULT SafeSysAddRefString(BSTR bstrString) {
    static PFN_SysAddRefString pfn = NULL;
    static int initialized = 0;
    if (!initialized) {
        HMODULE hModule = GetModuleHandleA("oleaut32.dll");
        if (!hModule) hModule = LoadLibraryA("oleaut32.dll");
        if (hModule) {
            pfn = (PFN_SysAddRefString)(void(*)(void))GetProcAddress(hModule, "SysAddRefString");
        }
        initialized = 1;
    }
    if (pfn) {
        return pfn(bstrString);
    }
    return 0;
}

typedef void (WINAPI *PFN_SysReleaseString)(BSTR);
static void SafeSysReleaseString(BSTR bstrString) {
    static PFN_SysReleaseString pfn = NULL;
    static int initialized = 0;
    if (!initialized) {
        HMODULE hModule = GetModuleHandleA("oleaut32.dll");
        if (!hModule) hModule = LoadLibraryA("oleaut32.dll");
        if (hModule) {
            pfn = (PFN_SysReleaseString)(void(*)(void))GetProcAddress(hModule, "SysReleaseString");
        }
        initialized = 1;
    }
    if (pfn) {
        pfn(bstrString);
    }
}
#endif

int32_t winapi_close_handle(int64_t hObject) {
#ifdef _WIN32
    return (int32_t)CloseHandle((HANDLE)(intptr_t)hObject);
#else
    (void)hObject;
    return 0;
#endif
}

uint32_t winapi_get_last_error(void) {
#ifdef _WIN32
    return (uint32_t)GetLastError();
#else
    return 0;
#endif
}

void winapi_set_last_error(uint32_t dwErrCode) {
#ifdef _WIN32
    SetLastError((DWORD)dwErrCode);
#else
    (void)dwErrCode;
#endif
}

void winapi_set_last_error_ex(uint32_t dwErrCode, uint32_t dwType) {
#ifdef _WIN32
    SafeSetLastErrorEx((DWORD)dwErrCode, (DWORD)dwType);
#else
    (void)dwErrCode;
    (void)dwType;
#endif
}

int64_t winapi_local_free(int64_t hMem) {
#ifdef _WIN32
    return (int64_t)(intptr_t)LocalFree((HLOCAL)(intptr_t)hMem);
#else
    (void)hMem;
    return 0;
#endif
}

int64_t winapi_global_free(int64_t hMem) {
#ifdef _WIN32
    return (int64_t)(intptr_t)GlobalFree((HGLOBAL)(intptr_t)hMem);
#else
    (void)hMem;
    return 0;
#endif
}

int32_t winapi_free_library(int64_t hLibModule) {
#ifdef _WIN32
    return (int32_t)FreeLibrary((HMODULE)(intptr_t)hLibModule);
#else
    (void)hLibModule;
    return 0;
#endif
}

int32_t winapi_set_handle_information(int64_t hObject, uint32_t dwMask, uint32_t dwFlags) {
#ifdef _WIN32
    return (int32_t)SetHandleInformation((HANDLE)(intptr_t)hObject, (DWORD)dwMask, (DWORD)dwFlags);
#else
    (void)hObject;
    (void)dwMask;
    (void)dwFlags;
    return 0;
#endif
}

int32_t winapi_get_handle_information(int64_t hObject, uint32_t *lpdwFlags) {
#ifdef _WIN32
    DWORD flags = 0;
    BOOL res = GetHandleInformation((HANDLE)(intptr_t)hObject, &flags);
    if (res && lpdwFlags != NULL) {
        *lpdwFlags = (uint32_t)flags;
    }
    return (int32_t)res;
#else
    (void)hObject;
    if (lpdwFlags != NULL) {
        *lpdwFlags = 0;
    }
    return 0;
#endif
}

int32_t winapi_duplicate_handle(
    int64_t hSourceProcessHandle,
    int64_t hSourceHandle,
    int64_t hTargetProcessHandle,
    int64_t *lpTargetHandle,
    uint32_t dwDesiredAccess,
    int32_t bInheritHandle,
    uint32_t dwOptions
) {
#ifdef _WIN32
    HANDLE targetHandle = NULL;
    BOOL res = DuplicateHandle(
        (HANDLE)(intptr_t)hSourceProcessHandle,
        (HANDLE)(intptr_t)hSourceHandle,
        (HANDLE)(intptr_t)hTargetProcessHandle,
        &targetHandle,
        (DWORD)dwDesiredAccess,
        (BOOL)bInheritHandle,
        (DWORD)dwOptions
    );
    if (res && lpTargetHandle != NULL) {
        *lpTargetHandle = (int64_t)(intptr_t)targetHandle;
    }
    return (int32_t)res;
#else
    (void)hSourceProcessHandle;
    (void)hSourceHandle;
    (void)hTargetProcessHandle;
    (void)dwDesiredAccess;
    (void)bInheritHandle;
    (void)dwOptions;
    if (lpTargetHandle != NULL) {
        *lpTargetHandle = 0;
    }
    return 0;
#endif
}

int32_t winapi_compare_object_handles(int64_t hFirstObjectHandle, int64_t hSecondObjectHandle) {
#ifdef _WIN32
    return (int32_t)SafeCompareObjectHandles(
        (HANDLE)(intptr_t)hFirstObjectHandle,
        (HANDLE)(intptr_t)hSecondObjectHandle
    );
#else
    (void)hFirstObjectHandle;
    (void)hSecondObjectHandle;
    return 0;
#endif
}

uint32_t winapi_rtl_nt_status_to_dos_error(int32_t status) {
#ifdef _WIN32
    return (uint32_t)SafeRtlNtStatusToDosError((LONG)status);
#else
    (void)status;
    return 0;
#endif
}

int64_t winapi_sys_alloc_string(int64_t psz) {
#ifdef _WIN32
    return (int64_t)(intptr_t)SysAllocString((const OLECHAR *)(intptr_t)psz);
#else
    (void)psz;
    return 0;
#endif
}

int64_t winapi_sys_alloc_string_byte_len(int64_t psz, uint32_t len) {
#ifdef _WIN32
    return (int64_t)(intptr_t)SysAllocStringByteLen((LPCSTR)(intptr_t)psz, (UINT)len);
#else
    (void)psz;
    (void)len;
    return 0;
#endif
}

int64_t winapi_sys_alloc_string_len(int64_t strIn, uint32_t ui) {
#ifdef _WIN32
    return (int64_t)(intptr_t)SysAllocStringLen((const OLECHAR *)(intptr_t)strIn, (UINT)ui);
#else
    (void)strIn;
    (void)ui;
    return 0;
#endif
}

void winapi_sys_free_string(int64_t bstrString) {
#ifdef _WIN32
    SysFreeString((BSTR)(intptr_t)bstrString);
#else
    (void)bstrString;
#endif
}

int32_t winapi_sys_re_alloc_string(int64_t *pbstr, int64_t psz) {
#ifdef _WIN32
    if (pbstr == NULL) return 0;
    BSTR bstr = (BSTR)(intptr_t)(*pbstr);
    INT res = SysReAllocString(&bstr, (const OLECHAR *)(intptr_t)psz);
    *pbstr = (int64_t)(intptr_t)bstr;
    return (int32_t)res;
#else
    (void)pbstr;
    (void)psz;
    return 0;
#endif
}

int32_t winapi_sys_re_alloc_string_len(int64_t *pbstr, int64_t psz, uint32_t len) {
#ifdef _WIN32
    if (pbstr == NULL) return 0;
    BSTR bstr = (BSTR)(intptr_t)(*pbstr);
    INT res = SysReAllocStringLen(&bstr, (const OLECHAR *)(intptr_t)psz, (UINT)len);
    *pbstr = (int64_t)(intptr_t)bstr;
    return (int32_t)res;
#else
    (void)pbstr;
    (void)psz;
    (void)len;
    return 0;
#endif
}

int32_t winapi_sys_add_ref_string(int64_t bstrString) {
#ifdef _WIN32
    return (int32_t)SafeSysAddRefString((BSTR)(intptr_t)bstrString);
#else
    (void)bstrString;
    return 0;
#endif
}

void winapi_sys_release_string(int64_t bstrString) {
#ifdef _WIN32
    SafeSysReleaseString((BSTR)(intptr_t)bstrString);
#else
    (void)bstrString;
#endif
}

uint32_t winapi_sys_string_byte_len(int64_t bstr) {
#ifdef _WIN32
    return (uint32_t)SysStringByteLen((BSTR)(intptr_t)bstr);
#else
    (void)bstr;
    return 0;
#endif
}

uint32_t winapi_sys_string_len(int64_t pbstr) {
#ifdef _WIN32
    return (uint32_t)SysStringLen((BSTR)(intptr_t)pbstr);
#else
    (void)pbstr;
    return 0;
#endif
}

uint32_t winapi_get_file_attributes_w(int64_t lpFileName) {
#ifdef _WIN32
    return (uint32_t)GetFileAttributesW((LPCWSTR)(intptr_t)lpFileName);
#else
    (void)lpFileName;
    return 0xFFFFFFFFU;
#endif
}

int32_t winapi_set_file_attributes_w(int64_t lpFileName, uint32_t dwFileAttributes) {
#ifdef _WIN32
    return (int32_t)SetFileAttributesW((LPCWSTR)(intptr_t)lpFileName, (DWORD)dwFileAttributes);
#else
    (void)lpFileName;
    (void)dwFileAttributes;
    return 0;
#endif
}

uint32_t winapi_get_file_attributes_a(int64_t lpFileName) {
#ifdef _WIN32
    return (uint32_t)GetFileAttributesA((LPCSTR)(intptr_t)lpFileName);
#else
    (void)lpFileName;
    return 0xFFFFFFFFU;
#endif
}

int32_t winapi_set_file_attributes_a(int64_t lpFileName, uint32_t dwFileAttributes) {
#ifdef _WIN32
    return (int32_t)SetFileAttributesA((LPCSTR)(intptr_t)lpFileName, (DWORD)dwFileAttributes);
#else
    (void)lpFileName;
    (void)dwFileAttributes;
    return 0;
#endif
}

int64_t winapi_create_file_w(
    int64_t lpFileName,
    uint32_t dwDesiredAccess,
    uint32_t dwShareMode,
    int64_t lpSecurityAttributes,
    uint32_t dwCreationDisposition,
    uint32_t dwFlagsAndAttributes,
    int64_t hTemplateFile
) {
#ifdef _WIN32
    return (int64_t)(intptr_t)CreateFileW(
        (LPCWSTR)(intptr_t)lpFileName,
        (DWORD)dwDesiredAccess,
        (DWORD)dwShareMode,
        (LPSECURITY_ATTRIBUTES)(intptr_t)lpSecurityAttributes,
        (DWORD)dwCreationDisposition,
        (DWORD)dwFlagsAndAttributes,
        (HANDLE)(intptr_t)hTemplateFile
    );
#else
    (void)lpFileName;
    (void)dwDesiredAccess;
    (void)dwShareMode;
    (void)lpSecurityAttributes;
    (void)dwCreationDisposition;
    (void)dwFlagsAndAttributes;
    (void)hTemplateFile;
    return -1;
#endif
}

int64_t winapi_create_file_a(
    int64_t lpFileName,
    uint32_t dwDesiredAccess,
    uint32_t dwShareMode,
    int64_t lpSecurityAttributes,
    uint32_t dwCreationDisposition,
    uint32_t dwFlagsAndAttributes,
    int64_t hTemplateFile
) {
#ifdef _WIN32
    return (int64_t)(intptr_t)CreateFileA(
        (LPCSTR)(intptr_t)lpFileName,
        (DWORD)dwDesiredAccess,
        (DWORD)dwShareMode,
        (LPSECURITY_ATTRIBUTES)(intptr_t)lpSecurityAttributes,
        (DWORD)dwCreationDisposition,
        (DWORD)dwFlagsAndAttributes,
        (HANDLE)(intptr_t)hTemplateFile
    );
#else
    (void)lpFileName;
    (void)dwDesiredAccess;
    (void)dwShareMode;
    (void)lpSecurityAttributes;
    (void)dwCreationDisposition;
    (void)dwFlagsAndAttributes;
    (void)hTemplateFile;
    return -1;
#endif
}

int32_t winapi_delete_file_w(int64_t lpFileName) {
#ifdef _WIN32
    return (int32_t)DeleteFileW((LPCWSTR)(intptr_t)lpFileName);
#else
    (void)lpFileName;
    return 0;
#endif
}

int32_t winapi_delete_file_a(int64_t lpFileName) {
#ifdef _WIN32
    return (int32_t)DeleteFileA((LPCSTR)(intptr_t)lpFileName);
#else
    (void)lpFileName;
    return 0;
#endif
}

int32_t winapi_flush_file_buffers(int64_t hFile) {
#ifdef _WIN32
    return (int32_t)FlushFileBuffers((HANDLE)(intptr_t)hFile);
#else
    (void)hFile;
    return 0;
#endif
}

int32_t winapi_are_file_apis_ansi(void) {
#ifdef _WIN32
    return (int32_t)AreFileApisANSI();
#else
    return 1;
#endif
}

uint32_t winapi_get_file_size(int64_t hFile, uint32_t *lpFileSizeHigh) {
#ifdef _WIN32
    DWORD high = 0;
    DWORD res = GetFileSize((HANDLE)(intptr_t)hFile, lpFileSizeHigh != NULL ? &high : NULL);
    if (lpFileSizeHigh != NULL) {
        *lpFileSizeHigh = (uint32_t)high;
    }
    return (uint32_t)res;
#else
    (void)hFile;
    if (lpFileSizeHigh != NULL) {
        *lpFileSizeHigh = 0;
    }
    return 0xFFFFFFFFU;
#endif
}

uint32_t winapi_set_file_pointer(
    int64_t hFile,
    int32_t lDistanceToMove,
    int32_t *lpDistanceToMoveHigh,
    uint32_t dwMoveMethod
) {
#ifdef _WIN32
    LONG high = 0;
    if (lpDistanceToMoveHigh != NULL) {
        high = (LONG)(*lpDistanceToMoveHigh);
    }
    DWORD res = SetFilePointer(
        (HANDLE)(intptr_t)hFile,
        (LONG)lDistanceToMove,
        lpDistanceToMoveHigh != NULL ? &high : NULL,
        (DWORD)dwMoveMethod
    );
    if (lpDistanceToMoveHigh != NULL) {
        *lpDistanceToMoveHigh = (int32_t)high;
    }
    return (uint32_t)res;
#else
    (void)hFile;
    (void)lDistanceToMove;
    (void)lpDistanceToMoveHigh;
    (void)dwMoveMethod;
    return 0xFFFFFFFFU;
#endif
}

int64_t winapi_get_current_process(void) {
#ifdef _WIN32
    return (int64_t)(intptr_t)GetCurrentProcess();
#else
    return -1;
#endif
}

uint32_t winapi_get_current_process_id(void) {
#ifdef _WIN32
    return (uint32_t)GetCurrentProcessId();
#else
    return 0;
#endif
}

int64_t winapi_get_current_thread(void) {
#ifdef _WIN32
    return (int64_t)(intptr_t)GetCurrentThread();
#else
    return -2;
#endif
}

uint32_t winapi_get_current_thread_id(void) {
#ifdef _WIN32
    return (uint32_t)GetCurrentThreadId();
#else
    return 0;
#endif
}

int32_t winapi_terminate_process(int64_t hProcess, uint32_t uExitCode) {
#ifdef _WIN32
    return (int32_t)TerminateProcess((HANDLE)(intptr_t)hProcess, (UINT)uExitCode);
#else
    (void)hProcess;
    (void)uExitCode;
    return 0;
#endif
}

void winapi_exit_process(uint32_t uExitCode) {
#ifdef _WIN32
    ExitProcess((UINT)uExitCode);
#else
    (void)uExitCode;
#endif
}

int64_t winapi_open_process(uint32_t dwDesiredAccess, int32_t bInheritHandle, uint32_t dwProcessId) {
#ifdef _WIN32
    return (int64_t)(intptr_t)OpenProcess((DWORD)dwDesiredAccess, (BOOL)bInheritHandle, (DWORD)dwProcessId);
#else
    (void)dwDesiredAccess;
    (void)bInheritHandle;
    (void)dwProcessId;
    return 0;
#endif
}

int32_t winapi_k32_empty_working_set(int64_t hProcess) {
#ifdef _WIN32
    return (int32_t)SafeK32EmptyWorkingSet((HANDLE)(intptr_t)hProcess);
#else
    (void)hProcess;
    return 0;
#endif
}

uint32_t winapi_get_time_zone_information(int64_t lpTimeZoneInformation) {
#ifdef _WIN32
    if (!lpTimeZoneInformation) {
        SetLastError(87);
        return 0xFFFFFFFFU;
    }
    return (uint32_t)GetTimeZoneInformation((LPTIME_ZONE_INFORMATION)(intptr_t)lpTimeZoneInformation);
#else
    (void)lpTimeZoneInformation;
    return 0xFFFFFFFFU;
#endif
}

int32_t winapi_lock_work_station(void) {
#ifdef _WIN32
    return (int32_t)SafeLockWorkStation();
#else
    return 0;
#endif
}

int32_t winapi_disconnect_named_pipe(int64_t hNamedPipe) {
#ifdef _WIN32
    return (int32_t)DisconnectNamedPipe((HANDLE)(intptr_t)hNamedPipe);
#else
    (void)hNamedPipe;
    return 0;
#endif
}
