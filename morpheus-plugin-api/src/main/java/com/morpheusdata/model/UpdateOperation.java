package com.morpheusdata.model;

import com.morpheusdata.model.projection.UpdateIdentityProjection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an update operation with progress tracking and status management.
 */
public class UpdateOperation extends UpdateIdentityProjection {
    protected String code;                  // unique code if needed
    protected String version;               // version of the update
    protected String name;                  // update name

    protected String refType;               // associated type of object this update is for
    protected Long refId;                   // associated id of the object this update is for

    protected OpState state;                // status of the update operation (e.g., pending, in-progress, failed, completed)
    protected String statusMessage;         // final status message of the operation
    protected int percentComplete = 0;      // completion percentage

    protected Date startedAt;            // start timestamp
    protected Date completedAt;          // completion timestamp

    // List of operation entries (flattened structure)
    protected List<OpEntry> opEntry = new ArrayList<>();

    /**
     * Enum representing the statuses of an update operation.
     */
    public enum OpState {
        READY,
        PENDING,
        IN_PROGRESS,
        FAILED,
        COMPLETED,
        UNKNOWN
    }

    /**
     * Enum representing the state of the step being performed.
     */
    public enum StepState {
        PASSED,
        FAILED
    }

    /**
     * Represents a single step in the update operation.
     */
    public static class OpEntry {
    protected String name;         // name of the step, can repeat. required for storage. Can be empty.
    protected List<String> message = new ArrayList<>(); // message(s) of the step
    protected StepState state;     // PASSED/FAILED, required for storage. Is be optional. Can be empty.

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public List<String> getMessage() { return message; }
        public void setMessage(List<String> message) { this.message = message; }

        public StepState getState() { return state; }
        public void setState(StepState state) { this.state = state; }
    }

    // Getters and setters for UpdateOperation fields
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRefType() { return refType; }
    public void setRefType(String refType) { this.refType = refType; }

    public Long getRefId() { return refId; }
    public void setRefId(Long refId) { this.refId = refId; }

    public OpState getState() { return state; }
    public void setState(OpState state) { this.state = state; }

    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

    public int getPercentComplete() { return percentComplete; }
    public void setPercentComplete(int percentComplete) { this.percentComplete = percentComplete; }

    public Date getStartedAt() { return startedAt; }
    public void setStartedAt(Date startedAt) { this.startedAt = startedAt; }

    public Date getCompletedAt() { return completedAt; }
    public void setCompletedAt(Date completedAt) { this.completedAt = completedAt; }

    public List<OpEntry> getOpEntry() { return opEntry; }
    public void setOpEntry(List<OpEntry> opEntry) { this.opEntry = opEntry; }
}
