package com.morpheusdata.model.system;

import com.morpheusdata.model.MorpheusModel;
import com.morpheusdata.model.OptionType;

import java.util.ArrayList;
import java.util.List;

public class SystemType extends MorpheusModel {
	protected String name;
	protected String code;
	protected String description;
	protected String category;
	protected Boolean active = true;
	protected List<OptionType> optionTypes = new ArrayList<>();
	protected List<SystemTypeLayout> layouts = new ArrayList<>();
	protected Boolean creatable = true;
	protected Boolean editable = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		markDirty("name", name);
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		markDirty("code", code);
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		markDirty("description", description);
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		markDirty("category", category);
		this.category = category;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		markDirty("active", active);
		this.active = active;
	}

	public List<OptionType> getOptionTypes() {
		return optionTypes;
	}

	public void setOptionTypes(List<OptionType> optionTypes) {
		markDirty("optionTypes", optionTypes);
		this.optionTypes = optionTypes;
	}

	public List<SystemTypeLayout> getLayouts() {
		return layouts;
	}

	public void setLayouts(List<SystemTypeLayout> layouts) {
		markDirty("layouts", layouts);
		this.layouts = layouts;
	}

	public Boolean getCreatable() {
		return creatable;
	}

	public void setCreatable(Boolean creatable) {
		markDirty("creatable", creatable);
		this.creatable = creatable;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		markDirty("editable", editable);
		this.editable = editable;
	}
}
