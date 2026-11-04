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

package com.morpheusdata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The result and progress of a storage day-two operation, returned by every operation facet, such as
 * {@link com.morpheusdata.core.providers.StorageProviderNodeRole} and
 * {@link com.morpheusdata.core.providers.StorageProviderDriveAddition}.
 * <p>
 * A dry run returns one {@link OpEntry} per step in the order taken, each with its own messages and a
 * {@link StepState#PASSED} or {@link StepState#FAILED} verdict. The same list carries progress during
 * the real run. Morpheus keeps the operation and polls the facet's {@code refresh} method with it;
 * {@link #getCode() code} names the operation from then on and keys every later call.
 *
 * @since 1.6.0
 * @see com.morpheusdata.request.StorageOperationRequest
 */
public class StorageOperation {

	protected String code;
	protected String name;
	protected String refType;
	protected Long refId;

	protected OpState state;
	protected String statusMessage;
	protected Integer percentComplete;

	protected Boolean overridable;
	protected Long retryAfterSeconds;

	protected Date startedAt;
	protected Date completedAt;

	protected List<OpEntry> opEntry = new ArrayList<>();

	/**
	 * Where the operation as a whole stands.
	 */
	public enum OpState {
		READY,
		PENDING,
		IN_PROGRESS,
		FAILED,
		COMPLETED,
		UNKNOWN
	}

	/**
	 * The verdict on a single step.
	 */
	public enum StepState {
		PASSED,
		FAILED
	}

	/**
	 * A single step of the operation, in the operation's own vocabulary
	 * (for example {@code claiming} or {@code rebalancing}).
	 */
	public static class OpEntry {

		protected String name;
		protected List<String> message = new ArrayList<>();
		protected StepState state;

		/**
		 * @return the step, in the operation's own vocabulary
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the step, in the operation's own vocabulary
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return what the step reports
		 */
		public List<String> getMessage() {
			return message;
		}

		/**
		 * @param message what the step reports
		 */
		public void setMessage(List<String> message) {
			this.message = message;
		}

		/**
		 * @return {@link StepState#PASSED} or {@link StepState#FAILED}
		 */
		public StepState getState() {
			return state;
		}

		/**
		 * @param state {@link StepState#PASSED} or {@link StepState#FAILED}
		 */
		public void setState(StepState state) {
			this.state = state;
		}
	}

	/**
	 * @return the code that names the operation from here on and keys every later call
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code that names the operation from here on and keys every later call
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the display name of the operation
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the display name of the operation
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type of the object the operation is for
	 */
	public String getRefType() {
		return refType;
	}

	/**
	 * @param refType the type of the object the operation is for
	 */
	public void setRefType(String refType) {
		this.refType = refType;
	}

	/**
	 * @return the id of the object the operation is for
	 */
	public Long getRefId() {
		return refId;
	}

	/**
	 * @param refId the id of the object the operation is for
	 */
	public void setRefId(Long refId) {
		this.refId = refId;
	}

	/**
	 * @return where the operation as a whole stands
	 */
	public OpState getState() {
		return state;
	}

	/**
	 * @param state where the operation as a whole stands
	 */
	public void setState(OpState state) {
		this.state = state;
	}

	/**
	 * @return the latest status message of the operation
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage the latest status message of the operation
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/**
	 * @return completion percentage, 0 to 100
	 */
	public Integer getPercentComplete() {
		return percentComplete;
	}

	/**
	 * @param percentComplete completion percentage, 0 to 100
	 */
	public void setPercentComplete(Integer percentComplete) {
		this.percentComplete = percentComplete;
	}

	/**
	 * @return whether a refusal may be overridden by the operator
	 */
	public Boolean getOverridable() {
		return overridable;
	}

	/**
	 * @param overridable whether a refusal may be overridden by the operator
	 */
	public void setOverridable(Boolean overridable) {
		this.overridable = overridable;
	}

	/**
	 * @return seconds until the next poll is expected to be useful
	 */
	public Long getRetryAfterSeconds() {
		return retryAfterSeconds;
	}

	/**
	 * @param retryAfterSeconds seconds until the next poll is expected to be useful
	 */
	public void setRetryAfterSeconds(Long retryAfterSeconds) {
		this.retryAfterSeconds = retryAfterSeconds;
	}

	/**
	 * @return when the operation started
	 */
	public Date getStartedAt() {
		return startedAt;
	}

	/**
	 * @param startedAt when the operation started
	 */
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	/**
	 * @return when the operation completed
	 */
	public Date getCompletedAt() {
		return completedAt;
	}

	/**
	 * @param completedAt when the operation completed
	 */
	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	/**
	 * @return the steps, in the order taken
	 */
	public List<OpEntry> getOpEntry() {
		return opEntry;
	}

	/**
	 * @param opEntry the steps, in the order taken
	 */
	public void setOpEntry(List<OpEntry> opEntry) {
		this.opEntry = opEntry;
	}
}
