#include <napi.h>

#if defined(_WIN32) || defined(WIN32)
#define WIN32_LEAN_AND_MEAN
#include <windows.h>
#include <oleauto.h>
#define IS_WINDOWS 1

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
    if (bstrString == NULL) return (HRESULT)0x80070057L;
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
    if (bstrString == NULL) return;
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

#else
#define IS_WINDOWS 0
#endif

Napi::Value CloseHandleBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t handleVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BOOL result = CloseHandle(reinterpret_cast<HANDLE>(static_cast<intptr_t>(handleVal)));
    return Napi::Number::New(env, result);
#else
    (void)handleVal;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value GetLastErrorBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
#if IS_WINDOWS
    DWORD err = GetLastError();
    return Napi::Number::New(env, static_cast<int32_t>(err));
#else
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SetLastErrorBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    if (info.Length() >= 1) {
        uint32_t err = info[0].As<Napi::Number>().Uint32Value();
#if IS_WINDOWS
        SetLastError(err);
#else
        (void)err;
#endif
    }
    return env.Undefined();
}

Napi::Value SetLastErrorExBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    if (info.Length() >= 2) {
        uint32_t err = info[0].As<Napi::Number>().Uint32Value();
        uint32_t type = info[1].As<Napi::Number>().Uint32Value();
#if IS_WINDOWS
        SafeSetLastErrorEx(err, type);
#else
        (void)err;
        (void)type;
#endif
    }
    return env.Undefined();
}

Napi::Value LocalFreeBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t ptrVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    HLOCAL res = LocalFree(reinterpret_cast<HLOCAL>(static_cast<intptr_t>(ptrVal)));
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(res));
#else
    (void)ptrVal;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value GlobalFreeBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t ptrVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    HGLOBAL res = GlobalFree(reinterpret_cast<HGLOBAL>(static_cast<intptr_t>(ptrVal)));
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(res));
#else
    (void)ptrVal;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value FreeLibraryBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t h = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BOOL result = FreeLibrary(reinterpret_cast<HMODULE>(static_cast<intptr_t>(h)));
    return Napi::Number::New(env, result);
#else
    (void)h;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SetHandleInformationBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t handleVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    uint32_t mask = info.Length() >= 2 ? info[1].As<Napi::Number>().Uint32Value() : 0;
    uint32_t flags = info.Length() >= 3 ? info[2].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    BOOL result = SetHandleInformation(
        reinterpret_cast<HANDLE>(static_cast<intptr_t>(handleVal)),
        static_cast<DWORD>(mask),
        static_cast<DWORD>(flags)
    );
    return Napi::Number::New(env, result);
#else
    (void)handleVal;
    (void)mask;
    (void)flags;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value GetHandleInformationBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t handleVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    DWORD flags = 0;
    BOOL result = GetHandleInformation(reinterpret_cast<HANDLE>(static_cast<intptr_t>(handleVal)), &flags);
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("success", Napi::Number::New(env, result));
    obj.Set("flags", Napi::Number::New(env, static_cast<int32_t>(flags)));
    return obj;
#else
    (void)handleVal;
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("success", Napi::Number::New(env, 0));
    obj.Set("flags", Napi::Number::New(env, 0));
    return obj;
#endif
}

Napi::Value CompareObjectHandlesBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t h1 = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    int64_t h2 = info.Length() >= 2 ? info[1].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BOOL result = SafeCompareObjectHandles(
        reinterpret_cast<HANDLE>(static_cast<intptr_t>(h1)),
        reinterpret_cast<HANDLE>(static_cast<intptr_t>(h2))
    );
    return Napi::Number::New(env, result);
