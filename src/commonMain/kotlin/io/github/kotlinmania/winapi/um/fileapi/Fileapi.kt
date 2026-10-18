// port-lint: source um/fileapi.rs
package io.github.kotlinmania.winapi.um.fileapi

import io.github.kotlinmania.winapi.shared.minwindef.BOOL
import io.github.kotlinmania.winapi.shared.minwindef.DWORD
import io.github.kotlinmania.winapi.shared.minwindef.FILETIME
import io.github.kotlinmania.winapi.shared.minwindef.UCHAR
import io.github.kotlinmania.winapi.shared.minwindef.ULONG
import io.github.kotlinmania.winapi.shared.minwindef.WORD
import io.github.kotlinmania.winapi.um.minwinbase.LPSECURITY_ATTRIBUTES
import io.github.kotlinmania.winapi.um.winnt.BOOLEAN
import io.github.kotlinmania.winapi.shared.ntdef.CCHAR
import io.github.kotlinmania.winapi.um.winnt.FILE_ID_128
import io.github.kotlinmania.winapi.shared.ntdef.HANDLE
import io.github.kotlinmania.winapi.shared.ntdef.LARGE_INTEGER
import io.github.kotlinmania.winapi.shared.ntdef.LONG
import io.github.kotlinmania.winapi.shared.ntdef.LPCSTR
import io.github.kotlinmania.winapi.shared.ntdef.LPCWSTR
import io.github.kotlinmania.winapi.shared.ntdef.ULONGLONG
public const val CREATE_NEW: DWORD = 1u
public const val CREATE_ALWAYS: DWORD = 2u
public const val OPEN_EXISTING: DWORD = 3u
public const val OPEN_ALWAYS: DWORD = 4u
public const val TRUNCATE_EXISTING: DWORD = 5u
public const val INVALID_FILE_SIZE: DWORD = 0xFFFFFFFFu
public const val INVALID_SET_FILE_POINTER: DWORD = 0xFFFFFFFFu
public const val INVALID_FILE_ATTRIBUTES: DWORD = 0xFFFFFFFFu

public data class WIN32_FILE_ATTRIBUTE_DATA(
    public var dwFileAttributes: DWORD = 0u,
    public var ftCreationTime: FILETIME = FILETIME(0u, 0u),
    public var ftLastAccessTime: FILETIME = FILETIME(0u, 0u),
    public var ftLastWriteTime: FILETIME = FILETIME(0u, 0u),
    public var nFileSizeHigh: DWORD = 0u,
    public var nFileSizeLow: DWORD = 0u,
)

public typealias LPWIN32_FILE_ATTRIBUTE_DATA = ULong

public data class BY_HANDLE_FILE_INFORMATION(
    public var dwFileAttributes: DWORD = 0u,
    public var ftCreationTime: FILETIME = FILETIME(0u, 0u),
    public var ftLastAccessTime: FILETIME = FILETIME(0u, 0u),
    public var ftLastWriteTime: FILETIME = FILETIME(0u, 0u),
    public var dwVolumeSerialNumber: DWORD = 0u,
    public var nFileSizeHigh: DWORD = 0u,
    public var nFileSizeLow: DWORD = 0u,
    public var nNumberOfLinks: DWORD = 0u,
    public var nFileIndexHigh: DWORD = 0u,
    public var nFileIndexLow: DWORD = 0u,
)

public typealias PBY_HANDLE_FILE_INFORMATION = ULong
public typealias LPBY_HANDLE_FILE_INFORMATION = ULong

public data class CREATEFILE2_EXTENDED_PARAMETERS(
    public var dwSize: DWORD = 0u,
    public var dwFileAttributes: DWORD = 0u,
    public var dwFileFlags: DWORD = 0u,
    public var dwSecurityQosFlags: DWORD = 0u,
    public var lpSecurityAttributes: LPSECURITY_ATTRIBUTES = 0uL,
    public var hTemplateFile: HANDLE = 0uL,
)

public typealias PCREATEFILE2_EXTENDED_PARAMETERS = ULong
public typealias LPCREATEFILE2_EXTENDED_PARAMETERS = ULong

public typealias PRIORITY_HINT = UInt

public const val IoPriorityHintVeryLow: PRIORITY_HINT = 0u
public const val IoPriorityHintLow: PRIORITY_HINT = 1u
public const val IoPriorityHintNormal: PRIORITY_HINT = 2u
public const val MaximumIoPriorityHintType: PRIORITY_HINT = 3u

public data class FILE_BASIC_INFO(
    public var CreationTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var LastAccessTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var LastWriteTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var ChangeTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var FileAttributes: DWORD = 0u,
)

public data class FILE_STANDARD_INFO(
    public var AllocationSize: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var EndOfFile: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var NumberOfLinks: DWORD = 0u,
    public var DeletePending: BOOLEAN = 0u,
    public var Directory: BOOLEAN = 0u,
)

public class FILE_NAME_INFO(
    public var FileNameLength: DWORD = 0u,
    public var FileName: UShortArray = UShortArray(1),
)

public class FILE_RENAME_INFO(
    public var ReplaceIfExists: BOOL = 0,
    public var RootDirectory: HANDLE = 0uL,
    public var FileNameLength: DWORD = 0u,
    public var FileName: UShortArray = UShortArray(1),
)

public data class FILE_DISPOSITION_INFO(
    public var DeleteFile: BOOLEAN = 0u,
)

