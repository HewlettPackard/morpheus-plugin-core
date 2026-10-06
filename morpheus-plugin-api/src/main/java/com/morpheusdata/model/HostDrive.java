/*
 * Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

/**
 * A physical drive discovered on a host, keyed by a durable {@code uniqueId} rather than the
 * transient {@code deviceName}/{@code devicePath} the OS assigns it, since those can change
 * across reboots or hardware moves.
 * <p>
 * Rows are populated by the HVM CLI's drive-inventory sync, which reconciles live discovery into
 * this persisted store (add/update/delete based on current state). {@code candidate},
 * {@code eligible}, {@code candidacyReason}, and {@code claimedBy} are left unset by that sync;
 * the storage provider plugin fills them in before the claim form renders.
 *
 * @since 1.5.1
 * @author HPE Storage Plugin Team
 */
public class HostDrive extends MorpheusModel {

	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected ComputeServer server;

	/** Stable across reboot; the identity the array is given. */
	protected String uniqueId;

	/** e.g. {@code /dev/nvme0n1} — for display only, never for identity. */
	protected String deviceName;

	/** e.g. {@code /dev/disk/by-id/...} — the stable path a script uses. */
	protected String devicePath;

	protected String serialNumber;
	protected String wwn;
	protected String vendor;
	protected String model;
	protected String firmwareVersion;
	protected Long sizeBytes;

	/** What the media is: {@code ssd}, {@code hdd}. */
	protected String mediaType;

	/** How it attaches: {@code nvme}, {@code sas}, {@code sata}. */
	protected String transport;

	protected Boolean removable;

	/** The host boots from it. */
	protected Boolean bootDevice;

	protected Boolean hasPartitionTable;
	protected Boolean hasFilesystem;

	/** Null when none was found. */
	protected String fileSystemType;

	protected Boolean mounted;

	/** {@code md}, {@code lvm}, {@code zfs}, or null. */
	protected String raidMember;

	/** As the drive reports it. */
	protected String healthStatus;

	/** Whether an identify LED can be driven. */
	protected String locateSupported;

	// Left empty by Morpheus and filled by the plugin before the form renders

	/** The plugin proposes claiming this drive. */
	protected Boolean candidate;

	/** The storage software is able to claim it. */
	protected Boolean eligible;

	/** Why it is not eligible, or not proposed. */
	protected String candidacyReason;

	/** Who owns the drive, if any. */
	protected String claimedBy;

	public ComputeServer getServer() { return server; }
	public void setServer(ComputeServer server) { this.server = server; markDirty("server", server); }

	public String getUniqueId() { return uniqueId; }
	public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; markDirty("uniqueId", uniqueId); }

	public String getDeviceName() { return deviceName; }
	public void setDeviceName(String deviceName) { this.deviceName = deviceName; markDirty("deviceName", deviceName); }

	public String getDevicePath() { return devicePath; }
	public void setDevicePath(String devicePath) { this.devicePath = devicePath; markDirty("devicePath", devicePath); }

	public String getSerialNumber() { return serialNumber; }
	public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; markDirty("serialNumber", serialNumber); }

	public String getWwn() { return wwn; }
	public void setWwn(String wwn) { this.wwn = wwn; markDirty("wwn", wwn); }

	public String getVendor() { return vendor; }
	public void setVendor(String vendor) { this.vendor = vendor; markDirty("vendor", vendor); }

	public String getModel() { return model; }
	public void setModel(String model) { this.model = model; markDirty("model", model); }

	public String getFirmwareVersion() { return firmwareVersion; }
	public void setFirmwareVersion(String firmwareVersion) { this.firmwareVersion = firmwareVersion; markDirty("firmwareVersion", firmwareVersion); }

	public Long getSizeBytes() { return sizeBytes; }
	public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; markDirty("sizeBytes", sizeBytes); }

	public String getMediaType() { return mediaType; }
	public void setMediaType(String mediaType) { this.mediaType = mediaType; markDirty("mediaType", mediaType); }

	public String getTransport() { return transport; }
	public void setTransport(String transport) { this.transport = transport; markDirty("transport", transport); }

	public Boolean getRemovable() { return removable; }
	public void setRemovable(Boolean removable) { this.removable = removable; markDirty("removable", removable); }

	public Boolean getBootDevice() { return bootDevice; }
	public void setBootDevice(Boolean bootDevice) { this.bootDevice = bootDevice; markDirty("bootDevice", bootDevice); }

	public Boolean getHasPartitionTable() { return hasPartitionTable; }
	public void setHasPartitionTable(Boolean hasPartitionTable) { this.hasPartitionTable = hasPartitionTable; markDirty("hasPartitionTable", hasPartitionTable); }

	public Boolean getHasFilesystem() { return hasFilesystem; }
	public void setHasFilesystem(Boolean hasFilesystem) { this.hasFilesystem = hasFilesystem; markDirty("hasFilesystem", hasFilesystem); }

	public String getFileSystemType() { return fileSystemType; }
	public void setFileSystemType(String fileSystemType) { this.fileSystemType = fileSystemType; markDirty("fileSystemType", fileSystemType); }

	public Boolean getMounted() { return mounted; }
	public void setMounted(Boolean mounted) { this.mounted = mounted; markDirty("mounted", mounted); }

	public String getRaidMember() { return raidMember; }
	public void setRaidMember(String raidMember) { this.raidMember = raidMember; markDirty("raidMember", raidMember); }

	public String getHealthStatus() { return healthStatus; }
	public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; markDirty("healthStatus", healthStatus); }

	public String getLocateSupported() { return locateSupported; }
	public void setLocateSupported(String locateSupported) { this.locateSupported = locateSupported; markDirty("locateSupported", locateSupported); }

	public Boolean getCandidate() { return candidate; }
	public void setCandidate(Boolean candidate) { this.candidate = candidate; markDirty("candidate", candidate); }

	public Boolean getEligible() { return eligible; }
	public void setEligible(Boolean eligible) { this.eligible = eligible; markDirty("eligible", eligible); }

	public String getCandidacyReason() { return candidacyReason; }
	public void setCandidacyReason(String candidacyReason) { this.candidacyReason = candidacyReason; markDirty("candidacyReason", candidacyReason); }

	public String getClaimedBy() { return claimedBy; }
	public void setClaimedBy(String claimedBy) { this.claimedBy = claimedBy; markDirty("claimedBy", claimedBy); }
}