#else
    (void)h1;
    (void)h2;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value DuplicateHandleBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t hSourceProcess = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    int64_t hSource = info.Length() >= 2 ? info[1].As<Napi::Number>().Int64Value() : 0;
    int64_t hTargetProcess = info.Length() >= 3 ? info[2].As<Napi::Number>().Int64Value() : 0;
    uint32_t dwDesiredAccess = info.Length() >= 4 ? info[3].As<Napi::Number>().Uint32Value() : 0;
    int32_t bInheritHandle = info.Length() >= 5 ? info[4].As<Napi::Number>().Int32Value() : 0;
    uint32_t dwOptions = info.Length() >= 6 ? info[5].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    HANDLE targetHandle = NULL;
    BOOL result = DuplicateHandle(
        reinterpret_cast<HANDLE>(static_cast<intptr_t>(hSourceProcess)),
        reinterpret_cast<HANDLE>(static_cast<intptr_t>(hSource)),
        reinterpret_cast<HANDLE>(static_cast<intptr_t>(hTargetProcess)),
        &targetHandle,
        static_cast<DWORD>(dwDesiredAccess),
        bInheritHandle != 0 ? TRUE : FALSE,
        static_cast<DWORD>(dwOptions)
    );
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("success", Napi::Number::New(env, result));
    obj.Set("targetHandle", Napi::Number::New(env, reinterpret_cast<intptr_t>(targetHandle)));
    return obj;
#else
    (void)hSourceProcess;
    (void)hSource;
    (void)hTargetProcess;
    (void)dwDesiredAccess;
    (void)bInheritHandle;
    (void)dwOptions;
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("success", Napi::Number::New(env, 0));
    obj.Set("targetHandle", Napi::Number::New(env, 0));
    return obj;
#endif
}

Napi::Value RtlNtStatusToDosErrorBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int32_t status = info.Length() >= 1 ? info[0].As<Napi::Number>().Int32Value() : 0;
#if IS_WINDOWS
    ULONG err = SafeRtlNtStatusToDosError(static_cast<LONG>(status));
    return Napi::Number::New(env, static_cast<int32_t>(err));
#else
    (void)status;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SysAllocStringBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t psz = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BSTR res = SysAllocString(reinterpret_cast<const OLECHAR*>(static_cast<intptr_t>(psz)));
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(res));
#else
    (void)psz;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SysAllocStringByteLenBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t psz = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    uint32_t len = info.Length() >= 2 ? info[1].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    BSTR res = SysAllocStringByteLen(reinterpret_cast<LPCSTR>(static_cast<intptr_t>(psz)), len);
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(res));
#else
    (void)psz;
    (void)len;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SysAllocStringLenBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t strIn = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    uint32_t ui = info.Length() >= 2 ? info[1].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    BSTR res = SysAllocStringLen(reinterpret_cast<const OLECHAR*>(static_cast<intptr_t>(strIn)), ui);
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(res));
#else
    (void)strIn;
    (void)ui;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SysFreeStringBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    if (info.Length() >= 1) {
        int64_t bstr = info[0].As<Napi::Number>().Int64Value();
#if IS_WINDOWS
        SysFreeString(reinterpret_cast<BSTR>(static_cast<intptr_t>(bstr)));
#else
        (void)bstr;
#endif
    }
    return env.Undefined();
}

Napi::Value SysReAllocStringBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t currentVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    int64_t psz = info.Length() >= 2 ? info[1].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BSTR bstr = reinterpret_cast<BSTR>(static_cast<intptr_t>(currentVal));
    INT res = SysReAllocString(&bstr, reinterpret_cast<const OLECHAR*>(static_cast<intptr_t>(psz)));
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("result", Napi::Number::New(env, res));
    obj.Set("bstr", Napi::Number::New(env, reinterpret_cast<intptr_t>(bstr)));
    return obj;
#else
    (void)psz;
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("result", Napi::Number::New(env, 0));
    obj.Set("bstr", Napi::Number::New(env, currentVal));
    return obj;
#endif
}

Napi::Value SysReAllocStringLenBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t currentVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    int64_t psz = info.Length() >= 2 ? info[1].As<Napi::Number>().Int64Value() : 0;
    uint32_t len = info.Length() >= 3 ? info[2].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    BSTR bstr = reinterpret_cast<BSTR>(static_cast<intptr_t>(currentVal));
    INT res = SysReAllocStringLen(&bstr, reinterpret_cast<const OLECHAR*>(static_cast<intptr_t>(psz)), len);
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("result", Napi::Number::New(env, res));
    obj.Set("bstr", Napi::Number::New(env, reinterpret_cast<intptr_t>(bstr)));
    return obj;
#else
    (void)psz;
    (void)len;
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("result", Napi::Number::New(env, 0));
    obj.Set("bstr", Napi::Number::New(env, currentVal));
    return obj;
#endif
}

Napi::Value SysAddRefStringBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t bstr = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    HRESULT hr = SafeSysAddRefString(reinterpret_cast<BSTR>(static_cast<intptr_t>(bstr)));
    return Napi::Number::New(env, hr);
