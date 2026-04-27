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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplianceHealthDatabaseStats {

	protected List<ApplianceHealthDatabaseConnection> connectionList = new ArrayList<>();
	protected List<ApplianceHealthDatabaseConnection> busyConnections = new ArrayList<>();
	protected Long maxConnections;
	protected Long maxUsedConnections;
	protected Integer usedConnections;
	protected Long abortedConnections;
	protected List<ApplianceHealthSlowQuery> slowQueries = new ArrayList<>();
	protected Map<String,Object> stats = new HashMap<>();
	protected Map<String,Object> innodbStats = new HashMap<>();
	protected Double scanPercent;
	protected String status;
	protected String statusMessage;

	public List<ApplianceHealthDatabaseConnection> getConnectionList() { return connectionList; }
	public void setConnectionList(List<ApplianceHealthDatabaseConnection> connectionList) { this.connectionList = connectionList; }

	public List<ApplianceHealthDatabaseConnection> getBusyConnections() { return busyConnections; }
	public void setBusyConnections(List<ApplianceHealthDatabaseConnection> busyConnections) { this.busyConnections = busyConnections; }

	public Long getMaxConnections() { return maxConnections; }
	public void setMaxConnections(Long maxConnections) { this.maxConnections = maxConnections; }

	public Long getMaxUsedConnections() { return maxUsedConnections; }
	public void setMaxUsedConnections(Long maxUsedConnections) { this.maxUsedConnections = maxUsedConnections; }

	public Integer getUsedConnections() { return usedConnections; }
	public void setUsedConnections(Integer usedConnections) { this.usedConnections = usedConnections; }

	public Long getAbortedConnections() { return abortedConnections; }
	public void setAbortedConnections(Long abortedConnections) { this.abortedConnections = abortedConnections; }

	public List<ApplianceHealthSlowQuery> getSlowQueries() { return slowQueries; }
	public void setSlowQueries(List<ApplianceHealthSlowQuery> slowQueries) { this.slowQueries = slowQueries; }

	public Map<String,Object> getStats() { return stats; }
	public void setStats(Map<String,Object> stats) { this.stats = stats; }

	public Map<String,Object> getInnodbStats() { return innodbStats; }
	public void setInnodbStats(Map<String,Object> innodbStats) { this.innodbStats = innodbStats; }

	public Double getScanPercent() { return scanPercent; }
	public void setScanPercent(Double scanPercent) { this.scanPercent = scanPercent; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

	@Override
	public String toString() {
		return "ApplianceHealthDatabaseStats{" +
			"maxConnections=" + maxConnections +
			", maxUsedConnections=" + maxUsedConnections +
			", usedConnections=" + usedConnections +
			", abortedConnections=" + abortedConnections +
			", scanPercent=" + scanPercent +
			", busyConnections=" + busyConnections.size() +
			", slowQueries=" + slowQueries.size() +
			", status='" + status + '\'' +
			", statusMessage='" + statusMessage + '\'' +
			'}';
	}
}
