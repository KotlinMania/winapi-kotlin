// port-lint: source winapi/src/shared/dxgitype.rs
package io.github.kotlinmania.winapi.shared.dxgitype

import io.github.kotlinmania.winapi.CFloat
import io.github.kotlinmania.winapi.CUint
import io.github.kotlinmania.winapi.shared.d3d9types.D3DCOLORVALUE
import io.github.kotlinmania.winapi.shared.dxgiformat.DXGI_FORMAT
import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.BYTE
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.UINT

public const val DXGI_CPU_ACCESS_NONE: DWORD = 0u
public const val DXGI_CPU_ACCESS_DYNAMIC: DWORD = 1u
public const val DXGI_CPU_ACCESS_READ_WRITE: DWORD = 2u
public const val DXGI_CPU_ACCESS_SCRATCH: DWORD = 3u
public const val DXGI_CPU_ACCESS_FIELD: DWORD = 15u

public typealias DXGI_USAGE = CUint

public const val DXGI_USAGE_SHADER_INPUT: DXGI_USAGE = 0x00000010u
public const val DXGI_USAGE_RENDER_TARGET_OUTPUT: DXGI_USAGE = 0x00000020u
public const val DXGI_USAGE_BACK_BUFFER: DXGI_USAGE = 0x00000040u
public const val DXGI_USAGE_SHARED: DXGI_USAGE = 0x00000080u
public const val DXGI_USAGE_READ_ONLY: DXGI_USAGE = 0x00000100u
public const val DXGI_USAGE_DISCARD_ON_PRESENT: DXGI_USAGE = 0x00000200u
public const val DXGI_USAGE_UNORDERED_ACCESS: DXGI_USAGE = 0x00000400u

public data class DXGI_RGB(
    val red: CFloat,
    val green: CFloat,
    val blue: CFloat,
)

public typealias DXGI_RGBA = D3DCOLORVALUE

public data class DXGI_GAMMA_CONTROL(
    val scale: DXGI_RGB,
    val offset: DXGI_RGB,
    val gammaCurve: List<DXGI_RGB>,
)

public data class DXGI_GAMMA_CONTROL_CAPABILITIES(
    val scaleAndOffsetSupported: BOOL,
    val maxConvertedValue: CFloat,
    val minConvertedValue: CFloat,
    val numGammaControlPoints: UINT,
    val controlPointPositions: List<CFloat>,
)

public data class DXGI_RATIONAL(
    val numerator: UINT,
    val denominator: UINT,
)

public typealias DXGI_MODE_SCANLINE_ORDER = CUint

public const val DXGI_MODE_SCANLINE_ORDER_UNSPECIFIED: DXGI_MODE_SCANLINE_ORDER = 0u
public const val DXGI_MODE_SCANLINE_ORDER_PROGRESSIVE: DXGI_MODE_SCANLINE_ORDER = 1u
public const val DXGI_MODE_SCANLINE_ORDER_UPPER_FIELD_FIRST: DXGI_MODE_SCANLINE_ORDER = 2u
public const val DXGI_MODE_SCANLINE_ORDER_LOWER_FIELD_FIRST: DXGI_MODE_SCANLINE_ORDER = 3u

public typealias DXGI_MODE_SCALING = CUint

public const val DXGI_MODE_SCALING_UNSPECIFIED: DXGI_MODE_SCALING = 0u
public const val DXGI_MODE_SCALING_CENTERED: DXGI_MODE_SCALING = 1u
public const val DXGI_MODE_SCALING_STRETCHED: DXGI_MODE_SCALING = 2u

public typealias DXGI_MODE_ROTATION = CUint

public const val DXGI_MODE_ROTATION_UNSPECIFIED: DXGI_MODE_ROTATION = 0u
public const val DXGI_MODE_ROTATION_IDENTITY: DXGI_MODE_ROTATION = 1u
public const val DXGI_MODE_ROTATION_ROTATE90: DXGI_MODE_ROTATION = 2u
public const val DXGI_MODE_ROTATION_ROTATE180: DXGI_MODE_ROTATION = 3u
public const val DXGI_MODE_ROTATION_ROTATE270: DXGI_MODE_ROTATION = 4u

