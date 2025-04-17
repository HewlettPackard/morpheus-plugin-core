/*
 *  Copyright 2025 Morpheus Data, LLC.
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
import com.morpheusdata.model.Datastore;

import java.io.Serializable;

public class DatastoreEvent implements Event<DatastoreEvent.DatastoreEventType>, Serializable {
	protected String message;
	protected DatastoreEventType type;
	protected Datastore datastore;
	protected ComputeServer server;
	protected ComputeServer sourceServer;
	protected Datastore sourceDatastore;
	protected ComputeServer sourceHost;

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public DatastoreEventType getType() {
		return type;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setType(DatastoreEventType type) {
		this.type = type;
	}

	public ComputeServer getServer() {
		return server;
	}

	public void setServer(ComputeServer server) {
		this.server = server;
	}

	public Datastore getDatastore() {
		return datastore;
	}

	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}

	public ComputeServer getSourceServer() {
		return sourceServer;
	}

	public void setSourceServer(ComputeServer sourceServer) {
		this.sourceServer = sourceServer;
	}

	public Datastore getSourceDatastore() {
		return sourceDatastore;
	}

	public void setSourceDatastore(Datastore sourceDatastore) {
		this.sourceDatastore = sourceDatastore;
	}

	public ComputeServer getSourceHost() {
		return sourceHost;
	}

	public void setSourceHost(ComputeServer sourceHost) {
		this.sourceHost = sourceHost;
	}

	public enum DatastoreEventType implements EventType {
		SERVER_MOVE,
		SERVER_SHUTDOWN,
		SERVER_STARTUP
	}
}
