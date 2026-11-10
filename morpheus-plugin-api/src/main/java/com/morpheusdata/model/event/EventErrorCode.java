/*
 * Copyright 2026 Morpheus Data, LLC.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import com.morpheusdata.response.ServiceResponse;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Machine-readable {@link ServiceResponse#getErrorCode()} values and status codes.
 * Expected failures use unsuccessful responses, never success-shaped empty data.
 * Adapters must preserve errorCode/statusCode explicitly; legacy ServiceResponse.toMap() omits them.
 * Unknown future codes remain available as the original response string.
 * @since 1.7.0
 */
public enum EventErrorCode {
	INVALID_REQUEST(400),
	FORBIDDEN(403),
	SUBSCRIPTION_NOT_FOUND(404),
	STALE_VERSION(409),
	QUEUE_GONE(410),
	DEPENDENCY_UNAVAILABLE(503);

	/** Recovery value in the errors map for a stale read or ACK, after owner authorization. */
	public static final String LAST_ACCEPTED_VERSION = "lastAcceptedVersion";
	private final int statusCode;

	EventErrorCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static Optional<EventErrorCode> fromCode(String code) {
		return Arrays.stream(values()).filter(value -> value.name().equals(code)).findFirst();
	}

	/** @return an explicit failure with no operation data */
	public <T> ServiceResponse<T> failure(String message) {
		ServiceResponse<T> response = new ServiceResponse<>(false, message, null, null);
		response.setErrorCode(name());
		response.setStatusCode(Integer.toString(statusCode));
		return response;
	}

	/** @return a 409 failure carrying accepted progress, not the rejected/pending batch token */
	public static <T> ServiceResponse<T> staleVersion(String message, String lastAcceptedVersion) {
		ServiceResponse<T> response = STALE_VERSION.failure(message);
		LinkedHashMap<String, String> errors = new LinkedHashMap<>();
		errors.put(LAST_ACCEPTED_VERSION, Objects.requireNonNull(lastAcceptedVersion, "lastAcceptedVersion"));
		response.setErrors(errors);
		return response;
	}
}
