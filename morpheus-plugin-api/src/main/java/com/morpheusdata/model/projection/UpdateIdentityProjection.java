package com.morpheusdata.model.projection;

/**
 * Identity projection for {@link com.morpheusdata.model.UpdateDefinition} records.
 *
 * <p>Used by {@link com.morpheusdata.core.MorpheusUpdateDefinitionService} and
 * {@link com.morpheusdata.core.synchronous.MorpheusSynchronousUpdateDefinitionService} for
 * efficient identity-based sync operations without loading full model state.</p>
 *
 * <p><strong>Stable sync identity:</strong> The {@link #code} field is the recommended stable
 * identity for matching update definitions during sync. It should be unique within a given
 * {@code refType}/{@code refId} scope (i.e., within a specific resource type). The appliance
 * {@code id} (inherited from {@link MorpheusIdentityModel} via {@link com.morpheusdata.model.MorpheusModel})
 * is the internal database key and should not be constructed by plugin authors — use {@code code}
 * for authoring and sync matching.</p>
 *
 * <p><strong>Scope:</strong> Update definitions are scoped to a resource type via
 * {@link com.morpheusdata.model.UpdateDefinition#getRefType()} and
 * {@link com.morpheusdata.model.UpdateDefinition#getRefId()} on the full model.
 * Expected {@code refType} values include {@code "ComputeServerType"}, {@code "StorageServerType"},
 * and {@code "NetworkServerType"}.</p>
 */
public class UpdateIdentityProjection extends MorpheusIdentityModel {

    /**
     * The stable plugin-defined code for this update definition. Used as the primary identity
     * key during sync operations. Should be unique within a {@code refType}/{@code refId} scope.
     */
    protected String code;

    /** Display name of the update definition. */
    protected String name;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
