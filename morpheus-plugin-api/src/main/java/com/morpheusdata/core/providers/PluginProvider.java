/*
 *  Copyright 2024 Morpheus Data, LLC.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morpheusdata.core.providers;

import com.morpheusdata.core.MorpheusContext;
import com.morpheusdata.core.Plugin;
import com.morpheusdata.model.AccountIntegration;
import com.morpheusdata.model.event.*;

import java.util.ArrayList;
import java.util.List;

/**
 * All Providers implement the Plugin Extension. Different Plugins for Morpheus provide different integration endpoints.
 * These could range from DNS, IPAM, and all the way up to Cloud Integrations. Each integration type extends this as a
 * base interface for providing core methods.
 * @author David Estes
 * @since 0.15.1
 *
 */
public interface PluginProvider {

	/**
	 * Returns the Morpheus Context for interacting with data stored in the Main Morpheus Application
	 *
	 * @return an implementation of the MorpheusContext for running Future based rxJava queries
	 */
	MorpheusContext getMorpheus();

	/**
	 * Returns the instance of the Plugin class that this provider is loaded from
	 * @return Plugin class contains references to other providers
	 */
	Plugin getPlugin();

	/**
	 * A unique shortcode used for referencing the provided provider. Make sure this is going to be unique as any data
	 * that is seeded or generated related to this provider will reference it by this code.
	 * @return short code string that should be unique across all other plugin implementations.
	 */
	String getCode();

	/**
	 * Provides the provider name for reference when adding to the Morpheus Orchestrator
	 * NOTE: This may be useful to set as an i18n key for UI reference and localization support.
	 *
	 * @return either an English name of a Provider or an i18n based key that can be scanned for in a properties file.
	 */
	String getName();

	/**
	 * Returns true if the Provider is a plugin. Always true for plugin but null or false for Morpheus internal providers.
	 * @return provider is plugin
	 */
	default boolean isPlugin() { return true; }

	/**
	 *	Applying this Facet to an integration will allow it to subscribe to events and perform operations based on the event
	 * @since 1.2.6
	 * @author David Estes
	 */
	public interface EventSubscriberFacet<E extends Event> {
		/**
		 * Gets a list of supported event types that this integration can subscribe to
		 * @return list of supported event types
		 */
		List<EventType> getSupportedEventTypes();

		/**
		 * Gets the list of scopes to limit the events received by the subscriber. This is useful for filtering events based on the associations of the event subject.
		 * For example, an integration may only want to receive events when the subject has an association to a cloud. When combined with event types, a provider could
		 * subscribe to only create network on a cloud events.
		 * @return list of event scopes
		 */
		default List<IEventScope> getEventScopes() {
			List<IEventScope> scopes = new ArrayList<>();
			scopes.add(com.morpheusdata.model.event.EventScope.ALL);
			return scopes;
		}

		/**
		 * Method triggered when an event that was subscribed to is triggered. This is useful for capturing hooks like
		 * perhaps, an action needs to be performed after a network is created or destroyed.
		 *
		 * @param event the event object that was triggered
		 * @see Event
		 * @see NetworkEvent
		 */
		void onEvent(E event, AccountIntegration integration);
	}
}
