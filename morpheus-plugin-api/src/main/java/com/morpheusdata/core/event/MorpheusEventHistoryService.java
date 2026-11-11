/*
 * Copyright 2026 Morpheus Data, LLC.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.core.event;

import com.morpheusdata.model.event.EventHistoryPage;
import com.morpheusdata.request.event.EventHistoryRequest;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Single;

/**
 * Filtered, authorized history with bounded pagination under EventConsumerContext's
 * tenant-scoped trusted SDK policy. REST callers separately require Events Read or Full.
 * Every query revalidates the loaded plugin, account/integration and current resource access.
 * Search outages return explicit dependency failures, not successful empty history.
 * History is retained for up to 30 days, independently of live ACKs. It is not a complete
 * audit trail, queue replay or inventory snapshot. Queries do not renew subscription activity.
 * @since 1.7.0
 */
public interface MorpheusEventHistoryService {
	/** @return authorized events and the appliance-wide bounded operational summary */
	Single<ServiceResponse<EventHistoryPage>> query(EventConsumerContext consumer, EventHistoryRequest request);
}
