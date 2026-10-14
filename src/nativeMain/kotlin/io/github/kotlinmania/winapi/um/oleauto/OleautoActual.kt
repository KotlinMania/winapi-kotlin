// port-lint: source um/oleauto.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.oleauto

import io.github.kotlinmania.winapi.shared.minwindef.INT
import io.github.kotlinmania.winapi.shared.minwindef.UINT
import io.github.kotlinmania.winapi.shared.wtypes.BSTR
import io.github.kotlinmania.winapi.shared.wtypesbase.LPCOLESTR
import io.github.kotlinmania.winapi.um.winnt.HRESULT
import io.github.kotlinmania.winapi.um.winnt.LPCSTR
import kotlinx.cinterop.LongVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import winapi.cinterop.winapi_sys_add_ref_string
import winapi.cinterop.winapi_sys_alloc_string
import winapi.cinterop.winapi_sys_alloc_string_byte_len
import winapi.cinterop.winapi_sys_alloc_string_len
import winapi.cinterop.winapi_sys_free_string
import winapi.cinterop.winapi_sys_re_alloc_string
import winapi.cinterop.winapi_sys_re_alloc_string_len
import winapi.cinterop.winapi_sys_release_string
import winapi.cinterop.winapi_sys_string_byte_len
import winapi.cinterop.winapi_sys_string_len

public actual fun SysAllocString(psz: LPCOLESTR): BSTR =
    winapi_sys_alloc_string(psz.toLong()).toULong()

public actual fun SysReAllocString(pbstr: ULongArray, psz: LPCOLESTR): INT =
    memScoped {
        val bstrVar = alloc<LongVar>()
        bstrVar.value = if (pbstr.isNotEmpty()) pbstr[0].toLong() else 0L
        val res = winapi_sys_re_alloc_string(bstrVar.ptr, psz.toLong())
        if (pbstr.isNotEmpty()) {
            pbstr[0] = bstrVar.value.toULong()
        }
        res
    }

public actual fun SysAllocStringLen(strIn: LPCOLESTR, ui: UINT): BSTR =
    winapi_sys_alloc_string_len(strIn.toLong(), ui).toULong()

public actual fun SysReAllocStringLen(pbstr: ULongArray, psz: LPCOLESTR, len: UINT): INT =
    memScoped {
        val bstrVar = alloc<LongVar>()
        bstrVar.value = if (pbstr.isNotEmpty()) pbstr[0].toLong() else 0L
        val res = winapi_sys_re_alloc_string_len(bstrVar.ptr, psz.toLong(), len)
        if (pbstr.isNotEmpty()) {
            pbstr[0] = bstrVar.value.toULong()
        }
        res
    }

public actual fun SysFreeString(bstrString: BSTR) {
    winapi_sys_free_string(bstrString.toLong())
}

public actual fun SysStringLen(pbstr: BSTR): UINT =
    winapi_sys_string_len(pbstr.toLong())

public actual fun SysStringByteLen(bstr: BSTR): UINT =
    winapi_sys_string_byte_len(bstr.toLong())

public actual fun SysAllocStringByteLen(psz: LPCSTR, len: UINT): BSTR =
    winapi_sys_alloc_string_byte_len(psz.toLong(), len).toULong()

public actual fun SysAddRefString(bstrString: BSTR): HRESULT =
    winapi_sys_add_ref_string(bstrString.toLong())

public actual fun SysReleaseString(bstrString: BSTR) {
    winapi_sys_release_string(bstrString.toLong())
}
