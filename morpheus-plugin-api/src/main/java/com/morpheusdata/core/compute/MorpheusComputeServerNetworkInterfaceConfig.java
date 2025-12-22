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
		map.put("macAddress", macAddress);
		map.put("externalId", externalId);
		map.put("externalType", externalType);
		map.put("type", type);
		map.put("networkInterfaceTypeId", networkInterfaceTypeId);
		map.put("index", index);
		map.put("parent", parent);
		map.put("isPrimaryOverride", isPrimaryOverride);
		map.put("opts", opts);
		map.put("ipMode", ipMode);
		map.put("replaceHostRecord", replaceHostRecord);
		map.put("isPrimary", isPrimary);
		map.put("network", network);
		map.put("nicName", nicName);
		map.put("ipAddress", ipAddress);
		return map;
	}
}
