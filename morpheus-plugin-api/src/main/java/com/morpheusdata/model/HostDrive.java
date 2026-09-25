/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morpheusdata.model;

/**
 * Facts about a single physical drive discovered on a host.
 * <p>
 * Instances are produced live by discovery run through the Morpheus agent (see
 * {@link com.morpheusdata.core.MorpheusComputeServerService#listHostDrives(ComputeServer)} and
 * {@link com.morpheusdata.core.MorpheusComputeServerGroupService#listHostDrives(ComputeServerGroup)}) and are not
 * persisted by Morpheus. {@code candidate}, {@code eligible}, {@code candidacyReason}, and {@code claimedBy} are
 * left unset by Morpheus; the storage provider plugin fills them in before the claim form renders.
 *
 * @since 1.5.1
 */
public class HostDrive {

	/** The host this drive was found on. */
	protected Long serverId;

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

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDevicePath() {
		return devicePath;
	}

	public void setDevicePath(String devicePath) {
		this.devicePath = devicePath;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getWwn() {
		return wwn;
	}

	public void setWwn(String wwn) {
		this.wwn = wwn;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public Long getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(Long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public Boolean getRemovable() {
		return removable;
	}

	public void setRemovable(Boolean removable) {
		this.removable = removable;
	}

	public Boolean getBootDevice() {
		return bootDevice;
	}

	public void setBootDevice(Boolean bootDevice) {
		this.bootDevice = bootDevice;
	}

	public Boolean getHasPartitionTable() {
		return hasPartitionTable;
	}

	public void setHasPartitionTable(Boolean hasPartitionTable) {
		this.hasPartitionTable = hasPartitionTable;
	}

	public Boolean getHasFilesystem() {
		return hasFilesystem;
	}

	public void setHasFilesystem(Boolean hasFilesystem) {
		this.hasFilesystem = hasFilesystem;
	}

	public String getFileSystemType() {
		return fileSystemType;
	}

	public void setFileSystemType(String fileSystemType) {
		this.fileSystemType = fileSystemType;
	}

	public Boolean getMounted() {
		return mounted;
	}

	public void setMounted(Boolean mounted) {
		this.mounted = mounted;
	}

	public String getRaidMember() {
		return raidMember;
	}

	public void setRaidMember(String raidMember) {
		this.raidMember = raidMember;
	}

	public String getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}

	public String getLocateSupported() {
		return locateSupported;
	}

	public void setLocateSupported(String locateSupported) {
		this.locateSupported = locateSupported;
	}

	public Boolean getCandidate() {
		return candidate;
	}

	public void setCandidate(Boolean candidate) {
		this.candidate = candidate;
	}

	public Boolean getEligible() {
		return eligible;
	}

	public void setEligible(Boolean eligible) {
		this.eligible = eligible;
	}

	public String getCandidacyReason() {
		return candidacyReason;
	}

	public void setCandidacyReason(String candidacyReason) {
		this.candidacyReason = candidacyReason;
	}

	public String getClaimedBy() {
		return claimedBy;
	}

	public void setClaimedBy(String claimedBy) {
		this.claimedBy = claimedBy;
	}
}
