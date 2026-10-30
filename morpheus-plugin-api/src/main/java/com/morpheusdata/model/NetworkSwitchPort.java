package com.morpheusdata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A port on a {@link NetworkSwitch} as reported by a
 * {@link com.morpheusdata.core.providers.SwitchesProvider}.
 */
public class NetworkSwitchPort {
	protected String externalId;
	protected String name;
	protected String description;
	protected NetworkSwitchPortType portType;
	protected NetworkSwitchPortState portState;
	protected String macAddress;
	/** VLAN ids carried untagged (the native VLAN for hybrid ports, the access VLAN for access ports). */
	protected List<Integer> untaggedVlans = new ArrayList<>();
	/** VLAN ids carried tagged (trunk and hybrid ports). */
	protected List<Integer> taggedVlans = new ArrayList<>();

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public NetworkSwitchPortType getPortType() {
		return portType;
	}

	public void setPortType(NetworkSwitchPortType portType) {
		this.portType = portType;
	}

	public NetworkSwitchPortState getPortState() {
		return portState;
	}

	public void setPortState(NetworkSwitchPortState portState) {
		this.portState = portState;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public List<Integer> getUntaggedVlans() {
		return untaggedVlans;
	}

	public void setUntaggedVlans(List<Integer> untaggedVlans) {
		this.untaggedVlans = untaggedVlans;
	}

	public List<Integer> getTaggedVlans() {
		return taggedVlans;
	}

	public void setTaggedVlans(List<Integer> taggedVlans) {
		this.taggedVlans = taggedVlans;
	}
}
