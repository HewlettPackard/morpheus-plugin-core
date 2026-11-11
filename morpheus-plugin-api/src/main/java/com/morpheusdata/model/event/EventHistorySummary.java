/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;

/**
 * Fixed-size appliance-wide history observations. Loss times are when loss was observed,
 * not missing-event dates or gap interval boundaries. Null times mean no recorded observation.
 * Zero observed loss or a recent indexing success never proves complete history.
 * @since 1.7.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventHistorySummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date lastSuccessfulIndexAt;
	public Long observedLossCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date firstObservedLossAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	public Date lastObservedLossAt;
}
