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

public class ApplianceHealthSlowQuery {

	protected Long count;
	protected Long time;
	protected String query;

	public Long getCount() { return count; }
	public void setCount(Long count) { this.count = count; }

	public Long getTime() { return time; }
	public void setTime(Long time) { this.time = time; }

	public String getQuery() { return query; }
	public void setQuery(String query) { this.query = query; }

	@Override
	public String toString() {
		return "ApplianceHealthSlowQuery{" +
			"count=" + count +
			", time=" + time +
			", query='" + query + '\'' +
			'}';
	}
}