#else
    (void)bstr;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SysReleaseStringBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    if (info.Length() >= 1) {
        int64_t bstr = info[0].As<Napi::Number>().Int64Value();
#if IS_WINDOWS
        SafeSysReleaseString(reinterpret_cast<BSTR>(static_cast<intptr_t>(bstr)));
#else
        (void)bstr;
#endif
    }
    return env.Undefined();
}

Napi::Value SysStringByteLenBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t bstr = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    UINT len = SysStringByteLen(reinterpret_cast<BSTR>(static_cast<intptr_t>(bstr)));
    return Napi::Number::New(env, static_cast<int32_t>(len));
#else
    (void)bstr;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SysStringLenBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t pbstr = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    UINT len = SysStringLen(reinterpret_cast<BSTR>(static_cast<intptr_t>(pbstr)));
    return Napi::Number::New(env, static_cast<int32_t>(len));
#else
    (void)pbstr;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SetFileAttributesWBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t path = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    uint32_t attr = info.Length() >= 2 ? info[1].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    BOOL res = SetFileAttributesW(reinterpret_cast<LPCWSTR>(static_cast<intptr_t>(path)), attr);
    return Napi::Number::New(env, res);
#else
    (void)path;
    (void)attr;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value SetFileAttributesABinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t path = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    uint32_t attr = info.Length() >= 2 ? info[1].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    BOOL res = SetFileAttributesA(reinterpret_cast<LPCSTR>(static_cast<intptr_t>(path)), attr);
    return Napi::Number::New(env, res);
#else
    (void)path;
    (void)attr;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value GetFileAttributesWBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t path = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    DWORD res = GetFileAttributesW(reinterpret_cast<LPCWSTR>(static_cast<intptr_t>(path)));
    return Napi::Number::New(env, static_cast<int32_t>(res));
#else
    (void)path;
    return Napi::Number::New(env, static_cast<int32_t>(0xFFFFFFFF));
#endif
}

Napi::Value GetFileAttributesABinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t path = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    DWORD res = GetFileAttributesA(reinterpret_cast<LPCSTR>(static_cast<intptr_t>(path)));
    return Napi::Number::New(env, static_cast<int32_t>(res));
#else
    (void)path;
    return Napi::Number::New(env, static_cast<int32_t>(0xFFFFFFFF));
#endif
}

Napi::Value CreateFileWBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t lpFileName = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    uint32_t dwDesiredAccess = info.Length() >= 2 ? info[1].As<Napi::Number>().Uint32Value() : 0;
    uint32_t dwShareMode = info.Length() >= 3 ? info[2].As<Napi::Number>().Uint32Value() : 0;
    int64_t lpSecurityAttributes = info.Length() >= 4 ? info[3].As<Napi::Number>().Int64Value() : 0;
    uint32_t dwCreationDisposition = info.Length() >= 5 ? info[4].As<Napi::Number>().Uint32Value() : 0;
    uint32_t dwFlagsAndAttributes = info.Length() >= 6 ? info[5].As<Napi::Number>().Uint32Value() : 0;
    int64_t hTemplateFile = info.Length() >= 7 ? info[6].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    HANDLE h = CreateFileW(
        reinterpret_cast<LPCWSTR>(static_cast<intptr_t>(lpFileName)),
        dwDesiredAccess,
        dwShareMode,
        reinterpret_cast<LPSECURITY_ATTRIBUTES>(static_cast<intptr_t>(lpSecurityAttributes)),
        dwCreationDisposition,
        dwFlagsAndAttributes,
        reinterpret_cast<HANDLE>(static_cast<intptr_t>(hTemplateFile))
    );
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(h));
#else
    (void)lpFileName;
    (void)dwDesiredAccess;
    (void)dwShareMode;
    (void)lpSecurityAttributes;
    (void)dwCreationDisposition;
    (void)dwFlagsAndAttributes;
    (void)hTemplateFile;
    return Napi::Number::New(env, -1);
#endif
}

