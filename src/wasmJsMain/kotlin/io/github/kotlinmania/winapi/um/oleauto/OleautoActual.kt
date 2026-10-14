// port-lint: source um/oleauto.rs
package io.github.kotlinmania.winapi.um.oleauto

import io.github.kotlinmania.winapi.internal.WinapiNapiWasm
import io.github.kotlinmania.winapi.shared.minwindef.INT
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.wtypes.BSTR
import io.github.kotlinmania.winapi.shared.wtypesbase.LPCOLESTR
import io.github.kotlinmania.winapi.um.winnt.HRESULT
import io.github.kotlinmania.winapi.um.winnt.LPCSTR

public actual fun SysAllocString(psz: LPCOLESTR): BSTR =
    WinapiNapiWasm.SysAllocString(psz.toLong()).toULong()

public actual fun SysReAllocString(pbstr: ULongArray, psz: LPCOLESTR): INT {
    val arr = LongArray(1) { if (pbstr.isNotEmpty()) pbstr[0].toLong() else 0L }
    val res = WinapiNapiWasm.SysReAllocString(arr, psz.toLong())
    if (pbstr.isNotEmpty()) {
        pbstr[0] = arr[0].toULong()
    }
    return res
}

public actual fun SysAllocStringLen(strIn: LPCOLESTR, ui: UINT): BSTR =
    WinapiNapiWasm.SysAllocStringLen(strIn.toLong(), ui).toULong()

public actual fun SysReAllocStringLen(pbstr: ULongArray, psz: LPCOLESTR, len: UINT): INT {
    val arr = LongArray(1) { if (pbstr.isNotEmpty()) pbstr[0].toLong() else 0L }
    val res = WinapiNapiWasm.SysReAllocStringLen(arr, psz.toLong(), len)
    if (pbstr.isNotEmpty()) {
        pbstr[0] = arr[0].toULong()
    }
    return res
}

public actual fun SysFreeString(bstrString: BSTR) {
    WinapiNapiWasm.SysFreeString(bstrString.toLong())
}

public actual fun SysStringLen(pbstr: BSTR): UINT =
    WinapiNapiWasm.SysStringLen(pbstr.toLong())

public actual fun SysStringByteLen(bstr: BSTR): UINT =
    WinapiNapiWasm.SysStringByteLen(bstr.toLong())

public actual fun SysAllocStringByteLen(psz: LPCSTR, len: UINT): BSTR =
    WinapiNapiWasm.SysAllocStringByteLen(psz.toLong(), len).toULong()

public actual fun SysAddRefString(bstrString: BSTR): HRESULT =
    WinapiNapiWasm.SysAddRefString(bstrString.toLong())

public actual fun SysReleaseString(bstrString: BSTR) {
    WinapiNapiWasm.SysReleaseString(bstrString.toLong())
}
