package com.morpheusdata.model.system;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.Account;
import com.morpheusdata.model.MorpheusModel;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class System extends MorpheusModel {
	protected String name;
	protected String description;
	protected String status = "ok";
	protected Boolean enabled = true;
	protected String statusMessage;
	protected Date statusMessageDate;
	protected SystemType type;
	protected SystemTypeLayout layout;
	protected String externalId;
	protected Date dateCreated;
	protected Date lastUpdated;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account owner;
	protected List<SystemComponent> components = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		markDirty("name", name);
		this.name = name;
	}

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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		markDirty("enabled", enabled);
		this.enabled = enabled;
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

	public SystemType getType() {
		return type;
	}

	public void setType(SystemType type) {
		markDirty("type", type);
		this.type = type;
	}

	public SystemTypeLayout getLayout() {
		return layout;
	}

	public void setLayout(SystemTypeLayout layout) {
		markDirty("layout", layout);
		this.layout = layout;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		markDirty("externalId", externalId);
		this.externalId = externalId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		markDirty("dateCreated", dateCreated);
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		markDirty("lastUpdated", lastUpdated);
		this.lastUpdated = lastUpdated;
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		markDirty("owner", owner);
		this.owner = owner;
	}

	public List<SystemComponent> getComponents() {
		return components;
	}

	public void setComponents(List<SystemComponent> components) {
		markDirty("components", components);
		this.components = components;
	}

	public Map<String,List<SystemComponent>> getGroupedComponents() {
		return this.components.stream()
			.collect(Collectors.groupingBy(component -> component.getType().getCode()));
	}

	public List<SystemComponent> getComponentsByCode(String code) {
		return this.components.stream()
			.filter(component -> component.getType().getCode().equals(code))
			.collect(Collectors.toList());
	}
}
