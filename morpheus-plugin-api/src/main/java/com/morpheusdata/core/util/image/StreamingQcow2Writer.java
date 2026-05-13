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

import org.apache.commons.codec.Charsets;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * A class for generating QCOW2 format disk images in a streaming manner.
 * This class constructs the QCOW2 header, refcount tables, and mapping tables
 * based on the specified virtual disk size and data ranges. It supports writing
 * the QCOW2 structures to output streams or random access files, and copying
 * data clusters from input streams or random access files while skipping sparse regions.
 *
 * @author David Estes
 */
public class StreamingQcow2Writer {

	private static final long CLUSTER_SIZE = 65536;
	private static final long REPORT_INTERVAL_BYTES = 500_000_000; // 500 MB

	private long inputSize;
	private int l1Clusters;
	private long l1Offset;
	private int refcountTableClusters;
	private long firstDataCluster;
	public List<Long> dataClusters;
	Long dataClusterSize = 0L;
	public DataClusterIterable dataClusterIterable;

	/**
	 * Constructs a StreamingQcow2Writer for generating QCOW2 format disk images.
	 * Initializes the QCOW2 header structure, calculates cluster mappings, and sets up
	 * the refcount tables based on the provided input size and data ranges.
	 *
	 * @param inputSize the virtual disk size in bytes
	 * @param ranges an iterable collection of byte ranges that contain actual data (non-sparse regions)
	 * @throws IllegalArgumentException if the data clusters in ranges are not sorted
	 */
	public StreamingQcow2Writer(long inputSize, Iterable<Range<Long>> ranges) {
		this.inputSize = inputSize;

		dataClusterIterable = new DataClusterIterable(ranges);
		this.dataClusters = new ArrayList<>();

		Long lastCluster = null;
		Iterator<Range<Long>> rangeIterator = ranges.iterator();
		while (rangeIterator.hasNext()) {
			Range<Long> range = rangeIterator.next();

			long fromCluster = range.getStart() / CLUSTER_SIZE;
			long toCluster = divideAndRoundUp(range.getEnd(), CLUSTER_SIZE);

			if (lastCluster != null) {
				if (fromCluster < lastCluster) {
					throw new IllegalArgumentException("Data clusters are not sorted");
				} else if (fromCluster == lastCluster) {
					fromCluster++;
				}
			}
			lastCluster = toCluster - 1;

			for (long cluster = fromCluster; cluster < toCluster; cluster++) {
//				dataClusters.add(cluster);
				dataClusterSize++;
			}
		}

		long guestClusters = divideAndRoundUp(inputSize, CLUSTER_SIZE);
		long l2Tables = divideAndRoundUp(guestClusters * 8, CLUSTER_SIZE);

		this.l1Clusters = (int) divideAndRoundUp(l2Tables * 8, CLUSTER_SIZE);

		long refcountBlocks = 1;
		this.refcountTableClusters = 1;

		while (true) {
			long totalClusters = 1 + refcountTableClusters + refcountBlocks + l1Clusters + l2Tables + dataClusterSize;
			long newRefcountBlocks = divideAndRoundUp(totalClusters * 2, CLUSTER_SIZE);
			if (newRefcountBlocks == refcountBlocks) {
				break;
			}
			refcountBlocks = newRefcountBlocks;
			this.refcountTableClusters = (int) divideAndRoundUp(refcountBlocks * 8, CLUSTER_SIZE);
		}

		this.l1Offset = CLUSTER_SIZE * (1 + refcountTableClusters + refcountBlocks);
		this.firstDataCluster = 1 + refcountTableClusters + refcountBlocks + l1Clusters + l2Tables;
	}

	/**
	 * Divides two numbers and rounds up to the nearest integer.
	 *
	 * @param a the dividend
	 * @param b the divisor
	 * @return the result of a/b rounded up to the nearest integer
	 */
	private static long divideAndRoundUp(long a, long b) {
		return (a + b - 1) / b;
	}

	/**
	 * Calculates the total file size of the QCOW2 image.
	 *
	 * @return the total file size in bytes (total clusters * cluster size)
	 */
	public long fileSize() {
		return CLUSTER_SIZE * totalClusters();
	}

	/**
	 * Calculates the total number of clusters in the QCOW2 image.
	 *
	 * @return the total number of clusters (header + metadata + data)
	 */
	private long totalClusters() {
		return firstDataCluster + dataClusterSize;
	}

