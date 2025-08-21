package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.projection.StorageHostIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

public class StorageHost extends StorageHostIdentityProjection {

	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	private Account owner;
	private String name;
	private String code;
	private String category;
	private String visibility = "public"; //['public', 'private']
	private String description;
	private String internalId;
	private String externalId;
	private String config;
	private String status;
	private String statusMessage;
	private Integer fiberPort;
	private Integer fiberNode;
	private Integer fiberSlot;
	private String fiberWwn;
	private String iscsiPath;
	private String initiatorChapName;
	private String targetChapName;
	private Boolean initiatorChap = false;
	private Boolean targetChap = false;
	private String hostType;
	private String hostPersona;


	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Integer getFiberPort() {
		return fiberPort;
	}

	public void setFiberPort(Integer fiberPort) {
		this.fiberPort = fiberPort;
	}

	public Integer getFiberNode() {
		return fiberNode;
	}

	public void setFiberNode(Integer fiberNode) {
		this.fiberNode = fiberNode;
	}

	public Integer getFiberSlot() {
		return fiberSlot;
	}

	public void setFiberSlot(Integer fiberSlot) {
		this.fiberSlot = fiberSlot;
	}

	public String getFiberWwn() {
		return fiberWwn;
	}

	public void setFiberWwn(String fiberWwn) {
		this.fiberWwn = fiberWwn;
	}

	public String getIscsiPath() {
		return iscsiPath;
	}

	public void setIscsiPath(String iscsiPath) {
		this.iscsiPath = iscsiPath;
	}

	public String getInitiatorChapName() {
		return initiatorChapName;
	}

	public void setInitiatorChapName(String initiatorChapName) {
		this.initiatorChapName = initiatorChapName;
	}

	public String getTargetChapName() {
		return targetChapName;
	}

	public void setTargetChapName(String targetChapName) {
		this.targetChapName = targetChapName;
	}

	public Boolean getInitiatorChap() {
		return initiatorChap;
	}

	public void setInitiatorChap(Boolean initiatorChap) {
		this.initiatorChap = initiatorChap;
	}

	public Boolean getTargetChap() {
		return targetChap;
	}

	public void setTargetChap(Boolean targetChap) {
		this.targetChap = targetChap;
	}

	public String getHostType() {
		return hostType;
	}

	public void setHostType(String hostType) {
		this.hostType = hostType;
	}

	public String getHostPersona() {
		return hostPersona;
	}

	public void setHostPersona(String hostPersona) {
		this.hostPersona = hostPersona;
	}
}
