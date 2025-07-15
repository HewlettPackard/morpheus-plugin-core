package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

/**
 * A device attached to a {@link ComputeServer}.
 * <p>
 * This would be something discoverable from the host such as a GPU, NIC, other PCI device, USB device, etc..
 */
public class ComputeDevice extends MorpheusModel {

	public String name;
	public ComputeDeviceType type;
	public Integer slot;
	public Integer domainId;
	public Integer bus;
	public Integer device;
	public Integer functionId;
	public String externalId;
	public String uniqueId;
	public String vendorId;
	public String productId;
	public Integer iommuGroup;
	public Integer iommuDeviceCount = 1;
	public Status status = Status.ATTACHED;
	public String refType;
	public Long refId;

	@JsonSerialize(using=ModelAsIdOnlySerializer.class)
	public ComputeServer server;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public ComputeDeviceType getType() {
		return type;
	}

	public void setType(ComputeDeviceType type) {
		this.type = type;
		markDirty("type", type);
	}

	public Integer getDomainId() {
		return domainId;
	}

	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
		markDirty("domainId", domainId);
	}

	public Integer getBus() {
		return bus;
	}

	public void setBus(Integer bus) {
		this.bus = bus;
		markDirty("bus", bus);
	}

	public Integer getSlot() {
		return slot;
	}

	public void setSlot(Integer slot) {
		this.slot = slot;
		markDirty("slot", slot);
	}

	public Integer getDevice() {
		return device;
	}

	public void setDevice(Integer device) {
		this.device = device;
		markDirty("device", device);
	}

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
		markDirty("functionId", functionId);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId", externalId);
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
		markDirty("uniqueId", uniqueId);
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
		markDirty("vendorId", vendorId);
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
		markDirty("productId", productId);
	}

	public Integer getIommuGroup() {
		return iommuGroup;
	}

	public void setIommuGroup(Integer iommuGroup) {
		this.iommuGroup = iommuGroup;
		markDirty("iommuGroup", iommuGroup);
	}

	public Integer getIommuDeviceCount() {
		return iommuDeviceCount;
	}

	public void setIommuDeviceCount(Integer iommuDeviceCount) {
		this.iommuDeviceCount = iommuDeviceCount;
		markDirty("iommuDeviceCount", iommuDeviceCount);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
		markDirty("status", status);
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

	public ComputeServer getServer() {
		return server;
	}

	public void setServer(ComputeServer server) {
		this.server = server;
		markDirty("server", server);
	}

	public enum Status {
		ATTACHED,
		DETACHED,
		PENDING,
		ASSIGNED
	}
}
