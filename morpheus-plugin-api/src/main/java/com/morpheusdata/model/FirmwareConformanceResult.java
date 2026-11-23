/*
 *  Copyright 2026 HPE Development Company, L.P.
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

import java.util.ArrayList;
import java.util.List;

/** Component-by-component comparison of a host's firmware with a baseline. */
public class FirmwareConformanceResult {
	protected ConformanceStatus overallStatus;
	protected List<FirmwareComponent> components = new ArrayList<>();
	protected int conformantCount;
	protected int driftCount;

	public ConformanceStatus getOverallStatus() { return overallStatus; }
	public void setOverallStatus(ConformanceStatus overallStatus) { this.overallStatus = overallStatus; }
	public List<FirmwareComponent> getComponents() { return components; }
	public void setComponents(List<FirmwareComponent> components) { this.components = components; }
	public int getConformantCount() { return conformantCount; }
	public void setConformantCount(int conformantCount) { this.conformantCount = conformantCount; }
	public int getDriftCount() { return driftCount; }
	public void setDriftCount(int driftCount) { this.driftCount = driftCount; }
}
