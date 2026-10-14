// port-lint: source um/oleauto.rs
package io.github.kotlinmania.winapi.um.oleauto

import io.github.kotlinmania.winapi.internal.WinapiNative
import io.github.kotlinmania.winapi.shared.minwindef.INT
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.wtypes.BSTR
import io.github.kotlinmania.winapi.shared.wtypesbase.LPCOLESTR
import io.github.kotlinmania.winapi.um.winnt.HRESULT
import io.github.kotlinmania.winapi.um.winnt.LPCSTR

public actual fun SysAllocString(psz: LPCOLESTR): BSTR =
    WinapiNative.SysAllocString(psz.toLong().toDouble()).toLong().toULong()

public actual fun SysReAllocString(pbstr: ULongArray, psz: LPCOLESTR): INT {
    val current = if (pbstr.isNotEmpty()) pbstr[0].toLong().toDouble() else 0.0
    val res = WinapiNative.SysReAllocString(current, psz.toLong().toDouble())
    if (pbstr.isNotEmpty()) {
        pbstr[0] = (res.bstr as Double).toLong().toULong()
    }
    return res.result as Int
}

public actual fun SysAllocStringLen(strIn: LPCOLESTR, ui: UINT): BSTR =
    WinapiNative.SysAllocStringLen(strIn.toLong().toDouble(), ui.toInt()).toLong().toULong()

public actual fun SysReAllocStringLen(pbstr: ULongArray, psz: LPCOLESTR, len: UINT): INT {
    val current = if (pbstr.isNotEmpty()) pbstr[0].toLong().toDouble() else 0.0
    val res = WinapiNative.SysReAllocStringLen(current, psz.toLong().toDouble(), len.toInt())
    if (pbstr.isNotEmpty()) {
        pbstr[0] = (res.bstr as Double).toLong().toULong()
    }
    return res.result as Int
}

public actual fun SysFreeString(bstrString: BSTR) {
    WinapiNative.SysFreeString(bstrString.toLong().toDouble())
}

public actual fun SysStringLen(pbstr: BSTR): UINT =
    WinapiNative.SysStringLen(pbstr.toLong().toDouble()).toUInt()

public actual fun SysStringByteLen(bstr: BSTR): UINT =
    WinapiNative.SysStringByteLen(bstr.toLong().toDouble()).toUInt()

public actual fun SysAllocStringByteLen(psz: LPCSTR, len: UINT): BSTR =
    WinapiNative.SysAllocStringByteLen(psz.toLong().toDouble(), len.toInt()).toLong().toULong()

public actual fun SysAddRefString(bstrString: BSTR): HRESULT =
    WinapiNative.SysAddRefString(bstrString.toLong().toDouble())

public actual fun SysReleaseString(bstrString: BSTR) {
    WinapiNative.SysReleaseString(bstrString.toLong().toDouble())
}
