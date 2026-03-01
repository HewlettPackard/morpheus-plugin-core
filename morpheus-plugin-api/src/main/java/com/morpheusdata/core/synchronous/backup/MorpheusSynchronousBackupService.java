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

package com.morpheusdata.core.synchronous.backup;

import com.bertramlabs.plugins.karman.StorageProvider;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.backup.*;
import com.morpheusdata.model.*;
import com.morpheusdata.model.BackupProvider;
import com.morpheusdata.model.projection.AccountIdentity;
import com.morpheusdata.model.projection.BackupIdentityProjection;
import com.morpheusdata.response.ServiceResponse;
import com.morpheusdata.model.VirtualImage;
import io.reactivex.rxjava3.core.Single;
import java.util.Map;

public interface MorpheusSynchronousBackupService extends MorpheusSynchronousDataService<Backup, BackupIdentityProjection>, MorpheusSynchronousIdentityService<BackupIdentityProjection> {

	/**
	 * Returns the MorpheusBackupTypeContext used for performing updates/queries on {@link BackupType} related assets
	 * within Morpheus.
	 * @return An instance of the BackupTypeContext to be used for calls by various backup providers
	 */
	MorpheusSynchronousBackupTypeService getType();

	/**
	 * Returns the BackupJobContext used for performing updates or queries on {@link BackupJob} related assets within Morpheus.
	 * Typically this would be called by a {@link BackupProvider}
	 * @return An instance of the Backup Job Context to be used for calls by various backup providers
	 */
	MorpheusSynchronousBackupJobService getBackupJob();

	/**
	 * Returns the BackupResultContext used for performing updates or queries on {@link BackupResult} related assets within Morpheus.
	 * Typically this would be called by a {@link BackupProvider}.
	 * @return An instance of the Backup Result Context to be used for calls by various backup providers
	 */
	MorpheusSynchronousBackupResultService getBackupResult();

	/**
	 * Returns the BackupRestoreContext used for performing updates or queries on {@link BackupRestore} related assets within Morpheus.
	 * Typically this would be called by a {@link BackupProvider}.
	 * @return An instance of the Backup Restore Context to be used for calls by various backup providers
	 */
	MorpheusSynchronousBackupRestoreService getBackupRestore();


	/**
	 * Returns the MorpheusReplicationContext used for performing updates/queries on {@link Replication} related assets
	 * within Morpheus.
	 * @return An instance of the MorpheusReplicationContext to be used for calls by various backup providers
	 */
	MorpheusSynchronousReplicationService getReplication();

	/**
	 * Returns the {@link StorageBucket} associated with a {@link Backup} object. If the backup does not have an associated
	 * storage bucket, the default backup storage bucket will be returned.
	 * @param accountIdentity the {@link AccountIdentity} object to use for the storage bucket lookup
	 * @param backupId the ID of the {@link Backup} object to use for the storage bucket lookup
	 * @return the Single Observable containing the {@link StorageBucket} object for subscription
	 */
	StorageBucket getBackupStorageBucket(AccountIdentity accountIdentity, Long backupId);

	/**
	 * Returns the {@link StorageBucket} associated with a {@link Backup} object. If the backup does not have an associated
	 * storage bucket, the default backup storage bucket will be returned.
	 * @param accountIdentity the {@link AccountIdentity} object to use for the storage bucket lookup
	 * @param backupId the ID of the {@link Backup} object to use for the storage bucket lookup
	 * @param storageProviderId the ID of the {@link StorageProvider} object to use for the storage bucket lookup
	 * @return the Single Observable containing the {@link StorageBucket} object for subscription
	 */
	StorageBucket getBackupStorageBucket(AccountIdentity accountIdentity, Long backupId, Long storageProviderId);

	/**
	 * Returns the {@link StorageProvider} for the default backup storage bucket.
	 * @return the Single Observable containing the {@link StorageProvider} object for subscription
	 */
	StorageProvider getBackupStorageProvider();

	/**
	 * Returns the {@link StorageProvider} for a specific storage bucket.
	 * @param storageBucketId the ID of the {@link StorageBucket} object to use for the storage provider lookup
	 * @return the Single Observable containing the {@link StorageProvider} object for subscription
	 */
	StorageProvider getBackupStorageProvider(Long storageBucketId);

	/**
	 * Returns the {@link StorageProvider} for a specific storage bucket and base path.
	 * @param storageBucketId the ID of the {@link StorageBucket} object to use for the storage provider lookup
	 * @param basePath the base path to use for the storage provider
	 * @return the Single Observable containing the {@link StorageProvider} object for subscription
	 */
	StorageProvider getBackupStorageProvider(Long storageBucketId, String basePath);

	/**
	 * Returns the working path for a backup result. This is typically used to store temporary files or
	 * directories for the backup process.
	 * @param backupId the id of the {@link Backup} object
	 * @param backupResultId the id of the {@link BackupResult} object
	 * @return the working path for the backup result
	 */
	String getBackupWorkingPath(Long backupId, Long backupResultId);

	/**
	 * Compresses the backup working directory into a zip archive and saves it to the configured backup storage provider.
	 * This is typically called at the end of {@link BackupExecutionProvider#extractBackup} after the backup data has been
	 * written to the working path. The result contains metadata needed to update the {@link BackupResult} (bucket,
	 * directory, archive name, and archive size).
	 * @param account the {@link AccountIdentity} that owns the backup
	 * @param workingPath the local directory path containing the backup data to compress and upload
	 * @param backupId the id of the {@link Backup} object
	 * @return a {@link ServiceResponse} with result metadata: providerType, basePath, targetBucket, targetDirectory,
	 *         targetArchive, archiveSize
	 */
	ServiceResponse saveBackupResults(AccountIdentity account, String workingPath, Long backupId);

	/**
	 * Transfers a backup archive from backup storage into a new {@link VirtualImage}, enabling restore-to-new-instance
	 * workflows. Files are streamed from the backup storage provider into the default virtual image storage location.
	 * The {@code sourceImage} parameter is optional and, when provided, is used to inherit cloudInit, installAgent,
	 * and SSH credential settings onto the new image.
	 * @param account the {@link AccountIdentity} that owns the backup
	 * @param backupResult the {@link BackupResult} whose archive should be transferred
	 * @param opts a map of options: {@code imageType} (e.g. 'vmdk'), {@code zoneTypeCode}, {@code osType}, {@code name}
	 * @param sourceImage optional source {@link VirtualImage} to inherit settings from; may be null
	 * @return a {@link ServiceResponse} whose data is the newly created {@link VirtualImage}
	 */
	ServiceResponse<VirtualImage> transferBackupToVirtualImage(AccountIdentity account, BackupResult backupResult, Map opts, VirtualImage sourceImage);
}
