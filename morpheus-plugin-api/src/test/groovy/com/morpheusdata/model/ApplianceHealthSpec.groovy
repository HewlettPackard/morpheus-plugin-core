package com.morpheusdata.model

import com.morpheusdata.model.health.ApplianceHealth
import com.morpheusdata.model.health.ApplianceHealthCpuStats
import com.morpheusdata.model.health.ApplianceHealthMemoryStats
import com.morpheusdata.model.health.ApplianceHealthStorageStats
import com.morpheusdata.model.health.ApplianceHealthStorageFile
import com.morpheusdata.model.health.ApplianceHealthThreadStats
import com.morpheusdata.model.health.ApplianceHealthDatabaseStats
import com.morpheusdata.model.health.ApplianceHealthRabbitStats
import com.morpheusdata.model.health.ApplianceHealthRabbitQueue
import com.morpheusdata.model.health.ApplianceHealthElasticStats
import com.morpheusdata.model.health.ApplianceHealthElasticMaster
import com.morpheusdata.model.health.ApplianceHealthElasticNode
import com.morpheusdata.model.health.ApplianceHealthElasticIndex
import com.morpheusdata.model.health.ApplianceHealthElasticClusterStats
import com.morpheusdata.model.health.ApplianceHealthDatabaseConnection
import com.morpheusdata.model.health.ApplianceHealthSlowQuery
import spock.lang.Specification

class ApplianceHealthSpec extends Specification {

	void "ApplianceHealth top-level fields are set and retrieved correctly"() {
		given:
		def health = new ApplianceHealth()
		def now = new Date()

		when:
		health.success = true
		health.statusMessage = 'ok'
		health.applianceUrl = 'https://morpheus.example.com'
		health.buildVersion = '9.0.0'
		health.uuid = 'abc-123'
		health.setupNeeded = false
		health.date = now

		then:
		health.success == true
		health.statusMessage == 'ok'
		health.applianceUrl == 'https://morpheus.example.com'
		health.buildVersion == '9.0.0'
		health.uuid == 'abc-123'
		health.setupNeeded == false
		health.date == now
	}

	void "ApplianceHealth sub-model sections are set and retrieved correctly"() {
		given:
		def health = new ApplianceHealth()

		when:
		health.cpu = new ApplianceHealthCpuStats(cpuLoad: 12.5, cpuTotalLoad: 25.0, processorCount: 4, processTime: 1000.0, systemLoad: 1.5, status: 'ok')
		health.memory = new ApplianceHealthMemoryStats(maxMemory: 4096L, totalMemory: 2048L, freeMemory: 512L, usedMemory: 1536L, status: 'ok')
		health.storage = new ApplianceHealthStorageStats(used: 50L, available: 50L, total: 100L, percent: 50.0, status: 'ok')
		health.threads = new ApplianceHealthThreadStats(totalThreads: 100, runningWebThreads: 5, status: 'ok')
		health.database = new ApplianceHealthDatabaseStats(usedConnections: 10, maxConnections: 150L, status: 'ok')
		health.rabbit = new ApplianceHealthRabbitStats(status: 'ok')
		health.elastic = new ApplianceHealthElasticStats(status: 'ok')

		then:
		health.cpu.cpuLoad == 12.5
		health.cpu.processorCount == 4
		health.cpu.status == 'ok'
		health.memory.maxMemory == 4096L
		health.memory.usedMemory == 1536L
		health.storage.total == 100L
		health.storage.percent == 50.0
		health.threads.totalThreads == 100
		health.threads.runningWebThreads == 5
		health.database.usedConnections == 10
		health.database.maxConnections == 150L
		health.rabbit.status == 'ok'
		health.elastic.status == 'ok'
	}

	void "ApplianceHealthStorageStats initializes files list"() {
		given:
		def storage = new ApplianceHealthStorageStats()

		when:
		def file = new ApplianceHealthStorageFile(name: '/dev/sda1', used: 20L, available: 80L, total: 100L, path: '/', percent: 20.0)
		storage.files << file

		then:
		storage.files.size() == 1
		storage.files[0].name == '/dev/sda1'
		storage.files[0].path == '/'
		storage.files[0].percent == 20.0
	}

	void "ApplianceHealthRabbitStats initializes queue lists"() {
		given:
		def rabbit = new ApplianceHealthRabbitStats()

		when:
		rabbit.queues << new ApplianceHealthRabbitQueue(name: 'morpheusJobs', count: 5L, status: 'ok')
		rabbit.busyQueues << new ApplianceHealthRabbitQueue(name: 'morpheusJobs', count: 500L, status: 'warning')

		then:
		rabbit.queues.size() == 1
		rabbit.queues[0].name == 'morpheusJobs'
		rabbit.busyQueues.size() == 1
		rabbit.errorQueues.size() == 0
	}

