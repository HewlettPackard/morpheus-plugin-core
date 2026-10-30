package com.morpheusdata.request

import com.fasterxml.jackson.databind.ObjectMapper
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.core.providers.HostProvisionProvider
import com.morpheusdata.core.providers.ProvisionProvider
import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.OptionType
import com.morpheusdata.model.ProvisionType
import com.morpheusdata.model.StorageVolume
import com.morpheusdata.response.BuildResizeVolumeChangelistResponse
import com.morpheusdata.response.ResizeV2WorkloadResponse
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

class StorageVolumeConfigRequestSpec extends Specification {

	void "legacy resize transports per-volume config through custom changelist and provider"() {
		given:
		def provider = new VolumeProvider()
		def mapper = new ObjectMapper()
		def current = [cache: [enabled: true], retained: 'existing', nullable: null]
		def volume = new StorageVolume(id: 10L)
		volume.setConfigMap(current)
		volume.markClean()
		def incoming = mapper.readValue(json, Map)
		def buildRequest = new BuildResizeChangelistRequest<StorageVolume>(
			desired: [[config: incoming.config], [config: incoming.config]], existing: [volume])

		when:
		def changelist = provider.buildVolumeChangelist(buildRequest)
		def request = new ResizeRequest(null, null, null, null, null,
			[], changelist.data.addList, changelist.data.updateList, [], [], [], [], [], [])
		def response = provider.resizeServer(new ComputeServer(), request, [:])
		def wire = mapper.readValue(mapper.writeValueAsString(provider.legacyRequest), Map)
		def changelistWire = mapper.readValue(mapper.writeValueAsString(changelist.data), Map)

		then:
		changelist.success
		response.success
		provider.legacyRequest.is(request)
		request.volumesUpdate.first().existingModel.is(volume)
		request.volumesUpdate.first().updateProps.keySet() == ['config'] as Set
		wire.volumesAdd.first().containsKey('config')
		wire.volumesAdd.first().config == incoming.config
		wire.volumesUpdate.first().updateProps == [config: incoming.config]
		wire.volumesUpdate.first().existingModel.configMap == current
		changelistWire.addList.first() == [config: incoming.config]
		changelistWire.updateList.first().updateProps == [config: incoming.config]
		volume.configMap == current
		volume.dirtyPropertyValues.isEmpty()

		where:
		json << [
			'{"config":{"cache":{"enabled":false,"levels":[1,null,{"active":false}]},"policy":null}}',
			'{"config":{}}',
			'{"config":null}'
		]
	}

	void "V2 resize preserves config for pre-created additions and existing-volume updates"() {
		given:
		def provider = new VolumeProvider()
		def mapper = new ObjectMapper()
		def model = new ProvisionType(storageVolumeOptionTypes: provider.getStorageVolumeOptionTypes())
		def definitionWire = mapper.readValue(mapper.writeValueAsString(model), Map)
		def incoming = mapper.readValue(json, Map)
		def existing = new StorageVolume(id: 10L)
		existing.setConfigMap([cache: [enabled: true], retained: 'existing'])
		existing.markClean()
		def added = new StorageVolume(id: 11L)
		def add = new UpdateModel<StorageVolume>(added, incoming)
		def update = new UpdateModel<StorageVolume>(existing, incoming)

		when:
		def request = new ResizeV2Request(null, null, null, null, null,
			[], [add], [update], [], [], [], [], [], [])
		def response = provider.resizeServer(new ComputeServer(), request, [:])
		def wire = mapper.readValue(mapper.writeValueAsString(provider.v2Request), Map)

		then:
		definitionWire.storageVolumeOptionTypes.first().fieldName == 'cache.enabled'
		response.success
		provider.v2Request.is(request)
		request.volumesAdd.first().existingModel.is(added)
		request.volumesUpdate.first().existingModel.is(existing)
		wire.volumesAdd.first().updateProps == incoming
		wire.volumesUpdate.first().updateProps == incoming
		wire.volumesAdd.first().updateProps.containsKey('config')
		wire.volumesUpdate.first().existingModel.configMap == [cache: [enabled: true], retained: 'existing']
		existing.configMap == [cache: [enabled: true], retained: 'existing']
		existing.dirtyPropertyValues.isEmpty()
		added.config == null
		added.dirtyPropertyValues.isEmpty()

		where:
		json << [
			'{"config":{"cache":{"enabled":false,"levels":[1,null,{"active":false}]},"policy":null}}',
			'{"config":{}}',
			'{"config":null}'
		]
	}

	static class VolumeProvider implements ProvisionProvider, ProvisionProvider.BuildResizeVolumeChangelistFacet,
		HostProvisionProvider.ResizeFacet, HostProvisionProvider.ResizeV2Facet {
		ResizeRequest legacyRequest
		ResizeV2Request v2Request

		MorpheusContext getMorpheus() { null }
		Plugin getPlugin() { null }
		String getCode() { 'volume-config-test' }
		String getName() { 'Volume config test' }

		@Override
		Collection<OptionType> getStorageVolumeOptionTypes() {
			[new OptionType(code: 'disk-cache', fieldContext: 'config', fieldName: 'cache.enabled',
				inputType: OptionType.InputType.CHECKBOX, defaultValue: 'false')]
		}

		@Override
		ServiceResponse<BuildResizeVolumeChangelistResponse> buildVolumeChangelist(BuildResizeChangelistRequest<StorageVolume> request) {
			// Fixed fixture roles: first desired volume is new, second updates the sole existing volume.
			ServiceResponse.success(new BuildResizeVolumeChangelistResponse(
				addList: [request.desired[0]],
				updateList: [new UpdateModel<StorageVolume>(request.existing[0], request.desired[1])]))
		}

		@Override
		ServiceResponse resizeServer(ComputeServer server, ResizeRequest request, Map opts) {
			legacyRequest = request
			ServiceResponse.success()
		}

		@Override
		ServiceResponse<ResizeV2WorkloadResponse> resizeServer(ComputeServer server, ResizeV2Request request, Map opts) {
			v2Request = request
			ServiceResponse.success(new ResizeV2WorkloadResponse())
		}
	}
}
