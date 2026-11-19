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

/** Firmware inventory for a single host — all components and their versions. */
public class FirmwareInventory {
	protected String serverModel;               // "ProLiant DL380 Gen11"
	protected String bmcType;                   // "iLO 6", "iDRAC 9", etc.
	protected String bmcVersion;                // "3.10"
	protected String biosVersion;               // "U46 v2.90"
	protected List<FirmwareComponent> components = new ArrayList<>(); // All firmware components

	public String getServerModel() { return serverModel; }
	public void setServerModel(String serverModel) { this.serverModel = serverModel; }
	public String getBmcType() { return bmcType; }
	public void setBmcType(String bmcType) { this.bmcType = bmcType; }
	public String getBmcVersion() { return bmcVersion; }
	public void setBmcVersion(String bmcVersion) { this.bmcVersion = bmcVersion; }
	public String getBiosVersion() { return biosVersion; }
	public void setBiosVersion(String biosVersion) { this.biosVersion = biosVersion; }
	public List<FirmwareComponent> getComponents() { return components; }
	public void setComponents(List<FirmwareComponent> components) { this.components = components; }
}
