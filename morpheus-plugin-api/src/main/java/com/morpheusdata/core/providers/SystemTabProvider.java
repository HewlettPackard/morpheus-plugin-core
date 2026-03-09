package com.morpheusdata.core.providers;

import com.morpheusdata.model.Account;
import com.morpheusdata.model.User;
import com.morpheusdata.model.system.System;
import com.morpheusdata.views.HTMLResponse;
/**
 * Renders tabs within a System in Morpheus.
 *
 * Intended for displaying additional System-specific information. To keep UI responsive,
 * implementors should render a lightweight shell and load heavier content dynamically via
 * an endpoint (ajax/fetch) if needed.
 *
 * @since 1.4.0
 * @author Prathyusha Vemula
 * @see AbstractSystemTabProvider for a base implementation with Handlebars rendering support.
 */
public interface SystemTabProvider extends UIExtensionProvider {
	/**
	 * System details provided to your rendering engine
	 * @param system System details
	 * @return result of rendering a template
	 */

	/** stable id used by UI + endpoints */
	String getCode();

	/** label shown in the UI */
	String getName();

	/** sort order */
	default Integer getOrder() { return 0; }

	HTMLResponse renderTemplate(System system);

	/**
	 * Provide logic when tab should be displayed. This logic is checked after permissions are validated.
	 *
	 * @param system System details
	 * @param user current User details
	 * @param account Account details
	 * @return whether the tab should be displayed
	 */
	Boolean show(System system, User user, Account account);
}