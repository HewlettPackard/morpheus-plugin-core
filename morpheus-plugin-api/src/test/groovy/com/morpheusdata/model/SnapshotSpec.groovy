package com.morpheusdata.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class SnapshotSpec extends Specification {
	void "Snapshot data store is serialized as an ID and remains optional for legacy snapshots"() {
		given:
		def mapper = new ObjectMapper()
		def snapshot = new Snapshot(datastore: new Datastore(id: 42, name: 'data-store'))

		when:
		def json = mapper.readValue(mapper.writeValueAsString(snapshot), Map)
		def legacySnapshot = mapper.readValue('{"id":7}', Snapshot)

		then:
		json.datastore == [id: 42]
		legacySnapshot.datastore == null
	}

	void "Snapshot data store changes are tracked including removal"() {
		given:
		def datastore = new Datastore(id: 42)
		def snapshot = new Snapshot()

		when:
		snapshot.datastore = datastore

		then:
		snapshot.dirtyPropertyValues.datastore.is(datastore)

		when:
		snapshot.markClean()
		snapshot.datastore = null

		then:
		snapshot.isDirty('datastore')
		snapshot.dirtyPropertyValues.containsKey('datastore')
		snapshot.dirtyPropertyValues.datastore == null
	}
}
