// port-lint: source um/oleauto.rs
package io.github.kotlinmania.winapi.um.oleauto

import io.github.kotlinmania.winapi.shared.minwindef.INT
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.minwindef.WORD
import io.github.kotlinmania.winapi.shared.wtypes.BSTR
import io.github.kotlinmania.winapi.shared.wtypesbase.LPCOLESTR
import io.github.kotlinmania.winapi.shared.ntdef.HRESULT
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.LPCSTR
public typealias DISPID = LONG
public typealias MEMBERID = DISPID

public const val DISPID_UNKNOWN: DISPID = -1
public const val MEMBERID_NIL: MEMBERID = DISPID_UNKNOWN

public const val DISPATCH_METHOD: WORD = 0x1u
public const val DISPATCH_PROPERTYGET: WORD = 0x2u
public const val DISPATCH_PROPERTYPUT: WORD = 0x4u
public const val DISPATCH_PROPERTYPUTREF: WORD = 0x8u

public typealias REGKIND = UInt

public const val REGKIND_DEFAULT: REGKIND = 0u
public const val REGKIND_REGISTER: REGKIND = 1u
public const val REGKIND_NONE: REGKIND = 2u

public expect fun SysAllocString(psz: LPCOLESTR): BSTR

public expect fun SysReAllocString(pbstr: ULongArray, psz: LPCOLESTR): INT

public expect fun SysAllocStringLen(strIn: LPCOLESTR, ui: UINT): BSTR

public expect fun SysReAllocStringLen(pbstr: ULongArray, psz: LPCOLESTR, len: UINT): INT

public expect fun SysFreeString(bstrString: BSTR)

public expect fun SysStringLen(pbstr: BSTR): UINT

public expect fun SysStringByteLen(bstr: BSTR): UINT

public expect fun SysAllocStringByteLen(psz: LPCSTR, len: UINT): BSTR

public expect fun SysAddRefString(bstrString: BSTR): HRESULT

public expect fun SysReleaseString(bstrString: BSTR)
