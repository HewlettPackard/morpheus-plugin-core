package com.morpheusdata.model;

import java.util.HashMap;
import java.util.Map;

public class FormErrors {
	private Map<String, String> errors = new HashMap<>();

	public void setGeneralError(String message) {
		errors.put("general", message);
	}

	public void addError(String field, String message) {
		errors.put(field, message);
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
