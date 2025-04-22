package com.morpheusdata.core.network;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.core.MorpheusIdentityService;
import com.morpheusdata.model.NetworkFloatingIp;
import com.morpheusdata.model.projection.NetworkFloatingIpIdentityProjection;

public interface MorpheusNetworkFloatingIpService extends MorpheusDataService<NetworkFloatingIp, NetworkFloatingIpIdentityProjection>, MorpheusIdentityService<NetworkFloatingIpIdentityProjection> {
	/**
	 * Returns the Network Floating IP Pool Context used for performing updates or queries on {@link NetworkFloatingIp} related assets within Morpheus
	 * @return the Network Floating IP Pool Context to use for calls by various floating ip providers.
	 */
	MorpheusNetworkFloatingIpPoolService getPool();
}
