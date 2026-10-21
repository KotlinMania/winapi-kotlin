// port-lint: source shared/tvout.rs
package io.github.kotlinmania.winapi.shared.tvout

import io.github.kotlinmania.winapi.shared.guiddef.GUID
import io.github.kotlinmania.winapi.shared.guiddef.IID_NULL
import io.github.kotlinmania.winapi.shared.minwindef.ULONG

public data class VIDEOPARAMETERS(
    public var Guid: GUID = IID_NULL,
    public var dwOffset: ULONG = 0u,
    public var dwCommand: ULONG = 0u,
    public var dwFlags: ULONG = 0u,
    public var dwMode: ULONG = 0u,
    public var dwTVStandard: ULONG = 0u,
    public var dwAvailableModes: ULONG = 0u,
    public var dwAvailableTVStandard: ULONG = 0u,
    public var dwFlickerFilter: ULONG = 0u,
    public var dwOverScanX: ULONG = 0u,
    public var dwOverScanY: ULONG = 0u,
    public var dwMaxUnscaledX: ULONG = 0u,
    public var dwMaxUnscaledY: ULONG = 0u,
    public var dwPositionX: ULONG = 0u,
    public var dwPositionY: ULONG = 0u,
    public var dwBrightness: ULONG = 0u,
    public var dwContrast: ULONG = 0u,
    public var dwCPType: ULONG = 0u,
    public var dwCPCommand: ULONG = 0u,
    public var dwCPStandard: ULONG = 0u,
    public var dwCPKey: ULONG = 0u,
    public var bCpApsTriggerBits: ULONG = 0u,
    public var bOEMCopyProtection: UByteArray = UByteArray(256),
) {
    init {
        require(bOEMCopyProtection.size == 256) { "VIDEOPARAMETERS.bOEMCopyProtection must be 256 bytes" }
    }
}

public typealias PVIDEOPARAMETERS = ULong
public typealias LPVIDEOPARAMETERS = ULong

public const val VP_COMMAND_GET: ULONG = 0x0001u
public const val VP_COMMAND_SET: ULONG = 0x0002u
public const val VP_FLAGS_TV_MODE: ULONG = 0x0001u
public const val VP_FLAGS_TV_STANDARD: ULONG = 0x0002u
public const val VP_FLAGS_FLICKER: ULONG = 0x0004u
public const val VP_FLAGS_OVERSCAN: ULONG = 0x0008u
public const val VP_FLAGS_MAX_UNSCALED: ULONG = 0x0010u
public const val VP_FLAGS_POSITION: ULONG = 0x0020u
public const val VP_FLAGS_BRIGHTNESS: ULONG = 0x0040u
public const val VP_FLAGS_CONTRAST: ULONG = 0x0080u
public const val VP_FLAGS_COPYPROTECT: ULONG = 0x0100u
public const val VP_MODE_WIN_GRAPHICS: ULONG = 0x0001u
public const val VP_MODE_TV_PLAYBACK: ULONG = 0x0002u
public const val VP_TV_STANDARD_NTSC_M: ULONG = 0x0001u
public const val VP_TV_STANDARD_NTSC_M_J: ULONG = 0x0002u
public const val VP_TV_STANDARD_PAL_B: ULONG = 0x0004u
public const val VP_TV_STANDARD_PAL_D: ULONG = 0x0008u
public const val VP_TV_STANDARD_PAL_H: ULONG = 0x0010u
public const val VP_TV_STANDARD_PAL_I: ULONG = 0x0020u
public const val VP_TV_STANDARD_PAL_M: ULONG = 0x0040u
public const val VP_TV_STANDARD_PAL_N: ULONG = 0x0080u
public const val VP_TV_STANDARD_SECAM_B: ULONG = 0x0100u
public const val VP_TV_STANDARD_SECAM_D: ULONG = 0x0200u
public const val VP_TV_STANDARD_SECAM_G: ULONG = 0x0400u
public const val VP_TV_STANDARD_SECAM_H: ULONG = 0x0800u
public const val VP_TV_STANDARD_SECAM_K: ULONG = 0x1000u
public const val VP_TV_STANDARD_SECAM_K1: ULONG = 0x2000u
public const val VP_TV_STANDARD_SECAM_L: ULONG = 0x4000u
public const val VP_TV_STANDARD_WIN_VGA: ULONG = 0x8000u
public const val VP_TV_STANDARD_NTSC_433: ULONG = 0x00010000u
public const val VP_TV_STANDARD_PAL_G: ULONG = 0x00020000u
public const val VP_TV_STANDARD_PAL_60: ULONG = 0x00040000u
public const val VP_TV_STANDARD_SECAM_L1: ULONG = 0x00080000u
public const val VP_CP_TYPE_APS_TRIGGER: ULONG = 0x0001u
public const val VP_CP_TYPE_MACROVISION: ULONG = 0x0002u
public const val VP_CP_CMD_ACTIVATE: ULONG = 0x0001u
public const val VP_CP_CMD_DEACTIVATE: ULONG = 0x0002u
public const val VP_CP_CMD_CHANGE: ULONG = 0x0004u
