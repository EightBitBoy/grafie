package de.eightbitboy.grafie

import de.eightbitboy.grafie.testhelper.TestProjectDirectory
import org.gradle.api.GradleException
import spock.lang.Shared
import spock.lang.Specification

class FileCryptoUtilTest extends Specification {

    @Shared
    private TestProjectDirectory projectDir

    private FileCryptoUtil cryptoUtil

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

    def "encrypt a specific file"() {
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
        !encryptedFile.text.isEmpty()
    }

    def "encrypt and decrypt a specific file"() {
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
        file.text == 'This is another test.'
    }

    def "encrypt and decrypt a file by suffix"() {
        setup:
        File file = new File(projectDir, 'a.txt')
        file.write('Secret!')
        File encryptedFile = new File(projectDir, 'a.txt.encrypted')
        encryptedFile.createNewFile()

        expect:
        encryptedFile.exists()
        encryptedFile.text.isEmpty()

        when:
        cryptoUtil.encryptFilesWithSuffix()
        then:
        !encryptedFile.text.isEmpty()

        when:
        file.delete()
        then:
        !file.exists()

        when:
        cryptoUtil.decryptFilesWithSuffix()
        then:
        file.exists()
        file.text == 'Secret!'
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
        !encryptedFile1.text.isEmpty()
        !encryptedFile2.text.isEmpty()
        encryptedFile1.text == encryptedFile2.text
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

    def "encrypting and decrypting a file with different passwords fails"() {
        setup:
        FileCryptoUtil cryptoUtilOne = new FileCryptoUtil('1234', '.encrypted')
        FileCryptoUtil cryptoUtilTwo = new FileCryptoUtil('abcd', '.encrypted')

        File file = new File(projectDir, 'cat.txt')
        file.write('Meeeooowwww!')

        File encryptedFile = new File(projectDir, 'cat.txt.encrypted')

        when:
        cryptoUtilOne.encryptFile(file)
        then:
        encryptedFile.exists()

        when:
        cryptoUtilTwo.decryptFile(encryptedFile)
        then:
        thrown(GradleException)
    }
}
