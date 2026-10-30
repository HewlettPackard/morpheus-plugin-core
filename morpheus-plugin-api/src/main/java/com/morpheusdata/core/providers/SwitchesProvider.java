package com.morpheusdata.core.providers;

import com.morpheusdata.model.NetworkServer;
import com.morpheusdata.model.NetworkSwitch;
import com.morpheusdata.model.NetworkSwitchPort;
import com.morpheusdata.model.NetworkSwitchPortType;
import com.morpheusdata.model.NetworkSwitchWithPorts;
import com.morpheusdata.response.ServiceResponse;

import java.util.List;

/**
 * Provides the operations a network integration implements to manage physical or virtual switches and their ports.
 * A SwitchesProvider is nested under a {@link NetworkProvider}: the network provider advertises support through
 * {@link NetworkProvider#hasSwitches()} and exposes the implementation via {@link NetworkProvider#getSwitchesProvider()}.
 *
 * @see NetworkSwitch
 * @see NetworkSwitchPort
 */
public interface SwitchesProvider extends PluginProvider {

	/**
	 * Lists the switches on the network server along with their ports, including each port's VLANs, type,
	 * state and MAC address.
	 * @param networkServer the network integration to query
	 * @return the switches and their ports
	 */
	ServiceResponse<List<NetworkSwitchWithPorts>> listSwitches(NetworkServer networkServer);

	/**
	 * Gets a single switch with its ports.
	 * @param networkServer the network integration to query
	 * @param networkSwitch the switch to load; {@link NetworkSwitch#getExternalId()} identifies it remotely
	 * @return the switch and its ports
	 */
	ServiceResponse<NetworkSwitchWithPorts> getSwitch(NetworkServer networkServer, NetworkSwitch networkSwitch);

	/**
	 * Lists the ports of a switch.
	 * @param networkServer the network integration to query
	 * @param networkSwitch the switch whose ports are listed
	 * @return the ports of the switch
	 */
	ServiceResponse<List<NetworkSwitchPort>> listSwitchPorts(NetworkServer networkServer, NetworkSwitch networkSwitch);

	/**
	 * Creates a switch in the external system.
	 * @param networkServer the network integration to create the switch on
	 * @param networkSwitch the switch to create
	 * @return the created switch, including its external identifiers
	 */
	ServiceResponse<NetworkSwitch> createSwitch(NetworkServer networkServer, NetworkSwitch networkSwitch);

	/**
	 * Updates a switch in the external system.
	 * @param networkServer the network integration the switch belongs to
	 * @param networkSwitch the switch with the desired changes applied
	 * @return the updated switch
	 */
	ServiceResponse<NetworkSwitch> updateSwitch(NetworkServer networkServer, NetworkSwitch networkSwitch);

	/**
	 * Deletes a switch from the external system.
	 * @param networkServer the network integration the switch belongs to
	 * @param networkSwitch the switch to delete
	 * @return whether the delete succeeded
	 */
	ServiceResponse<Void> deleteSwitch(NetworkServer networkServer, NetworkSwitch networkSwitch);

	/**
	 * Sets a port administratively up.
	 * @param networkServer the network integration the switch belongs to
	 * @param networkSwitch the switch owning the port
	 * @param port the port to bring up
	 * @return the updated port
	 */
	ServiceResponse<NetworkSwitchPort> setPortUp(NetworkServer networkServer, NetworkSwitch networkSwitch, NetworkSwitchPort port);

	/**
	 * Sets a port administratively down.
	 * @param networkServer the network integration the switch belongs to
	 * @param networkSwitch the switch owning the port
	 * @param port the port to shut down
	 * @return the updated port
	 */
	ServiceResponse<NetworkSwitchPort> setPortDown(NetworkServer networkServer, NetworkSwitch networkSwitch, NetworkSwitchPort port);

	/**
	 * Changes the VLAN mode of a port (access, hybrid or trunk).
	 * @param networkServer the network integration the switch belongs to
	 * @param networkSwitch the switch owning the port
	 * @param port the port to change
	 * @param portType the new port type
	 * @return the updated port
	 */
	ServiceResponse<NetworkSwitchPort> setPortType(NetworkServer networkServer, NetworkSwitch networkSwitch, NetworkSwitchPort port, NetworkSwitchPortType portType);

	/**
	 * Adds a VLAN to a port.
	 * @param networkServer the network integration the switch belongs to
	 * @param networkSwitch the switch owning the port
	 * @param port the port to add the VLAN to
	 * @param vlanId the VLAN id to add
	 * @param tagged whether the VLAN is carried tagged ({@code true}) or untagged ({@code false})
	 * @return the updated port
	 */
	ServiceResponse<NetworkSwitchPort> addPortVlan(NetworkServer networkServer, NetworkSwitch networkSwitch, NetworkSwitchPort port, Integer vlanId, Boolean tagged);

	/**
	 * Removes a VLAN from a port.
	 * @param networkServer the network integration the switch belongs to
	 * @param networkSwitch the switch owning the port
	 * @param port the port to remove the VLAN from
	 * @param vlanId the VLAN id to remove
	 * @return the updated port
	 */
	ServiceResponse<NetworkSwitchPort> removePortVlan(NetworkServer networkServer, NetworkSwitch networkSwitch, NetworkSwitchPort port, Integer vlanId);
}
