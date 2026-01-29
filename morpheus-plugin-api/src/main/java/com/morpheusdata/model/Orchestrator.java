/*
 *  Copyright 2024 Morpheus Data, LLC.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;
import com.morpheusdata.model.system.SystemTypeLayout;

import java.util.Date;

/**
 * Represents an orchestration workflow instance that maintains state across
 * multiple configuration steps. The orchestrator is linked to a
 * SystemTypeLayout
 * and uses an OrchestrationState object to track progress and configuration
 * data
 * collected through a multi-step orchestration process.
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public class Orchestrator extends MorpheusModel {

	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected Account owner;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected Account account;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected SystemTypeLayout systemTypeLayout;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected User createdBy;

	protected String name;
	protected String code;
	protected String description;

	// Audit fields
	protected Date dateCreated;
	protected Date lastUpdated;

	// Optional reference to parent object
	protected String refType;
	protected Long refId;

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
		markDirty("owner", owner);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
		markDirty("account", account);
	}

	public SystemTypeLayout getSystemTypeLayout() {
		return systemTypeLayout;
	}

	public void setSystemTypeLayout(SystemTypeLayout systemTypeLayout) {
		this.systemTypeLayout = systemTypeLayout;
		markDirty("systemTypeLayout", systemTypeLayout);
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
		markDirty("createdBy", createdBy);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
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

}