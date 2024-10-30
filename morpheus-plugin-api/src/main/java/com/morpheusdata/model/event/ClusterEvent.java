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

package com.morpheusdata.model.event;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.ComputeServerGroup;

import java.io.Serializable;

public class ClusterEvent implements Event<ClusterEvent.ClusterEventType>, Serializable {
	protected String message;
	protected ClusterEvent.ClusterEventType type;
	protected ComputeServerGroup cluster;
	protected ComputeServer server;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public ClusterEventType getType() {
		return type;
	}

	public void setType(ClusterEventType type) {
		this.type = type;
	}

	public ComputeServerGroup getCluster() {
		return cluster;
	}

	public void setCluster(ComputeServerGroup cluster) {
		this.cluster = cluster;
	}

	public ComputeServer getServer() {
		return server;
	}

	public void setServer(ComputeServer server) {
		this.server = server;
	}

	public enum ClusterEventType implements EventType {
		CREATE,
		DELETE,
		UPDATE,
		ADD_WORKER,
		REMOVE_WORKER
	}
}
