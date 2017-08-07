package de.eightbitboy.grafie

import de.eightbitboy.grafie.testhelper.TestProjectDirectory
import org.gradle.api.GradleException
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

        File encryptedFile = new File(projectDir, 'encryptMe.txt.encrypted')

        expect:
        !encryptedFile.exists()

        when:
        cryptoUtil.encryptFile(file)

        then:
        encryptedFile.exists()
        !encryptedFile.getText().isEmpty()
    }

    def "encrypt and decrypt a file"() {
        setup:
        File file = new File(projectDir, 'encryptMeToo.txt')
        file.write('This is another test.')

        File encryptedFile = new File(projectDir, 'encryptMeToo.txt.encrypted')

        expect:
        !encryptedFile.exists()

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
        file = new File(projectDir, 'encryptMeToo.txt')
        then:
        file.exists()
        file.getText() == 'This is another test.'
    }

    def "encrypt a text multiple times, the encrypted text should match"() {
        setup:
        File file1 = new File(projectDir, 'aFile.txt')
        file1.write('This is an encryption test!')
        File file2 = new File(projectDir, 'anotherFile.txt')
        file2.write('This is an encryption test!')


        File encryptedFile1 = new File(projectDir, 'aFile.txt.encrypted')
        File encryptedFile2 = new File(projectDir, 'anotherFile.txt.encrypted')

        when:
        cryptoUtil.encryptFile(file1)
        cryptoUtil.encryptFile(file2)

        then:
        encryptedFile1.exists()
        encryptedFile2.exists()

        and:
        !encryptedFile1.getText().isEmpty()
        !encryptedFile2.getText().isEmpty()
        encryptedFile1.getText() == encryptedFile2.getText()
    }

    def "encrypting a file which already has the file suffix is not possible"() {
        setup:
        File file = new File(projectDir, 'boom.txt.encrypted')
        file.write('This is some encrypted text!')

        when:
        cryptoUtil.encryptFile(file)

        then:
        thrown(GradleException)
    }

    def "decrypting a file which has no file suffix is not possible"() {
        setup:
        File file = new File(projectDir, 'dog.txt')
        file.write('The brown fox jumps over the lazy dog!')

        when:
        cryptoUtil.decryptFile(file)

        then:
        thrown(GradleException)
    }
}
