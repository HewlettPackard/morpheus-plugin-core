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

public class ApplianceHealthMemoryStats {

	protected Long maxMemory;
	protected Long totalMemory;
	protected Long freeMemory;
	protected Long usedMemory;
	protected Long systemMemory;
	protected Long committedMemory;
	protected Long systemFreeMemory;
	protected Long systemSwap;
	protected Long systemFreeSwap;
	protected Double memoryPercent;
	protected Double systemMemoryPercent;
	protected Double swapPercent;
	protected String status;
	protected String statusMessage;

	public Long getMaxMemory() { return maxMemory; }
	public void setMaxMemory(Long maxMemory) { this.maxMemory = maxMemory; }

	public Long getTotalMemory() { return totalMemory; }
	public void setTotalMemory(Long totalMemory) { this.totalMemory = totalMemory; }

	public Long getFreeMemory() { return freeMemory; }
	public void setFreeMemory(Long freeMemory) { this.freeMemory = freeMemory; }

	public Long getUsedMemory() { return usedMemory; }
	public void setUsedMemory(Long usedMemory) { this.usedMemory = usedMemory; }

	public Long getSystemMemory() { return systemMemory; }
	public void setSystemMemory(Long systemMemory) { this.systemMemory = systemMemory; }

	public Long getCommittedMemory() { return committedMemory; }
	public void setCommittedMemory(Long committedMemory) { this.committedMemory = committedMemory; }

	public Long getSystemFreeMemory() { return systemFreeMemory; }
	public void setSystemFreeMemory(Long systemFreeMemory) { this.systemFreeMemory = systemFreeMemory; }

	public Long getSystemSwap() { return systemSwap; }
	public void setSystemSwap(Long systemSwap) { this.systemSwap = systemSwap; }

	public Long getSystemFreeSwap() { return systemFreeSwap; }
	public void setSystemFreeSwap(Long systemFreeSwap) { this.systemFreeSwap = systemFreeSwap; }

	public Double getMemoryPercent() { return memoryPercent; }
	public void setMemoryPercent(Double memoryPercent) { this.memoryPercent = memoryPercent; }

	public Double getSystemMemoryPercent() { return systemMemoryPercent; }
	public void setSystemMemoryPercent(Double systemMemoryPercent) { this.systemMemoryPercent = systemMemoryPercent; }

	public Double getSwapPercent() { return swapPercent; }
	public void setSwapPercent(Double swapPercent) { this.swapPercent = swapPercent; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
}
