# winapi-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fwinapi--kotlin-blue.svg)](https://github.com/KotlinMania/winapi-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/winapi-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/winapi-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/winapi-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/winapi-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`retep998/winapi-rs`](https://github.com/retep998/winapi-rs).

**Original Project:** This port is based on [`retep998/winapi-rs`](https://github.com/retep998/winapi-rs). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `retep998/winapi-rs`

> The text below is reproduced and lightly edited from [`https://github.com/retep998/winapi-rs`](https://github.com/retep998/winapi-rs). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## winapi-rs
[![Build status](https://github.com/retep998/winapi-rs/workflows/Rust/badge.svg)](https://github.com/retep998/winapi-rs/actions)
[![Build status](https://ci.appveyor.com/api/projects/status/i47oonf5e7qm5utq/branch/0.3?svg=true)](https://ci.appveyor.com/project/retep998/winapi-rs/branch/0.3)
[![Build Status](https://travis-ci.org/retep998/winapi-rs.svg?branch=0.3)](https://travis-ci.org/retep998/winapi-rs)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/retep998/winapi-rs)
[![Crates.io](https://img.shields.io/crates/v/winapi.svg)](https://crates.io/crates/winapi)
![Lines of Code](https://tokei.rs/b1/github/retep998/winapi-rs)
![100% unsafe](https://img.shields.io/badge/unsafe-100%25-blue.svg)
[![Open issues](https://img.shields.io/github/issues-raw/retep998/winapi-rs.svg)](https://github.com/retep998/winapi-rs/issues)
[![License](https://img.shields.io/crates/l/winapi.svg)](https://github.com/retep998/winapi-rs)


[Documentation](https://docs.rs/winapi/)

Official communication channel: #windows-dev on the [Rust Community Discord](https://discord.gg/aVESxV8)

This crate provides raw FFI bindings to all of Windows API. They are gathered by hand using the Windows 10 SDK from Microsoft. I aim to replace all existing Windows FFI in other crates with this crate through the "[Embrace, extend, and extinguish](https://en.wikipedia.org/wiki/Embrace,_extend,_and_extinguish)" technique.

If this crate is missing something you need, feel free to create an issue, open a pull request, or contact me via [other means](https://www.rustaceans.org/retep998).

This crate depends on Rust 1.6 or newer on Windows. On other platforms this crate is a no-op and should compile with Rust 1.2 or newer.

## Frequently asked questions ##

### How do I create an instance of a union?

Use `std::mem::zeroed()` to create an instance of the union, and then assign the value you want using one of the variant methods.

### Why am I getting errors about unresolved imports?

Each module is gated on a feature flag, so you must enable the appropriate feature to gain access to those items. For example, if you want to use something from `winapi::um::winuser` you must enable the `winuser` feature.

### How do I know which module an item is defined in?

You can use the search functionality in the [documentation](https://docs.rs/winapi/) to find where items are defined.

### Why is there no documentation on how to use anything?

This crate is nothing more than raw bindings to Windows API. If you wish to know how to use the various functionality in Windows API, you can look up the various items on [MSDN](https://docs.microsoft.com/en-us/windows/win32/desktop-app-technologies) which is full of detailed documentation.

### Can I use this library in `no_std` projects?

Yes, absolutely! By default the `std` feature of `winapi` is disabled, allowing you to write Windows applications using nothing but `core` and `winapi`.

### Why is `winapi`'s `HANDLE` incompatible with `std`'s `HANDLE`?

Because `winapi` does not depend on `std` by default, it has to define `c_void` itself instead of using `std::os::raw::c_void`. However, if you enable the `std` feature of `winapi` then it will re-export `c_void` from `std` and cause `winapi`'s `HANDLE` to be the same type as `std`'s `HANDLE`.

### Should I still use those `-sys` crates such as `kernel32-sys`?

No. Those crates are a legacy of how `winapi` 0.2 was organized. Starting with `winapi` 0.3 all definitions are directly in `winapi` itself, and so there is no longer any need to use those `-sys` crates.

## Example ##

Cargo.toml:
```toml
[target.'cfg(windows)'.dependencies]
winapi = { version = "0.3", features = ["winuser"] }
```
main.rs:
```Rust
#[cfg(windows)] extern crate winapi;
use std::io::Error;

#[cfg(windows)]
fn print_message(msg: &str) -> Result<i32, Error> {
    use std::ffi::OsStr;
    use std::iter::once;
    use std::os::windows::ffi::OsStrExt;
    use std::ptr::null_mut;
    use winapi::um::winuser::{MB_OK, MessageBoxW};
    let wide: Vec<u16> = OsStr::new(msg).encode_wide().chain(once(0)).collect();
    let ret = unsafe {
        MessageBoxW(null_mut(), wide.as_ptr(), wide.as_ptr(), MB_OK)
    };
    if ret == 0 { Err(Error::last_os_error()) }
    else { Ok(ret) }
}
#[cfg(not(windows))]
fn print_message(msg: &str) -> Result<(), Error> {
    println!("{}", msg);
    Ok(())
}
fn main() {
    print_message("Hello, world!").unwrap();
}
```

## Financial Support
Do you use `winapi` in your projects? If so, you may be interested in financially supporting me on [Patreon](https://www.patreon.com/retep998). Companies in particular are especially encouraged to donate (I'm looking at you [Microsoft](https://github.com/Azure/iotedge/tree/master/edgelet)).

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:winapi-kotlin:0.1.0")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`retep998/winapi-rs`](https://github.com/retep998/winapi-rs). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the winapi-rs authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`retep998/winapi-rs`](https://github.com/retep998/winapi-rs) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
