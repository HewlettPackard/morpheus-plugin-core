/*
 * Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.model.projection;

import com.morpheusdata.model.HostDrive;

/**
 * Lightweight sync projection for {@link HostDrive}.
 * @since 1.5.1
 */
public class HostDriveIdentityProjection extends MorpheusIdentityModel {

	/** Stable across reboot; the identity the array is given. */
	protected String uniqueId;

	public HostDriveIdentityProjection() {}
	public HostDriveIdentityProjection(Long id, String uniqueId) {
		this.id = id;
		this.uniqueId = uniqueId;
	}

	public String getUniqueId() { return uniqueId; }
	public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; markDirty("uniqueId", uniqueId); }
}