Napi::Value CreateFileABinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t lpFileName = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    uint32_t dwDesiredAccess = info.Length() >= 2 ? info[1].As<Napi::Number>().Uint32Value() : 0;
    uint32_t dwShareMode = info.Length() >= 3 ? info[2].As<Napi::Number>().Uint32Value() : 0;
    int64_t lpSecurityAttributes = info.Length() >= 4 ? info[3].As<Napi::Number>().Int64Value() : 0;
    uint32_t dwCreationDisposition = info.Length() >= 5 ? info[4].As<Napi::Number>().Uint32Value() : 0;
    uint32_t dwFlagsAndAttributes = info.Length() >= 6 ? info[5].As<Napi::Number>().Uint32Value() : 0;
    int64_t hTemplateFile = info.Length() >= 7 ? info[6].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    HANDLE h = CreateFileA(
        reinterpret_cast<LPCSTR>(static_cast<intptr_t>(lpFileName)),
        dwDesiredAccess,
        dwShareMode,
        reinterpret_cast<LPSECURITY_ATTRIBUTES>(static_cast<intptr_t>(lpSecurityAttributes)),
        dwCreationDisposition,
        dwFlagsAndAttributes,
        reinterpret_cast<HANDLE>(static_cast<intptr_t>(hTemplateFile))
    );
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(h));
#else
    (void)lpFileName;
    (void)dwDesiredAccess;
    (void)dwShareMode;
    (void)lpSecurityAttributes;
    (void)dwCreationDisposition;
    (void)dwFlagsAndAttributes;
    (void)hTemplateFile;
    return Napi::Number::New(env, -1);
#endif
}

Napi::Value DeleteFileWBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t path = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BOOL res = DeleteFileW(reinterpret_cast<LPCWSTR>(static_cast<intptr_t>(path)));
    return Napi::Number::New(env, res);
#else
    (void)path;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value DeleteFileABinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t path = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BOOL res = DeleteFileA(reinterpret_cast<LPCSTR>(static_cast<intptr_t>(path)));
    return Napi::Number::New(env, res);
#else
    (void)path;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value FlushFileBuffersBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t h = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BOOL res = FlushFileBuffers(reinterpret_cast<HANDLE>(static_cast<intptr_t>(h)));
    return Napi::Number::New(env, res);
#else
    (void)h;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value AreFileApisANSIBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
#if IS_WINDOWS
    BOOL res = AreFileApisANSI();
    return Napi::Number::New(env, res);
#else
    return Napi::Number::New(env, 1);
#endif
}

Napi::Value GetFileSizeBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t hFile = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    bool hasHigh = info.Length() >= 2 && info[1].As<Napi::Number>().Int32Value() != 0;
#if IS_WINDOWS
    DWORD high = 0;
    DWORD low = GetFileSize(reinterpret_cast<HANDLE>(static_cast<intptr_t>(hFile)), hasHigh ? &high : NULL);
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("low", Napi::Number::New(env, static_cast<int32_t>(low)));
    obj.Set("high", Napi::Number::New(env, static_cast<int32_t>(high)));
    return obj;
#else
    (void)hFile;
    (void)hasHigh;
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("low", Napi::Number::New(env, static_cast<int32_t>(0xFFFFFFFF)));
    obj.Set("high", Napi::Number::New(env, 0));
    return obj;
#endif
}

Napi::Value SetFilePointerBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t hFile = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    int32_t lDistanceToMove = info.Length() >= 2 ? info[1].As<Napi::Number>().Int32Value() : 0;
    bool hasHigh = info.Length() >= 3 && info[2].As<Napi::Number>().Int32Value() != 0;
    int32_t inHigh = info.Length() >= 4 ? info[3].As<Napi::Number>().Int32Value() : 0;
    uint32_t dwMoveMethod = info.Length() >= 5 ? info[4].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    LONG high = static_cast<LONG>(inHigh);
    DWORD low = SetFilePointer(
        reinterpret_cast<HANDLE>(static_cast<intptr_t>(hFile)),
        static_cast<LONG>(lDistanceToMove),
        hasHigh ? &high : NULL,
        dwMoveMethod
    );
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("low", Napi::Number::New(env, static_cast<int32_t>(low)));
    obj.Set("high", Napi::Number::New(env, static_cast<int32_t>(high)));
    return obj;
#else
    (void)hFile;
    (void)lDistanceToMove;
    (void)hasHigh;
    (void)dwMoveMethod;
    Napi::Object obj = Napi::Object::New(env);
    obj.Set("low", Napi::Number::New(env, static_cast<int32_t>(0xFFFFFFFF)));
    obj.Set("high", Napi::Number::New(env, inHigh));
    return obj;
