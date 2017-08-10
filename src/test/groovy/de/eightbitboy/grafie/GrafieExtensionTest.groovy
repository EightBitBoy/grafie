package de.eightbitboy.grafie

import spock.lang.Specification

class GrafieExtensionTest extends Specification {

    GrafieExtension extension

    def setup() {
        extension = new GrafieExtension()
    }

    def "set and get the password"() {
        when:
        extension.setPassword('secret')

        then:
        extension.getPassword() == 'secret'
    }

    def "the password is null"() {
        expect:
        extension.getPassword() == null
    }
}
