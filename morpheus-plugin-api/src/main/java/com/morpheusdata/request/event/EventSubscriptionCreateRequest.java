/*
 * Copyright 2026 Morpheus Data, LLC.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.request.event;

import java.io.Serializable;
import java.util.List;

/**
 * Immutable subscription definition. Core validates bounded names, explicit nonempty supported
 * canonical event codes, resource scope and caller permissions. No client-supplied owner or queue.
 * @since 1.7.0
 */
public class EventSubscriptionCreateRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	public String name;
	public List<String> eventTypes;
	public EventScopeFilter scope;
}
