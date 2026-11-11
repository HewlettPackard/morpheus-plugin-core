/*
 * Copyright 2026 HPE Development Company, L.P.
 * Licensed under the PLUGIN CORE SOURCE LICENSE.
 * https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 */
package com.morpheusdata.model.event;

import java.util.Arrays;
import java.util.Optional;

/**
 * Initial HVM catalogue. Use {@link #getCode()} in filters; never deserialize an envelope
 * into this enum, since future event codes must remain readable.
 * @since 1.7.0
 */
public enum HvmEventType {
	VM_CREATED("vm.created"),
	VM_DELETED("vm.deleted"),
	VM_REGISTERED("vm.registered"),
	VM_REMOVED("vm.removed"),
	VM_CLONED("vm.cloned"),
	VM_RECONFIGURE_STARTED("vm.reconfigureStarted"),
	VM_RECONFIGURED("vm.reconfigured"),
	VM_MIGRATED("vm.migrated"),
	VM_FAILOVER("vm.failover"),
	VM_STARTED("vm.started"),
	VM_STOPPED("vm.stopped"),
	CLUSTER_ADD_WORKER("cluster.addWorker"),
	CLUSTER_REMOVE_WORKER("cluster.removeWorker");

	private final String code;

	HvmEventType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	/** @return the known catalogue entry, or empty without discarding the original code */
	public static Optional<HvmEventType> fromCode(String code) {
		return Arrays.stream(values()).filter(type -> type.code.equals(code)).findFirst();
	}
}
