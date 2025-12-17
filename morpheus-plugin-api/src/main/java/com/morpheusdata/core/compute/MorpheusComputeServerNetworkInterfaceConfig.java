package com.morpheusdata.core.compute;

import com.morpheusdata.model.ComputeServerInterface;
import com.morpheusdata.model.ComputeServerInterfaceType;
import com.morpheusdata.model.Network;

import java.util.Map;

public class MorpheusComputeServerNetworkInterfaceConfig {
	public String macAddress;
	public String externalId;
	public String externalType;
	public ComputeServerInterfaceType type;
	public String networkInterfaceTypeId;
	public int index;
	public ComputeServerInterface parent;
	public Boolean isPrimaryOverride;
	public String opts;
	public String ipMode;
	public Boolean replaceHostRecord;
	public Boolean isPrimary;
	public Network network;
	public String nicName;
	public String ipAddress;

	public Map<String, Object> toMap() {
		Map<String, Object> map = new java.util.HashMap<>();
		if (macAddress != null) map.put("macAddress", macAddress);
		if (externalId != null) map.put("externalId", externalId);
		if (externalType != null) map.put("externalType", externalType);
		if (type != null) map.put("type", type);
		if (networkInterfaceTypeId != null) map.put("networkInterfaceTypeId", networkInterfaceTypeId);
		map.put("index", index); // always include index as it's a primitive int
		if (parent != null) map.put("parent", parent);
		if (isPrimaryOverride != null) map.put("isPrimaryOverride", isPrimaryOverride);
		if (opts != null) map.put("opts", opts);
		if (ipMode != null) map.put("ipMode", ipMode);
		if (replaceHostRecord != null) map.put("replaceHostRecord", replaceHostRecord);
		if (isPrimary != null) map.put("isPrimary", isPrimary);
		if (network != null) map.put("network", network);
		if (nicName != null) map.put("nicName", nicName);
		if (ipAddress != null) map.put("ipAddress", ipAddress);
		return map;
	}
}
