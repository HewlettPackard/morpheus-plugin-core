package com.morpheusdata.response;

import com.morpheusdata.model.ComputeServer;

import java.util.Map;

public class BeforeConvertToManagedResponse {
	public ComputeServer server;
	public Map<String, Object> opts;

	public Map<String, Object> getOpts() {
		return opts;
	}

	public void setOpts(Map<String, Object> opts) {
		this.opts = opts;
	}

	public ComputeServer getServer() {
		return server;
	}

	public void setServer(ComputeServer server) {
		this.server = server;
	}
}
