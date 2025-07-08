package com.morpheusdata.core.process;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.ComputeServerGroup;
import com.morpheusdata.model.Instance;
import com.morpheusdata.model.Workload;

/**
 * A reference to the entity a process is associated with
 */
public class ProcessReference {

	public long id;
	public ReferenceType type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ReferenceType getType() {
		return type;
	}

	public void setType(ReferenceType type) {
		this.type = type;
	}

	/**
	 * The type the process is associated with
	 */
	public enum ReferenceType {
		COMPUTE_SERVER,
		COMPUTE_SERVER_GROUP,
		INSTANCE,
		WORKLOAD,
	}

	/**
	 * Helper function to derive a ProcessReference from a ComputeServer
	 *
	 * @param server The compute server group
	 * @return process reference
	 */
	static ProcessReference fromComputeServer(ComputeServer server) {
		ProcessReference subject = new ProcessReference();
		subject.id = server.getId();
		subject.type = ReferenceType.COMPUTE_SERVER;
		return subject;
	}

	/**
	 * Helper function to derive a ProcessReference from a ComputeServerGroup
	 * 
	 * @param computeServerGroup The compute server group
	 * @return process reference
	 */
	public static ProcessReference fromComputeServerGroup(ComputeServerGroup computeServerGroup) {
		ProcessReference subject = new ProcessReference();
		subject.id = computeServerGroup.getId();
		subject.type = ReferenceType.COMPUTE_SERVER_GROUP;
		return subject;
	}

	/**
	 * Helper function to derive a ProcessReference from an Instance
	 *
	 * @param instance the instance
	 * @return process reference
	 */
	public static ProcessReference fromInstance(Instance instance) {
		ProcessReference subject = new ProcessReference();
		subject.id = instance.getId();
		subject.type = ReferenceType.INSTANCE;
		return subject;
	}

	/**
	 * Helper function to derive a ProcessReference from a Workload
	 * 
	 * @param workload the workload
	 * @return process reference
	 */
	public static ProcessReference fromWorkload(Workload workload) {
		ProcessReference subject = new ProcessReference();
		subject.id = workload.getId();
		subject.type = ReferenceType.WORKLOAD;
		return subject;
	}
}
