package io.github.kotlinmania.winapi

// Built in primitive types provided by the C language. Aliases the C-FFI
// numeric types onto Kotlin fixed-width integers.

/**
 * The opaque C void type. The Kotlin port represents this as a sealed class
 * with no subclasses, matching the uninhabited shape: no caller can construct
 * a CVoid, so the type is only referenced through pointer aliases such as LPVOID.
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
