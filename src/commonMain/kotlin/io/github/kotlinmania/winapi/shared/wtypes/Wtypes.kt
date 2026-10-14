// port-lint: source shared/wtypes.rs
package io.github.kotlinmania.winapi.shared.wtypes

import io.github.kotlinmania.winapi.CDouble
import io.github.kotlinmania.winapi.CShort
import io.github.kotlinmania.winapi.CUshort
import io.github.kotlinmania.winapi.shared.guiddef.CLSID
import io.github.kotlinmania.winapi.shared.guiddef.GUID
import io.github.kotlinmania.winapi.shared.minwindef.BYTE
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.shared.minwindef.USHORT
import io.github.kotlinmania.winapi.shared.minwindef.WORD
import io.github.kotlinmania.winapi.shared.ntdef.LCID
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.LONGLONG
import io.github.kotlinmania.winapi.shared.ntdef.ULONGLONG
import io.github.kotlinmania.winapi.shared.wtypesbase.LPOLESTR

public class RemHGLOBAL(
    public var fNullHGlobal: LONG = 0,
    public var cbData: ULONG = 0u,
    public var data: UByteArray = UByteArray(1),
)

public class RemHMETAFILEPICT(
    public var mm: LONG = 0,
    public var xExt: LONG = 0,
    public var yExt: LONG = 0,
    public var cbData: ULONG = 0u,
    public var data: UByteArray = UByteArray(1),
)

public class RemHENHMETAFILE(
    public var cbData: ULONG = 0u,
    public var data: UByteArray = UByteArray(1),
)

public class RemHBITMAP(
    public var cbData: ULONG = 0u,
    public var data: UByteArray = UByteArray(1),
)

public class RemHPALETTE(
    public var cbData: ULONG = 0u,
    public var data: UByteArray = UByteArray(1),
)

public class RemHBRUSH(
    public var cbData: ULONG = 0u,
    public var data: UByteArray = UByteArray(1),
)

public const val ROTFLAGS_REGISTRATIONKEEPSALIVE: DWORD = 0x1u
public const val ROTFLAGS_ALLOWANYCLIENT: DWORD = 0x2u
public const val ROT_COMPARE_MAX: DWORD = 2048u

public typealias DVASPECT = UInt

public const val DVASPECT_CONTENT: DVASPECT = 1u
public const val DVASPECT_THUMBNAIL: DVASPECT = 2u
public const val DVASPECT_ICON: DVASPECT = 4u
public const val DVASPECT_DOCPRINT: DVASPECT = 8u

public typealias STGC = UInt

public const val STGC_DEFAULT: STGC = 0u
public const val STGC_OVERWRITE: STGC = 1u
public const val STGC_ONLYIFCURRENT: STGC = 2u
public const val STGC_DANGEROUSLYCOMMITMERELYTODISKCACHE: STGC = 4u
public const val STGC_CONSOLIDATE: STGC = 8u

public typealias STGMOVE = UInt

public const val STGMOVE_MOVE: STGMOVE = 0u
public const val STGMOVE_COPY: STGMOVE = 1u
public const val STGMOVE_SHALLOWCOPY: STGMOVE = 2u

public typealias STATFLAG = UInt

public const val STATFLAG_DEFAULT: STATFLAG = 0u
public const val STATFLAG_NONAME: STATFLAG = 1u
public const val STATFLAG_NOOPEN: STATFLAG = 2u

public typealias HCONTEXT = ULong

public const val WDT_INPROC_CALL: ULONG = 0x48746457u
public const val WDT_REMOTE_CALL: ULONG = 0x52746457u
public const val WDT_INPROC64_CALL: ULONG = 0x50746457u

public data class userCLIPFORMAT_u(
    public var raw: ULong = 0uL,
) {
    public var dwValue: DWORD
        get() = raw.toUInt()
        set(value) {
            raw = value.toULong()
        }
    public var pwszName: ULong
        get() = raw
        set(value) {
            raw = value
        }
}

public data class userCLIPFORMAT(
    public var fContext: LONG = 0,
    public var u: userCLIPFORMAT_u = userCLIPFORMAT_u(),
)

public typealias wireCLIPFORMAT = ULong
public typealias CLIPFORMAT = WORD

public data class GDI_NONREMOTE_u(
    public var raw: ULong = 0uL,
) {
    public var hInproc: LONG
        get() = raw.toInt()
        set(value) {
            raw = value.toUInt().toULong()
        }
    public var hRemote: ULong
        get() = raw
        set(value) {
            raw = value
        }
}

public data class GDI_NONREMOTE(
    public var fContext: LONG = 0,
    public var u: GDI_NONREMOTE_u = GDI_NONREMOTE_u(),
)

public data class userHGLOBAL_u(
    public var raw: ULong = 0uL,
) {
    public var hInproc: LONG
        get() = raw.toInt()
        set(value) {
            raw = value.toUInt().toULong()
        }
    public var hRemote: ULong
        get() = raw
        set(value) {
            raw = value
        }
    public var hInproc64: Long
        get() = raw.toLong()
        set(value) {
            raw = value.toULong()
        }
}