	/**
	 * Calculates the total number of clusters needed to represent the guest virtual disk.
	 *
	 * @return the number of clusters in the virtual disk
	 */
	public long totalGuestClusters() {
		return divideAndRoundUp(inputSize, CLUSTER_SIZE);
	}

	/**
	 * Writes the complete QCOW2 header, refcount table, and mapping tables to an output stream.
	 * This includes the QCOW2 magic bytes, version 3 format header fields, L1/L2 mapping tables,
	 * and refcount structures necessary for a valid QCOW2 image.
	 *
	 * @param outputStream the output stream to write the QCOW2 header to
	 * @throws IOException if an I/O error occurs during writing
	 */
	public void writeHeader(OutputStream outputStream) throws IOException {
		// Magic
		//String magic = "QFI" + (char) 0xFB;
		outputStream.write('Q');
		outputStream.write('F');
		outputStream.write('I');
		outputStream.write(0xFB);

		//outputStream.write(magic.getBytes(StandardCharsets.US_ASCII));

		// Version
		writeInt(outputStream, 3);

		// Backing file name offset (0 = no backing file)
		writeLong(outputStream, 0);

		// Backing file name length
		writeInt(outputStream, 0);

		// Number of bits per cluster address, 1<<bits is the cluster size
		assert CLUSTER_SIZE == 1 << 16;
		writeInt(outputStream, 16);

		// Virtual disk size in bytes
		writeLong(outputStream, inputSize);

		// Encryption method (none)
		writeInt(outputStream, 0);

		// L1 table size (number of entries)
		long l2EntriesPerCluster = CLUSTER_SIZE / 8;
		long l1Entries = divideAndRoundUp(totalGuestClusters(), l2EntriesPerCluster);
		writeInt(outputStream, (int) l1Entries);

		// L1 table offset
		writeLong(outputStream, l1Offset);

		// Refcount table offset
		writeLong(outputStream, CLUSTER_SIZE);

		// Refcount table length in clusters
		writeInt(outputStream, refcountTableClusters);

		// Number of snapshots in the image
		writeInt(outputStream, 0);

		// Offset of the snapshot table (must be aligned to clusters)
		writeLong(outputStream, 0);

		//incompatible_features
		writeLong(outputStream, 0);
		//compatible_features
		writeLong(outputStream, 0);
		//autoclear_features
		writeLong(outputStream, 0);
		//recount_order 4
		writeInt(outputStream, 4);
		//header length 104
		writeInt(outputStream, 104);


		outputStream.write(new byte[(int) CLUSTER_SIZE - 104]);

		writeRefcountTable(outputStream);
		writeMappingTable(outputStream);
	}

	/**
	 * Writes the complete QCOW2 header, refcount table, and mapping tables to a random access file.
	 * This method seeks to the beginning of the file and writes the QCOW2 magic bytes, version 3
	 * format header fields, L1/L2 mapping tables, and refcount structures necessary for a valid QCOW2 image.
	 *
	 * @param randomAccessFile the random access file to write the QCOW2 header to (will seek to position 0)
	 * @throws IOException if an I/O error occurs during writing
	 */
	public void writeHeader(RandomAccessFile randomAccessFile) throws IOException {
		// Seek to the beginning of the file
		FileChannel channel = randomAccessFile.getChannel();
		channel.position(0);

		// Write fixed 104-byte QCOW2 v3 header + pad to cluster boundary (all via one FlushingBuffer)
		FlushingBuffer fb = new FlushingBuffer(channel, 0, (int) CLUSTER_SIZE);

		// Magic
		//String magic = "QFI" + (char) 0xFB;
		fb.writeByte('Q');
		fb.writeByte('F');
		fb.writeByte('I');
		fb.writeByte(0xFB);
		// Version
		fb.writeInt(3);

		// Backing file name offset (0 = no backing file)
		fb.writeLong(0);

		// Backing file name length
		fb.writeInt(0);

		// Number of bits per cluster address, 1<<bits is the cluster size
		assert CLUSTER_SIZE == 1 << 16;
		fb.writeInt(16);

		// Virtual disk size in bytes
		fb.writeLong(inputSize);

		// Encryption method (none)
		fb.writeInt(0);

		// L1 table size (number of entries)
		long l2EntriesPerCluster = CLUSTER_SIZE / 8;
		long l1Entries = divideAndRoundUp(totalGuestClusters(), l2EntriesPerCluster);
		fb.writeInt((int) l1Entries);

		// L1 table offset
		fb.writeLong(l1Offset);

		// Refcount table offset
		fb.writeLong(CLUSTER_SIZE);

		// Refcount table length in clusters
		fb.writeInt(refcountTableClusters);

		// Number of snapshots in the image
		fb.writeInt(0);

		// Offset of the snapshot table (must be aligned to clusters)
		fb.writeLong(0);

		//incompatible_features
		fb.writeLong(0);
		//compatible_features
		fb.writeLong(0);
		//autoclear_features
		fb.writeLong(0);
		//recount_order 4
		fb.writeInt(4);
		//header length 104
		fb.writeInt(104);


		// Pad remainder of first cluster with zeros (buf was zero-initialised by allocate)
		// buf.position() is now 104; advance to CLUSTER_SIZE
		fb.buf.position((int) CLUSTER_SIZE);
		fb.flush();

		long refcountStartPos = CLUSTER_SIZE;
		long l1StartPos = writeRefcountTable(channel, refcountStartPos);
		writeL1AndL2Tables(channel, l1StartPos);
	}

