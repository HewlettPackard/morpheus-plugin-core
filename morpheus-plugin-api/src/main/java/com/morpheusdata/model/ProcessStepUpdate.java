package com.morpheusdata.model;

/**
 * Update model for a Process Step (AKA ProcessEvent)
 */
public class ProcessStepUpdate {

	/**
	 * The status for the step update
	 * <p>
	 * This should not be set to {@link com.morpheusdata.core.MorpheusProcessService#STATUS_COMPLETE} or
	 * {@link com.morpheusdata.core.MorpheusProcessService#STATUS_FAILED} during an update, as those are final states
	 * that should only be set when ending a process or a process step.
	 * <p>
	 * This can be found in the history detail view of the step.
	 */
	public String status;

	/**
	 * The output for the step update
	 * <p>
	 * If the output is set to append with update call (see {@link com.morpheusdata.core.MorpheusProcessService#updateProcessStep(Process, ProcessStepType, ProcessStepUpdate, Boolean)})
	 * this output is appended to the existing. Otherwise, it'll replace it entirely.
	 * <p>
	 * A preview of this can be found in the history list and the full output can be found in the history detail
	 * view of the step.
	 */
	public String output;

	/**
	 * The message for the step update
	 * <p>
	 * If the message is set to append with update call (see {@link com.morpheusdata.core.MorpheusProcessService#updateProcessStep(Process, ProcessStepType, ProcessStepUpdate, Boolean)})
	 * this message is appended to the existing. Otherwise, it'll replace it entirely.
	 * <p>
	 * This can be found in the history detail view of the step. It has its own line in the detail view and is also
	 * prepended to the top of the output text area. This isn't shown if the status is 'failed', in that case the
	 * error is displayed instead
	 */
	public String message;

	/**
	 * The error for the step update
	 * <p>
	 * If the error is set to append with update call (see {@link com.morpheusdata.core.MorpheusProcessService#updateProcessStep(Process, ProcessStepType, ProcessStepUpdate, Boolean)})
	 * this error is appended to the existing. Otherwise, it'll replace it entirely.
	 * <p>
	 * This can be found in the history detail view of the step. It has its own line in the detail view and is also
	 * prepended to the top of the output text area. This only shown if the status is 'failed', in that case the
	 * error is displayed in place of the message field.
	 */
	public String error;

	/**
	 * An override for username. If unspecified, the username associated with the process is used
	 */
	public String username;

	/**
	 * An override for userId. If unspecified, the userId associated with the process is used
	 */
	public String userId;

	/**
	 * An override for userDisplayName. If unspecified, the userDisplayName associated with the process is used
	 */
	public String userDisplayName;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}
}
