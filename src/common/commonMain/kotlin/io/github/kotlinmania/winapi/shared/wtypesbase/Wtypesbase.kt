// port-lint: source shared/wtypesbase.rs
package io.github.kotlinmania.winapi.shared.wtypesbase

import io.github.kotlinmania.winapi.CDouble
import io.github.kotlinmania.winapi.CShort
import io.github.kotlinmania.winapi.CUchar
import io.github.kotlinmania.winapi.CUshort
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.LPWSTR
import io.github.kotlinmania.winapi.shared.ntdef.WCHAR
import io.github.kotlinmania.winapi.shared.rpcndr.boolean

public typealias OLECHAR = WCHAR
public typealias LPOLESTR = ULong
public typealias LPCOLESTR = ULong
public typealias UCHAR = CUchar
public typealias SHORT = CShort
public typealias USHORT = CUshort
public typealias ULONG = DWORD
public typealias DOUBLE = CDouble

public data class COAUTHIDENTITY(
    public var User: ULong = 0uL,
    public var UserLength: ULONG = 0u,
    public var Domain: ULong = 0uL,
    public var DomainLength: ULONG = 0u,
    public var Password: ULong = 0uL,
    public var PasswordLength: ULONG = 0u,
    public var Flags: ULONG = 0u,
)

public data class COAUTHINFO(
    public var dwAuthnSvc: DWORD = 0u,
    public var dwAuthzSvc: DWORD = 0u,
    public var pwszServerPrincName: LPWSTR = 0uL,
    public var dwAuthnLevel: DWORD = 0u,
    public var dwImpersonationLevel: DWORD = 0u,
    public var pAuthIdentityData: ULong = 0uL,
    public var dwCapabilities: DWORD = 0u,
)

public typealias SCODE = LONG
public typealias PSCODE = ULong

public typealias MEMCTX = UInt

public const val MEMCTX_TASK: MEMCTX = 1u
public const val MEMCTX_SHARED: MEMCTX = 2u
public const val MEMCTX_MACSYSTEM: MEMCTX = 3u
public const val MEMCTX_UNKNOWN: MEMCTX = 0xFFFFFFFFu
public const val MEMCTX_SAME: MEMCTX = 0xFFFFFFFEu

public const val ROTREGFLAGS_ALLOWANYCLIENT: DWORD = 0x1u
public const val APPIDREGFLAGS_ACTIVATE_IUSERVER_INDESKTOP: DWORD = 0x1u
public const val APPIDREGFLAGS_SECURE_SERVER_PROCESS_SD_AND_BIND: DWORD = 0x2u
public const val APPIDREGFLAGS_ISSUE_ACTIVATION_RPC_AT_IDENTIFY: DWORD = 0x4u
public const val APPIDREGFLAGS_IUSERVER_UNMODIFIED_LOGON_TOKEN: DWORD = 0x8u
public const val APPIDREGFLAGS_IUSERVER_SELF_SID_IN_LAUNCH_PERMISSION: DWORD = 0x10u
public const val APPIDREGFLAGS_IUSERVER_ACTIVATE_IN_CLIENT_SESSION_ONLY: DWORD = 0x20u
public const val APPIDREGFLAGS_RESERVED1: DWORD = 0x40u
public const val APPIDREGFLAGS_RESERVED2: DWORD = 0x80u
public const val APPIDREGFLAGS_RESERVED3: DWORD = 0x100u
public const val APPIDREGFLAGS_RESERVED4: DWORD = 0x200u
public const val APPIDREGFLAGS_RESERVED5: DWORD = 0x400u
public const val APPIDREGFLAGS_RESERVED6: DWORD = 0x800u
public const val DCOMSCM_ACTIVATION_USE_ALL_AUTHNSERVICES: DWORD = 0x1u
public const val DCOMSCM_ACTIVATION_DISALLOW_UNSECURE_CALL: DWORD = 0x2u
public const val DCOMSCM_RESOLVE_USE_ALL_AUTHNSERVICES: DWORD = 0x4u
public const val DCOMSCM_RESOLVE_DISALLOW_UNSECURE_CALL: DWORD = 0x8u
public const val DCOMSCM_PING_USE_MID_AUTHNSERVICE: DWORD = 0x10u
public const val DCOMSCM_PING_DISALLOW_UNSECURE_CALL: DWORD = 0x20u

public typealias CLSCTX = UInt

