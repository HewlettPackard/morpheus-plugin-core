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

public class ApplianceHealthCpuStats {

	protected Double cpuLoad;
	protected Double cpuTotalLoad;
	protected Integer processorCount;
	protected Double processTime;
	protected Double systemLoad;
	protected String status;
	protected String statusMessage;

	public Double getCpuLoad() { return cpuLoad; }
	public void setCpuLoad(Double cpuLoad) { this.cpuLoad = cpuLoad; }

	public Double getCpuTotalLoad() { return cpuTotalLoad; }
	public void setCpuTotalLoad(Double cpuTotalLoad) { this.cpuTotalLoad = cpuTotalLoad; }

	public Integer getProcessorCount() { return processorCount; }
	public void setProcessorCount(Integer processorCount) { this.processorCount = processorCount; }

	public Double getProcessTime() { return processTime; }
	public void setProcessTime(Double processTime) { this.processTime = processTime; }

	public Double getSystemLoad() { return systemLoad; }
	public void setSystemLoad(Double systemLoad) { this.systemLoad = systemLoad; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
}
