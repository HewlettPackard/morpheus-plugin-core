package com.morpheusdata.model;

import com.morpheusdata.model.projection.UpdateIdentityProjection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an update operation with progress tracking and status management.
 */
public class UpdateOperation extends UpdateIdentityProjection {
    private String code;                  // unique code if needed
    private String version;               // version of the update
    private String name;                  // update name

    private String refType;               // associated type of object this update is for
    private String refId;                 // associated id of the object this update is for

    private OpState state;                 // status of the update operation (e.g., pending, in-progress, failed, completed)
    private String statusMessage;         // final status message of the operation
    private int percentComplete = 0;      // completion percentage

    private Instant startedAt;            // start timestamp
    private Instant completedAt;          // completion timestamp

    // List of operation entries (flattened structure)
    private List<OpEntry> opEntry = new ArrayList<>();

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
        private String name;         // name of the step, can repeat. required for storage. Can be empty.
        private List<String> message = new ArrayList<>(); // message(s) of the step
        private StepState state;     // PASSED/FAILED, required for storage. Is be optional. Can be empty.

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

    public String getRefId() { return refId; }
    public void setRefId(String refId) { this.refId = refId; }

    public OpState getState() { return state; }
    public void setState(OpState state) { this.state = state; }

    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

    public int getPercentComplete() { return percentComplete; }
    public void setPercentComplete(int percentComplete) { this.percentComplete = percentComplete; }

    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }

    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }

    public List<OpEntry> getOpEntry() { return opEntry; }
    public void setOpEntry(List<OpEntry> opEntry) { this.opEntry = opEntry; }
}
