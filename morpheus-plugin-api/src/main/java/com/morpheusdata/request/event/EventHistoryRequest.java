/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.request.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Intersecting history filters within current resource permissions. Core validates the time
 * range, filters and pagination. Null filters mean all authorized retained history;
 * they do not widen the caller's authority. Results order by eventDate then uuid ascending.
 * @since 1.7.0
 */
public class EventHistoryRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<String> eventTypes;
	public EventScopeFilter scope;
	public String subjectKey;
	/** Inclusive lower time bound. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date from;
	/** Exclusive upper time bound. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date to;
	/** Positive page size; null uses the bounded server default. */
	public Integer maxResults;
	/** Prior nextCursor with unchanged filters/owner; expires after 15 idle minutes. Null starts a search. */
	public String cursor;
}
