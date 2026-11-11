/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.request.event;

import java.io.Serializable;

/** Bounded listing for the validated owner; core rejects invalid/over-limit pagination.
 * @since 1.7.0
 */
public class EventSubscriptionListRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Positive page size; null uses the bounded server default. */
	public Integer maxResults;
	/** Prior nextCursor for the same owner/query; null starts a listing. */
	public String cursor;
}