#endif
}

Napi::Value GetCurrentProcessBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
#if IS_WINDOWS
    HANDLE h = GetCurrentProcess();
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(h));
#else
    return Napi::Number::New(env, -1);
#endif
}

Napi::Value GetCurrentProcessIdBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
#if IS_WINDOWS
    DWORD pid = GetCurrentProcessId();
    return Napi::Number::New(env, static_cast<int32_t>(pid));
#else
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value GetCurrentThreadBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
#if IS_WINDOWS
    HANDLE h = GetCurrentThread();
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(h));
#else
    return Napi::Number::New(env, -2);
#endif
}

Napi::Value GetCurrentThreadIdBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
#if IS_WINDOWS
    DWORD tid = GetCurrentThreadId();
    return Napi::Number::New(env, static_cast<int32_t>(tid));
#else
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value TerminateProcessBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t h = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
    uint32_t code = info.Length() >= 2 ? info[1].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    BOOL res = TerminateProcess(reinterpret_cast<HANDLE>(static_cast<intptr_t>(h)), code);
    return Napi::Number::New(env, res);
#else
    (void)h;
    (void)code;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value ExitProcessBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    if (info.Length() >= 1) {
        uint32_t code = info[0].As<Napi::Number>().Uint32Value();
#if IS_WINDOWS
        ExitProcess(code);
#else
        (void)code;
#endif
    }
    return env.Undefined();
}

Napi::Value OpenProcessBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    uint32_t access = info.Length() >= 1 ? info[0].As<Napi::Number>().Uint32Value() : 0;
    int32_t inherit = info.Length() >= 2 ? info[1].As<Napi::Number>().Int32Value() : 0;
    uint32_t pid = info.Length() >= 3 ? info[2].As<Napi::Number>().Uint32Value() : 0;
#if IS_WINDOWS
    HANDLE h = OpenProcess(access, inherit != 0 ? TRUE : FALSE, pid);
    return Napi::Number::New(env, reinterpret_cast<intptr_t>(h));
#else
    (void)access;
    (void)inherit;
    (void)pid;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value K32EmptyWorkingSetBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t handleVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BOOL res = SafeK32EmptyWorkingSet(reinterpret_cast<HANDLE>(static_cast<intptr_t>(handleVal)));
    return Napi::Number::New(env, res);
#else
    (void)handleVal;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value GetTimeZoneInformationBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t ptrVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    if (ptrVal == 0) {
        SetLastError(87);
        return Napi::Number::New(env, static_cast<int32_t>(0xFFFFFFFF));
    }
    DWORD res = GetTimeZoneInformation(reinterpret_cast<LPTIME_ZONE_INFORMATION>(static_cast<intptr_t>(ptrVal)));
    return Napi::Number::New(env, static_cast<int32_t>(res));
#else
    (void)ptrVal;
    return Napi::Number::New(env, static_cast<int32_t>(0xFFFFFFFF));
#endif
}

Napi::Value LockWorkStationBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
#if IS_WINDOWS
    BOOL res = SafeLockWorkStation();
    return Napi::Number::New(env, res);
#else
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value DisconnectNamedPipeBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    int64_t handleVal = info.Length() >= 1 ? info[0].As<Napi::Number>().Int64Value() : 0;
#if IS_WINDOWS
    BOOL res = DisconnectNamedPipe(reinterpret_cast<HANDLE>(static_cast<intptr_t>(handleVal)));
    return Napi::Number::New(env, res);
#else
    (void)handleVal;
    return Napi::Number::New(env, 0);
#endif
}

Napi::Value IsNativeAvailableBinding(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();
    return Napi::Boolean::New(env, IS_WINDOWS != 0);
}

