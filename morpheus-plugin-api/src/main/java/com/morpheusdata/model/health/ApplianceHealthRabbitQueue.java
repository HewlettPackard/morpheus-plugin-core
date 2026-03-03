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

public class ApplianceHealthRabbitQueue {

	protected String name;
	protected Long count;
	protected String status;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Long getCount() { return count; }
	public void setCount(Long count) { this.count = count; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
}
