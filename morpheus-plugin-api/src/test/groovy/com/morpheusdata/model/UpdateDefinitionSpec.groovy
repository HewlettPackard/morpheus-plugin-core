package com.morpheusdata.model

import com.morpheusdata.model.projection.UpdateIdentityProjection
import spock.lang.Specification

import java.util.Date

class UpdateDefinitionSpec extends Specification {

    void "UpdateDefinition has expected default values"() {
        when:
        def ud = new UpdateDefinition()

        then:
        ud.severity == 'normal'
        ud.type == 'security'
        ud.enabled == true
        ud.zeroDowntime == false
        ud.requiresReboot == false
        ud.requiresMaintenanceMode == false
        ud.requiresRestart == false
        ud.supportsRollback == false
        ud.peerPersistence == false
        ud.isPlugin == false
    }

    void "UpdateDefinition nullable fields default to null"() {
        when:
        def ud = new UpdateDefinition()

        then:
        ud.description == null
        ud.catalogVersion == null
        ud.updateReleaseDate == null
        ud.code == null
        ud.name == null
        ud.version == null
        ud.refType == null
        ud.refId == null
    }

    void "UpdateDefinition severity can be set"() {
        given:
        def ud = new UpdateDefinition()

        when:
        ud.setSeverity('critical')

        then:
        ud.getSeverity() == 'critical'
    }

    void "UpdateDefinition type can be set"() {
        given:
        def ud = new UpdateDefinition()

        when:
        ud.setType('bugfix')

        then:
        ud.getType() == 'bugfix'
    }

    void "UpdateDefinition description can be set"() {
        given:
        def ud = new UpdateDefinition()

        when:
        ud.setDescription('Fixes a critical security vulnerability')

        then:
        ud.getDescription() == 'Fixes a critical security vulnerability'
    }

    void "UpdateDefinition enabled can be toggled"() {
        given:
        def ud = new UpdateDefinition()

        when:
        ud.setEnabled(false)

        then:
        ud.getEnabled() == false
    }

    void "UpdateDefinition zeroDowntime can be set"() {
        given:
        def ud = new UpdateDefinition()

        when:
        ud.setZeroDowntime(true)

        then:
        ud.getZeroDowntime() == true
    }

    void "UpdateDefinition catalogVersion can be set"() {
        given:
        def ud = new UpdateDefinition()

        when:
        ud.setCatalogVersion('2024.1.0')

        then:
        ud.getCatalogVersion() == '2024.1.0'
    }

    void "UpdateDefinition updateReleaseDate can be set"() {
        given:
        def ud = new UpdateDefinition()
        def date = new Date()

        when:
        ud.setUpdateReleaseDate(date)

        then:
        ud.getUpdateReleaseDate() == date
    }

    void "UpdateDefinition extends UpdateIdentityProjection"() {
        expect:
        new UpdateDefinition() instanceof UpdateIdentityProjection
    }

    void "UpdateDefinition code and name are accessible via projection inheritance"() {
        given:
        def ud = new UpdateDefinition()

        when:
        ud.setCode('storage-server-type.firmware.1.0.0')
        ud.setName('Firmware 1.0.0')

        then:
        ud.getCode() == 'storage-server-type.firmware.1.0.0'
        ud.getName() == 'Firmware 1.0.0'
    }

    void "UpdateIdentityProjection exposes code and name for sync matching"() {
        given:
        def proj = new UpdateIdentityProjection()

        when:
        proj.setCode('my.update.code')
        proj.setName('My Update')

        then:
        proj.getCode() == 'my.update.code'
        proj.getName() == 'My Update'
    }

    void "UpdateIdentityProjection identity properties include code and name"() {
        given:
        def proj = new UpdateIdentityProjection()
        proj.setCode('test.code')
        proj.setName('Test Name')

        when:
        def identity = proj.getIdentityProperties()

        then:
        identity.containsKey('code')
        identity.containsKey('name')
        identity['code'] == 'test.code'
        identity['name'] == 'Test Name'
    }
}
