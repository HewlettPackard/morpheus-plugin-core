package com.morpheusdata.core.providers;

import com.morpheusdata.model.Account;
import com.morpheusdata.model.NetworkRouter;
import com.morpheusdata.model.User;
import com.morpheusdata.views.HTMLResponse;

/**
 * This provider will render additional tabs for Network Router details (NetworkRouter). This can be useful when building
 * a custom network integration provider that has remote accessible controls for additional information about a router or other network device.
 *
 * @author Jordon Saardchit
 * @since 1.3.0
 */
public interface NetworkRouterTabProvider extends UIExtensionProvider {
	/**
	 * NetworkRouter details provided to your rendering engine
	 * @param networkRouter details
	 * @return result of rendering a template
	 */
	HTMLResponse renderTemplate(NetworkRouter networkRouter);

	/**
	 * Provide logic when tab should be displayed. This logic is checked after permissions are validated.
	 *
	 * @param networkRouter Network Router details
	 * @param user current User details
	 * @param account Account details
	 * @return whether the tab should be displayed
	 */
	Boolean show(NetworkRouter networkRouter, User user, Account account);
}

