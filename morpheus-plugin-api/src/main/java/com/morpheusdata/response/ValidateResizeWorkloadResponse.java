package com.morpheusdata.response;

import com.morpheusdata.model.Instance;
import com.morpheusdata.model.Workload;
import com.morpheusdata.request.ResizeRequest;

import java.util.Map;

/**
 * Response model for {@link com.morpheusdata.core.providers.WorkloadProvisionProvider.ResizeFacet#validateResizeWorkload(Instance, Workload, ResizeRequest, Map)}
 */
public class ValidateResizeWorkloadResponse {
	public Boolean allowed;
	public Boolean hotResize;

	/**
	 * @return true if we're allowed to resize, false otherwise
	 */
	public Boolean getAllowed() {
		return allowed;
	}

	/**
	 * @param allowed true if we're allowed to resize, false otherwise
	 */
	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}

	/**
	 * @return true if resizing can be done without a restart, false if a restart is required
	 */
	public Boolean getHotResize() {
		return hotResize;
	}

	/**
	 * Flags if the resize requires a restart to take effect. The workload being operated on will be
	 * restarted if hotResize = false.
	 * @param hotResize true if resizing can be done without a restart, false if a restart is required
	 */
	public void setHotResize(Boolean hotResize) {
		this.hotResize = hotResize;
	}
}
