package com.morpheusdata.core.process;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.ComputeServerGroup;
import com.morpheusdata.model.Instance;
import com.morpheusdata.model.Workload;

/**
 * The subject a process is associated with
 */
public class ProcessSubject {

	public long id;
	public SubjectType type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SubjectType getType() {
		return type;
	}

	public void setType(SubjectType type) {
		this.type = type;
	}

	/**
	 * The type the process is associated with
	 */
	public enum SubjectType {
		COMPUTE_SERVER,
		COMPUTE_SERVER_GROUP,
		INSTANCE,
		WORKLOAD,
	}

	/**
	 * Helper function to derive a ProcessSubject from a ComputeServer
	 *
	 * @param server The compute server group
	 * @return process subject
	 */
	static ProcessSubject fromComputeServer(ComputeServer server) {
		ProcessSubject subject = new ProcessSubject();
		subject.id = server.getId();
		subject.type = SubjectType.COMPUTE_SERVER;
		return subject;
	}

	/**
	 * Helper function to derive a ProcessSubject from a ComputeServerGroup 
	 * 
	 * @param computeServerGroup The compute server group
	 * @return process subject
	 */
	public static ProcessSubject fromComputeServerGroup(ComputeServerGroup computeServerGroup) {
		ProcessSubject subject = new ProcessSubject();
		subject.id = computeServerGroup.getId();
		subject.type = SubjectType.COMPUTE_SERVER_GROUP;
		return subject;
	}

	/**
	 * Helper function to derive a ProcessSubject from an Instance 
	 *
	 * @param instance the instance
	 * @return process subject
	 */
	public static ProcessSubject fromInstance(Instance instance) {
		ProcessSubject subject = new ProcessSubject();
		subject.id = instance.getId();
		subject.type = SubjectType.INSTANCE;
		return subject;
	}

	/**
	 * Helper function to derive a ProcessSubject from a Workload
	 * 
	 * @param workload the workload
	 * @return process subject
	 */
	public static ProcessSubject fromWorkload(Workload workload) {
		ProcessSubject subject = new ProcessSubject();
		subject.id = workload.getId();
		subject.type = SubjectType.WORKLOAD;
		return subject;
	}
}
