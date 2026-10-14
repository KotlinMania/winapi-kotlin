// port-lint: source um/libloaderapi.rs
package io.github.kotlinmania.winapi.um.libloaderapi

import io.github.kotlinmania.winapi.internal.WinapiJni
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.HMODULE

public actual fun FreeLibrary(hLibModule: HMODULE): BOOL =
    WinapiJni.FreeLibrary(hLibModule.toLong())
