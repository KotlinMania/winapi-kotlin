// port-lint: source shared/mod.rs
// Tracking ledger for upstream `tmp/winapi/src/shared/mod.rs`.
//
// The Rust mod.rs is pure "Headers shared between user mode and kernel
// mode": every line is a `#[cfg(feature = "<name>")] pub mod <name>;`
// declaration of a sub-module. The submodules themselves carry the real
// content and each map to one .kt file under
// `io.github.kotlinmania.winapi.shared`. Feature gating from Cargo is
// upstream build configuration that does not survive translation; the
// Kotlin port surfaces every translated sub-module unconditionally
// (gated only by what has actually been ported).
package io.github.kotlinmania.winapi.shared

// Sub-modules translated from the Rust `shared/` directory:
//   basetsd  -> Basetsd.kt
//   cderr    -> Cderr.kt
//   guiddef  -> Guiddef.kt
//   in6addr  -> In6addr.kt
//   inaddr   -> Inaddr.kt
//   minwindef -> Minwindef.kt
//   ntdef    -> Ntdef.kt
//   windef   -> Windef.kt
//   windowsx -> Windowsx.kt
// Each Kotlin file corresponds to one upstream header file and is surfaced
// unconditionally (Cargo feature gating is upstream build configuration
// that does not survive translation).
internal object Mod
