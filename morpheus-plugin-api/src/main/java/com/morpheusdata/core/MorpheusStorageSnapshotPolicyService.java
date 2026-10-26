/*
 *  Copyright 2026 HPE Development Company, L.P.
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
package com.morpheusdata.core;

import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.StorageSnapshotPolicy;
import com.morpheusdata.model.StorageVolumeGroup;
import com.morpheusdata.model.projection.StorageSnapshotPolicyIdentityProjection;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.Map;

/**
 * Context methods for dealing with {@link StorageSnapshotPolicy} in Morpheus. A policy is the
 * schedule and retention attached to a {@link StorageVolumeGroup} (at most one per group) that
 * produces automatic snapshots.
 *
 * <p>This service owns the policy record and its CRUD. Executing the schedule (taking snapshots)
 * and enforcing retention are handled by the core scheduler, or by the array when
 * {@link StorageSnapshotPolicy#getUseNativeScheduler() useNativeScheduler} is true — not here.
 *
 * @since 1.6.0
 * @author HPE Storage Plugin Team
 */
public interface MorpheusStorageSnapshotPolicyService extends
		MorpheusDataService<StorageSnapshotPolicy, StorageSnapshotPolicyIdentityProjection>,
		MorpheusIdentityService<StorageSnapshotPolicyIdentityProjection> {

	/**
	 * Get the policy configured for a volume group.
	 *
	 * @param volumeGroupId ID of the {@link StorageVolumeGroup}
	 * @return Maybe emitting the group's policy, or completing empty when the group has none
	 */
	Maybe<StorageSnapshotPolicy> findByVolumeGroupId(Long volumeGroupId);

	// ============================================================================
	// Configure Operation (with Provider Delegation)
	// ============================================================================

	/**
	 * Configure (create or update) the snapshot policy for a volume group.
	 * <p>
	 * When the storage server's provider implements
	 * {@link com.morpheusdata.core.providers.StorageProviderVolumeGroupsFacet}, this delegates to
	 * {@link com.morpheusdata.core.providers.StorageProviderVolumeGroupsFacet#applySnapshotPolicy} so a
	 * native array scheduler can be configured; the resulting record is then persisted. When no such
	 * provider is present the record is persisted directly.
	 * <p>
	 * If validation fails or the provider returns an error, nothing is persisted and that error is
	 * returned.
	 *
	 * @param storageServer the storage server hosting the volume group
	 * @param volumeGroup the volume group the policy applies to
	 * @param policy the policy settings to apply (interval, retention, enabled, applicationConsistent, useNativeScheduler)
	 * @param opts additional options
	 * @return ServiceResponse with the applied policy
	 */
	Single<ServiceResponse<StorageSnapshotPolicy>> saveSnapshotPolicy(StorageServer storageServer, StorageVolumeGroup volumeGroup, StorageSnapshotPolicy policy, Map<String, Object> opts);

	// ============================================================================
	// Delete Operation (with Provider Delegation)
	// ============================================================================

	/**
	 * Delete the snapshot policy from its volume group, leaving the group with no policy.
	 * <p>
	 * When the storage server's provider implements
	 * {@link com.morpheusdata.core.providers.StorageProviderVolumeGroupsFacet}, this delegates to
	 * {@link com.morpheusdata.core.providers.StorageProviderVolumeGroupsFacet#removeSnapshotPolicy} so any
	 * array-native schedule is removed before the record is deleted. When no such provider is present
	 * the record is deleted directly. If the provider returns an error, the record is kept.
	 *
	 * @param policy the policy to delete
	 * @param opts additional options
	 * @return ServiceResponse indicating success/failure
	 */
	Single<ServiceResponse> deleteSnapshotPolicy(StorageSnapshotPolicy policy, Map<String, Object> opts);
}
