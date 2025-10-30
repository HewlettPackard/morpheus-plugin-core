package com.morpheusdata.response;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.Instance;
import com.morpheusdata.model.Workload;

import java.util.Collection;

public class AfterConvertToManagedResponse {
	public Instance instance;
	public Collection<Workload> workloads;
	public ComputeServer server;

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public Collection<Workload> getWorkloads() {
		return workloads;
	}

	public void setWorkload(Collection<Workload> workloads) {
		this.workloads = workloads;
	}

	public ComputeServer getServer() {
		return server;
	}

	public void setServer(ComputeServer server) {
		this.server = server;
	}
}
