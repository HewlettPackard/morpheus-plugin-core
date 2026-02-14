/*
 *  Copyright 2026 Morpheus Data, LLC.
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

import java.util.Date;

/**
 * A support bundle is a collection of logs, metrics, and info from various resources within
 * the application. It's stored as an archive file within the associated storage provider at a specified
 * file path. It's used by support engineers to root cause and triage issues in customer environments.
 *
 * @author Mike Carlin
 * @since 1.4.0
 */
public class SupportBundle extends MorpheusModel {

	/**
	 * Name of the storage bundle
	 */
	protected String name;
	/**
	 * Unique identifier for the support bundle, used for stable external references
	 * and storage naming
	 */
	protected String uuid;
	/**
	 * The current status of the support bundle
	 */
	protected Status status;

	/**
	 * The current status message, if any.
	 */
	protected String statusMessage;
	/**
	 * The datetime when the support bundle was requested to be generated
	 */
	protected Date startedAt;
	/**
	 * The datetime when the support bundle generation was completed
	 */
	protected Date completedAt;
	/**
	 * The storage provider for the support bundle. This is essentially where it's stored
	 */
	protected StorageBucket storageProvider;
	/**
	 * The path within the provider where we store the support bundle
	 */
	protected String filePath;
	/**
	 * The length of the bundle in bytes
	 */
	protected Long contentLength;
	/**
	 * The content type of the bundle
	 */
	protected String contentType;
	/**
	 * SHA-256 checksum of the support bundle file
	 */
	protected String checksum;

	/**
	 * Gets the name of the storage bundle.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the storage bundle.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	/**
	 * Gets the unique identifier for the support bundle, used for stable external references
	 * and storage naming.
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the unique identifier for the support bundle, used for stable external references
	 * and storage naming.
	 * @param uuid the uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
		markDirty("uuid", uuid);
	}

	/**
	 * Gets the current status of the support bundle.
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the current status of the support bundle.
	 * @param status the status
	 */
	public void setStatus(Status status) {
		this.status = status;
		markDirty("status", status);
	}

	/**
	 * Gets the current status message, if any.
	 * @return the status message
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * Sets the current status message, if any.
	 * @param statusMessage the status message
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
		markDirty("statusMessage", statusMessage);
	}

	/**
	 * Gets the datetime when the support bundle was requested to be generated.
	 * @return the start date
	 */
	public Date getStartedAt() {
		return startedAt;
	}

	/**
	 * Sets the datetime when the support bundle was requested to be generated.
	 * @param startedAt the start date
	 */
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
		markDirty("startedAt", startedAt);
	}

	/**
	 * Gets the datetime when the support bundle generation was completed.
	 * @return the completion date
	 */
	public Date getCompletedAt() {
		return completedAt;
	}

	/**
	 * Sets the datetime when the support bundle generation was completed.
	 * @param completedAt the completion date
	 */
	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
		markDirty("completedAt", completedAt);
	}

	/**
	 * Gets the storage provider for the support bundle. This is where it's stored.
	 * @return the storage provider
	 */
	public StorageBucket getStorageProvider() {
		return storageProvider;
	}

	/**
	 * Sets the storage provider for the support bundle. This is where it's stored.
	 * @param storageProvider the storage provider
	 */
	public void setStorageProvider(StorageBucket storageProvider) {
		this.storageProvider = storageProvider;
		markDirty("storageProvider", storageProvider);
	}

	/**
	 * Gets the path within the provider where we store the support bundle.
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Sets the path within the provider where we store the support bundle.
	 * @param filePath the file path
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
		markDirty("filePath", filePath);
	}

	/**
	 * Gets the length of the bundle in bytes.
	 * @return the content length
	 */
	public Long getContentLength() {
		return contentLength;
	}

	/**
	 * Sets the length of the bundle in bytes.
	 * @param contentLength the content length
	 */
	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
		markDirty("contentLength", contentLength);
	}

	/**
	 * Gets the content type of the bundle.
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type of the bundle.
	 * @param contentType the content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
		markDirty("contentType", contentType);
	}

	/**
	 * Gets the SHA-256 checksum of the support bundle file.
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * Sets the SHA-256 checksum of the support bundle file.
	 * @param checksum the checksum
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
		markDirty("checksum", checksum);
	}

	/**
	 * The state of a support bundle
	 */
	public enum Status {
		/**
		 * Requested but not started
		 */
		PENDING,
		/**
		 * Collection is in progress
		 */
		IN_PROGRESS,
		/**
		 * Support bundle is complete, present in the storage provider and available
		 */
		COMPLETED,
		/**
		 * Support bundle failed to generate
		 */
		FAILED
	}
}
