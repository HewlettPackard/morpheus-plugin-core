package com.morpheusdata.model;
import com.morpheusdata.model.projection.UpdateIdentityProjection;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;

/**
 * Represents an update operation with progress tracking and status management.
 */
public class UpdateOperation extends UpdateIdentityProjection {
    protected String code;                  // unique code if needed
    protected String version;               // version of the update
    protected String name;                  // update name
    protected String catalogVersion;        // version of the catalog this update came from

    protected String refType;               // associated type of object this update is for
    protected String refId;                 // associated id of the object this update is for

    protected String state;                 // status of the update operation (e.g., pending, in-progress, failed, completed)
    protected String statesMessage;         // final status message of the operation
    protected Integer percentComplete = 0;  // completion percentage

    protected Instant startedOn;            // start timestamp
    protected Instant completedOn;          // completion timestamp

    // List of operation progress messages
    protected List<OperationEntry> operations = new ArrayList<>();

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCatalogVersion() { return catalogVersion; }
    public void setCatalogVersion(String catalogVersion) { this.catalogVersion = catalogVersion; }

    public String getRefType() { return refType; }
    public void setRefType(String refType) { this.refType = refType; }

    public String getRefId() { return refId; }
    public void setRefId(String refId) { this.refId = refId; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getStatesMessage() { return statesMessage; }
    public void setStatesMessage(String statesMessage) { this.statesMessage = statesMessage; }

    public Integer getPercentComplete() { return percentComplete; }
    public void setPercentComplete(Integer percentComplete) { this.percentComplete = percentComplete; }

    public Instant getStartedOn() { return startedOn; }
    public void setStartedOn(Instant startedOn) { this.startedOn = startedOn; }

    public Instant getCompletedOn() { return completedOn; }
    public void setCompletedOn(Instant completedOn) { this.completedOn = completedOn; }

    public List<OperationEntry> getOperations() { return operations; }
    public void setOperations(List<OperationEntry> operations) { this.operations = operations; }

    /**
     * Represents a stage of the update operation and its associated messages.
     */
    public static class OperationEntry {
        protected UpdateStage stage;
        protected List<OpMessage> messages = new ArrayList<>();

        public UpdateStage getStage() { return stage; }
        public void setStage(UpdateStage stage) { this.stage = stage; }

        public List<OpMessage> getMessages() { return messages; }
        public void setMessages(List<OpMessage> messages) { this.messages = messages; }
    }

    /**
     * Represents a progress message for an operation.
     */
    public static class OpMessage {
        protected List<String> message = new ArrayList<>();
        protected String status;
        protected String name;

        public List<String> getMessage() { return message; }
        public void setMessage(List<String> message) { this.message = message; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    /**
     * Enum representing the stages of an update operation.
     */
    public enum UpdateStage {
        PRE_UPDATE,
        UPDATE,
        POST_UPDATE
    }
}