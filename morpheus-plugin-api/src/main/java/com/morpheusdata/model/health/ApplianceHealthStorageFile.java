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

public class ApplianceHealthStorageFile {

	protected String name;
	protected Long used;
	protected Long available;
	protected Long total;
	protected String path;
	protected Double percent;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Long getUsed() { return used; }
	public void setUsed(Long used) { this.used = used; }

	public Long getAvailable() { return available; }
	public void setAvailable(Long available) { this.available = available; }

	public Long getTotal() { return total; }
	public void setTotal(Long total) { this.total = total; }

	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }

	public Double getPercent() { return percent; }
	public void setPercent(Double percent) { this.percent = percent; }

	@Override
	public String toString() {
		return "ApplianceHealthStorageFile{" +
			"name='" + name + '\'' +
			", path='" + path + '\'' +
			", used=" + used +
			", available=" + available +
			", total=" + total +
			", percent=" + percent +
			'}';
	}
}
