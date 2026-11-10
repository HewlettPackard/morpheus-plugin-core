/*
 * Copyright 2026 Morpheus Data, LLC.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.core.event;

import com.morpheusdata.core.Plugin;
import java.util.Objects;

/**
 * JVM-local caller information, not a credential, registration or collector lease.
 * Core must validate the exact loaded plugin instance and resolve/authorize the account and
 * optional integration on every operation. IDs are untrusted selectors, never grants.
 * An integration must belong to both the plugin and account. Without an integration,
 * core must independently authorize the plugin's access to the selected account.
 * Calls after plugin unload must fail; unloading must not delete durable subscriptions.
 *
 * @since 1.7.0
 */
public final class EventConsumerContext {
	private final Plugin plugin;
	private final Long accountId;
	private final Long integrationId;

	/**
	 * Construct caller information after plugin initialization.
	 * @param plugin the actual loaded plugin instance, not a plugin code or owner key
	 * @param accountId account to resolve and authorize
	 * @param integrationId integration to resolve and authorize, or null for a plugin-only consumer
	 */
	public EventConsumerContext(Plugin plugin, Long accountId, Long integrationId) {
		this.plugin = Objects.requireNonNull(plugin, "plugin");
		this.accountId = Objects.requireNonNull(accountId, "accountId");
		this.integrationId = integrationId;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public Long getAccountId() {
		return accountId;
	}

	public Long getIntegrationId() {
		return integrationId;
	}
}
