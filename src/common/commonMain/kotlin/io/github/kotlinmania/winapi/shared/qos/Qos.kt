// port-lint: source shared/qos.rs
package io.github.kotlinmania.winapi.shared.qos

import io.github.kotlinmania.winapi.shared.minwindef.ULONG

/** QoS definitions for NDIS components. */
public typealias SERVICETYPE = ULONG

public data class FLOWSPEC(
    public var TokenRate: ULONG = 0u,
    public var TokenBucketSize: ULONG = 0u,
    public var PeakBandwidth: ULONG = 0u,
    public var Latency: ULONG = 0u,
    public var DelayVariation: ULONG = 0u,
    public var ServiceType: SERVICETYPE = 0u,
    public var MaxSduSize: ULONG = 0u,
    public var MinimumPolicedSize: ULONG = 0u,
)

public typealias PFLOWSPEC = ULong
public typealias LPFLOWSPEC = ULong
