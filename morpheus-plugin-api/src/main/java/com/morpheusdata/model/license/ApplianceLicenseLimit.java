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

/**
 * Represents the limit check result for a single license metric (e.g. maxManagedServers, maxSockets).
 */
public class ApplianceLicenseLimit {
	/** Metric code, e.g. "maxManagedServers", "maxSockets", "maxMemory" */
	public String code;
	/** Maximum allowed value. Null means unlimited. */
	public Long max;
	public Long used;
	public Double percentUsed;
	/** True when usage is ≥ 85% of max */
	public Boolean warning;
	/** True when usage is ≥ 100% of max */
	public Boolean limitReached;
	/** Optional unit, e.g. "bytes" for memory/storage limits */
	public String unit;
}
