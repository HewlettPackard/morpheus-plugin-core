/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.request.event;

import java.io.Serializable;

/**
 * Bounded read/wait options. Core rejects invalid or over-limit bounds, rather than silently
 * accepting an unbounded request. Reading does not seek or replay acknowledged messages.
 * @since 1.7.0
 */
public class EventReadRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Required last accepted version, initially obtained from subscription creation/get/list. */
	public String sinceVersion;
	/** Positive count within server limits; null uses the bounded server default. */
	public Integer maxEvents;
	/** Nonnegative bounded wait; null means no wait. */
	public Integer waitSeconds;
}
