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

package com.morpheusdata.model.health;

public class ApplianceHealthDatabaseConnection {

	protected Long id;
	protected String user;
	protected String host;
	protected String database;
	protected String command;
	protected Long time;
	protected String state;
	protected String info;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }

	public String getHost() { return host; }
	public void setHost(String host) { this.host = host; }

	public String getDatabase() { return database; }
	public void setDatabase(String database) { this.database = database; }

	public String getCommand() { return command; }
	public void setCommand(String command) { this.command = command; }

	public Long getTime() { return time; }
	public void setTime(Long time) { this.time = time; }

	public String getState() { return state; }
	public void setState(String state) { this.state = state; }

	public String getInfo() { return info; }
	public void setInfo(String info) { this.info = info; }

	@Override
	public String toString() {
		return "ApplianceHealthDatabaseConnection{" +
			"id=" + id +
			", user='" + user + '\'' +
			", host='" + host + '\'' +
			", database='" + database + '\'' +
			", command='" + command + '\'' +
			", time=" + time +
			", state='" + state + '\'' +
			", info='" + info + '\'' +
			'}';
	}
}
