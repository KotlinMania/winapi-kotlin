// port-lint: source um/timezoneapi.rs
package io.github.kotlinmania.winapi.um.timezoneapi

import io.github.kotlinmania.winapi.internal.WinapiJni
import io.github.kotlinmania.winapi.shared.minwindef.DWORD

public actual fun GetTimeZoneInformation(lpTimeZoneInformation: LPTIME_ZONE_INFORMATION): DWORD =
    WinapiJni.GetTimeZoneInformation(lpTimeZoneInformation.toLong()).toUInt()
