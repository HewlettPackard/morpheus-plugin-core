/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.model.projection;

import com.morpheusdata.model.StorageServerNodeDisk;

/**
 * Lightweight sync projection for {@link StorageServerNodeDisk}.
 * @since 1.5.1
 */
public class StorageServerNodeDiskIdentityProjection extends MorpheusIdentityModel {

	/** Durable, per-transport derived identity — survives reboot/device renumbering. */
	protected String uniqueId;

	public StorageServerNodeDiskIdentityProjection() {}
	public StorageServerNodeDiskIdentityProjection(Long id, String uniqueId) {
		this.id = id;
		this.uniqueId = uniqueId;
	}

	public String getUniqueId() { return uniqueId; }
	public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; markDirty("uniqueId", uniqueId); }
}
