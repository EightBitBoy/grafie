package de.eightbitboy.grafie

import org.apache.commons.lang3.ArrayUtils
import org.gradle.api.InvalidUserDataException
import org.gradle.internal.impldep.org.apache.commons.lang.RandomStringUtils
import spock.lang.Specification

class EncryptionKeyTest extends Specification {
    def "a password must not be null"() {
        when:
        EncryptionKey.fromPassword(null)

        then:
        thrown(InvalidUserDataException)
    }

    def "a password must not be empty"() {
        when:
        EncryptionKey.fromPassword('')

        then:
        thrown(InvalidUserDataException)
    }

    def "a key is not null or empty"() {
        when:
        byte[] key = EncryptionKey.fromPassword('secret')

        then:
        key != null
        ArrayUtils.isNotEmpty(key)
    }

    def "a key has a length of 16 byte"() {
        when:
        byte[] key = EncryptionKey.fromPassword('secret')

        then:
        key.length == 16
    }

    def "get keys from passwords of arbitrary length"() {
        when:
        byte[] key = EncryptionKey.fromPassword(password)

        then:
        key != null
        ArrayUtils.isNotEmpty(key)
        key.length == 16

        where:
        password << (1..100).collect { RandomStringUtils.random(it) }
    }
}
