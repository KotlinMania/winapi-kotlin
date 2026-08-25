# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 10/404 (2.5%)
- **Function parity:** 25/497 matched (target 45) — 5.0%
- **Class/type parity:** 333/5865 matched (target 404) — 5.7%
- **Combined symbol parity:** 358/6362 matched (target 449) — 5.6%
- **Average inline-code cosine:** 0.35 (function body across 10 matched files)
- **Average documentation cosine:** 0.04 (doc text across 10 matched files)
- **Cheat-zeroed Files:** 6
- **Critical Issues:** 6 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **shared.dxgiformat** (15 deps)
   - Path: `shared/dxgiformat.rs`
   - Essential for 15 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. shared.in6addr

- **Target:** `in6addr.In6addr [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 5
- **Priority Score:** 5000310.0
- **Functions:** 0/0 matched (target 3)
- **Missing functions:** _none_
- **Types:** 3/3 matched
- **Missing types:** _none_

### 2. shared.inaddr

- **Target:** `inaddr.Inaddr [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 4
- **Priority Score:** 4000310.0
- **Functions:** 0/0 matched (target 3)
- **Missing functions:** _none_
- **Types:** 3/3 matched (target 5)
- **Missing types:** _none_

### 3. shared.ntdef

- **Target:** `ntdef.Ntdef [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 28610.0
- **Functions:** 16/16 matched (target 24)
- **Missing functions:** _none_
- **Types:** 169/170 matched (target 198)
- **Missing types:** `VOID`

### 4. shared.minwindef

- **Target:** `minwindef.Minwindef [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 5410.0
- **Functions:** 6/6 matched
- **Missing functions:** _none_
- **Types:** 48/48 matched (target 60)
- **Missing types:** _none_

### 5. shared.basetsd

- **Target:** `basetsd.Basetsd [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 5110.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 51/51 matched
- **Missing types:** _none_

### 6. lib

- **Target:** `winapi.Lib [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 2510.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 25/25 matched
- **Missing types:** _none_

### 7. shared.windef

- **Target:** `windef.Windef`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 2200.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 22/22 matched (target 49)
- **Missing types:** _none_

### 8. shared.guiddef

- **Target:** `guiddef.Guiddef`
- **Similarity:** 0.88
- **Dependents:** 0
- **Priority Score:** 1301.2
- **Functions:** 1/1 matched (target 7)
- **Missing functions:** _none_
- **Types:** 12/12 matched (target 13)
- **Missing types:** _none_

### 9. shared.windowsx

- **Target:** `windowsx.Windowsx`
- **Similarity:** 0.65
- **Dependents:** 0
- **Priority Score:** 203.5
- **Functions:** 2/2 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 10. shared.cderr

- **Target:** `cderr.Cderr`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 0.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `km.mod` | `km.Mod` | 0 | `km/mod.rs` | `km/Mod.kt` |
| `shared.mod` | `shared.Mod` | 0 | `shared/mod.rs` | `shared/Mod.kt` |
| `ucrt.mod` | `ucrt.Mod` | 0 | `ucrt/mod.rs` | `ucrt/Mod.kt` |
| `gl.mod` | `um.gl.Mod` | 0 | `um/gl/mod.rs` | `um/gl/Mod.kt` |
| `um.mod` | `um.Mod` | 0 | `um/mod.rs` | `um/Mod.kt` |
| `vc.mod` | `vc.Mod` | 0 | `vc/mod.rs` | `vc/Mod.kt` |
| `winrt.mod` | `winrt.Mod` | 0 | `winrt/mod.rs` | `winrt/Mod.kt` |

