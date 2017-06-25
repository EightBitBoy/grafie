package de.eightbitboy.grafie

import org.gradle.internal.impldep.org.apache.commons.lang.RandomStringUtils
import spock.lang.Specification

class FileCryptoUtilTest extends Specification {
    FileCryptoUtil cryptoUtil

    def setup() {
        cryptoUtil = new FileCryptoUtil()
    }

    def "process a password"() {
        when:
        byte[] key = cryptoUtil.processKey("secret")

        then:
        key != null
        key.length == 16
    }

    def "process passwords of arbitrary length"() {
        when:
        byte[] key = cryptoUtil.processKey(password)
        then:

        then:
        key != null
        key.length == 16

        where:
        password << (1..200).collect() { RandomStringUtils.random(it) }
    }

    def "encrypt a file"() {

    }

    def "decrypt a file"() {

    }

    def "encrypt and decrypt files of arbitrary length"() {

    }
}