	/**
	 * Writes the QCOW2 refcount table and refcount blocks to an output stream.
	 * The refcount table tracks cluster usage for copy-on-write and snapshot functionality.
	 *
	 * @param outputStream the output stream to write the refcount table to
	 * @throws IOException if an I/O error occurs during writing
	 */
	private void writeRefcountTable(OutputStream outputStream) throws IOException {
		long refcountBlocks = divideAndRoundUp(totalClusters() * 2, CLUSTER_SIZE);

		// Table
		for (long block = 0; block < refcountBlocks; block++) {
			writeLong(outputStream, CLUSTER_SIZE * (1 + refcountTableClusters + block));
		}

		long refcountEntriesPerCluster = CLUSTER_SIZE / 8;
		long lastClusterEntries = refcountBlocks % refcountEntriesPerCluster;
		if (lastClusterEntries > 0) {
			for (long i = lastClusterEntries; i < refcountEntriesPerCluster; i++) {
				writeLong(outputStream, 0);
			}
		}

		// Blocks
		for (long i = 0; i < totalClusters(); i++) {
			writeShort(outputStream, (short) 1);
		}

		long blockEntriesPerCluster = CLUSTER_SIZE / 2;
		long lastBlockClusterEntries = totalClusters() % blockEntriesPerCluster;
		if (lastBlockClusterEntries > 0) {
			for (long i = lastBlockClusterEntries; i < blockEntriesPerCluster; i++) {
				writeShort(outputStream, (short) 0);
			}
		}
	}

	/**
	 * Writes the QCOW2 refcount table and refcount blocks to the FileChannel starting
	 * at startPos, using cluster-sized FlushingBuffer writes.
	 * Returns the file position immediately after the refcount structures.
	 */
	private long writeRefcountTable(FileChannel channel, long startPos) throws IOException {
		long refcountBlocks = divideAndRoundUp(totalClusters() * 2, CLUSTER_SIZE);
		FlushingBuffer fb = new FlushingBuffer(channel, startPos, (int) CLUSTER_SIZE);

		// Refcount table: one 8-byte pointer per refcount block
		for (long block = 0; block < refcountBlocks; block++) {
			fb.writeLong(CLUSTER_SIZE * (1 + refcountTableClusters + block));
		}
		long refcountEntriesPerCluster = CLUSTER_SIZE / 8;
		long lastClusterEntries = refcountBlocks % refcountEntriesPerCluster;
		if (lastClusterEntries > 0) {
			for (long i = lastClusterEntries; i < refcountEntriesPerCluster; i++) {
				fb.writeLong(0);
			}
		}
		fb.flush();

		// Refcount blocks: one 2-byte entry per cluster (value = 1 = in use)
		for (long i = 0; i < totalClusters(); i++) {
			fb.writeShort((short) 1);
		}
		long blockEntriesPerCluster = CLUSTER_SIZE / 2;
		long lastBlockClusterEntries = totalClusters() % blockEntriesPerCluster;
		if (lastBlockClusterEntries > 0) {
			for (long i = lastBlockClusterEntries; i < blockEntriesPerCluster; i++) {
				fb.writeShort((short) 0);
			}
		}
		fb.flush();

		return fb.currentPosition();
	}

