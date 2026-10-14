// port-lint: source um/oleauto.rs
package io.github.kotlinmania.winapi.um.oleauto

import io.github.kotlinmania.winapi.shared.minwindef.INT
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.wtypes.BSTR
import io.github.kotlinmania.winapi.shared.wtypesbase.LPCOLESTR
import io.github.kotlinmania.winapi.um.winnt.HRESULT
import io.github.kotlinmania.winapi.um.winnt.LPCSTR

public actual fun SysAllocString(psz: LPCOLESTR): BSTR = 0uL

public actual fun SysReAllocString(pbstr: ULongArray, psz: LPCOLESTR): INT = 0

public actual fun SysAllocStringLen(strIn: LPCOLESTR, ui: UINT): BSTR = 0uL

public actual fun SysReAllocStringLen(pbstr: ULongArray, psz: LPCOLESTR, len: UINT): INT = 0

public actual fun SysFreeString(bstrString: BSTR) {}

public actual fun SysStringLen(pbstr: BSTR): UINT = 0u

public actual fun SysStringByteLen(bstr: BSTR): UINT = 0u

public actual fun SysAllocStringByteLen(psz: LPCSTR, len: UINT): BSTR = 0uL

public actual fun SysAddRefString(bstrString: BSTR): HRESULT = 0

public actual fun SysReleaseString(bstrString: BSTR) {}
