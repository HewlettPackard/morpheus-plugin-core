package com.morpheusdata.request;

import com.morpheusdata.model.Process;

/**
 * Class to carry parameters associated with a Package Upgrade
 */
public class PackageUpgradeRequest {
	/**
	 * The process associated with the upgrade. This can be used via the {@link com.morpheusdata.core.MorpheusProcessService}
	 * to add steps for history.
	 */
	public Process process;

	/**
	 * The new version of the package to upgrade
	 */
	public String newVersion;

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public String getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}
}
