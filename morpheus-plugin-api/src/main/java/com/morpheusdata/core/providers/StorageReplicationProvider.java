/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core.providers;

import com.morpheusdata.model.StorageReplicationGroup;
import com.morpheusdata.model.StorageReplicationGroup.FailoverType;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.response.ServiceResponse;

import java.util.Map;

/**
 * Optional facet for {@link StorageProvider} implementations whose arrays support acting
 * on an existing array-to-array replication relationship, i.e. failover, failback, pause
 * and resume. Paired with {@link StorageProvider} the same way as
 * {@link StorageProviderVolumeGroupsFacet}.
 *
 * <p>CRUD and discovery of replication groups themselves live on
 * {@link StorageProviderReplicationGroupFacet}; a provider may implement one facet
 * without the other, e.g. an array that only reports replication state discovered via
 * {@link StorageProvider#refreshStorageServer} without supporting operator-driven
 * failover/failback from Morpheus.
 *
 * @since 1.5.0
 * @author HPE Storage Plugin Team
 * @see StorageProvider
 * @see StorageProviderReplicationGroupFacet
 */
public interface StorageReplicationProvider {

	/**
	 * Trigger a planned or unplanned failover. Planned flushes outstanding writes
	 * before reversing roles; unplanned promotes the secondary while the primary
	 * may be unreachable and may result in data loss.
	 *
	 * @param storageServer the local {@link StorageServer}
	 * @param group         the group to fail over
	 * @param type          {@link FailoverType#planned} or {@link FailoverType#unplanned}
	 * @param opts          provider-specific options
	 * @return ServiceResponse containing the updated group on success
	 */
	ServiceResponse<StorageReplicationGroup> failoverReplicationGroup(
			StorageServer storageServer, StorageReplicationGroup group, FailoverType type, Map opts);

	/**
	 * Fail back to the original primary. Resyncs the recovered array, then reverses
	 * roles. Never automatic — the operator must invoke this explicitly.
	 *
	 * @param storageServer the local {@link StorageServer}
	 * @param group         the group to fail back
	 * @param opts          provider-specific options
	 * @return ServiceResponse containing the updated group on success
	 */
	ServiceResponse<StorageReplicationGroup> failbackReplicationGroup(
			StorageServer storageServer, StorageReplicationGroup group, Map opts);

	/**
	 * Pause an active replication relationship without deleting it.
	 *
	 * @param storageServer the local {@link StorageServer}
	 * @param group         the group to pause
	 * @param opts          provider-specific options
	 * @return ServiceResponse containing the updated group on success
	 */
	ServiceResponse<StorageReplicationGroup> pauseReplication(
			StorageServer storageServer, StorageReplicationGroup group, Map opts);

	/**
	 * Resume a paused replication relationship.
	 *
	 * @param storageServer the local {@link StorageServer}
	 * @param group         the group to resume
	 * @param opts          provider-specific options
	 * @return ServiceResponse containing the updated group on success
	 */
	ServiceResponse<StorageReplicationGroup> resumeReplication(
			StorageServer storageServer, StorageReplicationGroup group, Map opts);
}
