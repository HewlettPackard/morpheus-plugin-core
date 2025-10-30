package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.projection.NetworkRouterBgpNeighborIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Date;

public class NetworkRouterBgpNeighbor extends NetworkRouterBgpNeighborIdentityProjection {
	protected String ipAddress;
	protected String description;
	protected String forwardingAddress;
	protected String protocolAddress;
	protected String remoteAs;
	protected Integer weight = 60;
	protected Integer keepAlive = 60; //seconds
	protected Integer holdDown = 180; //seconds
	protected String password;
	protected String routeFilteringType;
	protected String routeFilteringIn;
	protected String routeFilteringOut;
	protected Boolean bfdEnabled = false;
	protected Integer bfdInterval;
	protected Integer bfdMultiple;
	protected Boolean allowAsIn = false;
	protected Integer hopLimit = 1;
	protected String restartMode;
	protected String providerId;
	protected String syncSource = "external";
	protected String internalId;

	protected Date dateCreated;
	protected Date lastUpdated;

	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected NetworkRouter router;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getForwardingAddress() {
		return forwardingAddress;
	}

	public void setForwardingAddress(String forwardingAddress) {
		this.forwardingAddress = forwardingAddress;
	}

	public String getProtocolAddress() {
		return protocolAddress;
	}

	public void setProtocolAddress(String protocolAddress) {
		this.protocolAddress = protocolAddress;
	}

	public String getRemoteAs() {
		return remoteAs;
	}

	public void setRemoteAs(String remoteAs) {
		this.remoteAs = remoteAs;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(Integer keepAlive) {
		this.keepAlive = keepAlive;
	}

	public Integer getHoldDown() {
		return holdDown;
	}

	public void setHoldDown(Integer holdDown) {
		this.holdDown = holdDown;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRouteFilteringType() {
		return routeFilteringType;
	}

	public void setRouteFilteringType(String routeFilteringType) {
		this.routeFilteringType = routeFilteringType;
	}

	public String getRouteFilteringIn() {
		return routeFilteringIn;
	}

	public void setRouteFilteringIn(String routeFilteringIn) {
		this.routeFilteringIn = routeFilteringIn;
	}

	public String getRouteFilteringOut() {
		return routeFilteringOut;
	}

	public void setRouteFilteringOut(String routeFilteringOut) {
		this.routeFilteringOut = routeFilteringOut;
	}

	public Boolean getBfdEnabled() {
		return bfdEnabled;
	}

	public void setBfdEnabled(Boolean bfdEnabled) {
		this.bfdEnabled = bfdEnabled;
	}

	public Integer getBfdInterval() {
		return bfdInterval;
	}

	public void setBfdInterval(Integer bfdInterval) {
		this.bfdInterval = bfdInterval;
	}

	public Integer getBfdMultiple() {
		return bfdMultiple;
	}

	public void setBfdMultiple(Integer bfdMultiple) {
		this.bfdMultiple = bfdMultiple;
	}

	public Boolean getAllowAsIn() {
		return allowAsIn;
	}

	public void setAllowAsIn(Boolean allowAsIn) {
		this.allowAsIn = allowAsIn;
	}

	public Integer getHopLimit() {
		return hopLimit;
	}

	public void setHopLimit(Integer hopLimit) {
		this.hopLimit = hopLimit;
	}

	public String getRestartMode() {
		return restartMode;
	}

	public void setRestartMode(String restartMode) {
		this.restartMode = restartMode;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getSyncSource() {
		return syncSource;
	}

	public void setSyncSource(String syncSource) {
		this.syncSource = syncSource;
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public NetworkRouter getRouter() {
		return router;
	}

	public void setRouter(NetworkRouter router) {
		this.router = router;
	}
}
