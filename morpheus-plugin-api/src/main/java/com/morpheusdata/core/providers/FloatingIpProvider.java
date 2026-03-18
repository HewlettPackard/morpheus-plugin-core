package com.morpheusdata.core.providers;

import com.morpheusdata.model.NetworkFloatingIpPool;
import com.morpheusdata.model.NetworkServer;
import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.NetworkFloatingIp;
import com.morpheusdata.model.OptionType;
import com.morpheusdata.response.ServiceResponse;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Provides a standard set of methods for interacting with floating ips.
 * @author Mark Helt
 * @since 1.2.6
 */
// add type
public interface FloatingIpProvider extends PluginProvider {
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

	/**
	 * Called when a NetworkFloatingIpPool is created in Morpheus
	 * @param pool the {@link NetworkFloatingIpPool} that was created
	 * @return ServiceResponse containing the resulting {@link NetworkFloatingIp }. The resulting NetworkFloatingIp should contain the external system object
	 */
	default ServiceResponse<NetworkFloatingIpPool> createFloatingIpPool(NetworkFloatingIpPool pool) {
		return ServiceResponse.success(pool);
	}

	/**
	 * Called when a NetworkFloatingIpPool is updated in Morpheus
	 * @param pool the {@link NetworkFloatingIpPool} that was updated
	 * @return ServiceResponse containing the resulting {@link NetworkFloatingIp }. The resulting NetworkFloatingIp should contain the external system object
	 */
	default ServiceResponse<NetworkFloatingIpPool> updateFloatingIpPool(NetworkFloatingIpPool pool) {
		return ServiceResponse.success(pool);
	}

	/**
	 * Called when a NetworkFloatingIpPool is deleted in Morpheus
	 * @param pool the {@link NetworkFloatingIpPool} that was deleted
	 * @return ServiceResponse containing the resulting {@link NetworkFloatingIp }. The resulting NetworkFloatingIp should contain the external system object
	 */
	default ServiceResponse<NetworkFloatingIpPool> deleteFloatingIpPool(NetworkFloatingIpPool pool) {
		return ServiceResponse.success(pool);
	}

	/**
	 * Called when a NetworkFloatingIpPool is validated in Morpheus. This is called before a NetworkFloatingIpPool is created or updated in Morpheus, and can be used to validate the pool against the external system.
	 * @param pool the {@link NetworkFloatingIpPool} to validate
	 * @return ServiceResponse containing the resulting {@link NetworkFloatingIp }. The resulting NetworkFloatingIp should contain the external system object if validation is successful, or an error message if validation fails.
	 */
	default ServiceResponse<NetworkFloatingIpPool> validateFloatingIpPool(NetworkFloatingIpPool pool) {
		return ServiceResponse.success(pool);
	}

	/**
	 * Deprecated. Use {@link #getOptionTypes()} instead.
	 * @return a collection of {@link OptionType} objects representing the supported floating ip option types
	 */
	@Deprecated(since="1.3.2")
	default Collection<OptionType> getFloatingIpTypes() {
		return getOptionTypes();
	}

	/**
	 * Get the list of floating ip option types. The option types are used when adding Floating IPs to a provisioned server
	 * @return a collection of {@link OptionType} objects representing the supported floating ip option types
	 */
	default Collection<OptionType> getOptionTypes() {
		return new ArrayList<>();
	}

	/**
	 * Indicates whether this provider supports creating floating ip pools. If true, the UI will show the option to create floating ip pools and call the createFloatingIpPool method on this provider.
	 * @return true if this provider supports creating floating ip pools, false otherwise
	 */
	default Boolean floatingPoolsCreatable() {
		return false;
	}
}
