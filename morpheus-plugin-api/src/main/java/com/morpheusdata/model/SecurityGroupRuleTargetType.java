package com.morpheusdata.model;

public class SecurityGroupRuleTargetType extends MorpheusModel{
	protected String code;
	protected String name;
	protected String value;
	protected String description;
	protected String type; //source or destination
	protected Integer displayOrder;
	protected Boolean internal = false;

	public SecurityGroupRuleTargetType() { super();}

	public SecurityGroupRuleTargetType(String code, String name, String value, String description, String type, Integer displayOrder) {
		this.code = code;
		this.name = name;
		this.value = value;
		this.description = description;
		this.type = type;
		this.displayOrder = displayOrder;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Boolean getInternal() {
		return internal;
	}

	public void setInternal(Boolean internal) {
		this.internal = internal;
	}

	public static final SecurityGroupRuleTargetType SOURCE_NETWORK = new SecurityGroupRuleTargetType(
		"default.securityGroup.ruleType.source.network", "Network", "cidr", "Apply rule to a specific network", "source", 0
	);

	public static final SecurityGroupRuleTargetType SOURCE_GROUP = new SecurityGroupRuleTargetType(
		"default.securityGroup.ruleType.source.group", "Security Group", "group", "Apply rule to a specific security group", "source", 1
	);

	public static final SecurityGroupRuleTargetType SOURCE_TIER = new SecurityGroupRuleTargetType(
		"default.securityGroup.ruleType.source.tier", "Tier", "tier", "Apply rule to a tier", "source", 2
	);

	public static final SecurityGroupRuleTargetType SOURCE_ALL = new SecurityGroupRuleTargetType(
		"default.securityGroup.ruleType.source.all", "All", "all", "Apply rule to aall", "source", 3
	);

	public static final SecurityGroupRuleTargetType DESTINATION_NETWORK = new SecurityGroupRuleTargetType(
		"default.securityGroup.ruleType.destination.network", "Network", "cidr", "Apply rule to a specific network", "destination", 0
	);

	public static final SecurityGroupRuleTargetType DESTINATION_GROUP = new SecurityGroupRuleTargetType(
		"default.securityGroup.ruleType.destination.group", "Security Group", "group", "Apply rule to a specific security group", "destination", 1
	);

	public static final SecurityGroupRuleTargetType DESTINATION_TIER = new SecurityGroupRuleTargetType(
		"default.securityGroup.ruleType.destination.tier", "Tier", "tier", "Apply rule to a specific tier", "destination", 2
	);

	public static final SecurityGroupRuleTargetType DESTINATION_INSTANCE = new SecurityGroupRuleTargetType(
		"default.securityGroup.ruleType.destination.instance", "Instance", "instance", "Apply rule to a specific instance", "destination", 3
	);
}
