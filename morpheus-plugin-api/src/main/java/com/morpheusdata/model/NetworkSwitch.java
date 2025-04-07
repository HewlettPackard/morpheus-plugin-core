package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.core.providers.NetworkProvider;
import com.morpheusdata.model.projection.NetworkSwitchIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a NetworkSwitch that is typically provided via a {@link NetworkProvider}. These are used to sync in
 * add/update/delete switch models on a network integration
 *
 * @see NetworkSwitchType
 */
public class NetworkSwitch extends NetworkSwitchIdentityProjection {
	@JsonSerialize(using=ModelAsIdOnlySerializer.class)
	protected Account owner;
	@JsonSerialize(using=ModelAsIdOnlySerializer.class)
	protected NetworkSwitchType type;
	@JsonSerialize(using=ModelAsIdOnlySerializer.class)
	protected NetworkServer networkServer;
	@JsonSerialize(using=ModelAsIdOnlySerializer.class)
	protected Cloud cloud;
	protected String code;
	protected String category;
	protected String name;
	protected String description;
	protected String displayName;
	protected String visibility = "private";
	protected Boolean active = true;
	protected String externalId;
	protected String internalId;
	protected String uniqueId;
	protected String providerId;
	protected String scopeId;
	protected String externalType = "NetworkSwitch";
	protected String refUrl;
	protected String refType;
	protected Long refId;
	protected String switchId;
	protected String fabricId;
	protected Integer networkMtu;
	protected String networkTeaming;
	protected String uplinkId;
	protected Boolean promiscuousMode;
	protected String scopeMode;
	protected String status;
	protected String statusMessage;
	protected List<Network> networks = new ArrayList<>();

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
		markDirty("owner", this.owner);
	}

	public NetworkSwitchType getType() {
		return type;
	}

	public void setType(NetworkSwitchType type) {
		this.type = type;
		markDirty("type", this.type);
	}

	public NetworkServer getNetworkServer() {
		return networkServer;
	}

	public void setNetworkServer(NetworkServer networkServer) {
		this.networkServer = networkServer;
		markDirty("networkServer", this.networkServer);
	}

	public Cloud getCloud() {
		return cloud;
	}

	public void setCloud(Cloud cloud) {
		this.cloud = cloud;
		markDirty("cloud", this.cloud);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", this.code);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
		markDirty("category", this.category);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", this.name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", this.description);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		markDirty("displayName", this.displayName);
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
		markDirty("visibility", this.visibility);
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
		markDirty("active", this.active);
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
		markDirty("internalId", this.internalId);
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
		markDirty("uniqueId", this.uniqueId);
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
		markDirty("providerId", this.providerId);
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
		markDirty("scopeId", this.scopeId);
	}

	public String getExternalType() {
		return externalType;
	}

	public void setExternalType(String externalType) {
		this.externalType = externalType;
		markDirty("externalType", this.externalType);
	}

	public String getRefUrl() {
		return refUrl;
	}

	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
		markDirty("refUrl", this.refUrl);
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
		markDirty("refType", this.refType);
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
		markDirty("refId", this.refId);
	}

	public String getSwitchId() {
		return switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
		markDirty("switchId", this.switchId);
	}

	public String getFabricId() {
		return fabricId;
	}

	public void setFabricId(String fabricId) {
		this.fabricId = fabricId;
		markDirty("fabricId", this.fabricId);
	}

	public Integer getNetworkMtu() {
		return networkMtu;
	}

	public void setNetworkMtu(Integer networkMtu) {
		this.networkMtu = networkMtu;
		markDirty("networkMtu", this.networkMtu);
	}

	public String getNetworkTeaming() {
		return networkTeaming;
	}

	public void setNetworkTeaming(String networkTeaming) {
		this.networkTeaming = networkTeaming;
		markDirty("networkTeaming", this.networkTeaming);
	}

	public String getUplinkId() {
		return uplinkId;
	}

	public void setUplinkId(String uplinkId) {
		this.uplinkId = uplinkId;
		markDirty("uplinkId", this.uplinkId);
	}

	public Boolean getPromiscuousMode() {
		return promiscuousMode;
	}

	public void setPromiscuousMode(Boolean promiscuousMode) {
		this.promiscuousMode = promiscuousMode;
		markDirty("promiscuousMode", this.promiscuousMode);
	}

	public String getScopeMode() {
		return scopeMode;
	}

	public void setScopeMode(String scopeMode) {
		this.scopeMode = scopeMode;
		markDirty("scopeMode", this.scopeMode);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		markDirty("status", this.status);
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
		markDirty("statusMessage", this.statusMessage);
	}

	public List<Network> getNetworks() {
		return networks;
	}

	public void setNetworks(List<Network> networks) {
		this.networks = networks;
		markDirty("networks", this.networks);
	}
}