public data class userHGLOBAL(
    public var fContext: LONG = 0,
    public var u: userHGLOBAL_u = userHGLOBAL_u(),
)

public typealias wireHGLOBAL = ULong

public typealias userHMETAFILE_u = userHGLOBAL_u

public data class userHMETAFILE(
    public var fContext: LONG = 0,
    public var u: userHMETAFILE_u = userHMETAFILE_u(),
)

public data class remoteMETAFILEPICT(
    public var mm: LONG = 0,
    public var xExt: LONG = 0,
    public var yExt: LONG = 0,
    public var hMF: ULong = 0uL,
)

public typealias userHMETAFILEPICT_u = userHGLOBAL_u

public data class userHMETAFILEPICT(
    public var fContext: LONG = 0,
    public var u: userHMETAFILEPICT_u = userHMETAFILEPICT_u(),
)

public typealias userHENHMETAFILE_u = userHGLOBAL_u

public data class userHENHMETAFILE(
    public var fContext: LONG = 0,
    public var u: userHENHMETAFILE_u = userHENHMETAFILE_u(),
)

public class userBITMAP(
    public var bmType: LONG = 0,
    public var bmWidth: LONG = 0,
    public var bmHeight: LONG = 0,
    public var bmWidthBytes: LONG = 0,
    public var bmPlanes: WORD = 0u,
    public var bmBitsPixel: WORD = 0u,
    public var cbSize: ULONG = 0u,
    public var pBuffer: UByteArray = UByteArray(1),
)

public typealias userHBITMAP_u = userHGLOBAL_u

public data class userHBITMAP(
    public var fContext: LONG = 0,
    public var u: userHBITMAP_u = userHBITMAP_u(),
)

public typealias userHPALETTE_u = userHGLOBAL_u

public data class userHPALETTE(
    public var fContext: LONG = 0,
    public var u: userHPALETTE_u = userHPALETTE_u(),
)

public data class RemotableHandle_u(
    public var raw: UInt = 0u,
) {
    public var hInproc: LONG
        get() = raw.toInt()
        set(value) {
            raw = value.toUInt()
        }
    public var hRemote: LONG
        get() = raw.toInt()
        set(value) {
            raw = value.toUInt()
        }
}

public data class RemotableHandle(
    public var fContext: LONG = 0,
    public var u: RemotableHandle_u = RemotableHandle_u(),
)

public typealias wireHWND = ULong
public typealias wireHMENU = ULong
public typealias wireHACCEL = ULong
public typealias wireHBRUSH = ULong
public typealias wireHFONT = ULong
public typealias wireHDC = ULong
public typealias wireHICON = ULong
public typealias wireHRGN = ULong
public typealias wireHMONITOR = ULong
public typealias wireHBITMAP = ULong
public typealias wireHPALETTE = ULong
public typealias wireHENHMETAFILE = ULong
public typealias wireHMETAFILE = ULong
public typealias wireHMETAFILEPICT = ULong
public typealias HMETAFILEPICT = ULong
public typealias DATE = CDouble

public data class CY(
    public var int64: LONGLONG = 0L,
)

public typealias LPCY = ULong

public data class DECIMAL(
    public var wReserved: USHORT = 0u,
    public var scale: BYTE = 0u,
    public var sign: BYTE = 0u,
    public var Hi32: ULONG = 0u,
    public var Lo64: ULONGLONG = 0uL,
)

public const val DECIMAL_NEG: BYTE = 0x80u

public fun DECIMAL_SETZERO(dec: DECIMAL) {
    dec.Lo64 = 0uL
    dec.Hi32 = 0u
    dec.scale = 0u
    dec.sign = 0u
}

public typealias LPDECIMAL = ULong
public typealias wireBSTR = ULong
public typealias BSTR = ULong
public typealias LPBSTR = ULong
public typealias VARIANT_BOOL = CShort

public data class BSTRBLOB(
    public var cbSize: ULONG = 0u,
    public var pData: ULong = 0uL,
)

public typealias LPBSTRBLOB = ULong

public const val VARIANT_TRUE: VARIANT_BOOL = -1
public const val VARIANT_FALSE: VARIANT_BOOL = 0

public data class CLIPDATA(
    public var cbSize: ULONG = 0u,
    public var ulClipFmt: LONG = 0,
    public var pClipData: ULong = 0uL,
)

public fun CBPCLIPDATA(clipdata: CLIPDATA): ULONG = clipdata.cbSize - 4u

public typealias VARTYPE = CUshort
public typealias VARENUM = UInt

