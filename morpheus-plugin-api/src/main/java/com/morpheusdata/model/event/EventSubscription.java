/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Owner-authorized subscription metadata, with no broker queue name, delivery tag or collector lease.
 * Filters are immutable; create a replacement subscription to change them.
 * Null observations mean unavailable, not zero loss or an empty queue.
 * @since 1.7.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventSubscription implements Serializable {
	private static final long serialVersionUID = 1L;

	public String id;
	public String name;
	public List<String> eventTypes;
	public EventResourceScope scope;
	/** Last accepted opaque version, including the initial value returned by creation. */
	public String version;
	/** Persisted idle-expiry deadline, not the age of retained messages. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date expiresAt;
	public EventSubscriptionLimits limits;
	public Long observedLossCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date lastObservedLossAt;
	/** Best-effort broker backlog observations, not inventory or delivery guarantees. */
	public Long queuedEvents;
	public Long unacknowledgedEvents;
}
