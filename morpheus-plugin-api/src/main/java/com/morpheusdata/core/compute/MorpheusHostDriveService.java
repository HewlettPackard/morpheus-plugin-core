/*
 * Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core.compute;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.core.MorpheusIdentityService;
import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.HostDrive;
import com.morpheusdata.model.projection.HostDriveIdentityProjection;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

/**
 * Context methods for dealing with the persisted {@link HostDrive} record for a physical drive
 * discovered on a host.
 * <p>
 * These records are written by drive-inventory sync only; there are no CRUD operations, as a
 * drive's identity and discovery-owned fields are reconciled directly from the discovered
 * inventory rather than edited by a tenant.
 *
 * @since 1.5.1
 * @author HPE Storage Plugin Team
 */
public interface MorpheusHostDriveService extends
		MorpheusDataService<HostDrive, HostDriveIdentityProjection>,
		MorpheusIdentityService<HostDriveIdentityProjection> {

	/**
	 * List identity projections for all drives belonging to a host.
	 *
	 * @param serverId ID of the {@link ComputeServer}
	 * @return Observable stream of identity projections
	 */
	Observable<HostDriveIdentityProjection> listIdentityProjections(Long serverId);

	/**
	 * List all drives discovered on a host.
	 * @param server the compute server (host)
	 * @return Observable stream of drives
	 */
	Observable<HostDrive> listByServer(ComputeServer server);

	/**
	 * Find a drive by its durable {@code uniqueId} within a host.
	 * @param serverId the compute server ID
	 * @param uniqueId the durable, per-transport derived drive identity
	 * @return Single containing the drive if found
	 */
	Single<HostDrive> findByUniqueId(Long serverId, String uniqueId);
}
