// port-lint: source um/oleauto.rs
package io.github.kotlinmania.winapi.um.oleauto

import io.github.kotlinmania.winapi.internal.WinapiJni
import io.github.kotlinmania.winapi.shared.minwindef.INT
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.wtypes.BSTR
import io.github.kotlinmania.winapi.shared.wtypesbase.LPCOLESTR
import io.github.kotlinmania.winapi.shared.ntdef.HRESULT
import io.github.kotlinmania.winapi.shared.ntdef.LPCSTR
public actual fun SysAllocString(psz: LPCOLESTR): BSTR =
    WinapiJni.SysAllocString(psz.toLong()).toULong()

public actual fun SysReAllocString(pbstr: ULongArray, psz: LPCOLESTR): INT {
    val arr = LongArray(1) { if (pbstr.isNotEmpty()) pbstr[0].toLong() else 0L }
    val res = WinapiJni.SysReAllocString(arr, psz.toLong())
    if (pbstr.isNotEmpty()) {
        pbstr[0] = arr[0].toULong()
    }
    return res
}

public actual fun SysAllocStringLen(strIn: LPCOLESTR, ui: UINT): BSTR =
    WinapiJni.SysAllocStringLen(strIn.toLong(), ui.toInt()).toULong()

public actual fun SysReAllocStringLen(pbstr: ULongArray, psz: LPCOLESTR, len: UINT): INT {
    val arr = LongArray(1) { if (pbstr.isNotEmpty()) pbstr[0].toLong() else 0L }
    val res = WinapiJni.SysReAllocStringLen(arr, psz.toLong(), len.toInt())
    if (pbstr.isNotEmpty()) {
        pbstr[0] = arr[0].toULong()
    }
    return res
}

public actual fun SysFreeString(bstrString: BSTR) {
    WinapiJni.SysFreeString(bstrString.toLong())
}

public actual fun SysStringLen(pbstr: BSTR): UINT =
    WinapiJni.SysStringLen(pbstr.toLong()).toUInt()

public actual fun SysStringByteLen(bstr: BSTR): UINT =
    WinapiJni.SysStringByteLen(bstr.toLong()).toUInt()

public actual fun SysAllocStringByteLen(psz: LPCSTR, len: UINT): BSTR =
    WinapiJni.SysAllocStringByteLen(psz.toLong(), len.toInt()).toULong()

public actual fun SysAddRefString(bstrString: BSTR): HRESULT =
    WinapiJni.SysAddRefString(bstrString.toLong())

public actual fun SysReleaseString(bstrString: BSTR) {
    WinapiJni.SysReleaseString(bstrString.toLong())
}
