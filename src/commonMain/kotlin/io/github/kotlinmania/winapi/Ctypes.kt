// port-lint: source lib.rs
package io.github.kotlinmania.winapi

// Parceled from upstream lib.rs `pub mod ctypes { ... }`. The
// upstream module documents itself as "Built in primitive types
// provided by the C language" and aliases the C-FFI numeric types
// onto Rust fixed-width integers. Kotlin's stdlib already provides
// fixed-width integers, so the aliases map straight through.

/**
 * The opaque C `void` type. Upstream defines `c_void` as
 * `pub use std::os::raw::c_void` under the `std` feature and as the
 * uninhabited `pub enum c_void {}` under `#![no_std]`. The Kotlin port
 * collapses both flavors onto a sealed class with no subclasses, which
 * matches the uninhabited shape: no caller can construct a `CVoid`, so
 * the type is only ever referenced through pointer aliases such as
 * `LPVOID`.
 */
sealed class CVoid

typealias CChar = Byte
typealias CSchar = Byte
typealias CUchar = UByte
typealias CShort = Short
typealias CUshort = UShort
typealias CInt = Int
typealias CUint = UInt
typealias CLong = Int
typealias CUlong = UInt
typealias CLonglong = Long
typealias CUlonglong = ULong
typealias CFloat = Float
typealias CDouble = Double
typealias Int8 = Byte
typealias Uint8 = UByte
typealias Int16 = Short
typealias Uint16 = UShort
typealias Int32 = Int
typealias Uint32 = UInt
typealias Int64 = Long
typealias Uint64 = ULong
typealias WcharT = UShort
