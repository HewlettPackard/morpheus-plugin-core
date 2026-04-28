/*
 *  Copyright 2026 Morpheus Data, LLC.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morpheusdata.model.process;

import com.morpheusdata.model.ProcessEvent;

/**
 * Request model for {@link com.morpheusdata.core.MorpheusProcessService#insertProcessStep}.
 * Wraps a {@link com.morpheusdata.model.Process} and a {@link ProcessEvent} to insert as a new step.
 *
 * @since 1.4.0
 * @author Sean Ridgley
 */
public class InsertProcessStepRequest {

	/**
	 * The process to insert the step into.
	 */
	public com.morpheusdata.model.Process process;

	/**
	 * The process event defining the step to insert. Set {@code jobName} to the
	 * {@link com.morpheusdata.core.providers.ProcessJobProvider#getCode()} and {@code jobConfig}
	 * with any initial inputs.
	 */
	public ProcessEvent processEvent;

	public InsertProcessStepRequest() {}

	public InsertProcessStepRequest(com.morpheusdata.model.Process process, ProcessEvent processEvent) {
		this.process = process;
		this.processEvent = processEvent;
	}

	public com.morpheusdata.model.Process getProcess() {
		return process;
	}

	public void setProcess(com.morpheusdata.model.Process process) {
		this.process = process;
	}

	public ProcessEvent getProcessEvent() {
		return processEvent;
	}

	public void setProcessEvent(ProcessEvent processEvent) {
		this.processEvent = processEvent;
	}
}
