// port-lint: source um/namedpipeapi.rs
package io.github.kotlinmania.winapi.um.namedpipeapi

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
public actual fun DisconnectNamedPipe(hNamedPipe: HANDLE): BOOL =
    WinapiNapiWasm.DisconnectNamedPipe(hNamedPipe.toLong())
