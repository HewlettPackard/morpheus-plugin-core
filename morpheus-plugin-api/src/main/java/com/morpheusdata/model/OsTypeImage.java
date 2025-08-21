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

/**
 * Describes an Operating System Image at a high level
 * @see com.morpheusdata.model.ComputeServer
 * @see com.morpheusdata.model.VirtualImage
 * @see com.morpheusdata.model.OsType
 *
 * @author Luke Davitt
 * @since 1.2.0
 */
public class OsTypeImage extends MorpheusModel {

	protected String code;
	protected OsType osType;
	protected Account account;
	protected ProvisionType provisionType;
	protected CloudType cloudType;
	@Deprecated(since = "1.2.12") // use cloudType instead
	protected CloudType computeZoneType;
	protected Cloud cloud;
	@Deprecated(since = "1.2.12") // use cloud instead
	protected Cloud zone;
	protected ComputeSite site;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}


	public OsType getOsType() {
		return osType;
	}

	public void setOsType(OsType osType) {
		this.osType = osType;
		markDirty("osType", osType);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
		markDirty("account", account);
	}

	public ProvisionType provisionType() {
		return provisionType;
	}

	public void setProvisionType(ProvisionType provisionType) {
		this.provisionType = provisionType;
		markDirty("provisionType", provisionType);
	}

	public CloudType getCloudType() {
		return cloudType;
	}

	public void setCloudType(CloudType cloudType) {
		this.cloudType = cloudType;
		markDirty("cloudType", cloudType);
	}

	@Deprecated(since = "1.2.12") // use cloudType instead
	public CloudType getComputeZoneType() {
		return cloudType;
	}

	@Deprecated(since = "1.2.12") // use cloudType instead
	public void setComputeZoneType(CloudType cloudType) {
		this.cloudType = cloudType;
		markDirty("cloudType", cloudType);
	}

	public Cloud getCloud() {
		return cloud;
	}

	public void setCloud(Cloud cloud) {
		this.cloud = cloud;
		markDirty("cloud", cloud);
	}

	@Deprecated(since = "1.2.12") // use cloud instead
	public Cloud getZone() {
		return cloud;
	}

	@Deprecated(since = "1.2.12") // use cloud instead
	public void setZone(Cloud cloud) {
		this.cloud = cloud;
		markDirty("cloud", cloud);
	}


	public ComputeSite getSite() {
		return site;
	}

	public void setSite(ComputeSite site) {
		this.site = site;
		markDirty("site", site);
	}
}
