#include <jni.h>
#include <stdint.h>

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

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_CloseHandle
  (JNIEnv *env, jclass cls, jlong hObject) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)CloseHandle((HANDLE)(intptr_t)hObject);
#else
    (void)hObject;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetLastError
  (JNIEnv *env, jclass cls) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)GetLastError();
#else
    return 0;
#endif
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SetLastError
  (JNIEnv *env, jclass cls, jint dwErrCode) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    SetLastError((DWORD)dwErrCode);
#else
    (void)dwErrCode;
#endif
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SetLastErrorEx
  (JNIEnv *env, jclass cls, jint dwErrCode, jint dwType) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    SafeSetLastErrorEx((DWORD)dwErrCode, (DWORD)dwType);
#else
    (void)dwErrCode;
    (void)dwType;
#endif
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_LocalFree
  (JNIEnv *env, jclass cls, jlong hMem) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)LocalFree((HLOCAL)(intptr_t)hMem);
#else
    (void)hMem;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SetHandleInformation
  (JNIEnv *env, jclass cls, jlong hObject, jint dwMask, jint dwFlags) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SetHandleInformation((HANDLE)(intptr_t)hObject, (DWORD)dwMask, (DWORD)dwFlags);
#else
    (void)hObject;
    (void)dwMask;
    (void)dwFlags;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetHandleInformation
  (JNIEnv *env, jclass cls, jlong hObject, jintArray lpdwFlags) {
    (void)cls;
#ifdef _WIN32
    DWORD flags = 0;
    BOOL res = GetHandleInformation((HANDLE)(intptr_t)hObject, &flags);
    if (res && lpdwFlags != NULL) {
        jint val = (jint)flags;
        (*env)->SetIntArrayRegion(env, lpdwFlags, 0, 1, &val);
    }
    return (jint)res;
#else
    (void)env;
    (void)hObject;
    (void)lpdwFlags;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_DuplicateHandle
  (JNIEnv *env, jclass cls, jlong hSourceProcessHandle, jlong hSourceHandle, jlong hTargetProcessHandle,
   jlongArray lpTargetHandle, jint dwDesiredAccess, jint bInheritHandle, jint dwOptions) {
    (void)cls;
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
        jlong val = (jlong)(intptr_t)targetHandle;
        (*env)->SetLongArrayRegion(env, lpTargetHandle, 0, 1, &val);
    }
    return (jint)res;
#else
    (void)env;
    (void)hSourceProcessHandle;
    (void)hSourceHandle;
    (void)hTargetProcessHandle;
    (void)lpTargetHandle;
    (void)dwDesiredAccess;
    (void)bInheritHandle;
    (void)dwOptions;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_CompareObjectHandles
  (JNIEnv *env, jclass cls, jlong hFirstObjectHandle, jlong hSecondObjectHandle) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SafeCompareObjectHandles((HANDLE)(intptr_t)hFirstObjectHandle, (HANDLE)(intptr_t)hSecondObjectHandle);
#else
    (void)hFirstObjectHandle;
    (void)hSecondObjectHandle;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_FreeLibrary
  (JNIEnv *env, jclass cls, jlong hLibModule) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)FreeLibrary((HMODULE)(intptr_t)hLibModule);
#else
    (void)hLibModule;
    return 0;
#endif
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GlobalFree
  (JNIEnv *env, jclass cls, jlong hMem) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)GlobalFree((HGLOBAL)(intptr_t)hMem);
#else
    (void)hMem;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_RtlNtStatusToDosError
  (JNIEnv *env, jclass cls, jint status) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SafeRtlNtStatusToDosError((LONG)status);
#else
    (void)status;
    return 0;
#endif
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysAllocString
  (JNIEnv *env, jclass cls, jlong psz) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)SysAllocString((const OLECHAR *)(intptr_t)psz);
#else
    (void)psz;
    return 0;
#endif
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysAllocStringByteLen
  (JNIEnv *env, jclass cls, jlong psz, jint len) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)SysAllocStringByteLen((LPCSTR)(intptr_t)psz, (UINT)len);
#else
    (void)psz;
    (void)len;
    return 0;
#endif
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysAllocStringLen
  (JNIEnv *env, jclass cls, jlong strIn, jint ui) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)SysAllocStringLen((const OLECHAR *)(intptr_t)strIn, (UINT)ui);
#else
    (void)strIn;
    (void)ui;
    return 0;
#endif
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysFreeString
  (JNIEnv *env, jclass cls, jlong bstrString) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    SysFreeString((BSTR)(intptr_t)bstrString);
