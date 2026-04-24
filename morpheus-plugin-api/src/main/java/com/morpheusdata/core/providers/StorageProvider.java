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

import com.morpheusdata.model.*;
import com.morpheusdata.response.ServiceResponse;
import com.morpheusdata.views.HTMLResponse;

import java.util.Map;

/**
 * Represents a {@link StorageServerType} implementation for creating buckets, volumes and file shares.
 * Depending on the capabilities of the referenced storage server one can add implementations for the additional
 * interfaces such as {@link StorageProviderFileShares} or {@link StorageProviderBuckets} or {@link StorageProviderVolumes}.
 * @author David Estes
 * @since 0.15.1
 * @see StorageProviderFileShares
 * @see StorageProviderVolumes
 * @see StorageProviderBuckets
 */
public interface StorageProvider extends PluginProvider,UIExtensionProvider {

	/**
	 * Returns the description of the provider type
	 * @return String
	 */
	String getDescription();

	/**
	 * Returns the Storage Server Integration logo for display when a user needs to view or add this integration
	 * @return Icon representation of assets stored in the src/assets of the project.
	 */
	Icon getIcon();

	/**
	 * Provide a {@link StorageServerType} to be added to the morpheus environment
	 * as the type for this {@link StorageServer}. The StorageServerType also defines the
	 * OptionTypes for configuration of a new server and its volume types.
	 * @return StorageServerType
	 */
	StorageServerType getStorageServerType();

	/**
	 * Validation Method used to validate all inputs applied to the integration of a Storage Provider upon save.
	 * If an input fails validation or authentication information cannot be verified, Error messages should be returned
	 * via a {@link ServiceResponse} object where the key on the error is the field name and the value is the error message.
	 * If the error is a generic authentication error or unknown error, a standard message can also be sent back in the response.
	 *
	 * @param storageServer The Storage Server object contains all the saved information regarding configuration of the Storage Provider
	 * @param opts an optional map of parameters that could be sent. This may not currently be used and can be assumed blank
	 * @return A response is returned depending on if the inputs are valid or not.
	 */
	ServiceResponse verifyStorageServer(StorageServer storageServer, Map opts);

	/**
	 * Called on the first save / update of a storage server integration. Used to do any initialization of a new integration
	 * Often times this calls the periodic refresh method directly.
	 * @param storageServer The Storage Server object contains all the saved information regarding configuration of the Storage Provider.
	 * @param opts an optional map of parameters that could be sent. This may not currently be used and can be assumed blank
	 * @return a ServiceResponse containing the success state of the initialization phase
	 */
	ServiceResponse initializeStorageServer(StorageServer storageServer, Map opts);

	/**
	 * Refresh the provider with the associated data in the external system.
	 * @param storageServer The Storage Server object contains all the saved information regarding configuration of the Storage Provider.
	 * @param opts an optional map of parameters that could be sent. This may not currently be used and can be assumed blank
	 * @return a {@link ServiceResponse} object. A ServiceResponse with a success value of 'false' will indicate the
	 * refresh process has failed and will change the storage server status to 'error'
	 */
	ServiceResponse refreshStorageServer(StorageServer storageServer, Map opts);


	/**
	 * Integration details provided to your rendering engine
	 * @param storageServer details of a storage server
	 * @return result of rendering a template
	 */
	default HTMLResponse renderTemplate(StorageServer storageServer) {
		return null;
	}
	/**
	 * This interface enables remote update operations on Storage Servers such as Storage Arrays.
	 *
	 * <p><strong>Update lifecycle:</strong> {@code validateUpdate} → {@code executeUpdate} → {@code postUpdate}.
	 * If {@code executeUpdate} or {@code postUpdate} fail, the appliance calls {@code rollbackUpdate}.
	 * {@code refreshUpdate} is used only to poll the status of a long-running async operation — it is
	 * <strong>not</strong> part of the rollback path.</p>
	 *
	 * <p><strong>Parameter ordering note:</strong> {@code StorageUpdateFacet} takes {@code (StorageServer, UpdateDefinition)}
	 * — the server instance comes first. This is the opposite of {@code ComputeUpdateFacet}, which takes
	 * {@code (UpdateDefinition, ComputeServer...)}. Keep this in mind when implementing across resource types.</p>
	 *
	 * <p>{@link com.morpheusdata.model.UpdateDefinition} describes the <em>available update</em> (what to apply),
	 * scoped to a resource type via {@code refType}/{@code refId}. {@link com.morpheusdata.model.UpdateOperation}
	 * describes the <em>in-progress execution</em> on a specific instance, and is what {@code refreshUpdate}
	 * and {@code rollbackUpdate} operate against.</p>
	 */
	public interface StorageUpdateFacet extends UpdateFacet<StorageServer> {
		/**
		 * Perform a validation of the update against the target storage server. Check prerequisites,
		 * compatibility, or other conditions to ensure the update can be applied successfully.
		 *
		 * @param storageServer the target device to be updated
		 * @param update the update definition containing the details of the update to be applied
		 * @return a ServiceResponse with any errors if validation failed or a success response if validation passed
		 */
		ServiceResponse<UpdateOperation> validateUpdate(StorageServer storageServer, UpdateDefinition update);