public data class FILE_ALLOCATION_INFO(
    public var AllocationSize: LARGE_INTEGER = LARGE_INTEGER(0L),
)

public data class FILE_END_OF_FILE_INFO(
    public var EndOfFile: LARGE_INTEGER = LARGE_INTEGER(0L),
)

public class FILE_STREAM_INFO(
    public var NextEntryOffset: DWORD = 0u,
    public var StreamNameLength: DWORD = 0u,
    public var StreamSize: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var StreamAllocationSize: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var StreamName: UShortArray = UShortArray(1),
)

public class FILE_COMPRESSION_INFO(
    public var CompressedFileSize: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var CompressionFormat: WORD = 0u,
    public var CompressionUnitShift: UCHAR = 0u,
    public var ChunkShift: UCHAR = 0u,
    public var ClusterShift: UCHAR = 0u,
    public var Reserved: UByteArray = UByteArray(3),
)

public data class FILE_ATTRIBUTE_TAG_INFO(
    public var NextEntryOffset: DWORD = 0u,
    public var ReparseTag: DWORD = 0u,
)

public class FILE_ID_BOTH_DIR_INFO(
    public var NextEntryOffset: DWORD = 0u,
    public var FileIndex: DWORD = 0u,
    public var CreationTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var LastAccessTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var LastWriteTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var ChangeTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var EndOfFile: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var AllocationSize: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var FileAttributes: DWORD = 0u,
    public var FileNameLength: DWORD = 0u,
    public var EaSize: DWORD = 0u,
    public var ShortNameLength: CCHAR = 0,
    public var ShortName: UShortArray = UShortArray(12),
    public var FileId: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var FileName: UShortArray = UShortArray(1),
)

public data class FILE_IO_PRIORITY_HINT_INFO(
    public var PriorityHint: PRIORITY_HINT = IoPriorityHintNormal,
)

public class FILE_FULL_DIR_INFO(
    public var NextEntryOffset: ULONG = 0u,
    public var FileIndex: ULONG = 0u,
    public var CreationTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var LastAccessTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var LastWriteTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var ChangeTime: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var EndOfFile: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var AllocationSize: LARGE_INTEGER = LARGE_INTEGER(0L),
    public var FileAttributes: ULONG = 0u,
    public var FileNameLength: ULONG = 0u,
    public var EaSize: ULONG = 0u,
    public var FileName: UShortArray = UShortArray(1),
)

public data class FILE_STORAGE_INFO(
    public var LogicalBytesPerSector: ULONG = 0u,
    public var PhysicalBytesPerSectorForAtomicity: ULONG = 0u,
    public var PhysicalBytesPerSectorForPerformance: ULONG = 0u,
    public var FileSystemEffectivePhysicalBytesPerSectorForAtomicity: ULONG = 0u,
    public var Flags: ULONG = 0u,
    public var ByteOffsetForSectorAlignment: ULONG = 0u,
    public var ByteOffsetForPartitionAlignment: ULONG = 0u,
)

public data class FILE_ALIGNMENT_INFO(
    public var AlignmentRequirement: ULONG = 0u,
)

public class FILE_ID_INFO(
    public var VolumeSerialNumber: ULONGLONG = 0uL,
    public var FileId: FILE_ID_128 = FILE_ID_128(),
)

public typealias STREAM_INFO_LEVELS = UInt

public const val FindStreamInfoStandard: STREAM_INFO_LEVELS = 0u
public const val FindStreamInfoMaxInfoLevel: STREAM_INFO_LEVELS = 1u

public expect fun CreateFileA(
    lpFileName: LPCSTR,
    dwDesiredAccess: DWORD,
    dwShareMode: DWORD,
    lpSecurityAttributes: LPSECURITY_ATTRIBUTES,
    dwCreationDisposition: DWORD,
    dwFlagsAndAttributes: DWORD,
    hTemplateFile: HANDLE,
): HANDLE

public expect fun CreateFileW(
    lpFileName: LPCWSTR,
    dwDesiredAccess: DWORD,
    dwShareMode: DWORD,
    lpSecurityAttributes: LPSECURITY_ATTRIBUTES,
    dwCreationDisposition: DWORD,
    dwFlagsAndAttributes: DWORD,
    hTemplateFile: HANDLE,
): HANDLE

public expect fun DeleteFileA(lpFileName: LPCSTR): BOOL

public expect fun DeleteFileW(lpFileName: LPCWSTR): BOOL

public expect fun FlushFileBuffers(hFile: HANDLE): BOOL

public expect fun GetFileAttributesA(lpFileName: LPCSTR): DWORD

public expect fun GetFileAttributesW(lpFileName: LPCWSTR): DWORD

public expect fun GetFileSize(
    hFile: HANDLE,
    lpFileSizeHigh: UIntArray?,
): DWORD

public expect fun SetFileAttributesA(
    lpFileName: LPCSTR,
    dwFileAttributes: DWORD,
): BOOL

public expect fun SetFileAttributesW(
    lpFileName: LPCWSTR,
    dwFileAttributes: DWORD,
): BOOL

public expect fun SetFilePointer(
    hFile: HANDLE,
    lDistanceToMove: LONG,
    lpDistanceToMoveHigh: IntArray?,
    dwMoveMethod: DWORD,
): DWORD

public expect fun AreFileApisANSI(): BOOL
