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
import com.morpheusdata.model.StorageVolume;

import java.io.Serializable;

public class DatastoreEvent implements Event<DatastoreEvent.DatastoreEventType>, Serializable {
	protected String message;
	protected DatastoreEventType type;
	protected Datastore sourceDatastore;
	protected Datastore targetDatastore;
	protected ComputeServer server;
	protected StorageVolume volume;
	protected ComputeServer sourceHost;
	protected ComputeServer targetHost;

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

	public StorageVolume getVolume() {
		return volume;
	}

	public void setVolume(StorageVolume volume) {
		this.volume = volume;
	}

	// This is a wrapper method to get the datastore. Useful when there is no target datastore and sourceDatastore has less meaning.
	public Datastore getDatastore() {
		return sourceDatastore;
	}

	// This is a wrapper method to set the datastore. Useful when there is no target datastore and sourceDatastore has less meaning.
	public void setDatastore(Datastore datastore) {
		this.sourceDatastore = datastore;
	}

	public Datastore getSourceDatastore() {
		return sourceDatastore;
	}

	public void setSourceDatastore(Datastore datastore) {
		this.sourceDatastore = datastore;
	}

	public Datastore getTargetDatastore() {
		return targetDatastore;
	}

	public void setTargetDatastore(Datastore datastore) {
		this.targetDatastore = datastore;
	}

	// This a wrapper method to get the source host. Useful when there is no target host and source host has less meaning.
	public ComputeServer getHost() {
		return sourceHost;
	}

	// This a wrapper method to set the source host. Useful when there is no target host and source host has less meaning.
	public void setHost(ComputeServer sourceHost) {
		this.sourceHost = sourceHost;
	}

	public ComputeServer getSourceHost() {
		return sourceHost;
	}

	public void setSourceHost(ComputeServer sourceHost) {
		this.sourceHost = sourceHost;
	}

	public ComputeServer getTargetHost() {
		return targetHost;
	}

	public void setTargetHost(ComputeServer targetHost) {
		this.targetHost = targetHost;
	}

	public enum DatastoreEventType implements EventType {
		SERVER_MOVE,
		SERVER_SHUTDOWN,
		SERVER_STARTUP,
		VOLUME_ATTACH,
		VOLUME_DETACH,
		VOLUME_MOVE_INITIATED,
		VOLUME_MOVE_COMPLETED,
		VOLUME_MOVE_FAILED
	}
}
