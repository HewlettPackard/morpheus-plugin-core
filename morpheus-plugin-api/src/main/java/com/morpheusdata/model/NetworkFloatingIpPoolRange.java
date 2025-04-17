package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Date;

public class NetworkFloatingIpPoolRange extends MorpheusModel {
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected NetworkFloatingIpPool floatingIpPool;
	protected String startAddress;
	protected String endAddress;
	protected String internalId;
	protected String externalId;
	protected String description;
	protected Integer addressCount = 0;
	protected Boolean active = true;
	protected Date dateCreated;
	protected Date lastUpdated;

	public NetworkFloatingIpPool getFloatingIpPool() {
		return floatingIpPool;
	}

	public void setFloatingIpPool(NetworkFloatingIpPool floatingIpPool) {
		this.floatingIpPool = floatingIpPool;
		markDirty("floatingIpPool", floatingIpPool);
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
		markDirty("startAddress", startAddress);
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
		markDirty("endAddress", endAddress);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	public Integer getAddressCount() {
		return addressCount;
	}

	public void setAddressCount(Integer addressCount) {
		this.addressCount = addressCount;
		markDirty("addressCount", addressCount);
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
		markDirty("active", active);
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
