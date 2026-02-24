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

package com.morpheusdata.request;

import com.morpheusdata.model.Cloud;
import com.morpheusdata.model.User;

import java.io.InputStream;

/**
 * Request object for file copy operations.
 */
public class FileCopyRequest {

	/**
	 * Name of the file for the file copy request URL.
	 */
	public String fileName;

	/**
	 * The user initiating the request.
	 */
	public User user;

	/**
	 * Source {@link InputStream} to copy.
	 */
	public InputStream sourceStream;

	/**
	 * Size of the file to be copied.
	 */
	public Long contentLength;

	/**
	 * Max timeout in milliseconds to initialize the copy operation.
	 */
	public Long timeout;

	/**
	 * Automatically expand .tar.gz compressed files during upload.
	 */
	public Boolean autoExpand;

	/**
	 * Override the default application/octet-stream content type when serving the file.
	 */
	public String contentType;

	/**
	 * The cloud (zone) associated with this file copy operation.
	 */
	public Cloud cloud;

	public FileCopyRequest() {
	}

	/**
	 * Convenience constructor for the most common required fields.
	 *
	 * @param fileName      name of the file
	 * @param user          the user initiating the request
	 * @param sourceStream  source input stream
	 * @param contentLength size of the file
	 */
	public FileCopyRequest(String fileName, User user, InputStream sourceStream, Long contentLength) {
		this.fileName = fileName;
		this.user = user;
		this.sourceStream = sourceStream;
		this.contentLength = contentLength;
	}
}

