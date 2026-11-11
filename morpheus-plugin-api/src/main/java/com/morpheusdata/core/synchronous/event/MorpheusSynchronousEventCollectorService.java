/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.core.synchronous.event;

import com.morpheusdata.core.event.EventConsumerContext;
import com.morpheusdata.core.event.MorpheusEventCollectorService;
import com.morpheusdata.model.event.*;
import com.morpheusdata.request.event.*;
import com.morpheusdata.response.ServiceResponse;

/**
 * Blocking delegates with the same authorization, bounds, progress and loss semantics as
 * {@link MorpheusEventCollectorService}. Do not call from an event-loop thread.
 * @since 1.7.0
 */
public interface MorpheusSynchronousEventCollectorService {
	MorpheusEventCollectorService getEventCollectorService();

	default ServiceResponse<EventSubscription> create(EventConsumerContext consumer, EventSubscriptionCreateRequest request) {
		return getEventCollectorService().create(consumer, request).blockingGet();
	}

	default ServiceResponse<EventSubscription> get(EventConsumerContext consumer, String subscriptionId) {
		return getEventCollectorService().get(consumer, subscriptionId).blockingGet();
	}

	default ServiceResponse<EventSubscriptionPage> list(EventConsumerContext consumer, EventSubscriptionListRequest request) {
		return getEventCollectorService().list(consumer, request).blockingGet();
	}

	default ServiceResponse<EventUpdateSet> read(EventConsumerContext consumer, String subscriptionId, EventReadRequest request) {
		return getEventCollectorService().read(consumer, subscriptionId, request).blockingGet();
	}

	default ServiceResponse<EventSubscription> ack(EventConsumerContext consumer, String subscriptionId, EventAckRequest request) {
		return getEventCollectorService().ack(consumer, subscriptionId, request).blockingGet();
	}

	default ServiceResponse<Void> delete(EventConsumerContext consumer, String subscriptionId) {
		return getEventCollectorService().delete(consumer, subscriptionId).blockingGet();
	}
}
