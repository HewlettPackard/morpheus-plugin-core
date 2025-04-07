package com.morpheusdata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link com.morpheusdata.core.providers.NetworkProvider} can support the concept of network switches.  This class is
 * a configuration object for what these switches can support and/or how they are created.
 * @since 1.2.6
 */
public class NetworkSwitchType extends MorpheusModel {
	protected String name;
	protected String code;
	protected String category;
	protected String description;
	protected String externalType;
	protected Boolean creatable = false;
	protected Boolean nameEditable = false;
	protected Boolean deletable = false;
	private List<OptionType> optionTypes = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", this.name);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", this.code);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
		markDirty("category", this.category);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", this.description);
	}

	public String getExternalType() {
		return externalType;
	}

	public void setExternalType(String externalType) {
		this.externalType = externalType;
		markDirty("externalType", this.externalType);
	}

	public Boolean getCreatable() {
		return creatable;
	}

	public void setCreatable(Boolean creatable) {
		this.creatable = creatable;
		markDirty("creatable", this.creatable);
	}

	public Boolean getNameEditable() {
		return nameEditable;
	}

	public void setNameEditable(Boolean nameEditable) {
		this.nameEditable = nameEditable;
		markDirty("nameEditable", this.nameEditable);
	}

	public Boolean getDeletable() {
		return deletable;
	}

	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
		markDirty("deletable", this.deletable);
	}

	public List<OptionType> getOptionTypes() {
		return optionTypes;
	}

	public void setOptionTypes(List<OptionType> optionTypes) {
		this.optionTypes = optionTypes;
		markDirty("optionTypes", this.optionTypes);
	}
}
