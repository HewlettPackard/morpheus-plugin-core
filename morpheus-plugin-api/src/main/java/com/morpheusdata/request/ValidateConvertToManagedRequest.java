package com.morpheusdata.request;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.Instance;

import java.util.Map;

/**
 * Request model for {@link com.morpheusdata.core.providers.ProvisionProvider.ConvertToManagedFacet#validateConvertToManaged(ValidateConvertToManagedRequest)}
 */
public class ValidateConvertToManagedRequest {
	/** The persisted server — use for read-only context (e.g. {@code computeServerType}). */
	public ComputeServer server;

	/**
	 * The instance this server is being added to. Set only for the <em>add-node</em> (scale-instance)
	 * flow; {@code null} for the <em>convert-to-managed</em> flow.
	 * Plugins can use the presence of this field to distinguish between the two operations.
	 */
	public Instance instance;

	/**
	 * The ID of the {@code OsType} explicitly selected by the user in the convert-to-managed request
	 * (not yet persisted at validation time). Only populated for the <em>convert-to-managed</em> flow;
	 * {@code null} for the <em>add-node</em> flow.
	 */
	public Long osTypeId;

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

	public Long getOsTypeId() {
		return osTypeId;
	}

	public void setOsTypeId(Long osTypeId) {
		this.osTypeId = osTypeId;
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
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
