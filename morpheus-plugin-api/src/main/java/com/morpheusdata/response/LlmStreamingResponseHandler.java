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

package com.morpheusdata.response;

import com.morpheusdata.model.llm.LlmChatResponse;

/**
 * Callback handler for streamed LLM responses.
 */
public interface LlmStreamingResponseHandler {
	/**
	 * Called for partial text output from a streaming response.
	 * @param partialText partial output text
	 */
	default void onPartialResponse(String partialText) {
	}

	/**
	 * Called when a streaming response completes successfully.
	 * @param response completed chat response metadata and content
	 */
	default void onCompleteResponse(LlmChatResponse response) {
	}

	/**
	 * Called when the streaming response fails.
	 * @param error failure exception
	 */
	default void onError(Throwable error) {
	}
}
