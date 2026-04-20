package com.morpheusdata.sample.volumetab;

import com.morpheusdata.core.Plugin;

public class SampleVolumeTabPlugin extends Plugin {
	@Override
	public String getCode() {
		return "sample-volume-tab-plugin";
	}

	@Override
	public void initialize() {
		this.setName("Sample Volume Tab Plugin");
		this.registerProvider(new SampleVolumeTabProvider(this, this.morpheus));
	}

	@Override
	public void onDestroy() {
		// nothing to clean up for the sample
	}
}

