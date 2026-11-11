/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * Authorized event/subscription scope returned by core. IDs do not grant access.
 * Unapplicable resource IDs may be absent.
 * @since 1.7.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventResourceScope implements Serializable {
	private static final long serialVersionUID = 1L;

	public Long accountId;
	public Long cloudId;
	public Long serverGroupId;
	public Long serverId;
}
