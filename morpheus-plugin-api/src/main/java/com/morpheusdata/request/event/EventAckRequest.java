/*
 * Copyright 2026 Morpheus Data, LLC.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.request.event;

import java.io.Serializable;

/** ACK the entire nonempty batch after processing; no individual delivery tags are exposed.
 * @since 1.7.0
 */
public class EventAckRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Required opaque batch version, or the last accepted version for an idempotent retry. */
	public String version;
}
