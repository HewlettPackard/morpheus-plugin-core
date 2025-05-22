package com.morpheusdata.request;

import com.morpheusdata.model.Process;

/**
 * Class to carry parameters associated with a Package Install
 */
public class PackageInstallRequest {
	/**
	 * The process associated with the install. This can be used via the {@link com.morpheusdata.core.MorpheusProcessService}
	 * to add steps for history.
	 */
	public Process process;

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
}