	void "ApplianceHealthElasticStats initializes node and index lists"() {
		given:
		def elastic = new ApplianceHealthElasticStats()
		def master = new ApplianceHealthElasticMaster(id: 'node1', host: 'localhost', ip: '127.0.0.1', node: 'master-node')

		when:
		elastic.master = master
		elastic.nodes << new ApplianceHealthElasticNode(ip: '127.0.0.1', name: 'node1', role: 'mdi')
		elastic.indices << new ApplianceHealthElasticIndex(index: 'morpheus-logs', health: 'green', status: 'open')
		elastic.stats = new ApplianceHealthElasticClusterStats(clusterName: 'morpheus', nodeTotal: '1', status: 'green')

		then:
		elastic.master.id == 'node1'
		elastic.nodes.size() == 1
		elastic.nodes[0].ip == '127.0.0.1'
		elastic.indices.size() == 1
		elastic.indices[0].index == 'morpheus-logs'
		elastic.badIndices.size() == 0
		elastic.stats.clusterName == 'morpheus'
	}

	void "ApplianceHealthDatabaseStats initializes connection and query lists"() {
		given:
		def db = new ApplianceHealthDatabaseStats()

		when:
		db.connectionList << new ApplianceHealthDatabaseConnection(id: 1L, user: 'morpheus', host: 'localhost', command: 'Sleep', time: 0L)
		db.busyConnections << new ApplianceHealthDatabaseConnection(id: 2L, user: 'morpheus', command: 'Query', info: 'SELECT ...')
		db.slowQueries << new ApplianceHealthSlowQuery(count: 3L, time: 5000L, query: 'SELECT * FROM compute_server')
		db.maxConnections = 150L
		db.usedConnections = 2

		then:
		db.connectionList.size() == 1
		db.busyConnections.size() == 1
		db.slowQueries.size() == 1
		db.slowQueries[0].query == 'SELECT * FROM compute_server'
		db.maxConnections == 150L
		db.usedConnections == 2
	}

	void "ApplianceHealth toString includes all key fields"() {
		given:
		def health = new ApplianceHealth(
			success: true, applianceUrl: 'https://morpheus.example.com',
			buildVersion: '9.0.0', uuid: 'abc-123', setupNeeded: false,
			statusMessage: 'ok'
		)

		when:
		def str = health.toString()

		then:
		str.contains('success=true')
		str.contains('applianceUrl=\'https://morpheus.example.com\'')
		str.contains('buildVersion=\'9.0.0\'')
		str.contains('uuid=\'abc-123\'')
		str.contains('setupNeeded=false')
	}

	void "ApplianceHealthCpuStats toString includes all key fields"() {
		given:
		def stats = new ApplianceHealthCpuStats(cpuLoad: 12.5, cpuTotalLoad: 25.0, processorCount: 4, processTime: 1000.0, systemLoad: 1.5, status: 'ok', statusMessage: 'cpu ok')

		when:
		def str = stats.toString()

		then:
		str.contains('cpuLoad=12.5')
		str.contains('cpuTotalLoad=25.0')
		str.contains('processorCount=4')
		str.contains('systemLoad=1.5')
		str.contains("status='ok'")
	}

	void "ApplianceHealthMemoryStats toString includes key fields"() {
		given:
		def stats = new ApplianceHealthMemoryStats(maxMemory: 8192L, usedMemory: 4096L, memoryPercent: 0.5, status: 'ok')

		when:
		def str = stats.toString()

		then:
		str.contains('maxMemory=8192')
		str.contains('usedMemory=4096')
		str.contains('memoryPercent=0.5')
		str.contains("status='ok'")
	}

	void "ApplianceHealthStorageStats toString includes key fields"() {
		given:
		def stats = new ApplianceHealthStorageStats(used: 50L, available: 50L, total: 100L, percent: 50.0, status: 'ok')
		stats.files << new ApplianceHealthStorageFile(name: '/dev/sda1', path: '/', percent: 50.0)

		when:
		def str = stats.toString()

		then:
		str.contains('used=50')
		str.contains('total=100')
		str.contains('percent=50.0')
		str.contains('files=1')
	}

	void "ApplianceHealthStorageFile toString includes key fields"() {
		given:
		def file = new ApplianceHealthStorageFile(name: '/dev/sda1', path: '/', used: 20L, available: 80L, total: 100L, percent: 20.0)

		when:
		def str = file.toString()

		then:
		str.contains("name='/dev/sda1'")
		str.contains("path='/'")
		str.contains('percent=20.0')
	}

	void "ApplianceHealthThreadStats toString includes key fields"() {
		given:
		def stats = new ApplianceHealthThreadStats(totalThreads: 120, runningWebThreads: 5, totalCpuTime: 500000L, status: 'ok')
		stats.busyThreads << [id: 1, name: 'http-nio']
		stats.blockedThreads << [id: 2, name: 'blocked-thread']

		when:
		def str = stats.toString()

		then:
		str.contains('totalThreads=120')
		str.contains('runningWebThreads=5')
		str.contains('totalCpuTime=500000')
		str.contains('busyThreads=1')
		str.contains('blockedThreads=1')
	}