#else
    (void)bstrString;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysReAllocString
  (JNIEnv *env, jclass cls, jlongArray pbstr, jlong psz) {
    (void)cls;
#ifdef _WIN32
    if (pbstr == NULL) return 0;
    jlong currentVal = 0;
    (*env)->GetLongArrayRegion(env, pbstr, 0, 1, &currentVal);
    BSTR bstr = (BSTR)(intptr_t)currentVal;
    int res = SysReAllocString(&bstr, (const OLECHAR *)(intptr_t)psz);
    jlong newVal = (jlong)(intptr_t)bstr;
    (*env)->SetLongArrayRegion(env, pbstr, 0, 1, &newVal);
    return res;
#else
    (void)env;
    (void)pbstr;
    (void)psz;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysReAllocStringLen
  (JNIEnv *env, jclass cls, jlongArray pbstr, jlong psz, jint len) {
    (void)cls;
#ifdef _WIN32
    if (pbstr == NULL) return 0;
    jlong currentVal = 0;
    (*env)->GetLongArrayRegion(env, pbstr, 0, 1, &currentVal);
    BSTR bstr = (BSTR)(intptr_t)currentVal;
    int res = SysReAllocStringLen(&bstr, (const OLECHAR *)(intptr_t)psz, (UINT)len);
    jlong newVal = (jlong)(intptr_t)bstr;
    (*env)->SetLongArrayRegion(env, pbstr, 0, 1, &newVal);
    return res;
#else
    (void)env;
    (void)pbstr;
    (void)psz;
    (void)len;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysAddRefString
  (JNIEnv *env, jclass cls, jlong bstrString) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SafeSysAddRefString((BSTR)(intptr_t)bstrString);
#else
    (void)bstrString;
    return 0;
#endif
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysReleaseString
  (JNIEnv *env, jclass cls, jlong bstrString) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    SafeSysReleaseString((BSTR)(intptr_t)bstrString);
#else
    (void)bstrString;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysStringByteLen
  (JNIEnv *env, jclass cls, jlong bstr) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SysStringByteLen((BSTR)(intptr_t)bstr);
#else
    (void)bstr;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SysStringLen
  (JNIEnv *env, jclass cls, jlong pbstr) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SysStringLen((BSTR)(intptr_t)pbstr);
#else
    (void)pbstr;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SetFileAttributesW
  (JNIEnv *env, jclass cls, jlong lpFileName, jint dwFileAttributes) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SetFileAttributesW((LPCWSTR)(intptr_t)lpFileName, (DWORD)dwFileAttributes);
#else
    (void)lpFileName;
    (void)dwFileAttributes;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SetFileAttributesA
  (JNIEnv *env, jclass cls, jlong lpFileName, jint dwFileAttributes) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SetFileAttributesA((LPCSTR)(intptr_t)lpFileName, (DWORD)dwFileAttributes);
#else
    (void)lpFileName;
    (void)dwFileAttributes;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetFileAttributesW
  (JNIEnv *env, jclass cls, jlong lpFileName) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)GetFileAttributesW((LPCWSTR)(intptr_t)lpFileName);
#else
    (void)lpFileName;
    return -1;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetFileAttributesA
  (JNIEnv *env, jclass cls, jlong lpFileName) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)GetFileAttributesA((LPCSTR)(intptr_t)lpFileName);
#else
    (void)lpFileName;
    return -1;
#endif
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_CreateFileW
  (JNIEnv *env, jclass cls, jlong lpFileName, jint dwDesiredAccess, jint dwShareMode,
   jlong lpSecurityAttributes, jint dwCreationDisposition, jint dwFlagsAndAttributes, jlong hTemplateFile) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)CreateFileW(
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

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_CreateFileA
  (JNIEnv *env, jclass cls, jlong lpFileName, jint dwDesiredAccess, jint dwShareMode,
   jlong lpSecurityAttributes, jint dwCreationDisposition, jint dwFlagsAndAttributes, jlong hTemplateFile) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)CreateFileA(
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

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_DeleteFileW
  (JNIEnv *env, jclass cls, jlong lpFileName) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)DeleteFileW((LPCWSTR)(intptr_t)lpFileName);
#else
    (void)lpFileName;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_DeleteFileA
  (JNIEnv *env, jclass cls, jlong lpFileName) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)DeleteFileA((LPCSTR)(intptr_t)lpFileName);
#else
    (void)lpFileName;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_FlushFileBuffers
  (JNIEnv *env, jclass cls, jlong hFile) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)FlushFileBuffers((HANDLE)(intptr_t)hFile);
#else
    (void)hFile;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_AreFileApisANSI
  (JNIEnv *env, jclass cls) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)AreFileApisANSI();
