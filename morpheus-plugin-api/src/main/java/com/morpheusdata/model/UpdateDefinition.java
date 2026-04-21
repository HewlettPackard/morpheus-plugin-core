package com.morpheusdata.model;

import com.morpheusdata.model.projection.UpdateIdentityProjection;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents the definition of an update, including its metadata, requirements, and type.
 */
public class UpdateDefinition extends UpdateIdentityProjection {
    protected String code;                 // unique code if needed
    protected String version;              // version of the update
    protected String name;                 // name of the update
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
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

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
}
