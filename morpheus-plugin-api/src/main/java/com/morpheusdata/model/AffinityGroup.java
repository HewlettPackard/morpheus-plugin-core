/*
 *  Copyright 2025 Morpheus Data, LLC.
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
import com.morpheusdata.model.projection.MorpheusIdentityModel;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Collection;

public class AffinityGroup extends MorpheusIdentityModel {
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account owner;
	protected String name;
	protected Boolean active = true;
	protected String visibility = "private";
	protected AffinityType affinityType = AffinityType.KEEP_SEPARATE;
	protected String refType; //ComputeZone, ComputeServerGroup
	protected Long refId;
	protected String source = "user"; //user, sync
	protected String externalId;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected CloudPool pool;

	protected Collection<ComputeServer> servers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public Collection<ComputeServer> getServers() {
		return servers;
	}

	public void setServers(Collection<ComputeServer> servers) {
		this.servers = servers;
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public AffinityType getAffinityType() {
		return affinityType;
	}

	public void setAffinityType(AffinityType affinityType) {
		this.affinityType = affinityType;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public CloudPool getPool() {
		return pool;
	}

	public void setPool(CloudPool pool) {
		this.pool = pool;
	}

	public enum AffinityType {
		KEEP_SEPARATE,
		KEEP_TOGETHER
	}
}
