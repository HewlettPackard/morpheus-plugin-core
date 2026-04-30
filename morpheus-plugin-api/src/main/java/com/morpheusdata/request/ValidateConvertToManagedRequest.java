package com.morpheusdata.request;

import com.morpheusdata.model.ComputeServer;

import java.util.Map;

/**
 * Request model for {@link com.morpheusdata.core.providers.ProvisionProvider.ConvertToManagedFacet#validateConvertToManaged(ValidateConvertToManagedRequest)}
 */
public class ValidateConvertToManagedRequest {
	/** The persisted server — use for read-only context (e.g. {@code preProvisioned}, {@code computeServerType}). */
	public ComputeServer server;

	/**
	 * The submitted parameters to validate (e.g. {@code sshHost}, {@code sshUsername}, {@code sshPassword},
	 * {@code sshKeyPairId}). These may differ from the persisted server values when the caller is supplying
	 * credentials as part of the request. Validate against these values, falling back to the server fields
	 * when a key is absent.
	 */
	public Map<String, Object> opts;

	public ComputeServer getServer() {
		return server;
	}

	public void setServer(ComputeServer server) {
		this.server = server;
	}

	public Map<String, Object> getOpts() {
		return opts;
	}

	public void setOpts(Map<String, Object> opts) {
		this.opts = opts;
	}
}
