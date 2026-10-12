package com.morpheusdata.model;

/**
 * The VLAN mode of a {@link NetworkSwitchPort}.
 */
public enum NetworkSwitchPortType {
	/** Carries a single untagged VLAN. */
	ACCESS,
	/** Carries one untagged (native) VLAN and any number of tagged VLANs. */
	HYBRID,
	/** Carries tagged VLANs. */
	TRUNK
}
