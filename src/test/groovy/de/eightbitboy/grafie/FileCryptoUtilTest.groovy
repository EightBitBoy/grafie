package de.eightbitboy.grafie

import org.apache.commons.lang3.ArrayUtils
import org.gradle.internal.impldep.org.apache.commons.lang.RandomStringUtils
import spock.lang.PendingFeature
import spock.lang.Specification

class FileCryptoUtilTest extends Specification {
    FileCryptoUtil cryptoUtil

    def setup() {
        cryptoUtil = new FileCryptoUtil()
    }

    def "a password must not be null"() {
        when:
        cryptoUtil.processPassword(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "a password must not be empty"() {
        when:
        cryptoUtil.processPassword('')

        then:
        thrown(IllegalArgumentException)
    }

    def "process a password"() {
        when:
        byte[] key = cryptoUtil.processPassword('secret')

        then:
        key != null
        ArrayUtils.isNotEmpty(key)
        key.length == 16
    }

    def "process passwords of arbitrary length"() {
        when:
        byte[] key = cryptoUtil.processPassword(password)
        then:

        then:
        key != null
        ArrayUtils.isNotEmpty(key)
        key.length == 16

        where:
        password << (1..200).collect() { RandomStringUtils.random(it) }
    }

    def "decrypt a file"() {
        setup:
        File file = new File('file.txt')
        file.write("This is a test.")

        when:
        cryptoUtil.encrypt('password', file)

        then:
        new File('file.txt.grafie').exists()

        when:
        file.delete()

        then:
        !new File('file.txt').exists()
    }

    @PendingFeature
    def "encrypt a file"() {

    }

    @PendingFeature
    def "encrypt and decrypt files of arbitrary length"() {

    }
}
