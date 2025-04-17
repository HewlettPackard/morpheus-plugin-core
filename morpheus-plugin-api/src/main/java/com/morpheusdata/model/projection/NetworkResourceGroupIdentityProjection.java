package com.morpheusdata.model.projection;

public class NetworkResourceGroupIdentityProjection extends MorpheusIdentityModel {

	protected String externalId;
	protected String refType;
	protected Long refId;
	protected String name;

	public NetworkResourceGroupIdentityProjection(String externalId, String refType, Long refId, String name) {
		this.externalId = externalId;
		this.refType = refType;
		this.refId = refId;
		this.name = name;
	}

	public NetworkResourceGroupIdentityProjection() {

	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId",externalId);

	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name",name);
	}
}
