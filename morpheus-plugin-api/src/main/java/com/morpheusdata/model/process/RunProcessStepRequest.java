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
 * Request model for {@link com.morpheusdata.core.MorpheusProcessService#runProcessStep}.
 * Identifies which process and step to dispatch for execution.
 *
 * @since 1.4.0
 * @author Sean Ridgley
 */
public class RunProcessStepRequest {

	/**
	 * The process containing the step to run.
	 */
	public com.morpheusdata.model.Process process;

	/**
	 * The process event (step) to dispatch for execution.
	 */
	public ProcessEvent processEvent;

	public RunProcessStepRequest() {}

	public RunProcessStepRequest(com.morpheusdata.model.Process process, ProcessEvent processEvent) {
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
