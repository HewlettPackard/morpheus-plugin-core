package com.morpheusdata.model.system;

import java.util.HashMap;
import java.util.Map;

public class SystemRequest {
	protected Process process;
	protected Map configOptions = new HashMap();

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public Map getConfigOptions() {
		return configOptions;
	}

	public void setConfigOptions(Map configOptions) {
		this.configOptions = configOptions;
	}

	public Object getConfigOption(String key) {
		return configOptions.get(key);
	}

	public void addConfigOption(String key, Object value) {
		configOptions.put(key, value);
	}
}
