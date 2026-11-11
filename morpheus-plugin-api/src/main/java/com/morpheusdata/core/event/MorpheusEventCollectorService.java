/*
 * Copyright 2026 Morpheus Data, LLC.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.core.event;

import com.morpheusdata.model.event.*;
import com.morpheusdata.request.event.*;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Single;

/**
 * Owner-bound durable subscriptions, not a publisher or public collector-lease API.
 * Every operation revalidates {@link EventConsumerContext}, current permissions and subscription
 * ownership. A subscription ID or filter is not authorization. Plugin calls follow the
 * tenant-scoped trusted SDK policy described by EventConsumerContext. REST callers separately
 * require Events Full except listing, which permits Events Read. Unavailable dependencies
 * return explicit failures.
 *
 * <p>Events can be lost, duplicated or reordered. Reconcile authoritative inventory on startup,
 * after outages/loss, and periodically. Register/resume the subscription before scanning inventory;
 * there is no atomic snapshot/replay, exactly-once or complete-audit guarantee. Queues have
 * seven-day message TTL and finite drop-head limits, which can remove messages sooner.
 * Consumer identity is separate from internal collector node ownership and channel state.
 *
 * @since 1.7.0
 */
public interface MorpheusEventCollectorService {
	/** Create and bind a private durable queue before reporting success, with initial accepted version. */
	Single<ServiceResponse<EventSubscription>> create(EventConsumerContext consumer, EventSubscriptionCreateRequest request);

	/** Inspect an owned subscription without renewing activity. Removed/expired subscriptions return 404. */
	Single<ServiceResponse<EventSubscription>> get(EventConsumerContext consumer, String subscriptionId);

	/** List only authorized owned subscriptions; does not renew activity. Pagination is bounded. */
	Single<ServiceResponse<EventSubscriptionPage>> list(EventConsumerContext consumer, EventSubscriptionListRequest request);

	/**
	 * Read one bounded batch, optionally waiting. At most one batch may be pending.
	 * An empty read returns accepted progress and needs no ACK. A stale sinceVersion returns
	 * {@link EventErrorCode#STALE_VERSION} with errors.lastAcceptedVersion; missing/replaced
	 * queues return {@link EventErrorCode#QUEUE_GONE}, never an empty successful batch.
	 * Recheck resource permissions before delivery. Read activity may renew persisted idle expiry.
	 */
	Single<ServiceResponse<EventUpdateSet>> read(EventConsumerContext consumer, String subscriptionId, EventReadRequest request);

	/**
	 * Persist accepted progress before broker ACK. Database failure leaves messages unacknowledged.
	 * Broker ACK failure after persistence returns a dependency failure and invalidates the pending
	 * batch/channel; redelivery gets a new token. Deduplicate by event uuid.
	 * Retrying the last accepted version succeeds without acknowledging a different batch.
	 * Older/invalid tokens return 409 with errors.lastAcceptedVersion. Success returns accepted
	 * subscription progress and persisted expiry. Never ACK an empty read.
	 */
	Single<ServiceResponse<EventSubscription>> ack(EventConsumerContext consumer, String subscriptionId, EventAckRequest request);

	/** Stop collection and remove only this owner's private queue/configuration, not history. */
	Single<ServiceResponse<Void>> delete(EventConsumerContext consumer, String subscriptionId);
}