	/**
	 * Writes the QCOW2 L1 and L2 mapping tables to an output stream.
	 * These tables map guest virtual disk addresses to physical cluster offsets in the QCOW2 file.
	 *
	 * @param outputStream the output stream to write the mapping tables to
	 * @throws IOException if an I/O error occurs during writing
	 */
	private void writeMappingTable(OutputStream outputStream) throws IOException {


		// L1 table
		long l1EntriesPerCluster = CLUSTER_SIZE / 8;
		long l1Entries = divideAndRoundUp(totalGuestClusters(), l1EntriesPerCluster);
		for (long entry = 0; entry < l1Entries; entry++) {
			long offset = l1Offset + l1Clusters * CLUSTER_SIZE + entry * CLUSTER_SIZE;
			long l1Entry = offset | (1L << 63);
			writeLong(outputStream, l1Entry);
		}

		long lastClusterEntries = l1Entries % l1EntriesPerCluster;
		if (lastClusterEntries > 0) {
			for (long i = lastClusterEntries; i < l1EntriesPerCluster; i++) {
				writeLong(outputStream, 0);
			}
		}

		// L2 table

		Iterator<Long> iterator = dataClusterIterable.iterator();
		Long nextCluster = null;
		if(iterator.hasNext()) {
			nextCluster = iterator.next();
		}
		long clusterCounter=0;
		for (long guestCluster = 0; guestCluster < totalGuestClusters(); guestCluster++) {
			long l2Entry = 0L;
			if(nextCluster == guestCluster) {
				l2Entry = clusterCounter + firstDataCluster;
				if(iterator.hasNext()) {
					nextCluster = iterator.next();
					clusterCounter++;
				} else {
					nextCluster = null;
				}
			} else {
				l2Entry = 0L;
			}

			long offset = l2Entry*CLUSTER_SIZE;
			if (offset != 0) {
				offset |= (0L << 62) | (1L << 63);
			}

			writeLong(outputStream, offset);
		}

		long l2EntriesPerCluster = CLUSTER_SIZE / 8;
		lastClusterEntries = totalGuestClusters() % l2EntriesPerCluster;
		if (lastClusterEntries > 0) {
			for (long i = lastClusterEntries; i < l2EntriesPerCluster; i++) {
				writeLong(outputStream, 0);
			}
		}
	}

	/**
	 * Writes the QCOW2 L1 and L2 mapping tables to a random access file.
	 * These tables map guest virtual disk addresses to physical cluster offsets in the QCOW2 file.
	 *
	 * @param randomAccessFile the random access file to write the mapping tables to
	 * @throws IOException if an I/O error occurs during writing
	 */
	private void writeL1AndL2Tables(FileChannel channel, long startPos) throws IOException {
		FlushingBuffer fb = new FlushingBuffer(channel, startPos, (int) CLUSTER_SIZE);

		// L1 table: each entry points to its L2 table's file offset
		long l1EntriesPerCluster = CLUSTER_SIZE / 8;
		long l1Entries = divideAndRoundUp(totalGuestClusters(), l1EntriesPerCluster);
		for (long entry = 0; entry < l1Entries; entry++) {
			long offset = l1Offset + l1Clusters * CLUSTER_SIZE + entry * CLUSTER_SIZE;
			long l1Entry = offset | (1L << 63);
			fb.writeLong(l1Entry);
		}

		long lastClusterEntries = l1Entries % l1EntriesPerCluster;
		if (lastClusterEntries > 0) {
			for (long i = lastClusterEntries; i < l1EntriesPerCluster; i++) {
				fb.writeLong(0);
			}
		}
		fb.flush();

		// L2 tables: all entries initialised to 0 (sparse)
		// copyData will overwrite non-zero entries with actual data offsets
		for (long i = 0; i < totalGuestClusters(); i++) {
			fb.writeLong(0);
		}

		long l2EntriesPerCluster = CLUSTER_SIZE / 8;
		lastClusterEntries = totalGuestClusters() % l2EntriesPerCluster;
		if (lastClusterEntries > 0) {
			for (long i = lastClusterEntries; i < l2EntriesPerCluster; i++) {
				fb.writeLong(0);
			}
		}
		fb.flush();
	}

