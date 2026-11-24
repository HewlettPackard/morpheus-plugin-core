package com.morpheusdata.core.providers

import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.UpdateDefinition
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification
import spock.lang.Unroll

class OsUpdateLifecycleFacetSpec extends Specification {

	@Unroll
	void "default #phase succeeds without result data"() {
		given:
		def facet = new BaseFacet()
		def host = new ComputeServer()
		def update = new UpdateDefinition()

		when:
		def response = facet."$phase"(host, update)

		then:
		response.success
		response.data == null

		where:
		phase << ['onPreMaintenance', 'onPostMaintenance', 'onPreUpdate', 'onPostUpdate',
			'onFirmware', 'onPreReboot', 'onPostReboot', 'onPostUncordon']
	}

	void "priority defaults to 100 and named subtypes remain distinct"() {
		given:
		def base = new BaseFacet()
		def storage = new StorageFacet()
		def network = new NetworkFacet()

		expect:
		base.lifecyclePriority == 100
		storage.lifecyclePriority == 100
		network.lifecyclePriority == 100
		!(base instanceof StorageProvider.OsUpdateLifecycleFacet)
		!(base instanceof NetworkProvider.OsUpdateLifecycleFacet)
		storage instanceof PluginProvider.OsUpdateLifecycleFacet
		!(storage instanceof NetworkProvider.OsUpdateLifecycleFacet)
		network instanceof PluginProvider.OsUpdateLifecycleFacet
		!(network instanceof StorageProvider.OsUpdateLifecycleFacet)
	}

	void "overrides receive the original host and update and propagate failure"() {
		given:
		def facet = new OverridingFacet()
		def host = new ComputeServer()
		def update = new UpdateDefinition()

		when:
		def result = facet.onPreMaintenance(host, update)

		then:
		!result.success
		facet.host.is(host)
		facet.update.is(update)
	}

	private static class BaseFacet implements PluginProvider.OsUpdateLifecycleFacet {}
	private static class StorageFacet implements StorageProvider.OsUpdateLifecycleFacet {}
	private static class NetworkFacet implements NetworkProvider.OsUpdateLifecycleFacet {}

	private static class OverridingFacet implements StorageProvider.OsUpdateLifecycleFacet {
		ComputeServer host
		UpdateDefinition update

		@Override
		ServiceResponse onPreMaintenance(ComputeServer host, UpdateDefinition update) {
			this.host = host
			this.update = update
			ServiceResponse.error('blocked')
		}
	}
}
