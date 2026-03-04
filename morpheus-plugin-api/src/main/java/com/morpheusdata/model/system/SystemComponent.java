package com.morpheusdata.model.system;

import com.morpheusdata.model.projection.SystemComponentIdentityProjection;

import java.util.Date;

public class SystemComponent extends SystemComponentIdentityProjection {
	protected String description;
	protected String status = "ok";
	protected String statusMessage;
	protected Date statusMessageDate;
	protected SystemComponentType type;
	protected String refType;
	protected String refId;
	protected Date dateCreated;
	protected Date lastUpdated;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		markDirty("description", description);
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		markDirty("status", status);
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		markDirty("statusMessage", statusMessage);
		this.statusMessage = statusMessage;
	}

	public Date getStatusMessageDate() {
		return statusMessageDate;
	}

	public void setStatusMessageDate(Date statusMessageDate) {
		markDirty("statusMessageDate", statusMessageDate);
		this.statusMessageDate = statusMessageDate;
	}

	public SystemComponentType getType() {
		return type;
	}

	public void setType(SystemComponentType type) {
		markDirty("type", type);
		this.type = type;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		markDirty("refType", refType);
		this.refType = refType;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		markDirty("refId", refId);
		this.refId = refId;
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
}
