package com.morpheusdata.core.process;

import com.morpheusdata.model.ProcessStepType;
import com.morpheusdata.model.User;

public class ProcessStartRequest {
	/**
	 * The ProcessStepType for the process
	 */
	public ProcessStepType stepType;

	/**
	 * The User that starts the process (optional)
	 */
	public User user;

	/**
	 * A category to associate with this Process. The category is used to provide estimated
	 * durations for a Process based on previous run of processes with this same category.
	 */
	public String timerCategory;

	/**
	 * An event title to associate with this Process (optional)
	 */
	public String eventTitle;
}
