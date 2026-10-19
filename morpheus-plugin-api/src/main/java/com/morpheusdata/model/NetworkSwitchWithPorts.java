package com.morpheusdata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link NetworkSwitch} together with its {@link NetworkSwitchPort}s, as returned when listing switches.
 */
public class NetworkSwitchWithPorts {
	protected NetworkSwitch networkSwitch;
	protected List<NetworkSwitchPort> ports = new ArrayList<>();

	public NetworkSwitchWithPorts() {}

	public NetworkSwitchWithPorts(NetworkSwitch networkSwitch, List<NetworkSwitchPort> ports) {
		this.networkSwitch = networkSwitch;
		this.ports = ports;
	}

	public NetworkSwitch getNetworkSwitch() {
		return networkSwitch;
	}

	public void setNetworkSwitch(NetworkSwitch networkSwitch) {
		this.networkSwitch = networkSwitch;
	}

	public List<NetworkSwitchPort> getPorts() {
		return ports;
	}

	public void setPorts(List<NetworkSwitchPort> ports) {
		this.ports = ports;
	}
}
