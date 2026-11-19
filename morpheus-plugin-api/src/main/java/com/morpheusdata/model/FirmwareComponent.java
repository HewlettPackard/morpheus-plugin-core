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

/** Installed and target firmware version for one server component. */
public class FirmwareComponent {
	protected String name;        // "NIC - Mellanox ConnectX-6"
	protected String type;        // BIOS, BMC, NIC, STORAGE_CONTROLLER, GPU, etc.
	protected String installed;   // "14.32.1010"
	protected String baseline;    // "14.34.1020" (null if not in baseline)
	protected ConformanceStatus status; // CONFORMANT, NON_CONFORMANT, UNKNOWN

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getInstalled() { return installed; }
	public void setInstalled(String installed) { this.installed = installed; }
	public String getBaseline() { return baseline; }
	public void setBaseline(String baseline) { this.baseline = baseline; }
	public ConformanceStatus getStatus() { return status; }
	public void setStatus(ConformanceStatus status) { this.status = status; }
}
