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

import java.util.Map;

/**
 * Response model returned from {@link com.morpheusdata.core.providers.ProcessJobProvider#execute}.
 * Contains optional outputs to forward to the next step in the process chain.
 *
 * @since 1.4.0
 * @author Sean Ridgley
 */
public class ProcessJobExecutionResponse {

	/**
	 * Optional inputs for the next step. These are merged into the next event's {@code jobMsgConfig}
	 * before it is dispatched.
	 */
	public Map<String, Object> nextOpts;

	public ProcessJobExecutionResponse() {}

	public ProcessJobExecutionResponse(Map<String, Object> nextOpts) {
		this.nextOpts = nextOpts;
	}

	public Map<String, Object> getNextOpts() {
		return nextOpts;
	}

	public void setNextOpts(Map<String, Object> nextOpts) {
		this.nextOpts = nextOpts;
	}
}
