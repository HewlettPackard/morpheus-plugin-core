package com.morpheusdata.core;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.User;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Single;

import java.io.InputStream;

public interface MorpheusFileCopyService {

	/**
	 * Copy a file to the target server.
	 * @param server The target server
	 * @param fileName name of the copied file for the file copy request URL.
	 * @param filePath path on the server, including the file name (/some/path/file.txt), to place the file copy.
	 * @param sourceStream source {@link InputStream} to copy to the server
	 * @param contentLength size of the file to be copied
	 * @return {@link ServiceResponse} containing the success status of the copy operation
	 */
	Single<ServiceResponse> copyToServer(ComputeServer server, String fileName, String filePath, InputStream sourceStream, Long contentLength);

	/**
	 * Copy a file to the target server.
	 * @param server The target server
	 * @param fileName name of the copied file for the file copy request URL.
	 * @param filePath path on the server, including the file name (/some/path/file.txt), to place the file copy.
	 * @param sourceStream source {@link InputStream} to copy to the server
	 * @param contentLength size of the file to be copied
	 * @param timeout max timeout to initialize the copy operation
	 * @return {@link ServiceResponse} containing the success status of the copy operation
	 */
	Single<ServiceResponse> copyToServer(ComputeServer server, String fileName, String filePath, InputStream sourceStream, Long contentLength, Long timeout);

	/**
	 * Copy a file to the target server.
	 * @param server The target server
	 * @param fileName name of the copied file for the file copy request URL.
	 * @param filePath path on the server, including the file name (/some/path/file.txt), to place the file copy.
	 * @param sourceStream source {@link InputStream} to copy to the server
	 * @param contentLength size of the file to be copied
	 * @param timeout max timeout to initialize the copy operation
	 * @param autoExpand automatically expand .tar.gz compressed files during upload
	 * @return {@link ServiceResponse} containing the success status of the copy operation
	 */
	Single<ServiceResponse> copyToServer(ComputeServer server, String fileName, String filePath, InputStream sourceStream, Long contentLength, Long timeout, Boolean autoExpand);


	/**
	 * Create a url to copy a file to the target server.
	 * @param fileName name of the copied file for the file copy request URL.
	 * @param sourceStream source {@link InputStream} to copy to the server
	 * @param contentLength size of the file to be copied
	 * @param timeout max timeout to initialize the copy operation
	 * @param autoExpand automatically expand .tar.gz compressed files during upload
	 * @param contentType override the application/octet-stream content type when serving the file
	 * @return {@link ServiceResponse} containing the success status of the generate operation and data property containing the url
	 */
	Single<ServiceResponse> generateUrl(String fileName, User user, InputStream sourceStream, Long contentLength, Long timeout, Boolean autoExpand, String contentType);

	/**
	 * Create a url to copy a file to the target server.
	 * @param fileName name of the copied file for the file copy request URL.
	 * @param sourceStream source {@link InputStream} to copy to the server
	 * @param contentLength size of the file to be copied
	 * @param timeout max timeout to initialize the copy operation
	 * @param autoExpand automatically expand .tar.gz compressed files during upload
	 * @return {@link ServiceResponse} containing the success status of the generate operation and data property containing the url
	 */
	Single<ServiceResponse> generateUrl(String fileName, User user, InputStream sourceStream, Long contentLength, Long timeout, Boolean autoExpand);

	/**
	 * Create a url to copy a file to the target server.
	 * @param fileName name of the copied file for the file copy request URL.
	 * @param sourceStream source {@link InputStream} to copy to the server
	 * @param contentLength size of the file to be copied
	 * @param timeout max timeout to initialize the copy operation
	 * @return {@link ServiceResponse} containing the success status of the generate operation and data property containing the url
	 */
	Single<ServiceResponse> generateUrl(String fileName, User user, InputStream sourceStream, Long contentLength, Long timeout);


	/**
	 * Create a url to copy a file to the target server.
	 * @param fileName name of the copied file for the file copy request URL.
	 * @param sourceStream source {@link InputStream} to copy to the server
	 * @param contentLength size of the file to be copied
	 * @return {@link ServiceResponse} containing the success status of the generate operation and data property containing the url
	 */
	Single<ServiceResponse> generateUrl(String fileName, User user, InputStream sourceStream, Long contentLength);

	/**
	 * Expires AND deletes the temporary file content of a file copy url
	 * @param fileCopyUrl the original URL submitted
	 * @return {@link ServiceResponse} containing the success status of the copy operation
	 */
	Single<ServiceResponse> deleteUrl(String fileCopyUrl);
}
