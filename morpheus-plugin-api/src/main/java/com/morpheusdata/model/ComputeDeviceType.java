package com.morpheusdata.model;

public class ComputeDeviceType extends MorpheusModel {
	public Family family;
	public String name;
	public String code;
	public String busType;
	public String description;
	public String matchPattern;
	public Integer vendorId;
	public String vendorName;
	public Integer productId;
	public String capabilityType;
	public String productName;
	public Boolean hotpluggable = false;
	public Boolean assignable = false;

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
		markDirty("family", family);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
		markDirty("busType", busType);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	public String getMatchPattern() {
		return matchPattern;
	}

	public void setMatchPattern(String matchPattern) {
		this.matchPattern = matchPattern;
		markDirty("matchPattern", matchPattern);
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
		markDirty("vendorId", vendorId);
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
		markDirty("vendorName", vendorName);
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
		markDirty("productId", productId);
	}

	public String getCapabilityType() {
		return capabilityType;
	}

	public void setCapabilityType(String capabilityType) {
		this.capabilityType = capabilityType;
		markDirty("capabilityType", capabilityType);
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
		markDirty("productName", productName);
	}

	public Boolean getHotpluggable() {
		return hotpluggable;
	}

	public void setHotpluggable(Boolean hotpluggable) {
		this.hotpluggable = hotpluggable;
		markDirty("hotpluggable", hotpluggable);
	}

	public Boolean getAssignable() {
		return assignable;
	}

	public void setAssignable(Boolean assignable) {
		this.assignable = assignable;
		markDirty("assignable", assignable);
	}

	public enum Family {
		GPU,
		USB,
		PCI,
		STORAGE,
		NETWORK
	}
}
