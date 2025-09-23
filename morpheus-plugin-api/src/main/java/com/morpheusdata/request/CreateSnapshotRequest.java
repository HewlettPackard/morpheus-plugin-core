package com.morpheusdata.request;

public class CreateSnapshotRequest {
	/**
	 *  Indicates if the snapshot is for backup purposes
	 */
	public Boolean forBackup;

	/**
	 *  Indicates if the snapshot is for export purposes
	 */
	public  Boolean forExport;

	public CreateSnapshotRequest(){
	}

	public CreateSnapshotRequest(Boolean forBackup, Boolean forExport) {
		this.forBackup = forBackup;
		this.forExport = forExport;
	}
}
