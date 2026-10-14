// port-lint: source um/namedpipeapi.rs
package io.github.kotlinmania.winapi.um.namedpipeapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.um.winnt.HANDLE

public actual fun DisconnectNamedPipe(hNamedPipe: HANDLE): BOOL = 0
