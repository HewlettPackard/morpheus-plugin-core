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

public class ApplianceHealthStorageStats {

	protected List<ApplianceHealthStorageFile> files = new ArrayList<>();
	protected Long used;
	protected Long available;
	protected Long total;
	protected Double percent;
	protected String status;
	protected String statusMessage;

	public List<ApplianceHealthStorageFile> getFiles() { return files; }
	public void setFiles(List<ApplianceHealthStorageFile> files) { this.files = files; }

	public Long getUsed() { return used; }
	public void setUsed(Long used) { this.used = used; }

	public Long getAvailable() { return available; }
	public void setAvailable(Long available) { this.available = available; }

	public Long getTotal() { return total; }
	public void setTotal(Long total) { this.total = total; }

	public Double getPercent() { return percent; }
	public void setPercent(Double percent) { this.percent = percent; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
}
