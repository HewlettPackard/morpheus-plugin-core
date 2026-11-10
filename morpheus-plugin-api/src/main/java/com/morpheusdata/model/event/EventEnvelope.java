/*
 * Copyright 2026 Morpheus Data, LLC.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Queue/history event, independent of the legacy {@link Event} callback interface.
 * Unknown envelope fields are ignored; unknown canonical codes and details remain inspectable.
 * @since 1.7.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventEnvelope implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Stable identity for deduplication across redelivery, subscriptions and history. */
	public String uuid;
	/** Canonical string, not an enum: later producers may introduce new codes. */
	public String eventType;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date eventDate;
	/** Resource family and stable UUID, such as {@code vm:<uuid>}. */
	public String subjectKey;
	/** Resource-local revision, not a global sequence or a replay position. */
	public Long subjectRevision;
	public EventResourceScope scope;
	/** Producer-specific JSON-compatible values; not an inventory snapshot. */
	public Map<String, Object> details;
}
