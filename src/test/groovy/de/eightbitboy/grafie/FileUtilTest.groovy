package de.eightbitboy.grafie

import de.eightbitboy.grafie.testhelper.TestProjectDirectory
import org.gradle.api.GradleException
import spock.lang.Shared
import spock.lang.Specification

class FileUtilTest extends Specification {

    @Shared
    TestProjectDirectory projectDir

    FileUtil fileUtil

    def setup() {
        projectDir = new TestProjectDirectory()
        projectDir.mkdirs()

        fileUtil = new FileUtil('.encrypted', new File('.'))
    }

    def cleanup() {
        projectDir.deleteRecursively()
    }

    def "check a valid file suffix"() {
        when:
        FileUtil.checkFileSuffix('.encrypted')

        then:
        noExceptionThrown()
    }

    def "throw an error when the file suffix is invalid"() {
        when:
        FileUtil.checkFileSuffix('@encrypted')

        then:
        thrown(GradleException)
    }

    def "get an unencrypted file from an encrypted file"() {
        setup:
        File encryptedFile1 = new File(projectDir, 'file1.txt.encrypted')
        File encryptedFile2 = new File(projectDir, 'foo/file2.txt.encrypted')

        when:
        File file1 = fileUtil.findUnencryptedFileFromEncryptedFile(encryptedFile1)
        File file2 = fileUtil.findUnencryptedFileFromEncryptedFile(encryptedFile2)

        then:
        !file1.canonicalPath.endsWith('.encrypted')
        !file2.canonicalPath.endsWith('.encrypted')
        file1.canonicalPath == encryptedFile1.canonicalPath.replace('.encrypted', '')
        file2.canonicalPath == encryptedFile2.canonicalPath.replace('.encrypted', '')
        file1.canonicalPath == new File(projectDir, 'file1.txt').canonicalPath
        file2.canonicalPath == new File(projectDir, 'foo/file2.txt').canonicalPath
    }

    def "get encrypted file from an unencrypted file"() {
        setup:
        File file1 = new File(projectDir, 'file1.txt')
        File file2 = new File(projectDir, 'foo/file2.txt')

        when:
        File encryptedFile1 = fileUtil.findEncryptedFileFromUnencryptedFile(file1)
        File encryptedFile2 = fileUtil.findEncryptedFileFromUnencryptedFile(file2)

        then:
        encryptedFile1.canonicalPath.endsWith('.encrypted')
        encryptedFile2.canonicalPath.endsWith('.encrypted')
        encryptedFile1.canonicalPath == file1.canonicalPath + '.encrypted'
        encryptedFile2.canonicalPath == file2.canonicalPath + '.encrypted'
        encryptedFile1.canonicalPath == new File(projectDir, 'file1.txt.encrypted').canonicalPath
        encryptedFile2.canonicalPath == new File(projectDir, 'foo/file2.txt.encrypted').canonicalPath
    }

    def "find all encrypted files"() {
        setup:
        File file1 = new File(projectDir, 'file1.txt.encrypted')
        File file2 = new File(projectDir, 'file2.txt.encrypted')
        File file3 = new File(projectDir, 'foo/file3.txt.encrypted')
        File file4 = new File(projectDir, 'bar/file4.txt.encrypted')
        file3.getParentFile().mkdirs()
        file4.getParentFile().mkdirs()
        file1.createNewFile()
        file2.createNewFile()
        file3.createNewFile()
        file4.createNewFile()

        when:
        List<File> files = fileUtil.findAllEncryptedFiles()

        then:
        files.size() == 4
        files.find { it.canonicalPath == file1.canonicalPath }
        files.find { it.canonicalPath == file2.canonicalPath }
        files.find { it.canonicalPath == file3.canonicalPath }
        files.find { it.canonicalPath == file4.canonicalPath }
    }

    def "find all unencrypted files from encrypted files"() {
        File file1 = new File(projectDir, 'file1.txt.encrypted')
        File file2 = new File(projectDir, 'file2.txt.encrypted')
        File file3 = new File(projectDir, 'foo/file3.txt.encrypted')
        File file4 = new File(projectDir, 'bar/file4.txt.encrypted')
        file3.getParentFile().mkdirs()
        file4.getParentFile().mkdirs()
        file1.createNewFile()
        file2.createNewFile()
        file3.createNewFile()
        file4.createNewFile()

        when:
        List<File> files = fileUtil.findAllUnencryptedFiles()

        then:
        files.size() == 4
        files.find { it.canonicalPath == new File(projectDir, 'file1.txt').canonicalPath }
        files.find { it.canonicalPath == new File(projectDir, 'file2.txt').canonicalPath }
        files.find { it.canonicalPath == new File(projectDir, 'foo/file3.txt').canonicalPath }
        files.find { it.canonicalPath == new File(projectDir, 'bar/file4.txt').canonicalPath }
    }
}
