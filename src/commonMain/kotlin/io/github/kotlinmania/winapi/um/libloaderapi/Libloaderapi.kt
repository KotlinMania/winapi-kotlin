// port-lint: source um/libloaderapi.rs
package io.github.kotlinmania.winapi.um.libloaderapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.HMODULE
import io.github.kotlinmania.winapi.um.winnt.PVOID

public const val GET_MODULE_HANDLE_EX_FLAG_PIN: DWORD = 0x00000001u
public const val GET_MODULE_HANDLE_EX_FLAG_UNCHANGED_REFCOUNT: DWORD = 0x00000002u
public const val GET_MODULE_HANDLE_EX_FLAG_FROM_ADDRESS: DWORD = 0x00000004u
public const val DONT_RESOLVE_DLL_REFERENCES: DWORD = 0x00000001u
public const val LOAD_LIBRARY_AS_DATAFILE: DWORD = 0x00000002u
public const val LOAD_WITH_ALTERED_SEARCH_PATH: DWORD = 0x00000008u
public const val LOAD_IGNORE_CODE_AUTHZ_LEVEL: DWORD = 0x00000010u
public const val LOAD_LIBRARY_AS_IMAGE_RESOURCE: DWORD = 0x00000020u
public const val LOAD_LIBRARY_AS_DATAFILE_EXCLUSIVE: DWORD = 0x00000040u
public const val LOAD_LIBRARY_REQUIRE_SIGNED_TARGET: DWORD = 0x00000080u
public const val LOAD_LIBRARY_SEARCH_DLL_LOAD_DIR: DWORD = 0x00000100u
public const val LOAD_LIBRARY_SEARCH_APPLICATION_DIR: DWORD = 0x00000200u
public const val LOAD_LIBRARY_SEARCH_USER_DIRS: DWORD = 0x00000400u
public const val LOAD_LIBRARY_SEARCH_SYSTEM32: DWORD = 0x00000800u
public const val LOAD_LIBRARY_SEARCH_DEFAULT_DIRS: DWORD = 0x00001000u
public const val LOAD_LIBRARY_SAFE_CURRENT_DIRS: DWORD = 0x00002000u
public const val LOAD_LIBRARY_SEARCH_SYSTEM32_NO_FORWARDER: DWORD = 0x00004000u
public const val LOAD_LIBRARY_OS_INTEGRITY_CONTINUITY: DWORD = 0x00008000u

public typealias ENUMRESLANGPROCA = ULong
public typealias ENUMRESLANGPROCW = ULong
public typealias ENUMRESNAMEPROCA = ULong
public typealias ENUMRESNAMEPROCW = ULong
public typealias ENUMRESTYPEPROCA = ULong
public typealias ENUMRESTYPEPROCW = ULong
public typealias DLL_DIRECTORY_COOKIE = PVOID
public typealias PDLL_DIRECTORY_COOKIE = ULong

public expect fun FreeLibrary(hLibModule: HMODULE): BOOL
