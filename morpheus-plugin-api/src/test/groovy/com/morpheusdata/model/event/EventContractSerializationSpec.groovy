package com.morpheusdata.model.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.morpheusdata.request.event.*
import spock.lang.Specification
import spock.lang.Unroll

class EventContractSerializationSpec extends Specification {
	private final ObjectMapper mapper = new ObjectMapper()

	@Unroll
	void "canonical code #code survives newer envelope fields and nested payload serialization"() {
		given:
		def json = """{
			"uuid":"event-1", "eventType":"${code}", "eventDate":"2026-11-09T14:02:11Z",
			"subjectKey":"vm:stable-uuid", "subjectRevision":4294967298,
			"scope":{"accountId":1,"cloudId":12,"serverGroupId":44,"serverId":9120,"futureScope":"value"},
			"details":{"memory":8589934592,"nested":{"flags":[true,"unknown-detail"]}},
			"futureEnvelope":{"notRequiredByOldConsumers":true}
		}"""

		when:
		def event = mapper.readValue(json, EventEnvelope)
		def wire = mapper.readTree(mapper.writeValueAsBytes(event))
		def restored = mapper.treeToValue(wire, EventEnvelope)

		then:
		restored.eventType == code
		HvmEventType.fromCode(restored.eventType).present == known
		restored.eventDate.toInstant().toString() == '2026-11-09T14:02:11Z'
		wire.eventDate.textual
		restored.subjectRevision == 4294967298L
		restored.scope.serverGroupId == 44L
		restored.details == event.details
		restored.details.memory == 8589934592L
		wire.fieldNames().toList() as Set ==
			['uuid', 'eventType', 'eventDate', 'subjectKey', 'subjectRevision', 'scope', 'details'] as Set

		where:
		code                       | known
		'vm.created'               | true
		'future-family.newOutcome' | false
	}

	void "subscription listings retain accepted progress bounds and 64-bit observations"() {
		when:
		def page = mapper.readValue('''{
			"subscriptions":[{
				"id":"subscription-1","name":"consumer","version":"Opaque+/Accepted",
				"eventTypes":["vm.started"],"scope":{"accountId":1,"cloudId":12,"futureScope":true},
				"expiresAt":"2026-11-16T14:02:11.123Z",
				"limits":{"maxBatchEvents":500,"maxBatchBytes":8388608,"maxWaitSeconds":30,
					"messageTtlSeconds":604800,"maxQueueBytes":8589934592,"maxQueueMessages":100000,
					"futureLimit":1},
				"observedLossCount":4294967298,"lastObservedLossAt":"2026-11-09T14:02:11Z",
				"queuedEvents":4294967299,"unacknowledgedEvents":500,"futureSubscription":true
			}],
			"nextCursor":"opaque-list-page","futurePage":true
		}''', EventSubscriptionPage)
		def restored = mapper.readValue(mapper.writeValueAsBytes(page), EventSubscriptionPage)
		def subscription = restored.subscriptions.first()

		then:
		subscription.version == 'Opaque+/Accepted'
		subscription.limits.maxQueueBytes == 8589934592L
		subscription.observedLossCount == 4294967298L
		subscription.queuedEvents == 4294967299L
		subscription.expiresAt.toInstant().toString() == '2026-11-16T14:02:11.123Z'
		restored.nextCursor == 'opaque-list-page'
	}

	void "empty updates retain accepted progress without inventing loss observations"() {
		when:
		def batch = mapper.readValue('''{
			"version":"accepted-not-a-sequence","events":[],"truncated":false,"futureReadField":true
		}''', EventUpdateSet)
		def restored = mapper.readValue(mapper.writeValueAsBytes(batch), EventUpdateSet)

		then:
		restored.events.empty
		restored.version == 'accepted-not-a-sequence'
		!restored.truncated
		restored.observedLossCount == null
		restored.lastObservedLossAt == null
		restored.expiresAt == null
	}