#else
    return 1;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetFileSize
  (JNIEnv *env, jclass cls, jlong hFile, jintArray lpFileSizeHigh) {
    (void)cls;
#ifdef _WIN32
    DWORD high = 0;
    DWORD res = GetFileSize((HANDLE)(intptr_t)hFile, lpFileSizeHigh != NULL ? &high : NULL);
    if (lpFileSizeHigh != NULL) {
        jint val = (jint)high;
        (*env)->SetIntArrayRegion(env, lpFileSizeHigh, 0, 1, &val);
    }
    return (jint)res;
#else
    (void)env;
    (void)hFile;
    (void)lpFileSizeHigh;
    return -1;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_SetFilePointer
  (JNIEnv *env, jclass cls, jlong hFile, jint lDistanceToMove, jintArray lpDistanceToMoveHigh, jint dwMoveMethod) {
    (void)cls;
#ifdef _WIN32
    LONG high = 0;
    if (lpDistanceToMoveHigh != NULL) {
        jint inHigh = 0;
        (*env)->GetIntArrayRegion(env, lpDistanceToMoveHigh, 0, 1, &inHigh);
        high = (LONG)inHigh;
    }
    DWORD res = SetFilePointer(
        (HANDLE)(intptr_t)hFile,
        (LONG)lDistanceToMove,
        lpDistanceToMoveHigh != NULL ? &high : NULL,
        (DWORD)dwMoveMethod
    );
    if (lpDistanceToMoveHigh != NULL) {
        jint outHigh = (jint)high;
        (*env)->SetIntArrayRegion(env, lpDistanceToMoveHigh, 0, 1, &outHigh);
    }
    return (jint)res;
#else
    (void)env;
    (void)hFile;
    (void)lDistanceToMove;
    (void)lpDistanceToMoveHigh;
    (void)dwMoveMethod;
    return -1;
#endif
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetCurrentProcess
  (JNIEnv *env, jclass cls) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)GetCurrentProcess();
#else
    return -1;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetCurrentProcessId
  (JNIEnv *env, jclass cls) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)GetCurrentProcessId();
#else
    return 0;
#endif
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetCurrentThread
  (JNIEnv *env, jclass cls) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)GetCurrentThread();
#else
    return -2;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetCurrentThreadId
  (JNIEnv *env, jclass cls) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)GetCurrentThreadId();
#else
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_TerminateProcess
  (JNIEnv *env, jclass cls, jlong hProcess, jint uExitCode) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)TerminateProcess((HANDLE)(intptr_t)hProcess, (UINT)uExitCode);
#else
    (void)hProcess;
    (void)uExitCode;
    return 0;
#endif
}

JNIEXPORT void JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_ExitProcess
  (JNIEnv *env, jclass cls, jint uExitCode) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    ExitProcess((UINT)uExitCode);
#else
    (void)uExitCode;
#endif
}

JNIEXPORT jlong JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_OpenProcess
  (JNIEnv *env, jclass cls, jint dwDesiredAccess, jint bInheritHandle, jint dwProcessId) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jlong)(intptr_t)OpenProcess((DWORD)dwDesiredAccess, (BOOL)bInheritHandle, (DWORD)dwProcessId);
#else
    (void)dwDesiredAccess;
    (void)bInheritHandle;
    (void)dwProcessId;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_K32EmptyWorkingSet
  (JNIEnv *env, jclass cls, jlong hProcess) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SafeK32EmptyWorkingSet((HANDLE)(intptr_t)hProcess);
#else
    (void)hProcess;
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_GetTimeZoneInformation
  (JNIEnv *env, jclass cls, jlong lpTimeZoneInformation) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    if (!lpTimeZoneInformation) {
        SetLastError(87);
        return (jint)0xFFFFFFFF;
    }
    return (jint)GetTimeZoneInformation((LPTIME_ZONE_INFORMATION)(intptr_t)lpTimeZoneInformation);
#else
    (void)lpTimeZoneInformation;
    return (jint)0xFFFFFFFF;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_LockWorkStation
  (JNIEnv *env, jclass cls) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)SafeLockWorkStation();
#else
    return 0;
#endif
}

JNIEXPORT jint JNICALL Java_io_github_kotlinmania_winapi_internal_WinapiJni_DisconnectNamedPipe
  (JNIEnv *env, jclass cls, jlong hNamedPipe) {
    (void)env;
    (void)cls;
#ifdef _WIN32
    return (jint)DisconnectNamedPipe((HANDLE)(intptr_t)hNamedPipe);
#else
    (void)hNamedPipe;
    return 0;
#endif
}

#ifdef __cplusplus
}
#endif
