package com.morpheusdata.request;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.Instance;
import com.morpheusdata.model.Workload;

import java.util.Collection;
import java.util.Map;

public class AfterConvertToManagedRequest {
	public Instance instance;
	public Collection<Workload> workloads;
	public ComputeServer server;
	public Map<String, Object> opts;

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public Collection<Workload> getWorkloads() {
		return workloads;
	}

	public void setWorkloads(Collection<Workload> workloads) {
		this.workloads = workloads;
	}

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

