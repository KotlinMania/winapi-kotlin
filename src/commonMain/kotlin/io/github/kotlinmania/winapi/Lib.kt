// port-lint: source lib.rs
package io.github.kotlinmania.winapi

import io.github.kotlinmania.winapi.shared.guiddef.GUID

// Tracking ledger for upstream lib.rs. The Rust crate-level attributes
// (#![cfg(windows)], #![deny(unused, unused_qualifications)],
// #![warn(unused_attributes)], #![allow(bad_style, ...)],
// #![recursion_limit = "2563"], #![no_std]) describe how the upstream
// crate is compiled and have no direct Kotlin counterpart; they are
// retained here in prose so the parceling stays auditable.
//
// Upstream uncomments-as-needed clippy lint configuration block sits at
// the head of lib.rs; it is also configuration metadata with no Kotlin
// equivalent.
//
// Modules from upstream lib.rs (`pub mod km`, `pub mod shared`,
// `pub mod ucrt`, `pub mod um`, `pub mod vc`, `pub mod winrt`) become
// Kotlin sub-packages: `io.github.kotlinmania.winapi.km`, `.shared`,
// `.ucrt`, `.um`, `.vc`, `.winrt`. Each .rs in those modules is its own
// .kt file under the matching sub-package directory.
//
// The macro-only `mod macros;` (Rust `#[macro_use]`) becomes a per-call-site
// hand expansion in Kotlin; the macro definitions have no standalone
// emitted artifact.
//
// The inner `pub mod ctypes { ... }` block holds C primitive type aliases
// and is parceled out to Ctypes.kt under the root winapi package; see
// .ast_distance_config.json `reexport_modules` for the parceling
// permission.

/** This trait should be implemented for all COM interfaces. */
interface Interface {
    /** Returns the IID of the Interface. */
    fun uuidof(): GUID
}

/** This trait should be implemented for all COM classes. */
interface Class {
    /** Returns the CLSID of the Class. */
    fun uuidof(): GUID
}
