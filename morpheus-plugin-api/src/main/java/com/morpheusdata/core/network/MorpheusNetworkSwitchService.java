package com.morpheusdata.core.network;

import com.morpheusdata.core.MorpheusContext;
import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.core.MorpheusIdentityService;
import com.morpheusdata.model.NetworkSwitch;
import com.morpheusdata.model.projection.NetworkSwitchIdentityProjection;

/**
 * Morpheus Context as it relates to network switch related operations. This context contains methods for querying switches, creating,
 * updating and deleting switches.  Typically this class is accessed via the {@link MorpheusNetworkServerService}.
 *
 * @since 1.2.6
 */
public interface MorpheusNetworkSwitchService extends MorpheusDataService<NetworkSwitch, NetworkSwitchIdentityProjection>, MorpheusIdentityService<NetworkSwitchIdentityProjection> {
	public MorpheusNetworkSwitchTypeService getType();
}
