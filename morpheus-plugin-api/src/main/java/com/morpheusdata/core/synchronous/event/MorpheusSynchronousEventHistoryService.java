/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.core.synchronous.event;

import com.morpheusdata.core.event.EventConsumerContext;
import com.morpheusdata.core.event.MorpheusEventHistoryService;
import com.morpheusdata.model.event.EventHistoryPage;
import com.morpheusdata.request.event.EventHistoryRequest;
import com.morpheusdata.response.ServiceResponse;

/** Blocking history delegation with unchanged authorization and loss semantics.
 * @since 1.7.0
 * @see MorpheusEventHistoryService
 */
public interface MorpheusSynchronousEventHistoryService {
	MorpheusEventHistoryService getEventHistoryService();

	default ServiceResponse<EventHistoryPage> query(EventConsumerContext consumer, EventHistoryRequest request) {
		return getEventHistoryService().query(consumer, request).blockingGet();
	}
}
