package de.eightbitboy.grafie

import org.apache.commons.lang3.ArrayUtils
import org.gradle.internal.impldep.org.apache.commons.lang.RandomStringUtils
import spock.lang.PendingFeature
import spock.lang.Specification

class CryptoUtilTest extends Specification {
    CryptoUtil cryptoUtil

    def setup() {
        cryptoUtil = new CryptoUtil('password', '.encrypted')
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

    def "encrypt a file"() {
        setup:
        File file = new File('file.txt')
        file.write('This is a test.')

        when:
        cryptoUtil.encrypt('password', file)

        then:
        File encryptedFile = new File('file.txt.CryptoUtilTest')
        encryptedFile.exists()
        !encryptedFile.getText('UTF-8').isEmpty()

        cleanup:
        file.delete()
        encryptedFile.delete()
    }

    def "encrypt and decrypt a file"() {
        setup:
        File file = new File('file.txt')
        file.write('This is a test.')

        File encryptedFile = new File('file.txt.CryptoUtilTest')
        assert !encryptedFile.exists()

        when:
        cryptoUtil.encrypt('password', file)

        then:
        encryptedFile.exists()

        when:
        file.delete()

        then:
        !file.exists()

        when:
        cryptoUtil.decrypt('password', encryptedFile)
        file = new File('file.txt')

        then:
        file.exists()
        file.getText('UTF-8') == 'This is a test.'

        cleanup:
        file.delete()
        encryptedFile.delete()
    }

    @PendingFeature
    def "encrypt and decrypt files of arbitrary length"() {
        //TODO
    }

    def "encrypt a text multiple times, the encrypted text should not change"() {
        setup:
        File file1 = new File('file1.txt')
        file1.write('This is an encryption test!')
        File file2 = new File('file2.txt')
        file2.write('This is an encryption test!')

        cryptoUtil.encrypt('password', file1)
        cryptoUtil.encrypt('password', file2)

        File encryptedFile1 = new File('file1.txt.CryptoUtilTest')
        File encryptedfile2 = new File('file2.txt.CryptoUtilTest')

        file1.delete()
        file2.delete()

        expect:
        !file1.exists()
        !file2.exists()
        encryptedFile1.exists()
        encryptedFile1.exists()

        //TODO

        cleanup:
        file1.delete()
        file2.delete()
        encryptedFile1.delete()
        encryptedfile2.delete()
    }
}