		/**
		 * Execute the update on the target storage server. This is where the actual update logic should be
		 * implemented. Called after {@code validateUpdate} succeeds. On failure, the appliance will call
		 * {@code rollbackUpdate}.
		 *
		 * @param storageServer the target device to be updated
		 * @param update the update definition containing the details of the update to be applied
		 * @return a ServiceResponse indicating the success or failure of the update operation
		 */
		ServiceResponse<UpdateOperation> executeUpdate(StorageServer storageServer, UpdateDefinition update);

		/**
		 * Poll the status of a long-running update operation. Called by the appliance when an
		 * {@code UpdateOperation} is in a pending/in-progress state and needs a status refresh.
		 * This method is <strong>not</strong> called as part of the rollback path — use {@code rollbackUpdate}
		 * for failure recovery.
		 *
		 * @param storageServer the target device being updated
		 * @param updateOperation the in-progress operation whose status should be refreshed
		 * @return a ServiceResponse with the updated operation state
		 */
		ServiceResponse<UpdateOperation> refreshUpdate(StorageServer storageServer, UpdateOperation updateOperation);

		/**
		 * Post-update operations: cleanup, verification, or other finalization steps. Called after
		 * {@code executeUpdate} completes successfully. On failure, the appliance will call {@code rollbackUpdate}.
		 *
		 * @param storageServer the target device that was updated
		 * @param update the update definition
		 * @return a ServiceResponse indicating the success or failure of post-update steps
		 */
		ServiceResponse<UpdateOperation> postUpdate(StorageServer storageServer, UpdateDefinition update);

		/**
		 * Roll back the update on the target storage server. Called by the appliance when {@code executeUpdate}
		 * or {@code postUpdate} returns a failure response. Implement idempotent cleanup here.
		 *
		 * @param storageServer the target device to roll back
		 * @param update the update definition for the operation being rolled back
		 * @return a ServiceResponse indicating the success or failure of the rollback operation
		 */
		ServiceResponse<UpdateOperation> rollbackUpdate(StorageServer storageServer, UpdateDefinition update);
	}

	public interface StorageConfigurationDriftCheckFacet extends ConfigurationDriftCheckFacet<StorageServer> {
		/**
		 * Perform a configuration drift check on the target device.  This is useful for ensuring that the
		 * configuration of the device matches the expected configuration stored in Morpheus.
		 *
		 * @param storageServer the target device to check for configuration drift
		 * @param checkLevel the level of the drift check to perform (e.g., all, update)
		 * @return a ServiceResponse indicating the success or failure of the configuration drift check
		 */
		ServiceResponse<DriftState> runConfigurationDriftCheck(StorageServer storageServer, CheckLevel checkLevel);


		/**
		 * Retrieve details about the configuration that is required by a System plugin to crosscheck data against a whole system.
		 *
		 * @param storageServer the target device to check
		 * @return a ServiceResponse containing details about the configuration drift
		 */
		ServiceResponse<DriftState> getConfigurationDriftDetails(StorageServer storageServer, DriftState driftState);
	}

	/**
	 * Facet that enables a storage plugin to validate volume name and size before provisioning
	 * or reconfiguration (resize).
	 *
	 * The facet is only invoked when the storage server type is plugin-backed. Volumes with no associated storage
	 * server, and non-block volume types are not passed to this method.
	 */
	interface StorageVolumeProvisioningFacet {

		/**
		 * Validate a volume configuration before provisioning or reconfiguration.
		 *
		 * <p>Implementations should validate at minimum:
		 * <ul>
		 *   <li>Volume name against the storage system's naming rules</li>
		 *   <li>Volume size against the storage system's minimum/maximum bounds</li>
		 * </ul>
		 *
		 * @return a ServiceResponse containing field-keyed errors
		 *
		 * @param storageServer the storage server that will back this volume
		 * @param volumeConfig  raw volume configuration map as submitted by the user;
		 *                      contains at minimum: name (String), size (numeric, GiB),
		 *                      datastoreId (Long), storageType (Long, StorageVolumeType id)
		 * @param opts          additional context from the provision or reconfigure request
		 * @return a ServiceResponse containing field-keyed errors
		 */
		ServiceResponse validateVolumeConfig(StorageServer storageServer, Map volumeConfig, Map opts);
	}
}
