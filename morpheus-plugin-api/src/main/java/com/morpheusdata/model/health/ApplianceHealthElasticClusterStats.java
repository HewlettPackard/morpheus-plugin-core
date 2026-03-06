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

public class ApplianceHealthElasticClusterStats {

	protected String status;
	protected String clusterName;
	protected String nodeTotal;
	protected String nodeData;
	protected String shards;
	protected String primary;
	protected String relocating;
	protected String initializing;
	protected String unassigned;
	protected String pendingTasks;
	protected String activePercent;

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getClusterName() { return clusterName; }
	public void setClusterName(String clusterName) { this.clusterName = clusterName; }

	public String getNodeTotal() { return nodeTotal; }
	public void setNodeTotal(String nodeTotal) { this.nodeTotal = nodeTotal; }

	public String getNodeData() { return nodeData; }
	public void setNodeData(String nodeData) { this.nodeData = nodeData; }

	public String getShards() { return shards; }
	public void setShards(String shards) { this.shards = shards; }

	public String getPrimary() { return primary; }
	public void setPrimary(String primary) { this.primary = primary; }

	public String getRelocating() { return relocating; }
	public void setRelocating(String relocating) { this.relocating = relocating; }

	public String getInitializing() { return initializing; }
	public void setInitializing(String initializing) { this.initializing = initializing; }

	public String getUnassigned() { return unassigned; }
	public void setUnassigned(String unassigned) { this.unassigned = unassigned; }

	public String getPendingTasks() { return pendingTasks; }
	public void setPendingTasks(String pendingTasks) { this.pendingTasks = pendingTasks; }

	public String getActivePercent() { return activePercent; }
	public void setActivePercent(String activePercent) { this.activePercent = activePercent; }

	@Override
	public String toString() {
		return "ApplianceHealthElasticClusterStats{" +
			"status='" + status + '\'' +
			", clusterName='" + clusterName + '\'' +
			", nodeTotal='" + nodeTotal + '\'' +
			", nodeData='" + nodeData + '\'' +
			", shards='" + shards + '\'' +
			", primary='" + primary + '\'' +
			", relocating='" + relocating + '\'' +
			", initializing='" + initializing + '\'' +
			", unassigned='" + unassigned + '\'' +
			", pendingTasks='" + pendingTasks + '\'' +
			", activePercent='" + activePercent + '\'' +
			'}';
	}
}
