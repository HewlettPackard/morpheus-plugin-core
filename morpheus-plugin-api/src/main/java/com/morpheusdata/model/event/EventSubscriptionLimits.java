/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * Server-enforced bounds, not caller-configurable queue policy.
 * Seven-day TTL and finite drop-head size limits bound backlog; they do not guarantee retention.
 * @since 1.7.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventSubscriptionLimits implements Serializable {
	private static final long serialVersionUID = 1L;

	public Integer maxBatchEvents;
	public Long maxBatchBytes;
	public Integer maxWaitSeconds;
	public Long messageTtlSeconds;
	public Long maxQueueMessages;
	public Long maxQueueBytes;
}
