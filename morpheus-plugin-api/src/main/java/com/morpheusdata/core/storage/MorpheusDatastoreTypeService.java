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

package com.morpheusdata.core.storage;

import com.bertramlabs.plugins.karman.CloudFileInterface;
import com.morpheusdata.model.*;
import com.morpheusdata.response.ServiceResponse;
import com.morpheusdata.request.CreateSnapshotRequest;

/**
 * A service for interacting with associated Datastores
 * <p>
 * This service routes the action (e.g., createVolume()) to the datastore associated with the volume(s)
 * passed in for the action. Note: It is up to the caller of this service to save the related changes.
 */
public interface MorpheusDatastoreTypeService {
	/**
	 * Creates a volume on the associated datastore
	 * @param volume The volume to create
	 * @param server The server to create the volume on
	 * @return Created volume
	 */
	ServiceResponse<StorageVolume> createVolume(StorageVolume volume, ComputeServer server);

	/**
	 * Removes a volume on the associated datastore
	 * @param volume The volume to create
	 * @param server The server to remove the volume from
	 * @param removeSnapshots whether to remove snapshots associated with the volume. In some implementations this is mandatory and not separate.
	 * @param force whether to force the removal of the volume. This is typically used to force the removal of a volume that is in use.
	 */
	ServiceResponse removeVolume(StorageVolume volume, ComputeServer server, boolean removeSnapshots, boolean force);

	/**
	 * Clones a volume on the associated datastore
	 * @param volume The cloned volume
	 * @param server The server the volume is associated with
	 * @param sourceVolume The source volume to clone from
	 * @return Cloned volume
	 */
	ServiceResponse<StorageVolume> cloneVolume(StorageVolume volume, ComputeServer server, StorageVolume sourceVolume);

	/**
	 * Resize a volume on the associated datastore
	 * @param volume The volume to resize
	 * @param server The server to clone the volume on
	 * @return Cloned volume
	 */
	ServiceResponse<StorageVolume> resizeVolume(StorageVolume volume, ComputeServer server, boolean isShutdown);

	/**
	 * Clones a volume on the associated datastore
	 * @param volume The cloned volume
	 * @param server The server the volume is associated with
	 * @param server The server to clone the volume on
	 * @param virtualImage the virtual image this volume is being cloned out of
	 * @param cloudFile the specific disk file (Karman abstraction) that is being cloned
	 * @return Cloned volume
	 */
	ServiceResponse<StorageVolume> cloneVolume(StorageVolume volume, ComputeServer server, VirtualImage virtualImage, CloudFileInterface cloudFile);

	/**
	 * Refresh the provider with the associated data in the external system.
	 * @param datastore The Datastore object contains all the saved information regarding configuration of the Datastore.
	 * @return a {@link ServiceResponse} object. A ServiceResponse with a success value of 'false' will indicate the
	 * refresh process has failed and will change the datastore status to 'error'
	 */
	ServiceResponse<Datastore> refreshDatastore(Datastore datastore);

	/**
	 * Creates volume snapshots of all volumes associated with a server.
	 * @param server the server to create snapshots for
	 * @param forBackup whether this snapshot is for backup purposes
	 * @param forExport whether this snapshot is for export purposes (like an image import)
	 * @return the success state and a copy of the snapshot
	 */
	ServiceResponse<Snapshot> createSnapshot(ComputeServer server, Boolean forBackup, Boolean forExport);

	/**
	 * Reverts a server to a snapshot. This is used to revert a server to a previous state. The caller should
	 * ensure the server is powered off during this operation and powered back on to desired user state after this
	 * operation is complete. NOTE: These snapshots are typically volume based and not vm state based.
	 * @param server the server to revert the snapshot on
	 * @param snapshot the snapshot to revert to
	 * @return the success state and a copy of the snapshot
	 */
	ServiceResponse<Snapshot> revertSnapshot(ComputeServer server, Snapshot snapshot);

	/**
	 * Deletes volume snapshots of all volumes associated with a server.
	 * @param server the server to delete snapshots for
	 * @param snapshot the snapshot to delete
	 * @return the success state
	 */
	ServiceResponse removeSnapshot(ComputeServer server, Snapshot snapshot);

	/**
	 * Creates volume snapshots of all volumes associated with a server.
	 * NOTE: A snapshot for a multi workload instance is structured as follows:
	 * - Instance snapshot contains the snapshot files of each shared volume among the servers
	 * - Instance snapshot is the parent snapshot for each of the server snapshots
	 * - Server snapshot contains snapshot files for each of the server volumes.
	 * @param instance the instance to create snapshots for
	 * @param req the snapshot creation request containing
	 * - forBackup whether this snapshot is for backup purposes
	 * - forExport whether this snapshot is for export purposes (like an image import)
	 * @return the success state and a copy of the snapshot
	 */
	ServiceResponse<Snapshot> createSnapshot(Instance instance, CreateSnapshotRequest req);

	/**
	 * Reverts an instance to a snapshot. This is used to revert an instance to a previous state. The caller should
	 * ensure the instance is powered off during this operation and powered back on to desired user state after this
	 * operation is complete.
	 * @param instance the instance to revert the snapshot on
	 * @param snapshot the snapshot to revert to
	 * @return the success state and a copy of the snapshot
	 */
	ServiceResponse<Snapshot> revertSnapshot(Instance instance, Snapshot snapshot);

	/**
	 * Deletes snapshot associated with an instance.
	 * @param instance the instance to delete snapshots for
	 * @param snapshot the snapshot to delete
	 * @return the success state
	 */
	ServiceResponse removeSnapshot(Instance instance, Snapshot snapshot);
}
