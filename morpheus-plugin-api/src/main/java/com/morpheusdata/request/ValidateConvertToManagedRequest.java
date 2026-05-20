package com.morpheusdata.request;

import com.morpheusdata.model.ComputeServer;

import java.util.Map;

/**
 * Request model for {@link com.morpheusdata.core.providers.ProvisionProvider.ConvertToManagedFacet#validateConvertToManaged(ValidateConvertToManagedRequest)}
 */
public class ValidateConvertToManagedRequest {
	/** The persisted server — use for read-only context (e.g. {@code preProvisioned}, {@code computeServerType}). */
	public ComputeServer server;

	public Map<String, Object> opts;
	public String sshHost;
	public String sshUsername;
	public String sshPassword;

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

	public String getSshHost() {
		return sshHost;
	}

	public void setSshHost(String sshHost) {
		this.sshHost = sshHost;
	}

	public String getSshUsername() {
		return sshUsername;
	}

	public void setSshUsername(String sshUsername) {
		this.sshUsername = sshUsername;
	}

	public String getSshPassword() {
		return sshPassword;
	}

	public void setSshPassword(String sshPassword) {
		this.sshPassword = sshPassword;
	}
}
