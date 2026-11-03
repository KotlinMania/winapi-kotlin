// port-lint: source shared/transportsettingcommon.rs
package io.github.kotlinmania.winapi.shared.transportsettingcommon

import io.github.kotlinmania.winapi.shared.guiddef.GUID

public data class TRANSPORT_SETTING_ID(
    public var Guid: GUID,
)

public typealias PTRANSPORT_SETTING_ID = ULong
