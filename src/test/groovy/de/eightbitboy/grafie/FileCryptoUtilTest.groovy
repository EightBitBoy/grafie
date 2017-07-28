package de.eightbitboy.grafie

import spock.lang.PendingFeature
import spock.lang.Specification

class FileCryptoUtilTest extends Specification {
    FileCryptoUtil cryptoUtil

    def setup() {
        cryptoUtil = new FileCryptoUtil('password', '.encrypted')
    }

    def "encrypt a file"() {
        setup:
        File file = new File('file.txt')
        file.write('This is a test.')

        when:
        cryptoUtil.encrypt('password', file)

        then:
        File encryptedFile = new File('file.txt.FileCryptoUtilTest')
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

        File encryptedFile = new File('file.txt.FileCryptoUtilTest')
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

        File encryptedFile1 = new File('file1.txt.FileCryptoUtilTest')
        File encryptedfile2 = new File('file2.txt.FileCryptoUtilTest')

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
