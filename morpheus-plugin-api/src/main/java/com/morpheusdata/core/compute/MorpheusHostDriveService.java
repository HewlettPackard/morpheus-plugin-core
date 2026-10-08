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
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.Map;

/**
 * Context methods for dealing with the persisted {@link HostDrive} record for a physical drive
 * discovered on a host.
 * <p>
 * Most of these records are written by drive-inventory sync, but a tenant with sufficient
 * permission may also manage them directly through the REST API's CRUD operations below.
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

	// ============================================================================
	// CRUD Operations
	// ============================================================================

	/**
	 * Create a new drive record discovered on the given host.
	 *
	 * @param server the {@link ComputeServer} the drive belongs to
	 * @param drive the drive to create
	 * @param opts additional options
	 * @return ServiceResponse with the created drive
	 */
	Single<ServiceResponse<HostDrive>> createHostDrive(ComputeServer server, HostDrive drive, Map<String, Object> opts);

	/**
	 * Update an existing host drive.
	 * <p>
	 * This is a partial update: only fields the caller actually intends to change should be
	 * bound onto {@code drive} before calling. Since a plain model object can't distinguish
	 * "not sent" from "explicitly null," pass {@code opts.excludeFields} (a
	 * {@code List<String>} of property names) for any updatable field the caller did not
	 * submit, so the implementation can skip binding them and avoid clobbering existing values.
	 *
	 * @param drive the drive to update
	 * @param opts additional options; supports {@code excludeFields} (List&lt;String&gt;)
	 * @return ServiceResponse with the updated drive
	 */
	Single<ServiceResponse<HostDrive>> updateHostDrive(HostDrive drive, Map<String, Object> opts);

	/**
	 * Delete a host drive.
	 *
	 * @param drive the drive to delete
	 * @param opts additional options
	 * @return ServiceResponse indicating success/failure
	 */
	Single<ServiceResponse> deleteHostDrive(HostDrive drive, Map<String, Object> opts);
}