	/**
	 * Copies data clusters from a random access file to an output stream.
	 * Only copies clusters identified in the data ranges provided during construction,
	 * skipping sparse/empty regions. Progress is reported to stderr at regular intervals.
	 *
	 * @param reader the random access file to read data from
	 * @param writer the output stream to write data to
	 * @throws IOException if an I/O error occurs during copying
	 */
	public void copyData(RandomAccessFile reader, OutputStream writer) throws IOException {
		long written = firstDataCluster * CLUSTER_SIZE;
		byte[] buffer = new byte[(int) CLUSTER_SIZE];
		for (Long cluster : dataClusterIterable) {
			reader.seek(cluster * CLUSTER_SIZE);
			int bytesRead = reader.read(buffer);
			writer.write(buffer, 0, bytesRead);

			if ((written + CLUSTER_SIZE) / REPORT_INTERVAL_BYTES != written / REPORT_INTERVAL_BYTES) {
				System.err.printf("%d/%d bytes written%n", written + CLUSTER_SIZE, fileSize());
			}
			written += CLUSTER_SIZE;
		}
	}

	long position = 0;
	/**
	 * Copies data clusters from an input stream to an output stream.
	 * Only copies clusters identified in the data ranges provided during construction,
	 * skipping sparse/empty regions by advancing the input stream position.
	 * Progress is reported to stderr at regular intervals.
	 *
	 * @param inputStream the input stream to read data from
	 * @param writer the output stream to write data to (will be buffered)
	 * @throws IOException if an I/O error occurs during copying
	 */
	public void copyData(InputStream inputStream, OutputStream writer) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(writer, (int)(CLUSTER_SIZE * 2));
		long written = firstDataCluster * CLUSTER_SIZE;
		byte[] buffer = new byte[(int) CLUSTER_SIZE];
		for (Long cluster : dataClusterIterable) {
			long desiredPosition = cluster * CLUSTER_SIZE;
			long skipBytes = desiredPosition - position;
			if(skipBytes > 0) {
				inputStream.skip(skipBytes);
				position += skipBytes;
			}


			int bytesRead = inputStream.read(buffer);

			if(bytesRead > 0) {
				bos.write(buffer,0,bytesRead);
				position += bytesRead;
			}
			if ((written + CLUSTER_SIZE) / REPORT_INTERVAL_BYTES != written / REPORT_INTERVAL_BYTES) {
				System.err.printf("%d/%d bytes written%n", written + CLUSTER_SIZE, fileSize());
			}
			written += CLUSTER_SIZE;
		}
		bos.flush();
	}

	/**
	 * Copies data clusters from an input stream to a RandomAccessFile using two
	 * independent FlushingBuffers — one for L2 table entries, one for data clusters —
	 * both writing sequentially forward with no backward seeks.
	 *
	 * L2 entries are written for every guest cluster: 0 for zero-filled/sparse clusters,
	 * and the actual data file offset for non-zero clusters. Data clusters are coalesced
	 * into 1MB writes for efficiency on high-latency storage.
	 *
	 * @param inputStream the input stream to read data from
	 * @param writer      the RandomAccessFile wrapping the destination QCOW2 file
	 * @throws IOException if an I/O error occurs
	 */
	public void copyData(InputStream inputStream, RandomAccessFile writer) throws IOException {
		long written = firstDataCluster * CLUSTER_SIZE;
		position = 0;
		FileChannel channel = writer.getChannel();

		// L2 buffer: sequential, starts at beginning of L2 table region
		long l2StartPos = l1Offset + l1Clusters * CLUSTER_SIZE;
		FlushingBuffer l2Buf = new FlushingBuffer(channel, l2StartPos, (int) CLUSTER_SIZE);

		// Data buffer: sequential, starts at beginning of data cluster region, 1MB buffer
		long dataStartPos = firstDataCluster * CLUSTER_SIZE;
		FlushingBuffer dataBuf = new FlushingBuffer(channel, dataStartPos, 16 * (int) CLUSTER_SIZE);

		byte[] buffer = new byte[(int) CLUSTER_SIZE];
		long dataClusterIndex = 0;
		boolean streamEnded = false;

		for (Long cluster : dataClusterIterable) {
			if (streamEnded) {
				// Stream ended, mark all remaining clusters as empty
				l2Buf.writeLong(0L);

				if ((written + CLUSTER_SIZE) / REPORT_INTERVAL_BYTES != written / REPORT_INTERVAL_BYTES) {
					System.err.printf("%d/%d bytes written%n", written + CLUSTER_SIZE, fileSize());
				}
				written += CLUSTER_SIZE;
				dataClusterIndex++;
				continue;
			}

			long desiredPosition = cluster * CLUSTER_SIZE;
			long skipBytes = desiredPosition - position;
			if(skipBytes > 0) {
				long skipped = inputStream.skip(skipBytes);
				if (skipped < skipBytes) {
					// Could not skip, stream likely ended
					streamEnded = true;
					l2Buf.writeLong(0L);

					if ((written + CLUSTER_SIZE) / REPORT_INTERVAL_BYTES != written / REPORT_INTERVAL_BYTES) {
						System.err.printf("%d/%d bytes written%n", written + CLUSTER_SIZE, fileSize());
					}
					written += CLUSTER_SIZE;
					dataClusterIndex++;
					continue;
				}
				position += skipped;
			}

			// Read the full cluster, handling incomplete reads
			int totalBytesRead = 0;
			while (totalBytesRead < CLUSTER_SIZE) {
				int bytesRead = inputStream.read(buffer, totalBytesRead, (int) CLUSTER_SIZE - totalBytesRead);
				if (bytesRead == -1) {
					// End of stream reached
					streamEnded = true;
					if (totalBytesRead == 0) {
						// No data read for this cluster at all, mark as empty
						l2Buf.writeLong(0L);

						if ((written + CLUSTER_SIZE) / REPORT_INTERVAL_BYTES != written / REPORT_INTERVAL_BYTES) {
							System.err.printf("%d/%d bytes written%n", written + CLUSTER_SIZE, fileSize());
						}
						written += CLUSTER_SIZE;
						dataClusterIndex++;
						break;
					} else {
						// Partial data read, fill the rest with zeros
						for (int i = totalBytesRead; i < CLUSTER_SIZE; i++) {
							buffer[i] = 0;
						}
					}
					break;
				}
				totalBytesRead += bytesRead;
			}

			if(totalBytesRead > 0) {
				// Check if the entire buffer is zero-filled
				boolean isZeroFilled = true;
				for (int i = 0; i < CLUSTER_SIZE; i++) {
					if (buffer[i] != 0) {
						isZeroFilled = false;
						break;
					}
				}

				if (isZeroFilled) {
					// L2 entry stays 0 (sparse), advance L2 pointer
					l2Buf.writeLong(0L);
				} else {
					// Write the data and update the L2 table to point to it
					long dataPosition = dataBuf.currentPosition();
					long offset = dataPosition | (0L << 62) | (1L << 63);
					l2Buf.writeLong(offset);
					dataBuf.writeBytes(buffer, 0, (int) CLUSTER_SIZE);
				}

				position += totalBytesRead;
			}

			if ((written + CLUSTER_SIZE) / REPORT_INTERVAL_BYTES != written / REPORT_INTERVAL_BYTES) {
				System.err.printf("%d/%d bytes written%n", written + CLUSTER_SIZE, fileSize());
			}
			written += CLUSTER_SIZE;
			dataClusterIndex++;
		}

		l2Buf.flush();
		dataBuf.flush();
	}

