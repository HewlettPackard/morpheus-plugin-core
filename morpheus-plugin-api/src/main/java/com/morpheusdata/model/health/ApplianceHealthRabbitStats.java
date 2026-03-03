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

public class ApplianceHealthRabbitStats {

	protected List<ApplianceHealthRabbitQueue> queues = new ArrayList<>();
	protected List<ApplianceHealthRabbitQueue> busyQueues = new ArrayList<>();
	protected List<ApplianceHealthRabbitQueue> errorQueues = new ArrayList<>();
	protected String status;
	protected String statusMessage;

	public List<ApplianceHealthRabbitQueue> getQueues() { return queues; }
	public void setQueues(List<ApplianceHealthRabbitQueue> queues) { this.queues = queues; }

	public List<ApplianceHealthRabbitQueue> getBusyQueues() { return busyQueues; }
	public void setBusyQueues(List<ApplianceHealthRabbitQueue> busyQueues) { this.busyQueues = busyQueues; }

	public List<ApplianceHealthRabbitQueue> getErrorQueues() { return errorQueues; }
	public void setErrorQueues(List<ApplianceHealthRabbitQueue> errorQueues) { this.errorQueues = errorQueues; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }
}
