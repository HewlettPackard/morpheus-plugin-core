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

package com.morpheusdata.model.projection;

import com.morpheusdata.core.network.MorpheusNetworkRouterLocationService;

/**
 * Provides a subset of properties from the {@link com.morpheusdata.model.NetworkRouterLocation} object for doing a sync match
 * comparison with less bandwidth usage and memory footprint. This is a DTO Projection object
 * @see MorpheusNetworkRouterLocationService
 * @author Roger Kumpf
 */
public class NetworkRouterLocationIdentityProjection extends MorpheusIdentityModel {
	protected String externalId;

	public NetworkRouterLocationIdentityProjection() {}

	public NetworkRouterLocationIdentityProjection(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * Gets the external id (cloud provider id) of the NetworkRouterLocation
	 * @return the externalId of the NetworkRouterLocation
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * Sets the external id (cloud provider id) of the NetworkRouterLocation
	 * @param externalId the external id of the NetworkRouterLocation
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
}
