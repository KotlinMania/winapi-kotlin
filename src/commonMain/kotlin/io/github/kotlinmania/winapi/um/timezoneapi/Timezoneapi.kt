// port-lint: source um/timezoneapi.rs
package io.github.kotlinmania.winapi.um.timezoneapi

import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.um.minwinbase.SYSTEMTIME
import io.github.kotlinmania.winapi.um.winnt.BOOLEAN
import io.github.kotlinmania.winapi.um.winnt.LONG

public const val TIME_ZONE_ID_INVALID: DWORD = 0xFFFFFFFFu

public class TIME_ZONE_INFORMATION(
    public var Bias: LONG = 0,
    public var StandardName: UShortArray = UShortArray(32),
    public var StandardDate: SYSTEMTIME = SYSTEMTIME(),
    public var StandardBias: LONG = 0,
    public var DaylightName: UShortArray = UShortArray(32),
    public var DaylightDate: SYSTEMTIME = SYSTEMTIME(),
    public var DaylightBias: LONG = 0,
)

public typealias PTIME_ZONE_INFORMATION = ULong
public typealias LPTIME_ZONE_INFORMATION = ULong

public class DYNAMIC_TIME_ZONE_INFORMATION(
    public var Bias: LONG = 0,
    public var StandardName: UShortArray = UShortArray(32),
    public var StandardDate: SYSTEMTIME = SYSTEMTIME(),
    public var StandardBias: LONG = 0,
    public var DaylightName: UShortArray = UShortArray(32),
    public var DaylightDate: SYSTEMTIME = SYSTEMTIME(),
    public var DaylightBias: LONG = 0,
    public var TimeZoneKeyName: UShortArray = UShortArray(128),
    public var DynamicDaylightTimeDisabled: BOOLEAN = 0u,
)

public typealias PDYNAMIC_TIME_ZONE_INFORMATION = ULong

public expect fun GetTimeZoneInformation(lpTimeZoneInformation: LPTIME_ZONE_INFORMATION): DWORD
