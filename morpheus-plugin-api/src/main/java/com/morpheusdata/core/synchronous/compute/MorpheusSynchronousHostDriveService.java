/*
 * Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core.synchronous.compute;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.HostDrive;
import com.morpheusdata.model.projection.HostDriveIdentityProjection;

import java.util.List;

/**
 * Blocking counterpart to {@link com.morpheusdata.core.compute.MorpheusHostDriveService} for use
 * in synchronous plugin code. Provides {@code bulkCreate}/{@code bulkSave}/{@code bulkRemove} and
 * other standard CRUD/query methods via {@link MorpheusSynchronousDataService}.
 *
 * @since 1.5.1
 * @author HPE Storage Plugin Team
 */
public interface MorpheusSynchronousHostDriveService extends
		MorpheusSynchronousDataService<HostDrive, HostDriveIdentityProjection>,
		MorpheusSynchronousIdentityService<HostDriveIdentityProjection> {

	/**
	 * List identity projections for all drives belonging to a host.
	 *
	 * @param serverId ID of the {@link ComputeServer}
	 * @return a List of identity projections
	 */
	List<HostDriveIdentityProjection> listIdentityProjections(Long serverId);

	/**
	 * List all drives discovered on a host.
	 * @param server the compute server (host)
	 * @return a List of drives
	 */
	List<HostDrive> listByServer(ComputeServer server);

	/**
	 * Find a drive by its durable {@code uniqueId} within a host.
	 * @param serverId the compute server ID
	 * @param uniqueId the durable, per-transport derived drive identity
	 * @return the drive if found, otherwise null
	 */
	HostDrive findByUniqueId(Long serverId, String uniqueId);
}
