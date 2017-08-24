package de.eightbitboy.grafie

import spock.lang.Specification
import spock.lang.Subject

class GrafieExtensionTest extends Specification {

    @Subject
    private GrafieExtension extension

    def setup() {
        extension = new GrafieExtension()
    }

    def "set and get the password"() {
        when:
        extension.setPassword('secret')

        then:
        extension.password == 'secret'
    }

    def "the password is null"() {
        expect:
        extension.password == null
    }
}