//	public void copyData(InputStream reader, OutputStream writer) throws IOException {
//		long written = firstDataCluster * CLUSTER_SIZE;
//		byte[] buffer = new byte[(int) CLUSTER_SIZE];
//		for (Long cluster : dataClusters) {
//			reader.seek(cluster * CLUSTER_SIZE);
//			int bytesRead = reader.read(buffer);
//			writer.write(buffer, 0, bytesRead);
//
//			if ((written + CLUSTER_SIZE) / REPORT_INTERVAL_BYTES != written / REPORT_INTERVAL_BYTES) {
//				System.err.printf("%d/%d bytes written%n", written + CLUSTER_SIZE, fileSize());
//			}
//			written += CLUSTER_SIZE;
//		}
//	}

	/**
	 * A self-flushing buffer that accumulates writes into a configurable-sized ByteBuffer
	 * and flushes to a FileChannel using positional writes, so multiple FlushingBuffers
	 * can write to different regions of the same file simultaneously without seeking.
	 */
	private static class FlushingBuffer {
		private final ByteBuffer buf;
		private final FileChannel channel;
		private long position;

		FlushingBuffer(FileChannel channel, long startPosition, int bufferSize) {
			this.channel = channel;
			this.position = startPosition;
			this.buf = ByteBuffer.allocate(bufferSize).order(ByteOrder.BIG_ENDIAN);
		}

		void writeByte(int value) throws IOException {
			buf.put((byte) value);
			if (!buf.hasRemaining()) flush();
		}

		void writeInt(int value) throws IOException {
			buf.putInt(value);
			if (!buf.hasRemaining()) flush();
		}

		void writeLong(long value) throws IOException {
			buf.putLong(value);
			if (!buf.hasRemaining()) flush();
		}

		void writeShort(short value) throws IOException {
			buf.putShort(value);
			if (!buf.hasRemaining()) flush();
		}

		void writeBytes(byte[] data, int offset, int length) throws IOException {
			int remaining = length;
			int srcOffset = offset;
			while (remaining > 0) {
				int chunk = Math.min(remaining, buf.remaining());
				buf.put(data, srcOffset, chunk);
				srcOffset += chunk;
				remaining -= chunk;
				if (!buf.hasRemaining()) flush();
			}
		}

		/** Returns the file offset where the next byte written will land. */
		long currentPosition() {
			return position + buf.position();
		}

		void flush() throws IOException {
			if (buf.position() > 0) {
				buf.flip();
				channel.write(buf, position);
				position += buf.limit();
				buf.clear();
			}
		}
	}

	/**
	 * Calculates the file offset of the L2 table entry for a given guest cluster.
	 * The L2 table maps guest cluster indices to physical cluster offsets in the QCOW2 file.
	 *
	 * @param guestCluster the guest cluster index
	 * @return the file offset (in bytes) of the L2 table entry for this cluster
	 */
	private long calculateL2EntryOffset(long guestCluster) {
		long l2EntriesPerCluster = CLUSTER_SIZE / 8;

		// Calculate which L2 table this cluster belongs to
		long l2TableIndex = guestCluster / l2EntriesPerCluster;

		// Calculate the offset within that L2 table
		long l2EntryIndex = guestCluster % l2EntriesPerCluster;

		// Calculate the file offset of the L2 table
		long l2TableOffset = l1Offset + l1Clusters * CLUSTER_SIZE + l2TableIndex * CLUSTER_SIZE;

		// Calculate the file offset of the specific entry
		return l2TableOffset + l2EntryIndex * 8;
	}

	/**
	 * Writes a 32-bit integer value in big-endian byte order to an output stream.
	 *
	 * @param outputStream the output stream to write to
	 * @param value the integer value to write
	 * @throws IOException if an I/O error occurs during writing
	 */
	private void writeInt(OutputStream outputStream, int value) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
		buffer.putInt(value);
		outputStream.write(buffer.array());
	}

	/**
	 * Writes a 32-bit integer value in big-endian byte order to a random access file.
	 *
	 * @param randomAccessFile the random access file to write to
	 * @param value the integer value to write
	 * @throws IOException if an I/O error occurs during writing
	 */
	private void writeInt(RandomAccessFile randomAccessFile, int value) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
		buffer.putInt(value);
		randomAccessFile.write(buffer.array());
	}

	/**
	 * Writes a 64-bit long value in big-endian byte order to an output stream.
	 *
	 * @param outputStream the output stream to write to
	 * @param value the long value to write
	 * @throws IOException if an I/O error occurs during writing
	 */
	private void writeLong(OutputStream outputStream, long value) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
		buffer.putLong(value);
		outputStream.write(buffer.array());
	}

	/**
	 * Writes a 64-bit long value in big-endian byte order to a random access file.
	 *
	 * @param randomAccessFile the random access file to write to
	 * @param value the long value to write
	 * @throws IOException if an I/O error occurs during writing
	 */
	private void writeLong(RandomAccessFile randomAccessFile, long value) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
		buffer.putLong(value);
		randomAccessFile.write(buffer.array());
	}

	/**
	 * Writes a 16-bit short value in big-endian byte order to an output stream.
	 *
	 * @param outputStream the output stream to write to
	 * @param value the short value to write
	 * @throws IOException if an I/O error occurs during writing
	 */
	private void writeShort(OutputStream outputStream, short value) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN);
		buffer.putShort(value);
		outputStream.write(buffer.array());
	}

	/**
	 * Writes a 16-bit short value in big-endian byte order to a random access file.
	 *
	 * @param randomAccessFile the random access file to write to
	 * @param value the short value to write
	 * @throws IOException if an I/O error occurs during writing
	 */
	private void writeShort(RandomAccessFile randomAccessFile, short value) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN);
		buffer.putShort(value);
		randomAccessFile.write(buffer.array());
	}

	/**
	 * Represents a range with a start and end value.
	 * Used to define byte ranges that contain actual data in the virtual disk.
	 *
	 * @param <T> the type of the range bounds, must be comparable
	 */
	public static class Range<T extends Comparable<? super T>> {
		private final T start;
		private final T end;

		/**
		 * Constructs a new Range with the specified start and end values.
		 *
		 * @param start the start value of the range (inclusive)
		 * @param end the end value of the range (exclusive)
		 */
		public Range(T start, T end) {
			this.start = start;
			this.end = end;
		}

		/**
		 * Returns the start value of this range.
		 *
		 * @return the start value
		 */
		public T getStart() {
			return start;
		}

		/**
		 * Returns the end value of this range.
		 *
		 * @return the end value
		 */
		public T getEnd() {
			return end;
		}
	}

	/**
	 * An iterable that converts byte ranges into cluster indices.
	 * This allows iteration over all clusters that contain actual data,
	 * handling overlapping ranges and ensuring clusters are returned in sorted order.
	 */
	public static class DataClusterIterable implements Iterable<Long> {
		private final Iterable<Range<Long>> ranges;
		private Range<Long> currentRange = null;
		/**
		 * Constructs a DataClusterIterable from the given byte ranges.
		 *
		 * @param ranges the byte ranges containing actual data
		 */
		public DataClusterIterable(Iterable<Range<Long>> ranges) {
			this.ranges = ranges;
		}

		/**
		 * Returns an iterator over cluster indices in sorted order.
		 * Handles range boundaries and ensures no duplicate clusters are returned.
		 *
		 * @return an iterator of cluster indices
		 */
		@Override
		public Iterator<Long> iterator() {

			return new Iterator<Long>() {
				private Long nextCluster = null;
				public Iterator<Range<Long>> rangeIterator = ranges.iterator();
				private Range<Long> currentRange = null;
				private Long lastCluster = null;
				@Override
				public boolean hasNext() {
					if(nextCluster == null) {
						if(!loadNext()) {
							return false;
						} else {
							return true;
						}
					} else {
						return true;
					}
				}

				/**
				 * Loads the next cluster index from the ranges.
				 * Handles range transitions and ensures no duplicate clusters.
				 *
				 * @return true if a next cluster was loaded, false if no more clusters exist
				 */
				public boolean loadNext() {

					if(currentRange == null) {
						if (!rangeIterator.hasNext()) {
							return false;
						}
						currentRange = rangeIterator.next();
					}
					while(true) {
						long fromCluster = currentRange.getStart() / CLUSTER_SIZE;
						long toCluster = divideAndRoundUp(currentRange.getEnd(), CLUSTER_SIZE);
						if(nextCluster == null) {
							nextCluster = fromCluster;
							if(lastCluster != null && nextCluster <= lastCluster) {
								nextCluster++;
								if(nextCluster >= toCluster) {
									if (!rangeIterator.hasNext()) {
										return false;
									}
									currentRange = rangeIterator.next();
									nextCluster = null;
									continue;
								}
							}
							return true;
						}
						if(nextCluster < toCluster-1) {
							nextCluster++;
							return true;
						} else {
							if (!rangeIterator.hasNext()) {
								nextCluster = null;
								currentRange = null;
								return false;

							}
							currentRange = rangeIterator.next();
							lastCluster = nextCluster;
							nextCluster = null;
						}
					}
				}

				@Override
				public Long next() {

					if (!hasNext()) {
						throw new NoSuchElementException();
					}
					Long current = nextCluster;
					loadNext();
					return current;
				}
			};

		}
	}
}