public data class DXGI_MODE_DESC(
    val width: UINT,
    val height: UINT,
    val refreshRate: DXGI_RATIONAL,
    val format: DXGI_FORMAT,
    val scanlineOrdering: DXGI_MODE_SCANLINE_ORDER,
    val scaling: DXGI_MODE_SCALING,
)

public data class DXGI_SAMPLE_DESC(
    val count: UINT,
    val quality: UINT,
)

public data class DXGI_JPEG_DC_HUFFMAN_TABLE(
    val codeCounts: List<BYTE>,
    val codeValues: List<BYTE>,
)

public data class DXGI_JPEG_AC_HUFFMAN_TABLE(
    val codeCounts: List<BYTE>,
    val codeValues: List<BYTE>,
)

public data class DXGI_JPEG_QUANTIZATION_TABLE(
    val elements: List<BYTE>,
)

public typealias DXGI_COLOR_SPACE_TYPE = CUint

public const val DXGI_COLOR_SPACE_RGB_FULL_G22_NONE_P709: DXGI_COLOR_SPACE_TYPE = 0u
public const val DXGI_COLOR_SPACE_RGB_FULL_G10_NONE_P709: DXGI_COLOR_SPACE_TYPE = 1u
public const val DXGI_COLOR_SPACE_RGB_STUDIO_G22_NONE_P709: DXGI_COLOR_SPACE_TYPE = 2u
public const val DXGI_COLOR_SPACE_RGB_STUDIO_G22_NONE_P2020: DXGI_COLOR_SPACE_TYPE = 3u
public const val DXGI_COLOR_SPACE_RESERVED: DXGI_COLOR_SPACE_TYPE = 4u
public const val DXGI_COLOR_SPACE_YCBCR_FULL_G22_NONE_P709_X601: DXGI_COLOR_SPACE_TYPE = 5u
public const val DXGI_COLOR_SPACE_YCBCR_STUDIO_G22_LEFT_P601: DXGI_COLOR_SPACE_TYPE = 6u
public const val DXGI_COLOR_SPACE_YCBCR_FULL_G22_LEFT_P601: DXGI_COLOR_SPACE_TYPE = 7u
public const val DXGI_COLOR_SPACE_YCBCR_STUDIO_G22_LEFT_P709: DXGI_COLOR_SPACE_TYPE = 8u
public const val DXGI_COLOR_SPACE_YCBCR_FULL_G22_LEFT_P709: DXGI_COLOR_SPACE_TYPE = 9u
public const val DXGI_COLOR_SPACE_YCBCR_STUDIO_G22_LEFT_P2020: DXGI_COLOR_SPACE_TYPE = 10u
public const val DXGI_COLOR_SPACE_YCBCR_FULL_G22_LEFT_P2020: DXGI_COLOR_SPACE_TYPE = 11u
public const val DXGI_COLOR_SPACE_RGB_FULL_G2084_NONE_P2020: DXGI_COLOR_SPACE_TYPE = 12u
public const val DXGI_COLOR_SPACE_YCBCR_STUDIO_G2084_LEFT_P2020: DXGI_COLOR_SPACE_TYPE = 13u
public const val DXGI_COLOR_SPACE_RGB_STUDIO_G2084_NONE_P2020: DXGI_COLOR_SPACE_TYPE = 14u
public const val DXGI_COLOR_SPACE_YCBCR_STUDIO_G22_TOPLEFT_P2020: DXGI_COLOR_SPACE_TYPE = 15u
public const val DXGI_COLOR_SPACE_YCBCR_STUDIO_G2084_TOPLEFT_P2020: DXGI_COLOR_SPACE_TYPE = 16u
public const val DXGI_COLOR_SPACE_RGB_FULL_G22_NONE_P2020: DXGI_COLOR_SPACE_TYPE = 17u
public const val DXGI_COLOR_SPACE_CUSTOM: DXGI_COLOR_SPACE_TYPE = 0xFFFFFFFFu

public const val DXGI_CENTER_MULTISAMPLE_QUALITY_PATTERN: UINT = 0xfffffffeu
public const val DXGI_STANDARD_MULTISAMPLE_QUALITY_PATTERN: UINT = 0xffffffffu