	void "ApplianceHealthDatabaseStats toString includes key fields"() {
		given:
		def stats = new ApplianceHealthDatabaseStats(maxConnections: 150L, usedConnections: 5, abortedConnections: 2L, scanPercent: 12.5, status: 'ok')
		stats.busyConnections << new ApplianceHealthDatabaseConnection(id: 1L, user: 'morpheus')

		when:
		def str = stats.toString()

		then:
		str.contains('maxConnections=150')
		str.contains('usedConnections=5')
		str.contains('scanPercent=12.5')
		str.contains('busyConnections=1')
	}

	void "ApplianceHealthDatabaseConnection toString includes key fields"() {
		given:
		def conn = new ApplianceHealthDatabaseConnection(id: 42L, user: 'morpheus', host: 'localhost', database: 'morpheus_dev', command: 'Query', time: 3L, state: 'executing', info: 'SELECT 1')

		when:
		def str = conn.toString()

		then:
		str.contains('id=42')
		str.contains("user='morpheus'")
		str.contains("host='localhost'")
		str.contains("command='Query'")
		str.contains("info='SELECT 1'")
	}

	void "ApplianceHealthSlowQuery toString includes key fields"() {
		given:
		def sq = new ApplianceHealthSlowQuery(count: 5L, time: 10000L, query: 'SELECT * FROM logs')

		when:
		def str = sq.toString()

		then:
		str.contains('count=5')
		str.contains('time=10000')
		str.contains("query='SELECT * FROM logs'")
	}

	void "ApplianceHealthRabbitStats toString includes queue counts"() {
		given:
		def stats = new ApplianceHealthRabbitStats(status: 'ok')
		stats.queues << new ApplianceHealthRabbitQueue(name: 'morpheusJobs', count: 0L, status: 'ok')
		stats.errorQueues << new ApplianceHealthRabbitQueue(name: 'errorQueue', count: 5L, status: 'error')

		when:
		def str = stats.toString()

		then:
		str.contains('queues=1')
		str.contains('busyQueues=0')
		str.contains('errorQueues=1')
		str.contains("status='ok'")
	}

	void "ApplianceHealthRabbitQueue toString includes key fields"() {
		given:
		def queue = new ApplianceHealthRabbitQueue(name: 'morpheusJobs', count: 42L, status: 'warning')

		when:
		def str = queue.toString()

		then:
		str.contains("name='morpheusJobs'")
		str.contains('count=42')
		str.contains("status='warning'")
	}

	void "ApplianceHealthElasticStats toString includes summary counts"() {
		given:
		def stats = new ApplianceHealthElasticStats(status: 'ok')
		stats.master = new ApplianceHealthElasticMaster(id: 'n1', host: 'localhost', ip: '127.0.0.1', node: 'master')
		stats.nodes << new ApplianceHealthElasticNode(ip: '127.0.0.1', name: 'node1')
		stats.indices << new ApplianceHealthElasticIndex(index: 'morpheus-logs', health: 'green')
		stats.stats = new ApplianceHealthElasticClusterStats(clusterName: 'morpheus', status: 'green')

		when:
		def str = stats.toString()

		then:
		str.contains("status='ok'")
		str.contains('nodes=1')
		str.contains('indices=1')
		str.contains('badIndices=0')
		str.contains('master=')
	}

	void "ApplianceHealthElasticMaster toString includes key fields"() {
		given:
		def master = new ApplianceHealthElasticMaster(id: 'n1', host: 'localhost', ip: '127.0.0.1', node: 'master-node')

		when:
		def str = master.toString()

		then:
		str.contains("id='n1'")
		str.contains("host='localhost'")
		str.contains("ip='127.0.0.1'")
		str.contains("node='master-node'")
	}

	void "ApplianceHealthElasticNode toString includes key fields"() {
		given:
		def node = new ApplianceHealthElasticNode(ip: '10.0.0.1', name: 'node-1', role: 'mdi', master: '*', heapPercent: '45', ramPercent: '60', cpuCount: '4')

		when:
		def str = node.toString()

		then:
		str.contains("ip='10.0.0.1'")
		str.contains("name='node-1'")
		str.contains("role='mdi'")
		str.contains("heapPercent='45'")
	}

	void "ApplianceHealthElasticIndex toString includes key fields"() {
		given:
		def index = new ApplianceHealthElasticIndex(index: 'morpheus-logs', health: 'green', status: 'open', uuid: 'uuid1', primary: '1', replicas: '1', count: '1000', primarySize: '10mb', totalSize: '10mb')

		when:
		def str = index.toString()

		then:
		str.contains("index='morpheus-logs'")
		str.contains("health='green'")
		str.contains("status='open'")
		str.contains("count='1000'")
	}

	void "ApplianceHealthElasticClusterStats toString includes key fields"() {
		given:
		def stats = new ApplianceHealthElasticClusterStats(status: 'green', clusterName: 'morpheus', nodeTotal: '3', nodeData: '3', shards: '15', primary: '5', activePercent: '100%')

		when:
		def str = stats.toString()

		then:
		str.contains("status='green'")
		str.contains("clusterName='morpheus'")
		str.contains("nodeTotal='3'")
		str.contains("activePercent='100%'")
	}
}
