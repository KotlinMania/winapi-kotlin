# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 15/405 (3.7%)
- **Function parity:** 31/507 matched (target 51) — 6.1%
- **Class/type parity:** 312/5842 matched (target 403) — 5.3%
- **Combined symbol parity:** 343/6349 matched (target 454) — 5.4%
- **Average inline-code cosine:** 0.49 (function body across 13 matched files)
- **Average documentation cosine:** 0.03 (doc text across 13 matched files)
- **Cheat-zeroed Files:** 7
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

### 8. shared.windef

- **Target:** `windef.Windef`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 2200.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 22/22 matched (target 49)
- **Missing types:** _none_

### 9. shared.guiddef

- **Target:** `guiddef.Guiddef`
- **Similarity:** 0.88
- **Dependents:** 0
- **Priority Score:** 1301.2
- **Functions:** 1/1 matched (target 7)
- **Missing functions:** _none_
- **Types:** 12/12 matched (target 13)
- **Missing types:** _none_

### 10. shared.hidusage

- **Target:** `hidusage.Hidusage [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 210.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 11. shared.windowsx

- **Target:** `windowsx.Windowsx`
- **Similarity:** 0.65
- **Dependents:** 0
- **Priority Score:** 203.5
- **Functions:** 2/2 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 12. shared.dxgitype

- **Target:** `dxgitype.Dxgitype`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 15)
- **Missing types:** _none_

### 13. shared.mod

- **Target:** `shared.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 14. shared.cderr

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

### Matched

| Source | Target | Path |
|--------|--------|------|
| `winapi.lib` | `winapi.Lib` | `winapi/src/lib` |

