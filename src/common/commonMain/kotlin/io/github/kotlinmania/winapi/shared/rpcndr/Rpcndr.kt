// port-lint: source shared/rpcndr.rs
package io.github.kotlinmania.winapi.shared.rpcndr

import io.github.kotlinmania.winapi.CChar
import io.github.kotlinmania.winapi.CUchar
import io.github.kotlinmania.winapi.CUlong
import io.github.kotlinmania.winapi.Int64
import io.github.kotlinmania.winapi.Uint64

public const val NDR_CHAR_REP_MASK: CUlong = 0x0000000Fu
public const val NDR_INT_REP_MASK: CUlong = 0x000000F0u
public const val NDR_FLOAT_REP_MASK: CUlong = 0x0000FF00u
public const val NDR_LITTLE_ENDIAN: CUlong = 0x00000010u
public const val NDR_BIG_ENDIAN: CUlong = 0x00000000u
public const val NDR_IEEE_FLOAT: CUlong = 0x00000000u
public const val NDR_VAX_FLOAT: CUlong = 0x00000100u
public const val NDR_IBM_FLOAT: CUlong = 0x00000300u
public const val NDR_ASCII_CHAR: CUlong = 0x00000000u
public const val NDR_EBCDIC_CHAR: CUlong = 0x00000001u
public const val NDR_LOCAL_DATA_REPRESENTATION: CUlong = 0x00000010u
public const val NDR_LOCAL_ENDIAN: CUlong = NDR_LITTLE_ENDIAN

public typealias small = CChar
public typealias byte = CUchar
public typealias CsByte = byte
public typealias boolean = CUchar
public typealias hyper = Int64
public typealias MIDL_UHYPER = Uint64
