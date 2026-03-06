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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplianceHealthThreadStats {

	protected List<Map<String,Object>> threadList = new ArrayList<>();
	protected List<Map<String,Object>> busyThreads = new ArrayList<>();
	protected List<Map<String,Object>> blockedThreads = new ArrayList<>();
	protected List<Map<String,Object>> runningThreads = new ArrayList<>();
	protected Long totalCpuTime;
	protected Integer totalThreads;
	protected Integer runningWebThreads;
	protected String status;
	protected String statusMessage;

	public List<Map<String,Object>> getThreadList() { return threadList; }
	public void setThreadList(List<Map<String,Object>> threadList) { this.threadList = threadList; }

	public List<Map<String,Object>> getBusyThreads() { return busyThreads; }
	public void setBusyThreads(List<Map<String,Object>> busyThreads) { this.busyThreads = busyThreads; }

	public List<Map<String,Object>> getBlockedThreads() { return blockedThreads; }
	public void setBlockedThreads(List<Map<String,Object>> blockedThreads) { this.blockedThreads = blockedThreads; }

	public List<Map<String,Object>> getRunningThreads() { return runningThreads; }
	public void setRunningThreads(List<Map<String,Object>> runningThreads) { this.runningThreads = runningThreads; }

	public Long getTotalCpuTime() { return totalCpuTime; }
	public void setTotalCpuTime(Long totalCpuTime) { this.totalCpuTime = totalCpuTime; }

	public Integer getTotalThreads() { return totalThreads; }
	public void setTotalThreads(Integer totalThreads) { this.totalThreads = totalThreads; }

	public Integer getRunningWebThreads() { return runningWebThreads; }
	public void setRunningWebThreads(Integer runningWebThreads) { this.runningWebThreads = runningWebThreads; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

	@Override
	public String toString() {
		return "ApplianceHealthThreadStats{" +
			"totalThreads=" + totalThreads +
			", runningWebThreads=" + runningWebThreads +
			", totalCpuTime=" + totalCpuTime +
			", busyThreads=" + busyThreads.size() +
			", blockedThreads=" + blockedThreads.size() +
			", status='" + status + '\'' +
			", statusMessage='" + statusMessage + '\'' +
			'}';
	}
}
