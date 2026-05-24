// port-lint: ignore
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