	void "history preserves appliance observations without fabricating gap intervals"() {
		given:
		def json = '''{
			"events":[{"uuid":"e1","eventType":"future.outcome","details":{"newDetail":[1,2]}}],
			"nextCursor":"history-page","futurePage":1,
			"retentionStart":"2026-10-12T00:00:00Z","retentionEnd":"2026-11-10T02:00:00Z",
			"summary":{"lastSuccessfulIndexAt":"2026-11-10T01:00:00Z",
				"observedLossCount":4294967299,"firstObservedLossAt":null,
				"lastObservedLossAt":"2026-11-09T00:00:00Z","futureSummary":true}
		}'''

		when:
		def page = mapper.readValue(json, EventHistoryPage)
		def bytes = new ByteArrayOutputStream()
		new ObjectOutputStream(bytes).withCloseable { it.writeObject(page) }
		def restored = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))
			.withCloseable { it.readObject() } as EventHistoryPage

		then:
		restored.events.first().eventType == 'future.outcome'
		restored.events.first().details == [newDetail: [1, 2]]
		restored.summary.observedLossCount == 4294967299L
		restored.summary.firstObservedLossAt == null
		restored.summary.lastObservedLossAt.before(restored.summary.lastSuccessfulIndexAt)
		restored.retentionStart.toInstant().toString() == '2026-10-12T00:00:00Z'
		restored.retentionEnd.toInstant().toString() == '2026-11-10T02:00:00Z'
		mapper.readTree(mapper.writeValueAsBytes(restored)).retentionStart.textual
		mapper.readValue('{"events":[]}', EventHistoryPage).retentionStart == null
		mapper.readValue('{"events":[]}', EventHistoryPage).summary == null
	}

	@Unroll
	void "typed #type.simpleName request preserves its wire fields"() {
		when:
		def request = mapper.readValue(json, type)
		def restored = mapper.readTree(mapper.writeValueAsBytes(request))

		then:
		mapper.readTree(json).fields().every { field -> restored.get(field.key) == field.value }

		where:
		type                           | json
		EventSubscriptionCreateRequest | '{"name":"partner","eventTypes":["vm.created"],"scope":{"cloudId":12,"serverGroupId":null,"serverId":null}}'
		EventReadRequest               | '{"sinceVersion":"Opaque+/Token","maxEvents":500,"waitSeconds":30}'
		EventAckRequest                | '{"version":"Opaque+/Token"}'
		EventSubscriptionListRequest   | '{"maxResults":25,"cursor":"opaque-list-page"}'
		EventHistoryRequest            | '{"eventTypes":["vm.started"],"subjectKey":"vm:uuid","maxResults":25,"cursor":"opaque-history-page"}'
	}

	void "request scope has no caller-controlled tenant override"() {
		when:
		mapper.readValue('{"scope":{"accountId":2,"cloudId":12}}', EventSubscriptionCreateRequest)

		then:
		thrown(UnrecognizedPropertyException)
	}

	@Unroll
	void "#code failures preserve machine-readable status without success-shaped data"() {
		when:
		def response = code.failure('Explicit operation failure')
		def wire = mapper.readTree(mapper.writeValueAsBytes(response))

		then:
		!response.success
		response.data == null
		wire.statusCode.textValue() == status
		EventErrorCode.fromCode(wire.errorCode.textValue()).get() == code

		where:
		code                                  | status
		EventErrorCode.SUBSCRIPTION_NOT_FOUND | '404'
		EventErrorCode.QUEUE_GONE             | '410'
		EventErrorCode.DEPENDENCY_UNAVAILABLE | '503'
	}

	void "stale-token failures carry last accepted progress rather than operation data"() {
		when:
		def response = EventErrorCode.staleVersion('Stale token', 'Accepted+/Token')
		def wire = mapper.readTree(mapper.writeValueAsBytes(response))

		then:
		!response.success
		response.data == null
		wire.statusCode.textValue() == '409'
		EventErrorCode.fromCode(wire.errorCode.textValue()).get() == EventErrorCode.STALE_VERSION
		wire.errors.lastAcceptedVersion.textValue() == 'Accepted+/Token'
		!EventErrorCode.fromCode('FUTURE_FAILURE').present
	}
}
