package com.morpheusdata.core.event

import com.morpheusdata.core.MorpheusAsyncServices
import com.morpheusdata.core.MorpheusServices
import com.morpheusdata.core.Plugin
import com.morpheusdata.core.synchronous.event.MorpheusSynchronousEventCollectorService
import com.morpheusdata.core.synchronous.event.MorpheusSynchronousEventHistoryService
import com.morpheusdata.model.event.*
import com.morpheusdata.request.event.*
import com.morpheusdata.response.ServiceResponse
import groovy.transform.CompileStatic
import io.reactivex.rxjava3.core.Single
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir
import spock.lang.Unroll

import javax.tools.ToolProvider
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class EventServicesSpec extends Specification {
	@TempDir @Shared File compilationDir
	@Shared URLClassLoader legacyLoader

	void setupSpec() {
		def types = [MorpheusAsyncServices, MorpheusServices]
		def sources = []
		types.each { type ->
			def methods = type.methods.findAll { !(it.name in ['getEventCollector', 'getEventHistory']) }
			def signatures = methods.collect { method -> declaration(method) }
			def apiDir = new File(compilationDir, 'old-src/com/morpheusdata/core')
			assert apiDir.mkdirs() || apiDir.isDirectory()
			def oldInterface = new File(apiDir, "${type.simpleName}.java")
			oldInterface.text = "package com.morpheusdata.core; public interface ${type.simpleName} {" +
				signatures.collect { "${it};" }.join('\n') + '}'
			def implDir = new File(compilationDir, 'old-src/compat')
			assert implDir.mkdirs() || implDir.isDirectory()
			def implementation = new File(implDir, "Legacy${type.simpleName}.java")
			implementation.text = "package compat; public class Legacy${type.simpleName} implements ${type.name} {" +
				signatures.withIndex().collect { signature, index ->
					"public ${signature} { ${defaultReturn(methods[index])} }"
				}.join('\n') + '}'
			sources.addAll([oldInterface.path, implementation.path])
		}
		def classes = new File(compilationDir, 'old-classes')
		assert classes.mkdirs()
		def classpath = [MorpheusAsyncServices, Single].collect {
			new File(it.protectionDomain.codeSource.location.toURI()).path
		}.join(File.pathSeparator)
		def diagnostics = new ByteArrayOutputStream()
		def compiler = ToolProvider.systemJavaCompiler
		assert compiler != null: 'API binary compatibility test requires a JDK'
		def args = ['--release', '11', '-classpath', classpath, '-d', classes.path] + sources
		assert compiler.run(null, diagnostics, diagnostics, *args.toArray(new String[0])) == 0: diagnostics.toString()
		// Parent-first loading binds pre-addition bytecode to today's real aggregate interfaces.
		legacyLoader = new URLClassLoader([classes.toURI().toURL()] as URL[], EventServicesSpec.classLoader)
	}

	void cleanupSpec() {
		legacyLoader?.close()
	}

	@CompileStatic
	private static String declaration(Method method) {
		List<String> parameters = new ArrayList<>()
		Class<?>[] types = method.getParameterTypes()
		for (int index = 0; index < types.length; index++) {
			parameters.add(types[index].getCanonicalName() + ' arg' + index)
		}
		return method.getReturnType().getCanonicalName() + ' ' + method.getName() +
			'(' + String.join(', ', parameters) + ')'
	}

	@CompileStatic
	private static String defaultReturn(Method method) {
		Class<?> type = method.getReturnType()
		if (type.equals(Void.TYPE)) {
			return ''
		}
		return 'return ' + (type.equals(Boolean.TYPE) ? 'false' : type.isPrimitive() ? '0' : 'null') + ';'
	}

	@Unroll
	void "pre-addition #type.simpleName bytecode receives explicit unsupported #getter"() {
		given:
		def implementation = legacyLoader.loadClass("compat.Legacy${type.simpleName}").getConstructor().newInstance()
		def method = type.getMethod(getter)

		expect:
		type.isInstance(implementation)
		method.default
		type.getMethod('getUpdateDefinition').invoke(implementation) == null

		when:
		method.invoke(implementation)

		then:
		def exception = thrown(InvocationTargetException)
		exception.cause instanceof UnsupportedOperationException
		exception.cause.message.contains('not supported')

		where:
		type                  | getter
		MorpheusAsyncServices | 'getEventCollector'
		MorpheusAsyncServices | 'getEventHistory'
		MorpheusServices      | 'getEventCollector'
		MorpheusServices      | 'getEventHistory'
	}

	void "all synchronous collector operations preserve caller requests responses and failures"() {
		given:
		def async = Mock(MorpheusEventCollectorService)
		def sync = new SyncCollector(eventCollectorService: async)
		def consumer = new EventConsumerContext(Stub(Plugin), 1L, 7L)
		def createRequest = new EventSubscriptionCreateRequest(name: 'partner')
		def listRequest = new EventSubscriptionListRequest(maxResults: 25)
		def readRequest = new EventReadRequest(sinceVersion: 'accepted', maxEvents: 10, waitSeconds: 30)
		def ackRequest = new EventAckRequest(version: 'pending')
		def subscription = new ServiceResponse<EventSubscription>(true, null, null,
			new EventSubscription(id: 's1', version: 'accepted'))
		def page = new ServiceResponse<EventSubscriptionPage>(true, null, null,
			new EventSubscriptionPage(subscriptions: [subscription.data]))
		def batch = new ServiceResponse<EventUpdateSet>(true, null, null,
			new EventUpdateSet(version: 'pending', events: [new EventEnvelope(uuid: 'e1')]))
		def conflict = EventErrorCode.staleVersion('Invalidated pending token', 'accepted')
		def deleted = new ServiceResponse<Void>(true, null, null, null)

		when:
		def created = sync.create(consumer, createRequest)
		def fetched = sync.get(consumer, 's1')
		def listed = sync.list(consumer, listRequest)
		def read = sync.read(consumer, 's1', readRequest)
		def acknowledged = sync.ack(consumer, 's1', ackRequest)
		def removed = sync.delete(consumer, 's1')

		then:
		1 * async.create(consumer, createRequest) >> Single.just(subscription)
		1 * async.get(consumer, 's1') >> Single.just(subscription)
		1 * async.list(consumer, listRequest) >> Single.just(page)
		1 * async.read(consumer, 's1', readRequest) >> Single.just(batch)
		1 * async.ack(consumer, 's1', ackRequest) >> Single.just(conflict)
		1 * async.delete(consumer, 's1') >> Single.just(deleted)
		0 * async._
		created.is(subscription)
		fetched.is(subscription)
		listed.is(page)
		read.is(batch)
		acknowledged.is(conflict)
		removed.is(deleted)
	}

	void "empty reads do not trigger automatic acknowledgments"() {
		given:
		def async = Mock(MorpheusEventCollectorService)
		def sync = new SyncCollector(eventCollectorService: async)
		def consumer = new EventConsumerContext(Stub(Plugin), 1L, null)
		def request = new EventReadRequest(sinceVersion: 'accepted')

		when:
		def response = sync.read(consumer, 's1', request)

		then:
		1 * async.read(consumer, 's1', request) >> Single.just(
			new ServiceResponse<EventUpdateSet>(true, null, null, new EventUpdateSet(version: 'accepted', events: [])))
		0 * async._
		response.data.events.empty
		response.data.version == 'accepted'
	}

	void "history delegates explicit dependency failures without altering filters or caller"() {
		given:
		def async = Mock(MorpheusEventHistoryService)
		def sync = new SyncHistory(eventHistoryService: async)
		def consumer = new EventConsumerContext(Stub(Plugin), 1L, 7L)
		def request = new EventHistoryRequest(subjectKey: 'vm:uuid', maxResults: 25, cursor: 'page')
		def failure = EventErrorCode.DEPENDENCY_UNAVAILABLE.failure('Search unavailable')

		when:
		def response = sync.query(consumer, request)

		then:
		1 * async.query(consumer, request) >> Single.just(failure)
		0 * async._
		response.is(failure)
	}

	void "unexpected reactive failures propagate through blocking delegates"() {
		given:
		def async = Mock(MorpheusEventHistoryService)
		def sync = new SyncHistory(eventHistoryService: async)
		def consumer = new EventConsumerContext(Stub(Plugin), 1L, null)
		def request = new EventHistoryRequest()
		def failure = new IllegalStateException('Unexpected transport failure')

		when:
		sync.query(consumer, request)

		then:
		1 * async.query(consumer, request) >> Single.error(failure)
		def propagated = thrown(IllegalStateException)
		propagated.is(failure)
	}

	private static class SyncCollector implements MorpheusSynchronousEventCollectorService {
		MorpheusEventCollectorService eventCollectorService
	}

	private static class SyncHistory implements MorpheusSynchronousEventHistoryService {
		MorpheusEventHistoryService eventHistoryService
	}
}
