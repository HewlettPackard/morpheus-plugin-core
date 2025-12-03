/*
 *  Copyright 2025 Morpheus Data, LLC.
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
 * Representation of server statistics data for a specific point in time.
 * Used to track historical metrics for compute servers including CPU, memory, and storage usage.
 * This is a POJO (Plain Old Java Object) that encapsulates the server metrics data.
 *
 * @author Morpheus Data, LLC.
 * @since 1.2.14
 */
public class ServerStatsData {

	protected Long id;
	protected Date date;
	protected Long usedMemory = 0L;
	protected Long maxMemory = 0L;
	protected Long freeMemory = 0L;
	protected Long maxStorage = 0L;
	protected Long usedStorage = 0L;
	protected Long freeStorage = 0L;
	protected Float cpuUsage = 0.0f;
	protected Boolean running = true;

	/**
	 * Default constructor
	 */
	public ServerStatsData() {
	}

	/**
	 * Get the compute server ID
	 * @return the server ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set the compute server ID
	 * @param id the server ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get the timestamp for this stats data point
	 * @return the date/time of the stats
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the timestamp for this stats data point
	 * @param date the date/time of the stats
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Get the used memory in bytes
	 * @return used memory in bytes
	 */
	public Long getUsedMemory() {
		return usedMemory;
	}

	/**
	 * Set the used memory in bytes
	 * @param usedMemory used memory in bytes
	 */
	public void setUsedMemory(Long usedMemory) {
		this.usedMemory = usedMemory;
	}

	/**
	 * Get the maximum memory available in bytes
	 * @return max memory in bytes
	 */
	public Long getMaxMemory() {
		return maxMemory;
	}

	/**
	 * Set the maximum memory available in bytes
	 * @param maxMemory max memory in bytes
	 */
	public void setMaxMemory(Long maxMemory) {
		this.maxMemory = maxMemory;
	}

	/**
	 * Get the free memory in bytes
	 * @return free memory in bytes
	 */
	public Long getFreeMemory() {
		return freeMemory;
	}

	/**
	 * Set the free memory in bytes
	 * @param freeMemory free memory in bytes
	 */
	public void setFreeMemory(Long freeMemory) {
		this.freeMemory = freeMemory;
	}

	/**
	 * Get the maximum storage available in bytes
	 * @return max storage in bytes
	 */
	public Long getMaxStorage() {
		return maxStorage;
	}

	/**
	 * Set the maximum storage available in bytes
	 * @param maxStorage max storage in bytes
	 */
	public void setMaxStorage(Long maxStorage) {
		this.maxStorage = maxStorage;
	}

	/**
	 * Get the used storage in bytes
	 * @return used storage in bytes
	 */
	public Long getUsedStorage() {
		return usedStorage;
	}

	/**
	 * Set the used storage in bytes
	 * @param usedStorage used storage in bytes
	 */
	public void setUsedStorage(Long usedStorage) {
		this.usedStorage = usedStorage;
	}

	/**
	 * Get the free storage in bytes
	 * @return free storage in bytes
	 */
	public Long getFreeStorage() {
		return freeStorage;
	}

	/**
	 * Set the free storage in bytes
	 * @param freeStorage free storage in bytes
	 */
	public void setFreeStorage(Long freeStorage) {
		this.freeStorage = freeStorage;
	}

	/**
	 * Get the CPU usage as a percentage (0-100)
	 * @return CPU usage percentage
	 */
	public Float getCpuUsage() {
		return cpuUsage;
	}

	/**
	 * Set the CPU usage as a percentage (0-100)
	 * @param cpuUsage CPU usage percentage
	 */
	public void setCpuUsage(Float cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	/**
	 * Get whether the server is running
	 * @return true if the server is running, false otherwise
	 */
	public Boolean getRunning() {
		return running;
	}

	/**
	 * Set whether the server is running
	 * @param running true if the server is running, false otherwise
	 */
	public void setRunning(Boolean running) {
		this.running = running;
	}
}

