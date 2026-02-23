package com.morpheusdata.model;

public class NetworkFloatingIpPoolType extends MorpheusModel {
	protected String code;
	protected String name;
	protected Boolean isPlugin;
	protected Boolean isEmbedded;
	protected Boolean creatable;

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

	public Boolean getIsPlugin() {
		return isPlugin;
	}

	public void setIsPlugin(Boolean isPlugin) {
		this.isPlugin = isPlugin;
		markDirty("isPlugin", isPlugin);
	}

	public Boolean getIsEmbedded() {
		return isEmbedded;
	}

	public void setIsEmbedded(Boolean isEmbedded) {
		this.isEmbedded = isEmbedded;
		markDirty("isEmbedded", isEmbedded);
	}

	public Boolean getCreatable() {
		return creatable;
	}

	public void setCreatable(Boolean creatable) {
		this.creatable = creatable;
		markDirty("creatable", creatable);
	}
}
