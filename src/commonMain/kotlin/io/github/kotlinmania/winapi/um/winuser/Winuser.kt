// port-lint: source um/winuser.rs
package io.github.kotlinmania.winapi.um.winuser

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD

public const val SLE_ERROR: DWORD = 0x00000001u
public const val SLE_MINORERROR: DWORD = 0x00000002u
public const val SLE_WARNING: DWORD = 0x00000003u

public expect fun SetLastErrorEx(dwErrCode: DWORD, dwType: DWORD)

public expect fun LockWorkStation(): BOOL
