package de.eightbitboy.grafie

import org.apache.commons.lang3.ArrayUtils
import org.gradle.internal.impldep.org.apache.commons.lang.RandomStringUtils
import spock.lang.Specification

class PasswordConverterTest extends Specification {
    def "a password must not be null"() {
        when:
        PasswordConverter.convert(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "a password must not be empty"() {
        when:
        PasswordConverter.convert('')

        then:
        thrown(IllegalArgumentException)
    }

    def "a converted password is not null or empty"() {
        when:
        byte[] key = PasswordConverter.convert('secret')

        then:
        key != null
        ArrayUtils.isNotEmpty(key)
    }

    def "a converted password has a length of 16 byte"() {
        when:
        byte[] key = PasswordConverter.convert('secret')

        then:
        key.length == 16
    }

    def "convert passwords of arbitrary length"() {
        when:
        byte[] key = PasswordConverter.convert(password)

        then:
        key != null
        ArrayUtils.isNotEmpty(key)
        key.length == 16

        where:
        password << (1..200).collect() { RandomStringUtils.random(it) }
    }
}
