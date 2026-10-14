// port-lint: source um/namedpipeapi.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.namedpipeapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.um.winnt.HANDLE
import winapi.cinterop.winapi_disconnect_named_pipe

public actual fun DisconnectNamedPipe(hNamedPipe: HANDLE): BOOL =
    winapi_disconnect_named_pipe(hNamedPipe.toLong())