Napi::Object Init(Napi::Env env, Napi::Object exports) {
    exports.Set("CloseHandle", Napi::Function::New(env, CloseHandleBinding));
    exports.Set("GetLastError", Napi::Function::New(env, GetLastErrorBinding));
    exports.Set("SetLastError", Napi::Function::New(env, SetLastErrorBinding));
    exports.Set("SetLastErrorEx", Napi::Function::New(env, SetLastErrorExBinding));
    exports.Set("LocalFree", Napi::Function::New(env, LocalFreeBinding));
    exports.Set("GlobalFree", Napi::Function::New(env, GlobalFreeBinding));
    exports.Set("FreeLibrary", Napi::Function::New(env, FreeLibraryBinding));
    exports.Set("SetHandleInformation", Napi::Function::New(env, SetHandleInformationBinding));
    exports.Set("GetHandleInformation", Napi::Function::New(env, GetHandleInformationBinding));
    exports.Set("CompareObjectHandles", Napi::Function::New(env, CompareObjectHandlesBinding));
    exports.Set("DuplicateHandle", Napi::Function::New(env, DuplicateHandleBinding));
    exports.Set("RtlNtStatusToDosError", Napi::Function::New(env, RtlNtStatusToDosErrorBinding));
    exports.Set("SysAllocString", Napi::Function::New(env, SysAllocStringBinding));
    exports.Set("SysAllocStringByteLen", Napi::Function::New(env, SysAllocStringByteLenBinding));
    exports.Set("SysAllocStringLen", Napi::Function::New(env, SysAllocStringLenBinding));
    exports.Set("SysFreeString", Napi::Function::New(env, SysFreeStringBinding));
    exports.Set("SysReAllocString", Napi::Function::New(env, SysReAllocStringBinding));
    exports.Set("SysReAllocStringLen", Napi::Function::New(env, SysReAllocStringLenBinding));
    exports.Set("SysAddRefString", Napi::Function::New(env, SysAddRefStringBinding));
    exports.Set("SysReleaseString", Napi::Function::New(env, SysReleaseStringBinding));
    exports.Set("SysStringByteLen", Napi::Function::New(env, SysStringByteLenBinding));
    exports.Set("SysStringLen", Napi::Function::New(env, SysStringLenBinding));
    exports.Set("SetFileAttributesW", Napi::Function::New(env, SetFileAttributesWBinding));
    exports.Set("SetFileAttributesA", Napi::Function::New(env, SetFileAttributesABinding));
    exports.Set("GetFileAttributesW", Napi::Function::New(env, GetFileAttributesWBinding));
    exports.Set("GetFileAttributesA", Napi::Function::New(env, GetFileAttributesABinding));
    exports.Set("CreateFileW", Napi::Function::New(env, CreateFileWBinding));
    exports.Set("CreateFileA", Napi::Function::New(env, CreateFileABinding));
    exports.Set("DeleteFileW", Napi::Function::New(env, DeleteFileWBinding));
    exports.Set("DeleteFileA", Napi::Function::New(env, DeleteFileABinding));
    exports.Set("FlushFileBuffers", Napi::Function::New(env, FlushFileBuffersBinding));
    exports.Set("AreFileApisANSI", Napi::Function::New(env, AreFileApisANSIBinding));
    exports.Set("GetFileSize", Napi::Function::New(env, GetFileSizeBinding));
    exports.Set("SetFilePointer", Napi::Function::New(env, SetFilePointerBinding));
    exports.Set("GetCurrentProcess", Napi::Function::New(env, GetCurrentProcessBinding));
    exports.Set("GetCurrentProcessId", Napi::Function::New(env, GetCurrentProcessIdBinding));
    exports.Set("GetCurrentThread", Napi::Function::New(env, GetCurrentThreadBinding));
    exports.Set("GetCurrentThreadId", Napi::Function::New(env, GetCurrentThreadIdBinding));
    exports.Set("TerminateProcess", Napi::Function::New(env, TerminateProcessBinding));
    exports.Set("ExitProcess", Napi::Function::New(env, ExitProcessBinding));
    exports.Set("OpenProcess", Napi::Function::New(env, OpenProcessBinding));
    exports.Set("K32EmptyWorkingSet", Napi::Function::New(env, K32EmptyWorkingSetBinding));
    exports.Set("GetTimeZoneInformation", Napi::Function::New(env, GetTimeZoneInformationBinding));
    exports.Set("LockWorkStation", Napi::Function::New(env, LockWorkStationBinding));
    exports.Set("DisconnectNamedPipe", Napi::Function::New(env, DisconnectNamedPipeBinding));
    exports.Set("isNativeAvailable", Napi::Function::New(env, IsNativeAvailableBinding));
    return exports;
}

NODE_API_MODULE(winapi_native, Init)
