package com.morpheusdata.core.providers;

import com.morpheusdata.model.NetworkFloatingIpPool;
import com.morpheusdata.model.NetworkServer;
import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.NetworkFloatingIp;
import com.morpheusdata.response.ServiceResponse;

/**
 * Provides a standard set of methods for interacting with floating ips.
 * @author Mark Helt
 * @since 1.2.6
 */
// add type
public interface FloatingIpProvider extends PluginProvider{
	/**
	 * Attaches a floating ip to a server
	 * @param server the server to attach the floating ip to
	 * @param floatingIp the floating ip to attach
	 * @return ServiceResponse
	 */
	ServiceResponse attachFloatingIp(ComputeServer server, NetworkFloatingIp floatingIp);

	/**
	 * Detaches a floating ip from a server
	 * @param server the server to detach the floating ip from
	 * @param floatingIp the floating ip to detach
	 * @return ServiceResponse
	 */
	ServiceResponse detachFloatingIp(ComputeServer server, NetworkFloatingIp floatingIp);

	/**
	 * Releases a floating ip
	 * @param floatingIp the floating ip to release
	 * @return ServiceResponse
	 */
	ServiceResponse releaseFloatingIp(NetworkFloatingIp floatingIp);

	/**
	 * Allocates a floating ip
	 * @param networkServer the network server to allocate the floating ip from
	 * @param pool the floating ip pool to allocate the floating ip from
	 * @return ServiceResponse
	 */
	ServiceResponse allocateFloatingIp(NetworkServer networkServer, NetworkFloatingIpPool pool);
}
