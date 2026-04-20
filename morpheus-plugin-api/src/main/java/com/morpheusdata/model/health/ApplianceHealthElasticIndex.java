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

public class ApplianceHealthElasticIndex {

	protected String health;
	protected String status;
	protected String index;
	protected String uuid;
	protected String primary;
	protected String replicas;
	protected String count;
	protected String deleted;
	protected String primarySize;
	protected String totalSize;

	public String getHealth() { return health; }
	public void setHealth(String health) { this.health = health; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getIndex() { return index; }
	public void setIndex(String index) { this.index = index; }

	public String getUuid() { return uuid; }
	public void setUuid(String uuid) { this.uuid = uuid; }

	public String getPrimary() { return primary; }
	public void setPrimary(String primary) { this.primary = primary; }

	public String getReplicas() { return replicas; }
	public void setReplicas(String replicas) { this.replicas = replicas; }

	public String getCount() { return count; }
	public void setCount(String count) { this.count = count; }

	public String getDeleted() { return deleted; }
	public void setDeleted(String deleted) { this.deleted = deleted; }

	public String getPrimarySize() { return primarySize; }
	public void setPrimarySize(String primarySize) { this.primarySize = primarySize; }

	public String getTotalSize() { return totalSize; }
	public void setTotalSize(String totalSize) { this.totalSize = totalSize; }

	@Override
	public String toString() {
		return "ApplianceHealthElasticIndex{" +
			"index='" + index + '\'' +
			", health='" + health + '\'' +
			", status='" + status + '\'' +
			", uuid='" + uuid + '\'' +
			", primary='" + primary + '\'' +
			", replicas='" + replicas + '\'' +
			", count='" + count + '\'' +
			", primarySize='" + primarySize + '\'' +
			", totalSize='" + totalSize + '\'' +
			'}';
	}
}
