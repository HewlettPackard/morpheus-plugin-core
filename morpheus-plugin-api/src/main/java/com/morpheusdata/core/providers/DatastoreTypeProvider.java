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

package com.morpheusdata.core.providers;

import com.bertramlabs.plugins.karman.CloudFileInterface;
import com.morpheusdata.core.MorpheusContext;
import com.morpheusdata.model.*;
import com.morpheusdata.model.event.DatastoreEvent;
import com.morpheusdata.model.event.EventType;
import com.morpheusdata.response.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a DatastoreType and how a {@link StorageServer} interacts with various provisioners
 * There are several Facets related to this particular storage provider that grant certain abilities and use cases.
 * The {@link SnapshotFacet} allows for the creation and deletion of snapshots
 * The {@link MvmProvisionFacet} allows for MVM specific provisioning tasks for MVM/HPE Hypervisor Clusters
 * In order to use this with an external integration, it is best to first register a {@link StorageProvider} plugin, then
 * ensure the code used for that provider is set on the {@link DatastoreTypeProvider#getStorageProviderCode()} method.
 * Often times, it will be necessary to orchestrate behaviors on the host/hypervisor itself when provisioning a volume. This can be done
 * with the context {@link MorpheusContext#executeCommandOnServer} method.
 * <p>
 *     <code>
 *         ComputeServer host = server.parentServer //this is the host
 *         ServiceResponse response = morpheusContext.executeCommandOnServer(host, "virsh pool-refresh mypool")
 *     </code>
 * </p>
 * @since 1.1.18
 * @author David Estes, Dan Devilbiss
 */
public interface DatastoreTypeProvider extends PluginProvider {


	/**
	 * Returns the {@link ProvisionProvider} code for linking the generated {@link DatastoreType} with the appropriate {@link ProvisionType}
	 * @return the code of the relevant ProvisionProvider
	 */
	String getProvisionTypeCode();


	/**
	 * Returns the provider code for interacting with the {@link StorageServer} interface
	 * This is optional and can be null if there is no interaction with a storage server whatsoever
	 * @return the code for the storage provider (also matches the {@link StorageServerType} code)
	 */
	String getStorageProviderCode();

	/**
	 * Provide custom configuration options when creating a new {@link AccountIntegration}
	 * @return a List of OptionType
	 */
	List<OptionType> getOptionTypes();

	/**
	 * Provide custom list of {@link StorageVolumeType} for this datastore.
	 */
	default List<StorageVolumeType> getVolumeTypes() {
		return new ArrayList<>();
	}

	/**
	 * Returns the external type of the provider.
	 * @return the external type
	 */
	default String getExternalType() { return null; }

	/**
	 * Returns the external subtype of the provider that serves to differentiate it from other providers sharing the
	 * same external type.
	 * @return the external subtype
	 */
	default String getExternalSubType() { return null; }

	/**
	 * Flags if this datastore is heartbeat target capable in support of automatic failover of running workloads in
	 * the event of the loss of a host. If true, the option to designate as a heartbeat target will be available.
	 * @return whether, or not this datastore is heartbeat target capable
	 */
	default boolean getHeartbeatTargetCapable() { return false; }

	/**
	 * Flags if this datastore is image target capable. If true, the option to designate as an image target will be available.
	 * @return whether, or not this datastore is image target capable
	 */
	default boolean getImageTargetCapable() { return false; }

	/**
	 * Flags if this datastore can be created by the user. Some datastores are system injected and cannot be created by the user
	 * @return whether, or not this datastore can be created by the user
	 */
	boolean getCreatable();

	/**
	 * Flags if the datastore created for this is editable or not
	 * @return whether, or not this datastore can be edited once added
	 */
	boolean getEditable();

	/**
	 * Flags if the datastore created for this is removable or not
	 * @return whether, or not this datastore can be removed once added
	 */
	boolean getRemovable();

	/**
	 * Flags if the {@link DatastoreType} is a local storage type, or a shared storage integration for cross host clustering.
	 * @return if the {@link DatastoreType} is local
	 */
	default boolean getLocalStorage() {
		return false;
	}

	/**
	 * Flags if this {@link DatastoreType} implements its own refresh logic for refreshing volumes within the datastore type, or if this falls back to generic sync.
	 * @return if the {@link DatastoreType} implements its own refresh logic
	 */
	default boolean getPluginManagedVolumeRefresh() { return false; }

	/**
	 * Perform any operations necessary on the target to remove a volume. This is used to remove a volume on a storage server
	 * It is typically called as part of server teardown.
	 * @param volume the current volume to remove
	 * @param server the server the volume is being removed from (may contain information such as parentServer (hypervisor) or cluster)
	 * @param removeSnapshots whether to remove snapshots associated with the volume. In some implementations this is mandatory and not separate.
	 * @param force whether to force the removal of the volume. This is typically used to force the removal of a volume that is in use.
	 * @return the success state of the removal
	 */
	ServiceResponse removeVolume(StorageVolume volume, ComputeServer server, boolean removeSnapshots, boolean force);

	/**
	 * Perform any operations necessary on the target to create a volume. This is used to create a volume on a storage server
	 * It is typically called as part of server provisioning.
	 * @param volume the current volume to create
	 * @param server the server the volume is being created on (may contain information such as parentServer (hypervisor) or cluster)
	 * @return the success state and a copy of the volume
	 */
	ServiceResponse<StorageVolume> createVolume(StorageVolume volume, ComputeServer server);

	/**
	 * Clones a volume based on a source volume object. This is one of the most important methods for provisioning as most
	 * {@link VirtualImage} provisioning objects are cloned from a local image cache of source volumes. This is where the QCOW2 may reside
	 * Often times you can infer this from the combination of the sourceVolume object as well as its datastore
	 * <p>
	 *     <code>
	 *         String sourceVolumePath = sourceVolume.datastore.externalPath + '/' + sourceVolume.externalId
	 *         //this is the QCOW2 path typically used for clone operations
	 *         String command = "sudo mkdir -p \"${volume.datastore.externalPath}/${server.externalId ?: server.name}\" ; sudo ionice -c 3 cp -f \"${sourceVolume.datastore.externalPath}/${sourceVolume.externalId}\" \"${volume.datastore.externalPath}/${server.externalId ?: server.name}/${volume.volumeName}\""
	 *		   morpheusContext.executeCommandOnServer(server, command)
	 *     </code>
	 * </p>
	 * @param volume the volume we are creating and cloning into
	 * @param server the server the volume is associated with (typically the workload/vm)
	 * @param sourceVolume the source volume we are cloning from
	 * @return the success state and a copy of the volume
	 */
	ServiceResponse<StorageVolume> cloneVolume(StorageVolume volume, ComputeServer server, StorageVolume sourceVolume);

	/**
	 * Clones a volume based on a source being the reference to the actual File in the Virtual Image. This can be called in the event there is no image cache or we need to directly stream to an image target.
	 * Remember, this code runs in the manager or morpheus appliance and not on the host itself. In order to stream contents directly to the target , we need to create a link we can fetch using the {@link com.morpheusdata.core.MorpheusFileCopyService}
	 * @see com.morpheusdata.core.MorpheusFileCopyService
	 * @see com.morpheusdata.core.synchronous.MorpheusSynchronousFileCopyService
	 * @param volume the volume we are creating and cloning into
	 * @param server the server the volume is associated with (typically the workload/vm)
	 * @param virtualImage the virtual image this volume is being cloned out of
	 * @param cloudFile the specific disk file (Karman abstraction) that is being cloned
	 * @return the success state and a copy of the volume
	 */
	ServiceResponse<StorageVolume> cloneVolume(StorageVolume volume, ComputeServer server, VirtualImage virtualImage, CloudFileInterface cloudFile);

	/**
	 * Perform any operations necessary on the target to resize a volume. This is used to resize a volume on a storage server
	 * @param volume the current volume to resize
	 * @param server the server the volume is being resized on (may contain information such as parentServer (hypervisor) or cluster)
	 * @param newSize the new size of the volume... TODO: this exists on the volume record already, is newSize needed?
	 * @return the success state and a copy of the volume
	 */
	ServiceResponse<StorageVolume> resizeVolume(StorageVolume volume, ComputeServer server, Long newSize);

	/**
	 * Perform any validations necessary on the target prior to create. The default returns success.
	 * @param datastore the current datastore being created
	 * @return the service response containing success state or any errors upon failure
	 */
	default ServiceResponse validateDatastore(Datastore datastore) {
		return ServiceResponse.success();
	}

	/**
	 * Perform any operations necessary on the target to create and register a datastore.
	 * Most implementations iterate over the servers on the server group (hypervisors) and register a storage pool
	 * @param datastore the current datastore being created
	 * @return the service response containing success state or any errors upon failure
	 */
	ServiceResponse<Datastore> createDatastore(Datastore datastore);

	/**
	 * Perform any operations necessary on the target to update a datastore. The default is not implemented
	 * for this behavior. If {@link DatastoreTypeProvider#getEditable()} is true, this method should be implemented
	 * @param datastore the current datastore being updated
	 * @return the success state and a copy of the datastore
	 */
	default ServiceResponse<Datastore> updateDatastore(Datastore datastore) {
		return ServiceResponse.success(datastore);
	}

	/**
	 * Perform any operations necessary on the target to remove a datastore. this method should be implemented
	 * if {@link DatastoreTypeProvider#getRemovable()} is true. otherwise return null or an error.
	 * @param datastore the current datastore being removed
	 * @return the success state of the removal
	 */
	ServiceResponse removeDatastore(Datastore datastore);

	/**
	 * Refresh the provider with the associated data in the external system.
	 * @param datastore The Datastore object contains all the saved information regarding configuration of the Datastore.
	 * @return a {@link ServiceResponse} object. A ServiceResponse with a success value of 'false' will indicate the
	 * refresh process has failed and will change the datastore status to 'error'
	 */
	default ServiceResponse<Datastore> refreshDatastore(Datastore datastore){
		return ServiceResponse.success(datastore);
	}

	/**
	 * Perform any operations necessary on the target to create a snapshot of a volume. In order to use this simply
	 * implement this interface along with the {@link DatastoreTypeProvider}. If using along with VME/MVM, it is best to
	 * implement the {@link SnapshotFacet.SnapshotServerFacet} instead.
	 * @see Snapshot
	 * @see SnapshotFile
	 * @since 1.1.18
	 * @author David Estes, Dan Devilbiss
	 */
	public interface SnapshotFacet {

		/**
		 * Creates a snapshot of a volume. This is used to create a snapshot of a volume. A lot of backup providers may use this to create snapshots for exportability or backup purposes.
		 * NOTE: Currently this isn't really used by anything, as the server is normally associated with most snapshot tracking
		 * @param volume the volume to create a snapshot for
		 * @return the success state and a copy of the snapshot
		 */
		ServiceResponse<Snapshot> createSnapshot(StorageVolume volume);

		/**
		 * Reverts a volume to a snapshot. This is used to revert a volume to a previous state.
		 * @param volume
		 * @return
		 */
		ServiceResponse removeSnapshot(StorageVolume volume);

		/**
		 * Called to list all snapshots on an associated storage server (NOT USED YET)
		 * @param storageServer the storage server to list snapshots off of
		 * @return the success state of the listing
		 */
		ServiceResponse<Snapshot> listSnapshots(StorageServer storageServer);
		ServiceResponse<StorageVolume> cloneVolume(StorageVolume volume, Snapshot sourceSnapshot);

		/**
		 * This is a more refined Snapshot facet that is used for Compute Servers. This is used to create and revert snapshots.
		 * Sometimes snapshots have to be part of a server as a whole and cannot be per volume. This allows for that implementation style.
		 * NOTE: For VME/MVM Cluster types, this is the facet you want to implement.
		 *
		 * @since 1.1.18
		 * @author David Estes, Dan Devilbiss
		 */
		public interface SnapshotServerFacet {
			/**
			 * Creates volume snapshots of all volumes associated with a server. If the server volume is of type 'cdrom',
			 * it is best to not snapshot these as ISO formats are not typically worth snapshotting
			 * A lot of backup providers may use this to create snapshots for exportability or backup purposes.
			 * In those scenarios, it is important to create exportPath information on the {@link SnapshotFile} so the
			 * morphd agent can fetch the snapshot and store it in the backup provider.
			 * @param server the server to create snapshots for (typically a vm). Host can be acquired via {@link ComputeServer#getParentServer()}
			 * @param forBackup whether this snapshot is for backup purposes
			 * @param forExport whether this snapshot is for export purposes (like an image import)
			 * @return the success state and a copy of the snapshot
			 */
			ServiceResponse<Snapshot> createSnapshot(ComputeServer server, Boolean forBackup, Boolean forExport);

			/**
			 * Reverts a server to a snapshot. This is used to revert a server to a previous state. MVM/VME will automatically
			 * ensure the server is powered off during this operation and powered back on to desired user state after this
			 * operation is complete. NOTE: These snapshots are typically volume based and not vm state based.
			 * @param server the server to revert the snapshot on
			 * @param snapshot the snapshot to revert to
			 * @return the success state and a copy of the snapshot
			 */
			ServiceResponse<Snapshot> revertSnapshot(ComputeServer server, Snapshot snapshot);

			/**
			 * Removes a snapshot from a server. This is used to remove a snapshot from a server. Sometimes,
			 * this means snapshots have to be consolidated or flattened depending on the implementation.
			 * @param server the server to remove the snapshot from
			 * @param snapshot the snapshot to remove
			 * @return the success state of the removal
			 */
			ServiceResponse removeSnapshot(ComputeServer server, Snapshot snapshot);
		}
	}

	/**
	 * This facet is used to provide additional hooks for MVM/VME specific provisioning tasks for VME/HPE Hypervisor Clusters.
	 * In the future, this may change to be  more generic and allowed for other cloud provision providers to use (i.e. Baremetal)
	 * In order to use this, add it to your implementation of your {@link DatastoreTypeProvider}
	 * Often times, it will be necessary to orchestrate behaviors on the host itself when provisioning a volume. This can be done
	 * with the context {@link MorpheusContext#executeCommandOnServer} method.
	 * <p>
	 *     <code>
	 *         ServiceResponse response = morpheusContext.executeCommandOnServer(host, "virsh pool-refresh mypool");
	 *     </code>
	 * </p>
	 * @since 1.1.18
	 * @author David Estes , Dan Devilbiss
	 */
	public interface MvmProvisionFacet {

		/**
		 * This is a hook call to allow the plugin to prepare the host for the volume. This could be something like forcing a rescan
		 * or refresh if necessary on the host itself (not the vm)
		 * @param cluster
		 * @param volume
		 * @return
		 */
		ServiceResponse<StorageVolume> prepareHostForVolume(ComputeServerGroup cluster, ComputeServer server, StorageVolume volume);

		/**
		 * When creating/defining a virtual machine in libvirt, an XML specification must be generated. Within this specificaiton are device elements corresponding
		 * to disks. This method is called to allow the plugin to specify the disk config to be used for the disk device. It is important to factor in the
		 * server record and check if it has uefi or virtioToolsInstalled as this may change your {@link MvmDiskConfig.DiskMode} to VIRTIO
		 * @param cluster
		 * @param server
		 * @param volume
		 * @return
		 */
		ServiceResponse<MvmDiskConfig> buildDiskConfig(ComputeServerGroup cluster, ComputeServer server, StorageVolume volume);

		/**
		 * This is a hook call to allow the plugin to know if a vm is being moved off of a host or removed. It should not be used to remove volume
		 * but rather if there is work to be done to release the volume from the host. This could be something like forcing a rescan.
		 * @param cluster reference to the current cluster this is being run within
		 * @param volume
		 * @return
		 */
		ServiceResponse<StorageVolume> releaseVolumeFromHost(ComputeServerGroup cluster, ComputeServer server, StorageVolume volume);

		/**
		 * This represents a disk configuration for a virtual machine for libvirt/virsh XML specification as a disk device
		 * This is used to allow the plugin to override any special configuration needed for the disk device
		 * Please refer to the libvirt documentation on block devices:
		 * <p>
		 *     <a href="https://libvirt.org/formatdomain.html#devices">https://libvirt.org/formatdomain.html#devices</a>
		 * </p>
		 * @author David Estes, Dan Devilbiss
		 */
		public static class MvmDiskConfig {
			public DiskMode diskMode;
			public String diskType; //TODO: Enum
			public String deviceName; //sda etc
			public DeviceType deviceType = DeviceType.DISK;

			/**
			 * Represents the type of device for the disk. Is it a disk, cdrom, or floppy
			 */
			public enum DeviceType {
				DISK,
				CDROM,
				FLOPPY
			}

			/**
			 * This represents the libvirt disk mode for the disk device.. VIRTIO is best for performance, but requires guest drivers
			 */
			public enum DiskMode {
				VIRTIO,
				VIRTIO_SCSI,
				SATA,
				IDE
			}
		}
	}

	/**
	 * Provides an asynchronous event listener for subscribing to events that may pertain to a datastore integration. For example,
	 * it may be necessary to perform certain optimization operations on a datastore when a vm is moved between hosts, or to
	 * notify a datastore provider when a vm is inactive (powered off) to perform certain operations.
	 * @see DatastoreEvent
	 * @author David Estes
	 */
	public interface DatastoreEventFacet extends GenericIntegrationProvider.EventSubscriberFacet<DatastoreEvent> {
		@Override
		default List<EventType> getSupportedEventTypes() {
			List<EventType> types = new ArrayList<>();
			types.add(DatastoreEvent.DatastoreEventType.SERVER_MOVE);
			types.add(DatastoreEvent.DatastoreEventType.SERVER_SHUTDOWN);
			types.add(DatastoreEvent.DatastoreEventType.SERVER_STARTUP);
			return types;
		}

	}


}
