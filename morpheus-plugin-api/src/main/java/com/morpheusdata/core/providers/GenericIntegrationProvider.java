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

import com.morpheusdata.model.*;
import com.morpheusdata.model.event.Event;
import com.morpheusdata.model.event.EventType;
import com.morpheusdata.model.event.IEventScope;
import com.morpheusdata.model.event.NetworkEvent;
import com.morpheusdata.response.ServiceResponse;
import com.morpheusdata.views.HTMLResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Allows for the registration of a "Generic" {@link com.morpheusdata.model.AccountIntegrationType}.
 * These could be used to store metadata related to other providers. For example, a Jenkins Task Type
 * may not want the user to have to enter in credentials every time. Instead, they could choose an
 * integration that has those credentials. Additionally, the integration could store data for {@link DatasetProvider}
 * dropdowns in those task types. In the Jenkins example, a list of projects could be periodically
 * synced.
 *
 * @author David Estes
 * @since 0.15.4
 */
public interface GenericIntegrationProvider extends PluginProvider,UIExtensionProvider {

	/**
	 * Validation Method used to validate all inputs applied to the generic integration upon save.
	 * If an input fails validation or authentication information cannot be verified, Error messages should be returned
	 * via a {@link ServiceResponse} object where the key on the error is the field name and the value is the error message.
	 * If the error is a generic authentication error or unknown error, a standard message can also be sent back in the response.
	 * @since 1.1.10
	 *
	 * @param accountIntegration The Integration Object contains all the saved information regarding configuration of the Provider.
	 * @param opts any custom payload submission options may exist here
	 * @return A response is returned depending on if the inputs are valid or not.
	 */
	default public ServiceResponse verifyAccountIntegration(AccountIntegration accountIntegration, Map opts) {
		return ServiceResponse.success(accountIntegration);
	}

	/**
	 * The category of the integration. This is used to group integrations in the UI. Available categories are defined in {@link com.morpheusdata.model.AccountIntegration.Category}.
	 * @return
	 */
	String getCategory();

	/**
	 * Provide custom configuration options when creating a new {@link AccountIntegration}
	 * @return a List of OptionType
	 */
	List<OptionType> getOptionTypes();

	/**
	 * Refresh the integration with the latest data from the provider
	 * @param accountIntegration the integration to refresh
	 */
	void refresh(AccountIntegration accountIntegration);

	/**
	 * Returns the Integration logo for display when a user needs to view or add this integration
	 * @since 0.12.3
	 * @return Icon representation of assets stored in the src/assets of the project.
	 */
	Icon getIcon();

	/**
	 * Integration details provided to your rendering engine
	 * @param integration details of an Instance
	 * @return result of rendering a template
	 */
	HTMLResponse renderTemplate(AccountIntegration integration);

	/**
	 * Flags if the user can create this integration type
	 * @since 1.2.10
	 * @return a boolean indicating if the user can create this integration type
	 */
	default Boolean getCreatable(){
		return true;
	}

	/**
	 * Specify if this type of integration can be selectively associated with a cloud on the clouds advanced options
	 * @since 1.1.8
	 * @return the association type none,one, or many
	 */
	default AccountIntegrationType.AssociationType getCloudAssociationType() {
		return AccountIntegrationType.AssociationType.NONE;
	}

	/**
	 * Specify if this type of integration can be selectively associated with a cluster on the clusters advanced options
	 * @since 1.1.8
	 * @return the association type none,one, or many
	 */
	default AccountIntegrationType.AssociationType getClusterAssociationType() {
		return AccountIntegrationType.AssociationType.NONE;
	}

	/**
	 * Specify if this type of integration can be selectively associated with a group on the groups advanced options
	 * @since 1.1.8
	 * @return the association type none,one, or many
	 */
	default AccountIntegrationType.AssociationType getGroupAssociationType() {
		return AccountIntegrationType.AssociationType.NONE;
	}

	/**
	 *	Applying this Facet to an integration will allow it to subscribe to events and perform operations based on the event
	 * @since 1.1.8
	 * @author David Estes
	 * @deprecated Use {@link PluginProvider.EventSubscriberFacet} instead.
	 */
	@Deprecated(since = "1.2.6", forRemoval = true)
	public interface EventSubscriberFacet<E extends Event> extends PluginProvider.EventSubscriberFacet<E> {
		enum EventScope implements IEventScope {
			ALL,
			CLOUD,
			CLUSTER
		}
	}
}
