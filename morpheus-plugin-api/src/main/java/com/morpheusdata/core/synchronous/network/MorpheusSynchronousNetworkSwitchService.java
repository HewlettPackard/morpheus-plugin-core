package com.morpheusdata.core.synchronous.network;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.core.network.MorpheusNetworkServerService;
import com.morpheusdata.model.NetworkSwitch;
import com.morpheusdata.model.projection.NetworkSwitchIdentityProjection;

/**
 * Morpheus Context as it relates to network switch related operations. This context contains methods for querying switches, creating,
 * updating and deleting switches.  Typically this class is accessed via the {@link MorpheusNetworkServerService}.
 *
 * @since 1.2.14
 */
public interface MorpheusSynchronousNetworkSwitchService extends MorpheusSynchronousDataService<NetworkSwitch, NetworkSwitchIdentityProjection>, MorpheusSynchronousIdentityService<NetworkSwitchIdentityProjection> {
	MorpheusSynchronousNetworkSwitchTypeService getType();
}
