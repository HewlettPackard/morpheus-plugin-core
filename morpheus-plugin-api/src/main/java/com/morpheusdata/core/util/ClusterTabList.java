package com.morpheusdata.core.util;

import java.util.HashMap;
import java.util.Map;

public class ClusterTabList {

	Boolean network = true;
	Boolean storage = true;
	Boolean virtualImage = false;
	Boolean container = false;
	Boolean monitoring = false;
	Boolean history = true;
	Boolean wiki = true;
	Boolean vm = false;
	Boolean host = true;
	Boolean resource = false;
	Boolean router = false;
	Boolean service = false;

	public Boolean getNetwork() {
		return network;
	}

	public void setNetwork(Boolean network) {
		this.network = network;
	}

	public Boolean getStorage() {
		return storage;
	}

	public void setStorage(Boolean storage) {
		this.storage = storage;
	}

	public Boolean getVirtualImage() {
		return virtualImage;
	}

	public void setVirtualImage(Boolean virtualImage) {
		this.virtualImage = virtualImage;
	}

	public Boolean getContainer() {
		return container;
	}

	public void setContainer(Boolean container) {
		this.container = container;
	}

	public Boolean getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Boolean monitoring) {
		this.monitoring = monitoring;
	}

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	public Boolean getWiki() {
		return wiki;
	}

	public void setWiki(Boolean wiki) {
		this.wiki = wiki;
	}

	public Boolean getVm() {
		return vm;
	}

	public void setVm(Boolean vm) {
		this.vm = vm;
	}

	public Boolean getHost() {
		return host;
	}

	public void setHost(Boolean host) {
		this.host = host;
	}

	public Boolean getResource() {
		return resource;
	}

	public void setResource(Boolean resource) {
		this.resource = resource;
	}

	public Boolean getRouter() {
		return router;
	}

	public void setRouter(Boolean router) {
		this.router = router;
	}

	public Boolean getService() {
		return service;
	}

	public void setService(Boolean service) {
		this.service = service;
	}

	public Map<String, Boolean> toMap() {
		Map<String, Boolean> tabListMap = new HashMap<String, Boolean>();
		tabListMap.put("network", this.network);
		tabListMap.put("storage", this.storage);
		tabListMap.put("virtualImage", this.virtualImage);
		tabListMap.put("container", this.container);
		tabListMap.put("monitoring", this.monitoring);
		tabListMap.put("history", this.history);
		tabListMap.put("wiki", this.wiki);
		tabListMap.put("vm", this.vm);
		tabListMap.put("host", this.host);
		tabListMap.put("resource", this.resource);
		tabListMap.put("router", this.router);
		tabListMap.put("service", this.service);
		return tabListMap;
	}
}
