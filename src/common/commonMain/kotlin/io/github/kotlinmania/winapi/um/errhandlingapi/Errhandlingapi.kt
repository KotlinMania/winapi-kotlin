// port-lint: source um/errhandlingapi.rs
package io.github.kotlinmania.winapi.um.errhandlingapi

import io.github.kotlinmania.winapi.shared.minwindef.DWORD

public typealias PTOP_LEVEL_EXCEPTION_FILTER = ULong
public typealias LPTOP_LEVEL_EXCEPTION_FILTER = PTOP_LEVEL_EXCEPTION_FILTER

public expect fun GetLastError(): DWORD

public expect fun SetLastError(dwErrCode: DWORD)
