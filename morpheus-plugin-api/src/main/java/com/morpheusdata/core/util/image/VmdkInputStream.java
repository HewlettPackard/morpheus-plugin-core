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

import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class VmdkInputStream  extends InputStream {
	public VmdkHeader vmdkHeader;
	private InputStream sourceStream;
	private boolean cacheHeaderBytes = false;
	private Long position = 0L;
	private Long offset = 0L;
	private Long contentLength = 0L;
	private boolean twoPassNeeded = false;
	private VmdkDirectory vmdkDirectory;
	private long readBackFileBufferSize=0l;

	public VmdkInputStream(InputStream sourceStream,Long contentLength, boolean cacheHeaderBytes) throws IllegalArgumentException, IllegalStateException, IOException {

		this.sourceStream = sourceStream;
		this.cacheHeaderBytes = cacheHeaderBytes;
		this.contentLength = contentLength;
		initializeVmdkHeader();
	}

	public VmdkInputStream(InputStream sourceStream,Long contentLength) throws IllegalArgumentException, IllegalStateException, IOException {

		this(sourceStream,contentLength, false);
		this.contentLength = contentLength;
	}

	public VmdkInputStream(InputStream sourceStream, VmdkHeader vmdkHeader,Long contentLength) throws IllegalArgumentException, IllegalStateException, IOException {
		this.sourceStream = sourceStream;
		this.contentLength = contentLength;
		this.vmdkHeader = vmdkHeader;
	}

	public VmdkInputStream(InputStream sourceStream, VmdkHeader vmdkHeader,VmdkDirectory vmdkDirectory,Long contentLength) throws IllegalArgumentException, IllegalStateException, IOException {
		this.sourceStream = sourceStream;
		this.contentLength = contentLength;
		this.vmdkHeader = vmdkHeader;
		this.vmdkDirectory = vmdkDirectory;
		readBackFileBufferSize=vmdkHeader.getGrainNumberSectors() * vmdkHeader.getGrainTableEntries() * 512 * 2; //extra room just in case
	}

	public VmdkDirectory readDirectory() throws IOException {
		if(vmdkDirectory != null) {
			return vmdkDirectory;
		} else {
			vmdkDirectory = new VmdkDirectory();
		}

		long grainDirectoryPosition = vmdkHeader.getGrainDirectorySector()*512;
		long grainDirectoryEntries = vmdkHeader.getCapacity() / (512 * 128);
		if(vmdkHeader.getCapacity() % (512 * 128) > 0) {
			grainDirectoryEntries++;
		}
		if(bufferCacheStartPosition > 0 && grainDirectoryPosition > bufferCacheStartPosition && grainDirectoryPosition < bufferCacheStartPosition + bufferCache.length) {
			long bufferPosition = grainDirectoryPosition - bufferCacheStartPosition;
			while(grainDirectoryEntries>0) {

				byte[] buff = new byte[4];
				buff[0] =  bufferCache[(int)bufferPosition];
				buff[1] =  bufferCache[(int)bufferPosition+1];
				buff[2] =  bufferCache[(int)bufferPosition+2];
				buff[3] =  bufferCache[(int)bufferPosition+3];
				bufferPosition += 4;
				long grainDirectoryEntry =  (((long) buff[0]) & 0xFFL) + ((((long) buff[1]) & 0xFFL) << 8) + ((((long) buff[2]) & 0xFFL) << 16) + ((((long) buff[3]) & 0xFFL) << 24);

				vmdkDirectory.vmdkDirectoryEntries.add(grainDirectoryEntry);
				grainDirectoryEntries--;
			}
		} else {
			seek(grainDirectoryPosition);
			while(grainDirectoryEntries>0) {
				long grainDirectoryEntry = read32();
				vmdkDirectory.vmdkDirectoryEntries.add(grainDirectoryEntry);
				grainDirectoryEntries--;
			}
		}





		return vmdkDirectory;

	}


	public boolean isTwoPassNeeded() {
		return twoPassNeeded;
	}
	private void initializeVmdkHeader() throws IllegalStateException, IOException {
		this.vmdkHeader = new VmdkHeader();
		//Note: we dont want to use any Helper classes for parsing the stream because most of them close the stream
		int b;
		b = read_();
		vmdkHeader.setMagic(new String(new char[]{(char)b}));
		b = read_();
		vmdkHeader.setMagic(vmdkHeader.getMagic() + (char)b);
		b = read_();
		vmdkHeader.setMagic(vmdkHeader.getMagic() + (char)b);
		b = read_();
		vmdkHeader.setMagic(vmdkHeader.getMagic() + (char)b);
		position = 4L;
		//b = sourceStream.read()
		vmdkHeader.setVersion(read32());
		vmdkHeader.setFlags(read32());
		vmdkHeader.setCapacity(read64());
		vmdkHeader.setGrainNumberSectors(read64());
		vmdkHeader.setDescriptorSector(read64());
		vmdkHeader.setDescriptorSectorNumberOfSectors(read64());
		vmdkHeader.setGrainTableEntries(read32());
		vmdkHeader.setSecondaryGrainDirectorySector(read64());
		vmdkHeader.setGrainDirectorySector(read64());
		vmdkHeader.setMetadataNumberOfSectors(read64());
		boolean isDirty = read_() == 1 ? true : false;
		position++;
		vmdkHeader.setDirty(isDirty);
		boolean compressed = read16() == 1L; // metadata we dont need
		vmdkHeader.setCompressed(compressed);
		if(vmdkHeader.getGrainDirectorySector() == -1) {
			twoPassNeeded = true;
			//grain directory is at the end of the file, so we need to read a header at position -1024

			seek(contentLength - 1024, 1024*1024*5); //5megs of historical data
			initializeVmdkHeader();
		}
		sourceStream.close();
		this.close();

	}

	public void readMarker() throws IOException {
		System.out.println("Reading Marker at Position: " + position);
		long markerNumber = read64();
		long readMarkerSize = read32();
		System.out.println("Marker Number: " + markerNumber + " - size: " + readMarkerSize);
		if(readMarkerSize == 0) {
			long markerType = read32();
			System.out.println(" - Marker Type: " + markerType);
			seek(position + 496);
		} else {
			long sectorNumber = read64();
			long grainDataSizeCompressed = read32();
			System.out.println(" - Grain Size Sector: " + sectorNumber + " - Size: " + grainDataSizeCompressed);
			seek(position + 484);
		}

	}

	int currentGrainTable = 0;
	int grainTablePosition=0;
	int currentGrainTableEntry = 0;
	VmdkGrainTable grainTable = null;
	InputStream tempFileInputStream = null;
	InflaterInputStream tempFileGzipInputStream = null;
	int tempFileReadPosition = 0;
	File tempFile = null;
	long tempFileStartPosition=0;
	long tempFileSize=0;
	/**
	 * Reads the next byte of data from the input stream. The value byte is
	 * returned as an {@code int} in the range {@code 0} to
	 * {@code 255}. If no byte is available because the end of the stream
	 * has been reached, the value {@code -1} is returned. This method
	 * blocks until input data is available, the end of the stream is detected,
	 * or an exception is thrown.
	 *
	 * <p> A subclass must provide an implementation of this method.
	 *
	 * @return the next byte of data, or {@code -1} if the end of the
	 * stream is reached.
	 * @throws IOException if an I/O error occurs.
	 */
	@Override
	public int read() throws IOException {
		//we need to read sourceStream into a file buffer in temp space storing readBackFileBufferSize bytes until we find the grain table
		if(grainTable == null) {
			loadNextGrainTable();
		}

		//lets see where we are
		if(currentGrainTableEntry >= grainTable.vmdkGrainEntries.size()) {
			//we need to read the next grain table
			currentGrainTable++;
			currentGrainTableEntry = 0;
			grainTable = null;
			if(tempFileInputStream != null) {
				tempFileInputStream.close();
				tempFile.delete();
				tempFile = null;
				tempFileInputStream = null;
				tempFileReadPosition = 0;
				tempFileStartPosition = 0;
				tempFileSize = 0;
			}

			if(currentGrainTable >= vmdkDirectory.vmdkDirectoryEntries.size()) {
				System.out.println("End of Directories: " + vmdkDirectory.vmdkDirectoryEntries.size());
				return -1;
			}
			return read();
		}
		long grainTableEntry = grainTable.vmdkGrainEntries.get(currentGrainTableEntry);
		if(grainTableEntry == 0) {
			if(grainTablePosition >= vmdkHeader.getGrainNumberSectors() * 512) {
				//we are at the end of the grain table
				currentGrainTableEntry++;
				grainTablePosition = 0;
				//System.out.println("Zero Fill: " + currentGrainTableEntry);
				return read();
			} else {
				//zero fill
				grainTablePosition++;
				return 0;
			}
		} else {
			long grainDataPosition = grainTableEntry * 512L;
			if(grainDataPosition >= tempFileStartPosition && grainDataPosition < tempFileStartPosition + tempFileSize) {
				if(grainTablePosition >= vmdkHeader.getGrainNumberSectors() * 512) {
					currentGrainTableEntry++;
					grainTablePosition = 0;
					if(tempFileGzipInputStream != null) {
						tempFileGzipInputStream.close();
						tempFileGzipInputStream = null;
					}
					return read();
				} else {
					if(vmdkHeader.isGrainDataCompressed()) {
						int fread;
						if(tempFileGzipInputStream == null) {
							long skipAmount = ((grainDataPosition+grainTablePosition) - tempFileStartPosition) - tempFileReadPosition;
							if(skipAmount >0) {
								tempFileInputStream.skip(skipAmount);
								tempFileReadPosition += skipAmount;
							}
							tempFileInputStream.skip(8);
							tempFileReadPosition+=8;
							byte[] buff = new byte[4];
							int c = tempFileInputStream.read(buff);
							if(c < 0) {
								return -1;
							}
							tempFileReadPosition += 4;
							long compressedSize = (((long) buff[0]) & 0xFFL) + ((((long) buff[1]) & 0xFFL) << 8) + ((((long) buff[2]) & 0xFFL) << 16) + ((((long) buff[3]) & 0xFFL) << 24);
							byte[] compressedData = new byte[(int)compressedSize];
							int bytesRead = tempFileInputStream.read(compressedData);
							tempFileReadPosition+=bytesRead;
							ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
							tempFileGzipInputStream = new InflaterInputStream(bis);
							grainTablePosition++;
							fread = tempFileGzipInputStream.read();
						} else {
							grainTablePosition++;
							fread = tempFileGzipInputStream.read();
							if(fread == -1) {
								throw new IOException("Premature end of file reached while reading grain data");
							}
						}
						return fread;

					} else {
						long skipAmount = ((grainDataPosition+grainTablePosition) - tempFileStartPosition) - tempFileReadPosition;
						if(skipAmount >0) {
							tempFileInputStream.skip(skipAmount);
							tempFileReadPosition += skipAmount;
						}
						tempFileReadPosition++;
						grainTablePosition++;
						int fread = tempFileInputStream.read();
						if(fread == -1) {
							throw new IOException("Premature end of file reached while reading grain data");
						}
						return fread;
					}
				}

			} else {
				throw new IOException("Grain Data Position is not in the temp file buffer - " + grainDataPosition + " File Start: " + tempFileStartPosition + " File End: " + (tempFileStartPosition + tempFileSize));
			}
		}


	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int bytesRead = 0;
		int writePosition = 0;
		if(grainTable == null) {
			loadNextGrainTable();
		}
		//lets see where we are
		if(currentGrainTableEntry >= grainTable.vmdkGrainEntries.size()) {
			//we need to read the next grain table
			currentGrainTable++;
			currentGrainTableEntry = 0;
			grainTable = null;
			if(tempFileInputStream != null) {
				tempFileInputStream.close();
				tempFile.delete();
				tempFile = null;
				tempFileInputStream = null;
				tempFileReadPosition = 0;
				tempFileStartPosition = 0;
				tempFileSize = 0;
			}

			if(currentGrainTable >= vmdkDirectory.vmdkDirectoryEntries.size()) {
				System.out.println("End of Directories: " + vmdkDirectory.vmdkDirectoryEntries.size());
				return -1;
			}
			return read(b,off,len);
		}
		long grainTableEntry = grainTable.vmdkGrainEntries.get(currentGrainTableEntry);
		if(grainTableEntry == 0) {
			if(grainTablePosition >= vmdkHeader.getGrainNumberSectors() * 512) {
				//we are at the end of the grain table
				currentGrainTableEntry++;
				grainTablePosition = 0;
				//System.out.println("Zero Fill: " + currentGrainTableEntry);
				return read(b,off,len);
			} else {
				long remainingZeroFill = vmdkHeader.getGrainNumberSectors() * 512 - grainTablePosition;
				if(remainingZeroFill > len) {
					remainingZeroFill = len;
					//create zero  byte array of size remainingZeroFill
					for(;writePosition<remainingZeroFill;writePosition++) {
						b[off+writePosition] = 0;
					}
					grainTablePosition += writePosition;
					return writePosition;
				} else {
					//create zero  byte array of size remainingZeroFill
					for(;writePosition<remainingZeroFill;writePosition++) {
						b[off+writePosition] = 0;
					}
					grainTablePosition = 0;
					currentGrainTableEntry++;
					int subRead = read(b,off+writePosition,len-writePosition);
					if(subRead == -1) {
						return writePosition;
					} else {
						return writePosition + subRead;
					}
				}
			}
		} else {
			long grainDataPosition = grainTableEntry * 512L;
			if(grainDataPosition >= tempFileStartPosition && grainDataPosition < tempFileStartPosition + tempFileSize) {
				if(grainTablePosition >= vmdkHeader.getGrainNumberSectors() * 512) {
					currentGrainTableEntry++;
					grainTablePosition = 0;
					if(tempFileGzipInputStream != null) {
						tempFileGzipInputStream.close();
						tempFileGzipInputStream = null;
					}
					return read();
				} else {
					if(vmdkHeader.isGrainDataCompressed()) {
						int fread;
						if(tempFileGzipInputStream == null) {
							long skipAmount = ((grainDataPosition+grainTablePosition) - tempFileStartPosition) - tempFileReadPosition;
							if(skipAmount >0) {
								tempFileInputStream.skip(skipAmount);
								tempFileReadPosition += skipAmount;
							}
							tempFileInputStream.skip(8);
							tempFileReadPosition+=8;
							byte[] buff = new byte[4];
							int c = tempFileInputStream.read(buff);
							if(c < 0) {
								return -1;
							}
							tempFileReadPosition += 4;
							long compressedSize = (((long) buff[0]) & 0xFFL) + ((((long) buff[1]) & 0xFFL) << 8) + ((((long) buff[2]) & 0xFFL) << 16) + ((((long) buff[3]) & 0xFFL) << 24);
							byte[] compressedData = new byte[(int)compressedSize];
							int bytesRead = tempFileInputStream.read(compressedData);
							tempFileReadPosition+=bytesRead;
							ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
							tempFileGzipInputStream = new InflaterInputStream(bis);
							grainTablePosition++;
							fread = tempFileGzipInputStream.read();
						} else {
							grainTablePosition++;
							fread = tempFileGzipInputStream.read();
							if(fread == -1) {
								throw new IOException("Premature end of file reached while reading grain data");
							}
						}
						return fread;

					} else {
						long skipAmount = ((grainDataPosition+grainTablePosition) - tempFileStartPosition) - tempFileReadPosition;
						if(skipAmount >0) {
							tempFileInputStream.skip(skipAmount);
							tempFileReadPosition += skipAmount;
						}
						tempFileReadPosition++;
						grainTablePosition++;
						int fread = tempFileInputStream.read();
						if(fread == -1) {
							throw new IOException("Premature end of file reached while reading grain data");
						}
						return fread;
					}
				}

			} else {
				throw new IOException("Grain Data Position is not in the temp file buffer - " + grainDataPosition + " File Start: " + tempFileStartPosition + " File End: " + (tempFileStartPosition + tempFileSize));
			}
		}

	}

	/**
	 * One of several internal read methods that will perform reads on the sourceStream of this instance
	 * and cache the bytes of the QcowHeader to be used later, only if this stream was instantiated
	 * with the cache instruction
	 * @return
	 * @throws IOException
	 */
	private int read_() throws IOException {
		int b = sourceStream.read();
		if (cacheHeaderBytes)
			vmdkHeader.cacheByte(b);
		return b;
	}

	private int read_(byte[] b, int off, int len) throws IOException {
		int bytesRead = sourceStream.read(b, off, len);
		if (cacheHeaderBytes)
			vmdkHeader.cacheBytes(b, off, bytesRead);
		return bytesRead;
	}

	private int read_(byte[] b) throws IOException {
		int bytesRead = sourceStream.read(b);
		if (cacheHeaderBytes)
			vmdkHeader.cacheBytes(b, 0, bytesRead);
		return bytesRead;
	}

	private long read32() throws IOException {
		byte[] buff = new byte[4];
		int c = read_(buff);
		if(c < 0) {
			return -1;
		}
		position += 4;
		return (((long) buff[0]) & 0xFFL) + ((((long) buff[1]) & 0xFFL) << 8) + ((((long) buff[2]) & 0xFFL) << 16) + ((((long) buff[3]) & 0xFFL) << 24);
	}

	private long read16() throws IOException {
		byte[] buff = new byte[2];
		int c = read_(buff);
		if(c < 0) {
			return -1;
		}
		position += 2;
		return (((long) buff[0]) & 0xFFL) + ((((long) buff[1]) & 0xFFL) << 8);
	}

	private long read64() throws IOException {
		long[] byteElement = new long[1];
		read64(byteElement);
		return ((byteElement[0]));
	}

	private int read64(long[] longArray) throws IOException {
		int elementsRead = 0;
		byte[] buff = new byte[longArray.length * 8];

		int c = read_(buff);
		if(c < 0) {
			return -1;
		}

		position += c;
		if(c % 8 > 0) {
			throw new IOException("L1 Table from QCOW2 File is non readable or corrupt.");
		}
		while(c > 0) {
			longArray[elementsRead] = (((long) buff[elementsRead * 8]) & 0xFFL) + ((((long) buff[1 + elementsRead * 8]) & 0xFFL) << 8) + ((((long) buff[2 + elementsRead * 8]) & 0xFFL) << 16) + ((((long) buff[3 + elementsRead * 8]) & 0xFFL) << 24) + ((((long) buff[4 + elementsRead * 8]) & 0xFFL) << 32) + ((((long) buff[5 + elementsRead * 8]) & 0xFFL) << 40) + ((((long) buff[6 + elementsRead * 8]) & 0xFFL) << 48) + ((((long) buff[7 + elementsRead * 8]) & 0xFFL) << 56);
			c -= 8;
			elementsRead++;
		}


		return elementsRead;
	}

	private Long seek(long destinationPos) throws IOException {

		if(destinationPos < position) {
			throw new IOException("Invalid attempt to seek backwards in a stream. Can only seek forward.");
		}
		long seekDistance = destinationPos - position;
		while(seekDistance > 0) {
			byte[] throwAway = new byte[1024];
			int readLen = DefaultGroovyMethods.asType(Math.min(1024, seekDistance), Integer.class);
			int c = read_(throwAway, 0, readLen);
			if(c < 0) {
				throw new IOException("Premature end of file reached while seeking to position");
			}
			seekDistance -= c;
		}
		return position = destinationPos;
	}

	byte[] bufferCache;
	long bufferCacheStartPosition=0;

	private Long seek(long destinationPos, long lookbackCacheSize) throws IOException {

		if(destinationPos < position) {
			throw new IOException("Invalid attempt to seek backwards in a stream. Can only seek forward.");
		}
		//create lookback cache of size
		if(lookbackCacheSize > 0) {
			bufferCache = new byte[(int)lookbackCacheSize];
			seek(destinationPos - lookbackCacheSize);
			bufferCacheStartPosition = position;
			read_(bufferCache);

		} else {
			bufferCache = null;
			bufferCacheStartPosition=0;
			long seekDistance = destinationPos - position;
			while(seekDistance > 0) {
				byte[] throwAway = new byte[1024];
				int readLen = DefaultGroovyMethods.asType(Math.min(1024, seekDistance), Integer.class);
				int c = read_(throwAway, 0, readLen);
				if(c < 0) {
					throw new IOException("Premature end of file reached while seeking to position");
				}
				seekDistance -= c;
			}
		}


		return position = destinationPos;
	}

	protected void loadNextGrainTable() throws IOException {
		//System.out.println("Next Grain Table: " + currentGrainTable);
		long grainTableSector = vmdkDirectory.vmdkDirectoryEntries.get(currentGrainTable);
		//if zero it means we have to zero fill this grain table
		if(grainTableSector == 0) {
			grainTable = new VmdkGrainTable();
			for(int x=0;x < vmdkHeader.getGrainTableEntries().intValue();x++) {
				grainTable.vmdkGrainEntries.add(0L);
			}
		} else {
			//we need to read the grain table
			long grainTablePosition = grainTableSector* 512L;
			if(position < grainTablePosition - readBackFileBufferSize) {
				seek(grainTablePosition - readBackFileBufferSize);
			}

			tempFile = File.createTempFile("vmdk", "grainTableBuffer");
			OutputStream fileOutputStream = new FileOutputStream(tempFile);
			BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
			tempFileStartPosition = position;
			tempFileSize = 0;
			//read up to grain table position into file
			byte[] buffer = new byte[512];

			int bytesRead = 0;
			while(position < grainTablePosition) {
				int remainingAmount = (int)(grainTablePosition - position);
				bytesRead = read_(buffer,0,Math.min(512,remainingAmount));
				position+=bytesRead;
				if(bytesRead < 0) {
					throw new IOException("Premature end of file reached while reading grain table");
				}
				tempFileSize += bytesRead;
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			outputStream.close();
			tempFileInputStream = new FileInputStream(tempFile);

			System.out.println("Reading Grain Table: " + position);
			grainTable = new VmdkGrainTable();
			for(int counter = 0 ; counter < vmdkHeader.getGrainTableEntries().intValue(); counter++) {
				grainTable.vmdkGrainEntries.add(read32());
			}

		}
		//we need to read until we have a grain table
		System.out.println("Grain Table: " + currentGrainTable + " - " + grainTable.vmdkGrainEntries.size());

	}
}
