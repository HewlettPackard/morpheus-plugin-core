/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
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
	/**
	 * Opaque continuation for unchanged filters/owner; null when the bounded scan is exhausted.
	 * An empty authorized page can still have a continuation. Each continuation expires after
	 * 15 idle minutes; using it returns a fresh continuation. No snapshot guarantee.
	 */
	public String nextCursor;
	/** Inclusive UTC retention boundary, not the earliest indexed event or a completeness claim. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date retentionStart;
	/** Exclusive UTC retention boundary observed for this page, not the latest indexed event. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date retentionEnd;
	/** Appliance-wide observations, not completeness of this filtered page or tenant. */
	public EventHistorySummary summary;
}
