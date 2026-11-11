/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.request.event;

import java.io.Serializable;

/**
 * Intersecting resource filters within the authorized consumer's account.
 * Absent IDs leave that dimension unrestricted within current permissions, never across tenants.
 * Core validates positive IDs, resource relationships and authorization before accepting a request.
 * @since 1.7.0
 */
public class EventScopeFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	public Long cloudId;
	public Long serverGroupId;
	public Long serverId;
}
