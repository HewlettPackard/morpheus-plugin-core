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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

/**
 * A physical drive already claimed by (or a candidate for) a {@link StorageServerNode}.
 * <p>
 * This is the post-claim record: once a drive belongs to a node, its identity
 * is tracked here by a durable {@code uniqueId} rather than by the transient
 * {@code deviceName}/{@code pciAddress} the OS assigns it, since those can
 * change across reboots or hardware moves.
 * <p>
 * What makes a drive safe to claim — boot/root exclusion, filesystem/partition
 * signatures, media type limits, minimum/maximum drive counts, etc. — is
 * Storage-team policy, implemented and enforced by the array/provider, not
 * Morpheus. This model only records the resulting verdict ({@code candidacy})
 * and the reason ({@code candidacyReason}); it does not evaluate candidacy
 * itself.
 *
 * @since 1.5.1
 * @author HPE Storage Plugin Team
 */
public class StorageServerNodeDisk extends MorpheusModel {

	/** The verdict on whether a drive is safe to claim; see {@code candidacyReason} for why. */
	public enum Candidacy { eligible, ineligible, claimed, excluded }

	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected StorageServerNode node;

	/** /dev/nvme0n1 — may change across reboots, display only. */
	protected String deviceName;
	/** May also change across reboots or hardware moves. */
	protected String pciAddress;
	/** Derived stable identity — the durable key, never parsed by a plugin. */
	protected String uniqueId;
	protected String model;
	protected Long sizeBytes;
	/** ssd | hdd */
	protected String mediaType;
	/** nvme | sas | sata */
	protected String transport;
	protected Boolean claimed;
	/** healthy | predicted-failure | failed | rebuilding */
	protected String status;
	/** The verdict on whether this drive is safe to claim, per Storage-team policy. */
	protected Candidacy candidacy;
	/** Why the drive is eligible/ineligible/excluded, e.g. an exclusion reason to show inline in the UI. */
	protected String candidacyReason;

	public StorageServerNode getNode() { return node; }
	public void setNode(StorageServerNode node) { this.node = node; markDirty("node", node); }

	public String getDeviceName() { return deviceName; }
	public void setDeviceName(String deviceName) { this.deviceName = deviceName; markDirty("deviceName", deviceName); }

	public String getPciAddress() { return pciAddress; }
	public void setPciAddress(String pciAddress) { this.pciAddress = pciAddress; markDirty("pciAddress", pciAddress); }

	public String getUniqueId() { return uniqueId; }
	public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; markDirty("uniqueId", uniqueId); }

	public String getModel() { return model; }
	public void setModel(String model) { this.model = model; markDirty("model", model); }

	public Long getSizeBytes() { return sizeBytes; }
	public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; markDirty("sizeBytes", sizeBytes); }

	public String getMediaType() { return mediaType; }
	public void setMediaType(String mediaType) { this.mediaType = mediaType; markDirty("mediaType", mediaType); }

	public String getTransport() { return transport; }
	public void setTransport(String transport) { this.transport = transport; markDirty("transport", transport); }

	public Boolean getClaimed() { return claimed; }
	public void setClaimed(Boolean claimed) { this.claimed = claimed; markDirty("claimed", claimed); }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; markDirty("status", status); }

	public Candidacy getCandidacy() { return candidacy; }
	public void setCandidacy(Candidacy candidacy) { this.candidacy = candidacy; markDirty("candidacy", candidacy); }

	public String getCandidacyReason() { return candidacyReason; }
	public void setCandidacyReason(String candidacyReason) { this.candidacyReason = candidacyReason; markDirty("candidacyReason", candidacyReason); }
}
