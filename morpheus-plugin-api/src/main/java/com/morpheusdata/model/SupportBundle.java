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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
		markDirty("uuid", uuid);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
		markDirty("state", status);
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
		markDirty("startedAt", startedAt);
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
		markDirty("completedAt", completedAt);
	}

	public StorageBucket getStorageProvider() {
		return storageProvider;
	}

	public void setStorageProvider(StorageBucket storageProvider) {
		this.storageProvider = storageProvider;
		markDirty("storageProvider", storageProvider);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		markDirty("filePath", filePath);
	}

	public Long getContentLength() {
		return contentLength;
	}

	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
		markDirty("contentLength", contentLength);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
		markDirty("contentType", contentType);
	}

	public String getChecksum() {
		return checksum;
	}

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
