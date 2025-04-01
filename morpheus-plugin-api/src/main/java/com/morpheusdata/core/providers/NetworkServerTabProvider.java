package com.morpheusdata.core.providers;

import com.morpheusdata.model.Account;
import com.morpheusdata.model.NetworkServer;
import com.morpheusdata.model.User;
import com.morpheusdata.views.HTMLResponse;

/**
 * This provider will render additional tabs for Network Integration details (NetworkServer).  This can be useful when building
 * a custom network integration provider that has remote accessible controls for additional information (a switch, router, or other device).
 *
 * @author Jordon Saardchit
 * @since 1.2.5
 */
public interface NetworkServerTabProvider extends UIExtensionProvider {
	/**
	 * NetworkServer details provided to your rendering engine
	 * @param networkServer details
	 * @return result of rendering a template
	 */
	HTMLResponse renderTemplate(NetworkServer networkServer);

	/**
	 * Provide logic when tab should be displayed. This logic is checked after permissions are validated.
	 *
	 * @param networkServer Network details
	 * @param user current User details
	 * @param account Account details
	 * @return whether the tab should be displayed
	 */
	Boolean show(NetworkServer networkServer, User user, Account account);
}
