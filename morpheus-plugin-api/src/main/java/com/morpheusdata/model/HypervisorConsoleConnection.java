/*
 *  Copyright 2024 Morpheus Data, LLC.
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

import java.util.List;
import java.util.Map;

/**
 * Describes the connection details needed to establish a hypervisor console session.
 *
 * The {@code protocols} property controls the {@code Sec-WebSocket-Protocol} request header
 * when browser-based console access is proxied through Morpheus:
 *
 * <ul>
 *     <li>{@code null}: no override specified. Morpheus will preserve its default behavior.</li>
 *     <li>empty list: explicitly send no {@code Sec-WebSocket-Protocol} header.</li>
 *     <li>non-empty list: send the provided values in order.</li>
 * </ul>
 *
 * @since 1.3.4
 */
public class HypervisorConsoleConnection {
	protected String url;
	protected String token;
	protected String host;
	protected Integer port;
	protected Map<String,String> headers;
	protected List<String> protocols;
	protected String keyExtension;
	protected String keymap;
	protected Boolean fakePasswordVNC;
	protected String vmxHandshakePath;
	protected String httpVersion;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public List<String> getProtocols() {
		return protocols;
	}

	public void setProtocols(List<String> protocols) {
		this.protocols = protocols;
	}

	public String getKeyExtension() {
		return keyExtension;
	}

	public void setKeyExtension(String keyExtension) {
		this.keyExtension = keyExtension;
	}

	public String getKeymap() {
		return keymap;
	}

	public void setKeymap(String keymap) {
		this.keymap = keymap;
	}

	public Boolean getFakePasswordVNC() {
		return fakePasswordVNC;
	}

	public void setFakePasswordVNC(Boolean fakePasswordVNC) {
		this.fakePasswordVNC = fakePasswordVNC;
	}

	public String getVmxHandshakePath() {
		return vmxHandshakePath;
	}

	public void setVmxHandshakePath(String vmxHandshakePath) {
		this.vmxHandshakePath = vmxHandshakePath;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}
}
