# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 15/404 (3.7%)
- **Function parity:** 31/497 matched (target 65) — 6.2%
- **Class/type parity:** 337/5865 matched (target 429) — 5.7%
- **Combined symbol parity:** 368/6362 matched (target 494) — 5.8%
- **Average inline-code cosine:** 0.45 (function body across 14 matched files)
- **Average documentation cosine:** 0.03 (doc text across 14 matched files)
- **Cheat-zeroed Files:** 8
- **Critical Issues:** 8 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. shared.dxgiformat

- **Target:** `dxgiformat.Dxgiformat`
- **Similarity:** 1.00
- **Dependents:** 15
- **Priority Score:** 15000000.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 2. shared.in6addr

- **Target:** `in6addr.In6addr [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 5
- **Priority Score:** 5000310.0
- **Functions:** 0/0 matched (target 3)
- **Missing functions:** _none_
- **Types:** 3/3 matched
- **Missing types:** _none_

### 3. shared.inaddr

- **Target:** `inaddr.Inaddr [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 4
- **Priority Score:** 4000310.0
- **Functions:** 0/0 matched (target 3)
- **Missing functions:** _none_
- **Types:** 3/3 matched (target 5)
- **Missing types:** _none_

### 4. shared.d3d9types

- **Target:** `d3d9types.D3d9types`
- **Similarity:** 0.80
- **Dependents:** 0
- **Priority Score:** 41102.0
- **Functions:** 6/6 matched
- **Missing functions:** _none_
- **Types:** 1/5 matched
- **Missing types:** `LPD3DVERTEXELEMENT9`, `LPD3DDEVINFO_RESOURCEMANAGER`, `LPD3DDEVINFO_D3DVERTEXSTATS`, `LPD3DDEVINFO_VCACHE`

### 5. shared.ntdef

- **Target:** `ntdef.Ntdef [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 28610.0
- **Functions:** 16/16 matched (target 24)
- **Missing functions:** _none_
- **Types:** 169/170 matched (target 198)
- **Missing types:** `VOID`

### 6. shared.minwindef

- **Target:** `minwindef.Minwindef [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 5410.0
- **Functions:** 6/6 matched
- **Missing functions:** _none_
- **Types:** 48/48 matched (target 60)
- **Missing types:** _none_

### 7. shared.basetsd

- **Target:** `basetsd.Basetsd [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 5110.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 51/51 matched
- **Missing types:** _none_

### 8. lib

- **Target:** `winapi.Lib [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 2510.0
- **Functions:** 0/0 matched (target 14)
- **Missing functions:** _none_
- **Types:** 25/25 matched (target 26)
- **Missing types:** _none_

### 9. shared.windef

- **Target:** `windef.Windef`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 2200.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 22/22 matched (target 49)
- **Missing types:** _none_

### 10. shared.guiddef

- **Target:** `guiddef.Guiddef`
- **Similarity:** 0.88
- **Dependents:** 0
- **Priority Score:** 1301.2
- **Functions:** 1/1 matched (target 7)
- **Missing functions:** _none_
- **Types:** 12/12 matched (target 13)
- **Missing types:** _none_

### 11. shared.hidusage

- **Target:** `hidusage.Hidusage [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 210.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 12. shared.windowsx

- **Target:** `windowsx.Windowsx`
- **Similarity:** 0.65
- **Dependents:** 0
- **Priority Score:** 203.5
- **Functions:** 2/2 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 13. shared.dxgitype

- **Target:** `dxgitype.Dxgitype`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 15)
- **Missing types:** _none_

### 14. shared.mod

- **Target:** `shared.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 15. shared.cderr

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

