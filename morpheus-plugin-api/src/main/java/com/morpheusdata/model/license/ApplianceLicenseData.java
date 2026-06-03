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

package com.morpheusdata.model.license;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Represents a single appliance license configuration, either the currently merged/active license
 * or one of the individually installed licenses.
 */
public class ApplianceLicenseData {
	public Long id;
	public String keyId;
	public String hash;
	public Integer licenseVersion;
	public String productTier;
	public Date startDate;
	public Date endDate;

	// workload limitType limits
	public Long maxInstances;
	public Long maxMemory;
	public Long maxStorage;

	// limit type
	public String limitType;

	// standard limitType limits
	public Long maxManagedServers;
	public Long maxDiscoveredServers;
	public Long maxHosts;
	/** @deprecated use maxMvmSockets */
	@Deprecated
	public Long maxMvm;
	public Long maxMvmSockets;
	public Long maxSockets;
	public Long maxIac;
	public Long maxXaas;
	public Long maxExecutions;
	public Long maxDistributedWorkers;
	public Long maxDiscoveredObjects;

	public Boolean hardLimit;
	public Boolean multiTenant;
	public Boolean whiteLabel;
	public Boolean reportStatus;
	public String supportLevel;
	public String accountName;

	/** Arbitrary key/value config embedded in the license */
	public Map<String, Object> config;
	public List<String> amazonProductCodes;

	/** Map of feature name → enabled flag */
	public Map<String, Boolean> features;
	public List<String> zoneTypes;
	public List<String> zoneTypesExcluded;
	public List<String> taskTypes;
	public List<String> taskTypesExcluded;

	public Date lastUpdated;
	public Date dateCreated;
	/** Date when license limits should be recalculated (e.g. after a stacked license expires) */
	public Date recalculationDate;
}
