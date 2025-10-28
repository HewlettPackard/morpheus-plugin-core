package com.morpheusdata.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents the level of a drift check, including targeted rules and level type.
 */
public class CheckLevel {
    protected String code;              // unique code if needed
    protected String refType;           // associated type of object this drift check is for
    protected String refId;             // associated id of the object this drift check is for
    protected CheckLevelEnum checkLevel; // level of the drift check (e.g., all, update)
    protected List<String> driftRules = new ArrayList<>(); // Mandatory when checkLevel = TARGETED & ignored for other levels
    protected List<String> excludeDriftRules = new ArrayList<>(); // use this if some rules need to excluded from execution when checkLevel=ALL or UPDATE

    /**
     * Enum for levels sent during input.
     * ALL = All system config
     * TARGETED = specific rules given in driftRules field.
     */
    public enum CheckLevelEnum { ALL, TARGETED, UPDATE }

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getRefType() { return refType; }
    public void setRefType(String refType) { this.refType = refType; }

    public String getRefId() { return refId; }
    public void setRefId(String refId) { this.refId = refId; }

    public CheckLevelEnum getCheckLevel() { return checkLevel; }
    public void setCheckLevel(CheckLevelEnum checkLevel) { this.checkLevel = checkLevel; }

    public List<String> getDriftRules() { return driftRules; }
    public void setDriftRules(List<String> driftRules) { this.driftRules = driftRules; }

    public List<String> getExcludeDriftRules() { return excludeDriftRules; }
    public void setExcludeDriftRules(List<String> excludeDriftRules) { this.excludeDriftRules = excludeDriftRules; }
}