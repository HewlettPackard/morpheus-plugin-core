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
	 * Allocates a floating ip in external system, the result floating ip should contain the external system object id
	 *
	 * <p><strong>Example NetworkFloatingIp To include in ServiceResponse:</strong></p>
	 * <pre>{@code
	 * def networkFloatingIp = new NetworkFloatingIp(
	 * 	floatingPool:pool, externalId:externalObject.id, internalId:externalObject.port_id, refType:'ComputeZone',
	 * 	refId:cloud.id, active:(externalObject.status == 'ACTIVE' || externalObject.status == 'DOWN'), staticIp:true,
	 * 	ipAddress:externalObject.floating_ip_address, ipStatus:(externalObject.port_id || externalObject.fixed_ip_address ? 'assigned' : 'free'),
	 * 	ptrId:item.fixed_ip_address, category: objCategory)
	 *
	 * 	networkFloatingIp.setConfigMap(externalObject)
	 *
	 * 	return ServiceResponse<NetworkFloatingIp>.success(networkFloatingIp)
	 * }</pre>
	 * @param networkServer the network server to allocate the floating ip from
	 * @param pool the floating ip pool to allocate the floating ip from
	 * @return ServiceResponse containing the resulting {@link NetworkFloatingIp }
	 */
	ServiceResponse<NetworkFloatingIp> allocateFloatingIp(NetworkServer networkServer, NetworkFloatingIpPool pool);
}
