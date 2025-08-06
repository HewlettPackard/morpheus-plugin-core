package com.morpheusdata.model;

public class ComputeType extends MorpheusModel{

	protected String code;
	protected String name;
	protected String description;
	protected String hostType;
	protected String isPlugin;
	protected String isEmbedded;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	public String getHostType() {
		return hostType;
	}

	public void setHostType(String hostType) {
		this.hostType = hostType;
		markDirty("hostType", hostType);
	}

	public String getIsPlugin() {
		return isPlugin;
	}

	public void setIsPlugin(String isPlugin) {
		this.isPlugin = isPlugin;
		markDirty("isPlugin", isPlugin);
	}

	public String getIsEmbedded() {
		return isEmbedded;
	}

	public void setIsEmbedded(String isEmbedded) {
		this.isEmbedded = isEmbedded;
		markDirty("isEmbedded", isEmbedded);
	}
}
