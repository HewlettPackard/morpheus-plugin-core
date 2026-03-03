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

package com.morpheusdata.model.health;

public class ApplianceHealthElasticNode {

	protected String ip;
	protected String heapPercent;
	protected String ramPercent;
	protected String cpuCount;
	protected String loadOne;
	protected String loadFive;
	protected String loadFifteen;
	protected String role;
	protected String master;
	protected String name;

	public String getIp() { return ip; }
	public void setIp(String ip) { this.ip = ip; }

	public String getHeapPercent() { return heapPercent; }
	public void setHeapPercent(String heapPercent) { this.heapPercent = heapPercent; }

	public String getRamPercent() { return ramPercent; }
	public void setRamPercent(String ramPercent) { this.ramPercent = ramPercent; }

	public String getCpuCount() { return cpuCount; }
	public void setCpuCount(String cpuCount) { this.cpuCount = cpuCount; }

	public String getLoadOne() { return loadOne; }
	public void setLoadOne(String loadOne) { this.loadOne = loadOne; }

	public String getLoadFive() { return loadFive; }
	public void setLoadFive(String loadFive) { this.loadFive = loadFive; }

	public String getLoadFifteen() { return loadFifteen; }
	public void setLoadFifteen(String loadFifteen) { this.loadFifteen = loadFifteen; }

	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }

	public String getMaster() { return master; }
	public void setMaster(String master) { this.master = master; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
