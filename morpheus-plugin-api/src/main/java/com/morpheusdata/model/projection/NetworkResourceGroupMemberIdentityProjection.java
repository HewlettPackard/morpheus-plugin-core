package com.morpheusdata.model.projection;

public class NetworkResourceGroupMemberIdentityProjection extends MorpheusIdentityModel {

	protected String externalId;
	protected String refType;
	protected Long refId;
	protected String memberName;

	public NetworkResourceGroupMemberIdentityProjection(String externalId, String refType, Long refId, String memberName) {
		this.externalId = externalId;
		this.refType = refType;
		this.refId = refId;
		this.memberName = memberName;
	}

	public NetworkResourceGroupMemberIdentityProjection() {

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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
		markDirty("memberName",memberName);
	}
}
