/*
 * Copyright 2026 Morpheus Data, LLC.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;

/**
 * Bounded authorized history, retained for up to 30 days independently of subscription ACKs.
 * History is neither complete audit nor replay into a subscription, and does not renew activity.
 * @since 1.7.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventHistoryPage implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<EventEnvelope> events;
	/** Opaque continuation for unchanged filters/owner; null at the end. No snapshot guarantee. */
	public String nextCursor;
	/** Appliance-wide observations, not completeness of this filtered page or tenant. */
	public EventHistorySummary summary;
}