public const val CLSCTX_INPROC_SERVER: CLSCTX = 0x1u
public const val CLSCTX_INPROC_HANDLER: CLSCTX = 0x2u
public const val CLSCTX_LOCAL_SERVER: CLSCTX = 0x4u
public const val CLSCTX_INPROC_SERVER16: CLSCTX = 0x8u
public const val CLSCTX_REMOTE_SERVER: CLSCTX = 0x10u
public const val CLSCTX_INPROC_HANDLER16: CLSCTX = 0x20u
public const val CLSCTX_RESERVED1: CLSCTX = 0x40u
public const val CLSCTX_RESERVED2: CLSCTX = 0x80u
public const val CLSCTX_RESERVED3: CLSCTX = 0x100u
public const val CLSCTX_RESERVED4: CLSCTX = 0x200u
public const val CLSCTX_NO_CODE_DOWNLOAD: CLSCTX = 0x400u
public const val CLSCTX_RESERVED5: CLSCTX = 0x800u
public const val CLSCTX_NO_CUSTOM_MARSHAL: CLSCTX = 0x1000u
public const val CLSCTX_ENABLE_CODE_DOWNLOAD: CLSCTX = 0x2000u
public const val CLSCTX_NO_FAILURE_LOG: CLSCTX = 0x4000u
public const val CLSCTX_DISABLE_AAA: CLSCTX = 0x8000u
public const val CLSCTX_ENABLE_AAA: CLSCTX = 0x10000u
public const val CLSCTX_FROM_DEFAULT_CONTEXT: CLSCTX = 0x20000u
public const val CLSCTX_ACTIVATE_32_BIT_SERVER: CLSCTX = 0x40000u
public const val CLSCTX_ACTIVATE_64_BIT_SERVER: CLSCTX = 0x80000u
public const val CLSCTX_ENABLE_CLOAKING: CLSCTX = 0x100000u
public const val CLSCTX_APPCONTAINER: CLSCTX = 0x400000u
public const val CLSCTX_ACTIVATE_AAA_AS_IU: CLSCTX = 0x800000u
public const val CLSCTX_PS_DLL: CLSCTX = 0x80000000u

public const val CLSCTX_VALID_MASK: CLSCTX = 0x80DDD51Fu

public typealias MSHLFLAGS = UInt

public const val MSHLFLAGS_NORMAL: MSHLFLAGS = 0u
public const val MSHLFLAGS_TABLESTRONG: MSHLFLAGS = 1u
public const val MSHLFLAGS_TABLEWEAK: MSHLFLAGS = 2u
public const val MSHLFLAGS_NOPING: MSHLFLAGS = 4u
public const val MSHLFLAGS_RESERVED1: MSHLFLAGS = 8u
public const val MSHLFLAGS_RESERVED2: MSHLFLAGS = 16u
public const val MSHLFLAGS_RESERVED3: MSHLFLAGS = 32u
public const val MSHLFLAGS_RESERVED4: MSHLFLAGS = 64u

public typealias MSHCTX = UInt

public const val MSHCTX_LOCAL: MSHCTX = 0u
public const val MSHCTX_NOSHAREDMEM: MSHCTX = 1u
public const val MSHCTX_DIFFERENTMACHINE: MSHCTX = 2u
public const val MSHCTX_INPROC: MSHCTX = 3u
public const val MSHCTX_CROSSCTX: MSHCTX = 4u

public class BYTE_BLOB(
    public var clSize: ULONG = 0u,
    public var abData: UByteArray = UByteArray(1),
)

public typealias UP_BYTE_BLOB = ULong

public class WORD_BLOB(
    public var clSize: ULONG = 0u,
    public var asData: UShortArray = UShortArray(1),
)

public typealias UP_WORD_BLOB = ULong

public class DWORD_BLOB(
    public var clSize: ULONG = 0u,
    public var alData: UIntArray = UIntArray(1),
)

public typealias UP_DWORD_BLOB = ULong

public class FLAGGED_BYTE_BLOB(
    public var fFlags: ULONG = 0u,
    public var clSize: ULONG = 0u,
    public var abData: UByteArray = UByteArray(1),
)

public typealias UP_FLAGGED_BYTE_BLOB = ULong

public class FLAGGED_WORD_BLOB(
    public var fFlags: ULONG = 0u,
    public var clSize: ULONG = 0u,
    public var alData: UIntArray = UIntArray(1),
)

public typealias UP_FLAGGED_WORD_BLOB = ULong

public data class BYTE_SIZEDARR(
    public var clSize: ULONG = 0u,
    public var pData: ULong = 0uL,
)

public data class WORD_SIZEDARR(
    public var clSize: ULONG = 0u,
    public var pData: ULong = 0uL,
)

public data class DWORD_SIZEDARR(
    public var clSize: ULONG = 0u,
    public var pData: ULong = 0uL,
)

public data class HYPER_SIZEDARR(
    public var clSize: ULONG = 0u,
    public var pData: ULong = 0uL,
)

public typealias BOOLEAN = boolean

public data class BLOB(
    public var cbSize: ULONG = 0u,
    public var pBlobData: ULong = 0uL,
)

public typealias LPBLOB = ULong
