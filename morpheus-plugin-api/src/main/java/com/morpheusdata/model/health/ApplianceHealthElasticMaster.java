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

public class ApplianceHealthElasticMaster {

	protected String id;
	protected String host;
	protected String ip;
	protected String node;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getHost() { return host; }
	public void setHost(String host) { this.host = host; }

	public String getIp() { return ip; }
	public void setIp(String ip) { this.ip = ip; }

	public String getNode() { return node; }
	public void setNode(String node) { this.node = node; }

	@Override
	public String toString() {
		return "ApplianceHealthElasticMaster{" +
			"id='" + id + '\'' +
			", host='" + host + '\'' +
			", ip='" + ip + '\'' +
			", node='" + node + '\'' +
			'}';
	}
}
