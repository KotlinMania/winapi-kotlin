// port-lint: source winapi/src/shared/windowsx.rs
package io.github.kotlinmania.winapi.shared.windowsx

import io.github.kotlinmania.winapi.CInt
import io.github.kotlinmania.winapi.shared.minwindef.HIWORD
import io.github.kotlinmania.winapi.shared.minwindef.LOWORD
import io.github.kotlinmania.winapi.shared.minwindef.LPARAM

// Macro APIs, window message crackers, and control APIs

fun GET_X_LPARAM(lp: LPARAM): CInt = LOWORD(lp.toUInt()).toShort().toInt()

fun GET_Y_LPARAM(lp: LPARAM): CInt = HIWORD(lp.toUInt()).toShort().toInt()
