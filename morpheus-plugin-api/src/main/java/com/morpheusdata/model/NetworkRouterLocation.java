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
import com.morpheusdata.model.projection.NetworkRouterLocationIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

/**
 * Represents a Network Router Location, which defines geographic or logical locations associated with network routers
 * and cloud resources. Network Router Locations can be used to organize routers by region, datacenter, or other
 * logical groupings.
 *
 * @author Roger Kumpf
 */
public class NetworkRouterLocation extends NetworkRouterLocationIdentityProjection {
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account account;
	protected String refType;
	protected Long refId;
	protected String refUUID;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected NetworkRouter router;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
		markDirty("account", account);
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

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
		markDirty("refUUID", refUUID);
	}

	public NetworkRouter getRouter() {
		return router;
	}

	public void setRouter(NetworkRouter router) {
		this.router = router;
		markDirty("router", router);
	}
}
