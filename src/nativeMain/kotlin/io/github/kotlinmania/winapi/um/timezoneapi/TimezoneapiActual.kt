// port-lint: source um/timezoneapi.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.winapi.um.timezoneapi

import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import winapi.cinterop.winapi_get_time_zone_information

public actual fun GetTimeZoneInformation(lpTimeZoneInformation: LPTIME_ZONE_INFORMATION): DWORD =
    winapi_get_time_zone_information(lpTimeZoneInformation.toLong())
