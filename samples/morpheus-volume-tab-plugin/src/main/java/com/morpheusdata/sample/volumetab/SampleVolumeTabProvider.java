package com.morpheusdata.sample.volumetab;

import com.morpheusdata.core.AbstractVolumeTabProvider;
import com.morpheusdata.core.MorpheusContext;
import com.morpheusdata.core.Plugin;
import com.morpheusdata.model.Account;
import com.morpheusdata.model.StorageVolume;
import com.morpheusdata.model.User;
import com.morpheusdata.views.HTMLResponse;
import com.morpheusdata.views.ViewModel;

public class SampleVolumeTabProvider extends AbstractVolumeTabProvider {
	protected MorpheusContext morpheusContext;
	protected Plugin plugin;

	public SampleVolumeTabProvider(Plugin plugin, MorpheusContext morpheusContext) {
		this.morpheusContext = morpheusContext;
		this.plugin = plugin;
	}

	@Override
	public HTMLResponse renderTemplate(StorageVolume volume) {
		ViewModel<StorageVolume> model = new ViewModel<>();
		model.object = volume;
		return getRenderer().renderTemplate("hbs/volumeTab", model);
	}

	@Override
	public Boolean show(StorageVolume volume, User user, Account account) {
		return true;
	}

	@Override
	public MorpheusContext getMorpheus() {
		return this.morpheusContext;
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}

	@Override
	public String getCode() {
		return "sample-volume-tab-plugin-volumeTab";
	}

	@Override
	public String getName() {
		return "Sample Volume Tab";
	}
}

