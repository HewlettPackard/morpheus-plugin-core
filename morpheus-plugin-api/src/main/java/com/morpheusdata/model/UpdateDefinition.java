package com.morpheusdata.model;

import com.morpheusdata.model.projection.UpdateIdentityProjection;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents the definition of an update, including its metadata, requirements, and type.
 *
 * <p>An {@code UpdateDefinition} describes an available update for a resource type identified by
 * {@link #refType} and {@link #refId} (e.g., a {@code StorageServerType} or {@code ComputeServerType}).
 * It does <em>not</em> represent a specific in-progress operation on an instance — that is modeled by
 * {@code UpdateOperation}, whose {@code refType}/{@code refId} point to the concrete resource instance.</p>
 *
 * <p>Plugin authors should use {@link com.morpheusdata.core.MorpheusUpdateDefinitionService} (async) or
 * {@link com.morpheusdata.core.synchronous.MorpheusSynchronousUpdateDefinitionService} (sync) to create,
 * sync, and query update definitions. The stable sync identity for matching existing records is {@link #code}
 * scoped to a given {@code refType}/{@code refId} pair.</p>
 */
public class UpdateDefinition extends UpdateIdentityProjection {
    protected String version;              // version of the update (mapped from appliance updateVersion)
    protected OpType updateOpType;         // enum to differentiate dryrun vs update, spp vs ilo

    protected String refType;              // associated type of object this update is for
    protected Long refId;                  // associated id of the object this update is for
    protected String updateImagePath;      // file location of the update
    protected ImagePathType imagePathType; // vme_archive_http_path, local_fs_path, external_http_path, vme_image_lib_path

    protected Boolean peerPersistence = false;            // true/false, true to indicate peer persistence enabled configuration.
    protected Boolean requiresReboot = false;             // does this require a reboot.
    protected Boolean requiresMaintenanceMode = false;    // does this require maintenance mode to be enabled.
    protected Boolean requiresRestart = false;            // does this require a service restart.
    protected Boolean supportsRollback = false;           // can this update be rolled back. place holder.

    protected Boolean isPlugin = false;                   // is this update code located in a plugin
    protected Object hasMany;                             // relationship placeholder (could be typed more strictly)
    protected List<String> validateRules;                 // optional - in case any specific rule needs to be executed pre-check.

    // Appliance-aligned fields
    /** Severity of the update. Typical values: {@code "normal"}, {@code "important"}, {@code "critical"}. */
    protected String severity = "normal";

    /**
     * Category of the update. Typical values: {@code "security"}, {@code "feature"}, {@code "bugfix"},
     * {@code "enhancement"}.
     */
    protected String type = "security";

    /** Human-readable description of the update (e.g., changelog text). May be null. */
    protected String description;

    /** Whether this update definition is active and eligible to be applied. Defaults to {@code true}. */
    protected Boolean enabled = true;

    /**
     * Whether this update can be applied with zero downtime (no reboot, no service interruption).
     * Defaults to {@code false}.
     */
    protected Boolean zeroDowntime = false;

    /**
     * Version of the update catalog or bundle this definition was sourced from.
     * May be null if not catalog-driven.
     */
    protected String catalogVersion;

    /** Date this update was released by the vendor or source. May be null. */
    protected Date updateReleaseDate;

    /**
     * Enum to represent the operation name/type.
     */
    public enum OpType {
        UPDATE,      // default - for storage, spp and others
        DRY_RUN
    }

    /**
     * Enum to represent the image path type.
     */
    public enum ImagePathType {
    VME_ARCHIVE_HTTP_PATH,
    LOCAL_FS_PATH,
    EXTERNAL_HTTP_PATH,
    VME_IMAGE_LIB_PATH
    }

    // Getters and setters
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public OpType getUpdateOpType() { return updateOpType; }
    public void setUpdateOpType(OpType updateOpType) { this.updateOpType = updateOpType; }

    public String getRefType() { return refType; }
    public void setRefType(String refType) { this.refType = refType; }

    public Long getRefId() { return refId; }
    public void setRefId(Long refId) { this.refId = refId; }

    public String getUpdateImagePath() { return updateImagePath; }
    public void setUpdateImagePath(String updateImagePath) { this.updateImagePath = updateImagePath; }

    public ImagePathType getImagePathType() { return imagePathType; }
    public void setImagePathType(ImagePathType imagePathType) { this.imagePathType = imagePathType; }

    public List<String> getValidateRules() { return validateRules; }
    public void setValidateRules(List<String> validateRules) { this.validateRules = validateRules; }

    public Boolean getPeerPersistence() { return peerPersistence; }
    public void setPeerPersistence(Boolean peerPersistence) { this.peerPersistence = peerPersistence; }

    public Boolean getRequiresReboot() { return requiresReboot; }
    public void setRequiresReboot(Boolean requiresReboot) { this.requiresReboot = requiresReboot; }

    public Boolean getRequiresMaintenanceMode() { return requiresMaintenanceMode; }
    public void setRequiresMaintenanceMode(Boolean requiresMaintenanceMode) { this.requiresMaintenanceMode = requiresMaintenanceMode; }

    public Boolean getRequiresRestart() { return requiresRestart; }
    public void setRequiresRestart(Boolean requiresRestart) { this.requiresRestart = requiresRestart; }

    public Boolean getSupportsRollback() { return supportsRollback; }
    public void setSupportsRollback(Boolean supportsRollback) { this.supportsRollback = supportsRollback; }

    public Boolean getIsPlugin() { return isPlugin; }
    public void setIsPlugin(Boolean isPlugin) { this.isPlugin = isPlugin; }

    public Object getHasMany() { return hasMany; }
    public void setHasMany(Object hasMany) { this.hasMany = hasMany; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public Boolean getZeroDowntime() { return zeroDowntime; }
    public void setZeroDowntime(Boolean zeroDowntime) { this.zeroDowntime = zeroDowntime; }

    public String getCatalogVersion() { return catalogVersion; }
    public void setCatalogVersion(String catalogVersion) { this.catalogVersion = catalogVersion; }

    public Date getUpdateReleaseDate() { return updateReleaseDate; }
    public void setUpdateReleaseDate(Date updateReleaseDate) { this.updateReleaseDate = updateReleaseDate; }
}
