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

public class ApplianceHealthElasticStats {

	protected String status;
	protected String statusMessage;
	protected ApplianceHealthElasticMaster master;
	protected List<ApplianceHealthElasticNode> nodes = new ArrayList<>();
	protected List<ApplianceHealthElasticIndex> indices = new ArrayList<>();
	protected List<ApplianceHealthElasticIndex> badIndices = new ArrayList<>();
	protected ApplianceHealthElasticClusterStats stats;

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

	public ApplianceHealthElasticMaster getMaster() { return master; }
	public void setMaster(ApplianceHealthElasticMaster master) { this.master = master; }

	public List<ApplianceHealthElasticNode> getNodes() { return nodes; }
	public void setNodes(List<ApplianceHealthElasticNode> nodes) { this.nodes = nodes; }

	public List<ApplianceHealthElasticIndex> getIndices() { return indices; }
	public void setIndices(List<ApplianceHealthElasticIndex> indices) { this.indices = indices; }

	public List<ApplianceHealthElasticIndex> getBadIndices() { return badIndices; }
	public void setBadIndices(List<ApplianceHealthElasticIndex> badIndices) { this.badIndices = badIndices; }

	public ApplianceHealthElasticClusterStats getStats() { return stats; }
	public void setStats(ApplianceHealthElasticClusterStats stats) { this.stats = stats; }
}
