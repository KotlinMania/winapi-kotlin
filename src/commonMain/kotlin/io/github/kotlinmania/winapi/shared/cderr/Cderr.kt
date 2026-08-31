// port-lint: source winapi/src/shared/cderr.rs
package io.github.kotlinmania.winapi.shared.cderr

import io.github.kotlinmania.winapi.shared.minwindef.DWORD

public const val CDERR_DIALOGFAILURE: DWORD = 0xFFFFu
public const val CDERR_GENERALCODES: DWORD = 0x0000u
public const val CDERR_STRUCTSIZE: DWORD = 0x0001u
public const val CDERR_INITIALIZATION: DWORD = 0x0002u
public const val CDERR_NOTEMPLATE: DWORD = 0x0003u
public const val CDERR_NOHINSTANCE: DWORD = 0x0004u
public const val CDERR_LOADSTRFAILURE: DWORD = 0x0005u
public const val CDERR_FINDRESFAILURE: DWORD = 0x0006u
public const val CDERR_LOADRESFAILURE: DWORD = 0x0007u
public const val CDERR_LOCKRESFAILURE: DWORD = 0x0008u
public const val CDERR_MEMALLOCFAILURE: DWORD = 0x0009u
public const val CDERR_MEMLOCKFAILURE: DWORD = 0x000Au
public const val CDERR_NOHOOK: DWORD = 0x000Bu
public const val CDERR_REGISTERMSGFAIL: DWORD = 0x000Cu

public const val PDERR_PRINTERCODES: DWORD = 0x1000u
public const val PDERR_SETUPFAILURE: DWORD = 0x1001u
public const val PDERR_PARSEFAILURE: DWORD = 0x1002u
public const val PDERR_RETDEFFAILURE: DWORD = 0x1003u
public const val PDERR_LOADDRVFAILURE: DWORD = 0x1004u
public const val PDERR_GETDEVMODEFAIL: DWORD = 0x1005u
public const val PDERR_INITFAILURE: DWORD = 0x1006u
public const val PDERR_NODEVICES: DWORD = 0x1007u
public const val PDERR_NODEFAULTPRN: DWORD = 0x1008u
public const val PDERR_DNDMMISMATCH: DWORD = 0x1009u
public const val PDERR_CREATEICFAILURE: DWORD = 0x100Au
public const val PDERR_PRINTERNOTFOUND: DWORD = 0x100Bu
public const val PDERR_DEFAULTDIFFERENT: DWORD = 0x100Cu

public const val CFERR_CHOOSEFONTCODES: DWORD = 0x2000u
public const val CFERR_NOFONTS: DWORD = 0x2001u
public const val CFERR_MAXLESSTHANMIN: DWORD = 0x2002u

public const val FNERR_FILENAMECODES: DWORD = 0x3000u
public const val FNERR_SUBCLASSFAILURE: DWORD = 0x3001u
public const val FNERR_INVALIDFILENAME: DWORD = 0x3002u
public const val FNERR_BUFFERTOOSMALL: DWORD = 0x3003u

public const val FRERR_FINDREPLACECODES: DWORD = 0x4000u
public const val FRERR_BUFFERLENGTHZERO: DWORD = 0x4001u

public const val CCERR_CHOOSECOLORCODES: DWORD = 0x5000u