public const val VT_EMPTY: VARENUM = 0u
public const val VT_NULL: VARENUM = 1u
public const val VT_I2: VARENUM = 2u
public const val VT_I4: VARENUM = 3u
public const val VT_R4: VARENUM = 4u
public const val VT_R8: VARENUM = 5u
public const val VT_CY: VARENUM = 6u
public const val VT_DATE: VARENUM = 7u
public const val VT_BSTR: VARENUM = 8u
public const val VT_DISPATCH: VARENUM = 9u
public const val VT_ERROR: VARENUM = 10u
public const val VT_BOOL: VARENUM = 11u
public const val VT_VARIANT: VARENUM = 12u
public const val VT_UNKNOWN: VARENUM = 13u
public const val VT_DECIMAL: VARENUM = 14u
public const val VT_I1: VARENUM = 16u
public const val VT_UI1: VARENUM = 17u
public const val VT_UI2: VARENUM = 18u
public const val VT_UI4: VARENUM = 19u
public const val VT_I8: VARENUM = 20u
public const val VT_UI8: VARENUM = 21u
public const val VT_INT: VARENUM = 22u
public const val VT_UINT: VARENUM = 23u
public const val VT_VOID: VARENUM = 24u
public const val VT_HRESULT: VARENUM = 25u
public const val VT_PTR: VARENUM = 26u
public const val VT_SAFEARRAY: VARENUM = 27u
public const val VT_CARRAY: VARENUM = 28u
public const val VT_USERDEFINED: VARENUM = 29u
public const val VT_LPSTR: VARENUM = 30u
public const val VT_LPWSTR: VARENUM = 31u
public const val VT_RECORD: VARENUM = 36u
public const val VT_INT_PTR: VARENUM = 37u
public const val VT_UINT_PTR: VARENUM = 38u
public const val VT_FILETIME: VARENUM = 64u
public const val VT_BLOB: VARENUM = 65u
public const val VT_STREAM: VARENUM = 66u
public const val VT_STORAGE: VARENUM = 67u
public const val VT_STREAMED_OBJECT: VARENUM = 68u
public const val VT_STORED_OBJECT: VARENUM = 69u
public const val VT_BLOB_OBJECT: VARENUM = 70u
public const val VT_CF: VARENUM = 71u
public const val VT_CLSID: VARENUM = 72u
public const val VT_VERSIONED_STREAM: VARENUM = 73u
public const val VT_BSTR_BLOB: VARENUM = 0xfffu
public const val VT_VECTOR: VARENUM = 0x1000u
public const val VT_ARRAY: VARENUM = 0x2000u
public const val VT_BYREF: VARENUM = 0x4000u
public const val VT_RESERVED: VARENUM = 0x8000u
public const val VT_ILLEGAL: VARENUM = 0xffffu
public const val VT_ILLEGALMASKED: VARENUM = 0xfffu
public const val VT_TYPEMASK: VARENUM = 0xfffu

public typealias PROPID = ULONG

public data class PROPERTYKEY(
    public var fmtid: GUID = io.github.kotlinmania.winapi.shared.guiddef.IID_NULL,
    public var pid: DWORD = 0u,
)

public data class CSPLATFORM(
    public var dwPlatformId: DWORD = 0u,
    public var dwVersionHi: DWORD = 0u,
    public var dwVersionLo: DWORD = 0u,
    public var dwProcessorArch: DWORD = 0u,
)

public data class QUERYCONTEXT(
    public var dwContext: DWORD = 0u,
    public var Platform: CSPLATFORM = CSPLATFORM(),
    public var Locale: LCID = 0u,
    public var dwVersionHi: DWORD = 0u,
    public var dwVersionLo: DWORD = 0u,
)

public typealias TYSPEC = UInt

public const val TYSPEC_CLSID: TYSPEC = 0u
public const val TYSPEC_FILEEXT: TYSPEC = 1u
public const val TYSPEC_MIMETYPE: TYSPEC = 2u
public const val TYSPEC_FILENAME: TYSPEC = 3u
public const val TYSPEC_PROGID: TYSPEC = 4u
public const val TYSPEC_PACKAGENAME: TYSPEC = 5u
public const val TYSPEC_OBJECTID: TYSPEC = 6u

public data class uCLSSPEC_ByName(
    public var pPackageName: LPOLESTR = 0uL,
    public var PolicyId: GUID = io.github.kotlinmania.winapi.shared.guiddef.IID_NULL,
)

public data class uCLSSPEC_ByObjectId(
    public var ObjectId: GUID = io.github.kotlinmania.winapi.shared.guiddef.IID_NULL,
    public var PolicyId: GUID = io.github.kotlinmania.winapi.shared.guiddef.IID_NULL,
)

public class uCLSSPEC_u(
    public var clsid: CLSID = io.github.kotlinmania.winapi.shared.guiddef.IID_NULL,
    public var pFileExt: LPOLESTR = 0uL,
    public var pMimeType: LPOLESTR = 0uL,
    public var pProgId: LPOLESTR = 0uL,
    public var pFileName: LPOLESTR = 0uL,
    public var ByName: uCLSSPEC_ByName = uCLSSPEC_ByName(),
    public var ByObjectId: uCLSSPEC_ByObjectId = uCLSSPEC_ByObjectId(),
)

public data class uCLSSPEC(
    public var tyspec: DWORD = 0u,
    public var u: uCLSSPEC_u = uCLSSPEC_u(),
)
