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
 * One bounded batch. Process and ACK a nonempty batch before reading again.
 * An empty successful read contains an empty events list and the last accepted version; do not ACK it.
 * No response field guarantees complete delivery. Missing observations remain null.
 * @since 1.7.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventUpdateSet implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Opaque subscription batch token, never a delivery tag, global sequence or seek cursor. */
	public String version;
	public List<EventEnvelope> events;
	public Long observedLossCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date lastObservedLossAt;
	/** The read reached a configured bound; false does not imply complete history. */
	public Boolean truncated;
	/** Persisted deadline after permitted activity renewal. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date expiresAt;
}
