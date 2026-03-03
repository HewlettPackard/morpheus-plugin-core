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
}
