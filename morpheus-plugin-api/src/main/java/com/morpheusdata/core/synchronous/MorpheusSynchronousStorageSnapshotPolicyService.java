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
package com.morpheusdata.core.synchronous;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.model.StorageSnapshotPolicy;
import com.morpheusdata.model.projection.StorageSnapshotPolicyIdentityProjection;

/**
 * Synchronous context methods for {@link StorageSnapshotPolicy} in Morpheus. A policy is the schedule
 * and retention attached to a {@link com.morpheusdata.model.StorageVolumeGroup} (at most one per
 * group). Provides the standard CRUD/query methods via {@link MorpheusSynchronousDataService}.
 *
 * @since 1.6.0
 * @author HPE Storage Plugin Team
 */
public interface MorpheusSynchronousStorageSnapshotPolicyService extends
		MorpheusSynchronousDataService<StorageSnapshotPolicy, StorageSnapshotPolicyIdentityProjection>,
		MorpheusSynchronousIdentityService<StorageSnapshotPolicyIdentityProjection> {

	/**
	 * Get the policy configured for a volume group, or {@code null} if the group has none.
	 *
	 * @param volumeGroupId ID of the {@link com.morpheusdata.model.StorageVolumeGroup}
	 * @return the group's policy, or {@code null} when none is set
	 */
	StorageSnapshotPolicy findByVolumeGroupId(Long volumeGroupId);
}
