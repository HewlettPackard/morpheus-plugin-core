/*
 * Copyright 2026 Morpheus Data, LLC.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;

/** Bounded owner-authorized listing; listing does not renew subscription activity.
 * @since 1.7.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventSubscriptionPage implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<EventSubscription> subscriptions;
	/** Opaque continuation for this owner/query; null at the end. Not a snapshot or batch version. */
	public String nextCursor;
}
