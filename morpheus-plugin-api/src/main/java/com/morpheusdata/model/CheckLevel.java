package com.morpheusdata.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents the level of a drift check, including targeted rules and level type.
 */
public class CheckLevel {
    protected String code;              // unique code if needed
    protected String refType;           // associated type of object this drift check is for
    protected Long refId;               // associated id of the object this drift check is for
    protected CheckLevelEnum checkLevel; // level of the drift check (e.g., system, update)
    protected List<String> driftRules = new ArrayList<>();
    protected List<String> excludeDriftRules = new ArrayList<>(); // use this if some rules need to excluded from execution when checkLevel=SYSTEM or UPDATE

    /**
     * Enum for levels sent during input.
     * ALL = All system config
     * TARGETED = specific rules given in driftRules field.
     */
    public enum CheckLevelEnum { SYSTEM, UPDATE } 

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getRefType() { return refType; }
    public void setRefType(String refType) { this.refType = refType; }

    public Long getRefId() { return refId; }
    public void setRefId(Long refId) { this.refId = refId; }

    public CheckLevelEnum getCheckLevel() { return checkLevel; }
    public void setCheckLevel(CheckLevelEnum checkLevel) { this.checkLevel = checkLevel; }

    public List<String> getDriftRules() { return driftRules; }
    public void setDriftRules(List<String> driftRules) { this.driftRules = driftRules; }

    public List<String> getExcludeDriftRules() { return excludeDriftRules; }
    public void setExcludeDriftRules(List<String> excludeDriftRules) { this.excludeDriftRules = excludeDriftRules; }
} 