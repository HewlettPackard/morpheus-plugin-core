package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Date;

public class NetworkRouterNAT extends MorpheusModel {

	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected NetworkRouter router;

	protected String name;
	protected String description;
	protected Boolean enabled = true;
	protected String sourceNetwork;
	protected String sourceRangeType;
	protected String sourceSecondaryRangesName;
	protected String destinationNetwork;
	protected String translatedNetwork;
	protected String sourcePorts;
	protected String destinationPorts;
	protected String translatedPorts;
	protected Integer priority = 0;
	protected String protocol;
	protected String matchIpv6DestinationPrefix;
	protected String translatedIpv4SourcePrefix;

	// linking
	protected String refType;
	protected Long refId;

	// sync
	protected String syncSource = "external";
	protected String internalId;
	protected String externalId;
	protected String providerId;
	protected String externalType;

	// config
	protected String rawData;

	protected Date dateCreated;
	protected Date lastUpdated;

	public NetworkRouter getRouter() {
		return router;
	}

	public void setRouter(NetworkRouter router) {
		this.router = router;
		markDirty("router", router);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
	}

	public String getSourceNetwork() {
		return sourceNetwork;
	}

	public void setSourceNetwork(String sourceNetwork) {
		this.sourceNetwork = sourceNetwork;
		markDirty("sourceNetwork", sourceNetwork);
	}

	public String getSourceRangeType() {
		return sourceRangeType;
	}

	public void setSourceRangeType(String sourceRangeType) {
		this.sourceRangeType = sourceRangeType;
		markDirty("sourceRangeType", sourceRangeType);
	}

	public String getSourceSecondaryRangesName() {
		return sourceSecondaryRangesName;
	}

	public void setSourceSecondaryRangesName(String sourceSecondaryRangesName) {
		this.sourceSecondaryRangesName = sourceSecondaryRangesName;
		markDirty("sourceSecondaryRangesName", sourceSecondaryRangesName);
	}

	public String getDestinationNetwork() {
		return destinationNetwork;
	}

	public void setDestinationNetwork(String destinationNetwork) {
		this.destinationNetwork = destinationNetwork;
		markDirty("destinationNetwork", destinationNetwork);
	}

	public String getTranslatedNetwork() {
		return translatedNetwork;
	}

	public void setTranslatedNetwork(String translatedNetwork) {
		this.translatedNetwork = translatedNetwork;
		markDirty("translatedNetwork", translatedNetwork);
	}

	public String getSourcePorts() {
		return sourcePorts;
	}

	public void setSourcePorts(String sourcePorts) {
		this.sourcePorts = sourcePorts;
		markDirty("sourcePorts", sourcePorts);
	}

	public String getDestinationPorts() {
		return destinationPorts;
	}

	public void setDestinationPorts(String destinationPorts) {
		this.destinationPorts = destinationPorts;
		markDirty("destinationPorts", destinationPorts);
	}

	public String getTranslatedPorts() {
		return translatedPorts;
	}

	public void setTranslatedPorts(String translatedPorts) {
		this.translatedPorts = translatedPorts;
		markDirty("translatedPorts", translatedPorts);
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
		markDirty("priority", priority);
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
		markDirty("protocol", protocol);
	}

	public String getMatchIpv6DestinationPrefix() {
		return matchIpv6DestinationPrefix;
	}

	public void setMatchIpv6DestinationPrefix(String matchIpv6DestinationPrefix) {
		this.matchIpv6DestinationPrefix = matchIpv6DestinationPrefix;
		markDirty("matchIpv6DestinationPrefix", matchIpv6DestinationPrefix);
	}

	public String getTranslatedIpv4SourcePrefix() {
		return translatedIpv4SourcePrefix;
	}

	public void setTranslatedIpv4SourcePrefix(String translatedIpv4SourcePrefix) {
		this.translatedIpv4SourcePrefix = translatedIpv4SourcePrefix;
		markDirty("translatedIpv4SourcePrefix", translatedIpv4SourcePrefix);
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
		markDirty("refType", refType);
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
		markDirty("refId", refId);
	}

	public String getSyncSource() {
		return syncSource;
	}

	public void setSyncSource(String syncSource) {
		this.syncSource = syncSource;
		markDirty("syncSource", syncSource);
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
		markDirty("internalId", internalId);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId", externalId);

	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
		markDirty("providerId", providerId);
	}

	public String getExternalType() {
		return externalType;
	}

	public void setExternalType(String externalType) {
		this.externalType = externalType;
		markDirty("externalType", externalType);
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
		markDirty("rawData", rawData);
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
		markDirty("dateCreated", dateCreated);
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		markDirty("lastUpdated", lastUpdated);
	}
}
