package com.morpheusdata.core.synchronous.network;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.core.network.MorpheusNetworkFloatingIpPoolService;
import com.morpheusdata.model.NetworkFloatingIp;
import com.morpheusdata.model.projection.NetworkFloatingIpIdentityProjection;

public interface MorpheusSynchronousNetworkFloatingIpService extends MorpheusSynchronousDataService<NetworkFloatingIp, NetworkFloatingIpIdentityProjection>, MorpheusSynchronousIdentityService<NetworkFloatingIpIdentityProjection> {
	/**
	 * Returns the Network Floating IP Pool Context  used for performing updates or queries on {@link NetworkFloatingIp} related assets within Morpheus
	 * @return the Network Floating IP Pool Context to use for calls by various floating ip providers.
	 */
	MorpheusSynchronousNetworkFloatingIpPoolService getPool();
}
