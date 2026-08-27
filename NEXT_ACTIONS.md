# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 11/405 (2.7%)
- **Function parity:** 25/507 matched (target 56) — 4.9%
- **Class/type parity:** 333/5867 matched (target 406) — 5.7%
- **Combined symbol parity:** 358/6374 matched (target 462) — 5.6%
- **Average inline-code cosine:** 0.39 (function body across 9 matched files)
- **Average documentation cosine:** 0.05 (doc text across 9 matched files)
- **Cheat-zeroed Files:** 7
- **Critical Issues:** 7 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **shared.dxgiformat** (15 deps)
   - Path: `winapi/src/shared/dxgiformat.rs`
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

### 6. winapi.lib

- **Target:** `winapi.Lib [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 2510.0
- **Functions:** 0/0 matched (target 11)
- **Missing functions:** _none_
- **Types:** 25/25 matched (target 26)
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

### 10. shared.mod

- **Target:** `shared.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 11. shared.cderr

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

