package de.eightbitboy.grafie

import de.eightbitboy.grafie.testhelper.TestProjectDirectory
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Specification

class FileCryptoUtilTest extends Specification {

    @Shared
    TestProjectDirectory projectDir

    FileCryptoUtil cryptoUtil

    def setupSpec() {
        projectDir = new TestProjectDirectory()
        projectDir.mkdirs()
    }

    def cleanupSpec() {
        projectDir.deleteRecursively()
    }

    def setup() {
        cryptoUtil = new FileCryptoUtil('password', '.encrypted')
    }

    def "encrypt a file"() {
        setup:
        File file = new File(projectDir, 'encryptMe.txt')
        file.write('This is a test.')

        when:
        cryptoUtil.encryptFile(file)

        then:
        File encryptedFile = new File(projectDir, 'encryptMe.txt.encrypted')
        encryptedFile.exists()
        !encryptedFile.getText('UTF-8').isEmpty()
    }

    def "encrypt and decrypt a file"() {
        setup:
        File file = new File('file.txt')
        file.write('This is a test.')

        File encryptedFile = new File('file.txt.FileCryptoUtilTest')
        assert !encryptedFile.exists()

        when:
        cryptoUtil.encryptFile(file)

        then:
        encryptedFile.exists()

        when:
        file.delete()

        then:
        !file.exists()

        when:
        cryptoUtil.decryptFile(encryptedFile)
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

        cryptoUtil.encryptFile(file1)
        cryptoUtil.encryptFile(file2)

        File encryptedFile1 = new File('file1.txt.FileCryptoUtilTest')
        File encryptedFile2 = new File('file2.txt.FileCryptoUtilTest')

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
        encryptedFile2.delete()
    }

    @PendingFeature
    def "encrypting a file which already has the file suffix is not possible"() {

    }

    @PendingFeature
    def "decrypting a file which has no file suffix is not possible"() {

    }
}
