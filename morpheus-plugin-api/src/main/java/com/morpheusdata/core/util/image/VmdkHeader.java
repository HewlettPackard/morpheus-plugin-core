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

package com.morpheusdata.core.util.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VmdkHeader {

	public VmdkHeader() {
		this.cachedBytes = new ByteArrayOutputStream();
	}

	private String magic;

	public String getMagic() {
		return magic;
	}

	public void setMagic(String magic) {
		this.magic = magic;
	}

	public byte[] getBytes() {
		return cachedBytes.toByteArray();
	}

	protected void cacheByte(int b) throws IOException {
		this.cachedBytes.write(b);
	}

	protected void cacheBytes(byte[] bytes) throws IOException {
		this.cachedBytes.write(bytes);
	}

	protected void cacheBytes(byte[] b, int off, int len) throws IOException {
		this.cachedBytes.write(b, off, len);
	}

	protected void closeCache() throws IOException {
		if (cachedBytes != null)
			cachedBytes.close();
	}



	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}



	public Long getFlags() {
		return flags;
	}

	public void setFlags(Long flags) {
		this.flags = flags;
	}

	private Long version;
	private ByteArrayOutputStream cachedBytes;
	private Long flags;
	private Long capacity;
	private Long grainNumberSectors;
	private Long descriptorSector;
	private Long descriptorSectorNumberOfSectors;
	private Long grainTableEntries;
	private Long secondaryGrainDirectorySector;
	private Long grainDirectorySector;
	private Long metadataNumberOfSectors;
	private boolean isDirty;
	private boolean isCompressed;


	public Long getGrainNumberSectors() {
		return grainNumberSectors;
	}

	public void setGrainNumberSectors(Long grainNumberSectors) {
		this.grainNumberSectors = grainNumberSectors;
	}

	public Long getDescriptorSector() {
		return descriptorSector;
	}

	public void setDescriptorSector(Long descriptorSector) {
		this.descriptorSector = descriptorSector;
	}

	public Long getDescriptorSectorNumberOfSectors() {
		return descriptorSectorNumberOfSectors;
	}

	public void setDescriptorSectorNumberOfSectors(Long descriptorSectorNumberOfSectors) {
		this.descriptorSectorNumberOfSectors = descriptorSectorNumberOfSectors;
	}

	public Long getGrainTableEntries() {
		return grainTableEntries;
	}

	public void setGrainTableEntries(Long grainTableEntries) {
		this.grainTableEntries = grainTableEntries;
	}

	public Long getSecondaryGrainDirectorySector() {
		return secondaryGrainDirectorySector;
	}

	public void setSecondaryGrainDirectorySector(Long secondaryGrainDirectorySector) {
		this.secondaryGrainDirectorySector = secondaryGrainDirectorySector;
	}

	public Long getGrainDirectorySector() {
		return grainDirectorySector;
	}

	public void setGrainDirectorySector(Long grainDirectorySector) {
		this.grainDirectorySector = grainDirectorySector;
	}

	public Long getMetadataNumberOfSectors() {
		return metadataNumberOfSectors;
	}

	public void setMetadataNumberOfSectors(Long metadataNumberOfSectors) {
		this.metadataNumberOfSectors = metadataNumberOfSectors;
	}

	public Long getCapacity() {
		return capacity;
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean dirty) {
		isDirty = dirty;
	}

	public boolean isCompressed() {
		return isCompressed;
	}

	public void setCompressed(boolean compressed) {
		isCompressed = compressed;
	}

	boolean isGrainDataCompressed() {
		//check if the 17th bit is set on flags
		boolean b = 1 == ((getFlags() >> 16) & 0x01);
		return b;

	}
}
