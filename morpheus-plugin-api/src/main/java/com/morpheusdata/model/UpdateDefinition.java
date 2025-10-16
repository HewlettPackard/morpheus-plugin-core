package com.morpheusdata.model;
import com.morpheusdata.model.projection.UpdateIdentityProjection;

/**
 * Represents the definition of an update, including its metadata, requirements, and type.
 */
public class UpdateDefinition extends UpdateIdentityProjection {
    protected String code;                 // unique code if needed
    protected String version;              // version of the update
    protected String name;                 // name of the update
    protected String catalogVersion;       // version of the catalog this update came from
    protected String updateType;           // enum to differentiate dryrun vs update, spp vs ilo

    protected String refType;              // associated type of object this update is for
    protected String refId;                // associated id of the object this update is for
    protected String updateFile;           // file location of the update

    protected Boolean requiresMaintenanceMode = false; // does this require maintenance mode to be enabled
    protected Boolean requiresRestart = false;         // does this require a service restart
    protected Boolean supportsRollback = false;        // can this update be rolled back

    protected Boolean isPlugin = false;    // is this update code located in a plugin
    protected Object hasMany;              // relationship placeholder (could be typed more strictly)

    /**
     * Enum to represent the operation name/type.
     */
    public enum OpName {
        UPDATE,      // default - for storage, spp and others
        DRY_RUN,
        ILO_UPDATE   // used to differentiate SPP and ILO
    }

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCatalogVersion() { return catalogVersion; }
    public void setCatalogVersion(String catalogVersion) { this.catalogVersion = catalogVersion; }

    public String getUpdateType() { return updateType; }
    public void setUpdateType(String updateType) { this.updateType = updateType; }

    public String getRefType() { return refType; }
    public void setRefType(String refType) { this.refType = refType; }

    public String getRefId() { return refId; }
    public void setRefId(String refId) { this.refId = refId; }

    public String getUpdateFile() { return updateFile; }
    public void setUpdateFile(String updateFile) { this.updateFile = updateFile; }

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