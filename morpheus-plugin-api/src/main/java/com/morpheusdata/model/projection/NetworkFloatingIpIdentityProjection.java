package com.morpheusdata.model.projection;

public class NetworkFloatingIpIdentityProjection extends MorpheusIdentityModel {
    protected String externalId;
    protected String ipAddress;

    public NetworkFloatingIpIdentityProjection() {
        //
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
        markDirty("externalId",externalId);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        markDirty("ipAddress",ipAddress);
    }
}
