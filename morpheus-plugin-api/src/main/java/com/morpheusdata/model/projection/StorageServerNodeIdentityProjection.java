/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.model.projection;

import com.morpheusdata.model.StorageServerNode;

/**
 * Lightweight sync projection for {@link StorageServerNode}.
 * @since 1.5.1
 */
public class StorageServerNodeIdentityProjection extends MorpheusIdentityModel {

	/** SDS-assigned node identifier — the external key used to match this node during sync. */
	protected String nodeId;

	public StorageServerNodeIdentityProjection() {}
	public StorageServerNodeIdentityProjection(Long id, String nodeId) {
		this.id = id;
		this.nodeId = nodeId;
	}

	public String getNodeId() { return nodeId; }
	public void setNodeId(String nodeId) { this.nodeId = nodeId; markDirty("nodeId", nodeId); }
}
