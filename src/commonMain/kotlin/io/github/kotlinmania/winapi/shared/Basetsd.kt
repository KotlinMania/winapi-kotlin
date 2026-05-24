// port-lint: source shared/basetsd.rs
package io.github.kotlinmania.winapi.shared

import io.github.kotlinmania.winapi.CInt
import io.github.kotlinmania.winapi.CSchar
import io.github.kotlinmania.winapi.CShort
import io.github.kotlinmania.winapi.CUchar
import io.github.kotlinmania.winapi.CUint
import io.github.kotlinmania.winapi.CUshort
import io.github.kotlinmania.winapi.Int64
import io.github.kotlinmania.winapi.Uint64

// Type definitions for the basic sized types.
//
// Rust upstream conditionally compiles the half-pointer and pointer-sized
// types on `target_pointer_width`. The Kotlin port targets 64-bit hosts
// only (mingwX64 is the only Windows native target in the matrix), so
// each `#[cfg(target_pointer_width = "64")]` branch is what survives and
// the 32-bit branch is dropped as inapplicable.
//
// Rust pointer types `*mut T` translate to ULong (pointer value) on the
// 64-bit Kotlin port: callers manipulate the pointer as an opaque
// address token and never dereference it from Kotlin.

typealias POINTER_64_INT = ULong
typealias INT8 = CSchar
typealias PINT8 = ULong
typealias INT16 = CShort
typealias PINT16 = ULong
typealias INT32 = CInt
typealias PINT32 = ULong
typealias INT64 = Int64
typealias PINT64 = ULong
typealias UINT8 = CUchar
typealias PUINT8 = ULong
typealias UINT16 = CUshort
typealias PUINT16 = ULong
typealias UINT32 = CUint
typealias PUINT32 = ULong
typealias UINT64 = Uint64
typealias PUINT64 = ULong
typealias LONG32 = CInt
typealias PLONG32 = ULong
typealias ULONG32 = CUint
typealias PULONG32 = ULong
typealias DWORD32 = CUint
typealias PDWORD32 = ULong
typealias INT_PTR = Long
typealias PINT_PTR = ULong
typealias UINT_PTR = ULong
typealias PUINT_PTR = ULong
typealias LONG_PTR = Long
typealias PLONG_PTR = ULong
typealias ULONG_PTR = ULong
typealias PULONG_PTR = ULong
typealias SHANDLE_PTR = Long
typealias HANDLE_PTR = ULong
typealias UHALF_PTR = CUint
typealias PUHALF_PTR = ULong
typealias HALF_PTR = CInt
typealias PHALF_PTR = ULong
typealias SIZE_T = ULONG_PTR
typealias PSIZE_T = ULong
typealias SSIZE_T = LONG_PTR
typealias PSSIZE_T = ULong
typealias DWORD_PTR = ULONG_PTR
typealias PDWORD_PTR = ULong
typealias LONG64 = Int64
typealias PLONG64 = ULong
typealias ULONG64 = Uint64
typealias PULONG64 = ULong
typealias DWORD64 = Uint64
typealias PDWORD64 = ULong
typealias KAFFINITY = ULONG_PTR
typealias PKAFFINITY = ULong
