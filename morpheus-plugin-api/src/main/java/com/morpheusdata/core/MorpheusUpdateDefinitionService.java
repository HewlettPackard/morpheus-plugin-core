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

package com.morpheusdata.core;

import com.morpheusdata.model.UpdateDefinition;
import com.morpheusdata.model.projection.UpdateIdentityProjection;

/**
 * Provides async CRUD access to {@link UpdateDefinition} objects within the Morpheus appliance.
 *
 * <p>Use this service to create, sync, and query update definitions from a plugin provider.
 * Update definitions describe available updates for a resource type (e.g., {@code StorageServerType},
 * {@code ComputeServerType}, {@code NetworkServerType}) and are scoped via
 * {@link UpdateDefinition#getRefType()} and {@link UpdateDefinition#getRefId()}.</p>
 *
 * <p><strong>refType vs UpdateOperation refType:</strong> The {@code refType}/{@code refId} on an
 * {@code UpdateDefinition} identifies the <em>resource type</em> (e.g., a specific
 * {@code StorageServerType} record). The {@code refType}/{@code refId} on an {@code UpdateOperation}
 * identifies the <em>specific resource instance</em> being updated (e.g., a single
 * {@code StorageServer}).</p>
 *
 * <p><strong>Sync pattern:</strong> Use {@code code} as the stable identity key when matching
 * existing records. A typical sync loop looks like:
 * <pre>{@code
 * List<UpdateDefinition> adds = new ArrayList<>();
 * List<UpdateDefinition> updates = new ArrayList<>();
 *
 * // list existing projections scoped to your resource type
 * List<UpdateIdentityProjection> existing = morpheusContext.getAsync().getUpdateDefinition()
 *     .listIdentityProjections(refType, refId).toList().blockingGet();
 *
 * for (MyUpdateInfo info : fetchFromSource()) {
 *     Optional<UpdateIdentityProjection> match = existing.stream()
 *         .filter(p -> p.getCode().equals(info.getCode())).findFirst();
 *     if (match.isPresent()) {
 *         UpdateDefinition ud = morpheusContext.getAsync().getUpdateDefinition()
 *             .get(match.get().getId()).blockingGet();
 *         // apply changes, add to updates list
 *     } else {
 *         UpdateDefinition ud = new UpdateDefinition();
 *         ud.setCode(info.getCode());
 *         ud.setRefType("StorageServerType");
 *         ud.setRefId(storageServerTypeId);
 *         // set other fields
 *         adds.add(ud);
 *     }
 * }
 * morpheusContext.getAsync().getUpdateDefinition().bulkCreate(adds).blockingGet();
 * morpheusContext.getAsync().getUpdateDefinition().bulkSave(updates).blockingGet();
 * }</pre>
 *
 * @author alex.clement
 * @since 1.4.0
 * @see com.morpheusdata.core.synchronous.MorpheusSynchronousUpdateDefinitionService
 * @see UpdateDefinition
 * @see com.morpheusdata.model.projection.UpdateIdentityProjection
 */
public interface MorpheusUpdateDefinitionService extends MorpheusDataService<UpdateDefinition, UpdateIdentityProjection>, MorpheusIdentityService<UpdateIdentityProjection> {
}
