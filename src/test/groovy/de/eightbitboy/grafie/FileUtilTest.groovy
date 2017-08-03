package de.eightbitboy.grafie

import spock.lang.Specification

class FileUtilTest extends Specification {

    FileUtil fileUtil

    def setup() {
        fileUtil = new FileUtil('.FileUtilTest', new File('.'))
    }

    def "check the file suffix"() {
        when:
        FileUtil util1 = new FileUtil('.test1', new File('.'))
        FileUtil util2 = new FileUtil('_test2', new File('.'))

        then:
        util1.getFileSuffix() == '.test1'
        util2.getFileSuffix() == '_test2'
    }

    def "throw an error on invalid file suffix"() {
        expect:
        'a' == 'b'
    }

    def "get an unencrypted file from an encrypted file"() {
        setup:
        File encryptedFile1 = new File('file1.txt.FileUtilTest')
        File encryptedFile2 = new File('foo/file2.txt.FileUtilTest')

        when:
        File file1 = fileUtil.findUnencryptedFileFromEncryptedFile(encryptedFile1)
        File file2 = fileUtil.findUnencryptedFileFromEncryptedFile(encryptedFile2)

        then:
        !file1.canonicalPath.endsWith('.FileUtilTest')
        !file2.canonicalPath.endsWith('.FileUtilTest')
        file1.canonicalPath == encryptedFile1.canonicalPath.replace('.FileUtilTest', '')
        file2.canonicalPath == encryptedFile2.canonicalPath.replace('.FileUtilTest', '')
        file1.canonicalPath == new File('file1.txt').canonicalPath
        file2.canonicalPath == new File('foo/file2.txt').canonicalPath

        cleanup:
        encryptedFile1.delete()
        encryptedFile2.delete()
        file1.delete()
        file2.delete()
        new File('foo').delete()
    }

    def "get encrypted file from an unencrypted file"() {
        setup:
        File file1 = new File('file1.txt')
        File file2 = new File('foo/file2.txt')

        when:
        File encryptedFile1 = fileUtil.findEncryptedFileFromUnencryptedFile(file1)
        File encryptedFile2 = fileUtil.findEncryptedFileFromUnencryptedFile(file2)

        then:
        encryptedFile1.canonicalPath.endsWith('.FileUtilTest')
        encryptedFile2.canonicalPath.endsWith('.FileUtilTest')
        encryptedFile1.canonicalPath == file1.canonicalPath + '.FileUtilTest'
        encryptedFile2.canonicalPath == file2.canonicalPath + '.FileUtilTest'
        encryptedFile1.canonicalPath == new File('file1.txt.FileUtilTest').canonicalPath
        encryptedFile2.canonicalPath == new File('foo/file2.txt.FileUtilTest').canonicalPath

        cleanup:
        file1.delete()
        file2.delete()
        encryptedFile1.delete()
        encryptedFile2.delete()
        new File('foo').delete()
    }

    def "find all encrypted files"() {
        setup:
        File file1 = new File('file1.txt.FileUtilTest')
        File file2 = new File('file2.txt.FileUtilTest')
        File file3 = new File('foo/file3.txt.FileUtilTest')
        File file4 = new File('bar/file4.txt.FileUtilTest')
        file3.getParentFile().mkdirs()
        file4.getParentFile().mkdirs()
        file1.createNewFile()
        file2.createNewFile()
        file3.createNewFile()
        file4.createNewFile()

        when:
        List<File> files = fileUtil.findAllEncryptedFiles()

        then:
        files.find { it.canonicalPath == file1.canonicalPath }
        files.find { it.canonicalPath == file2.canonicalPath }
        files.find { it.canonicalPath == file3.canonicalPath }
        files.find { it.canonicalPath == file4.canonicalPath }

        cleanup:
        file1.delete()
        file2.delete()
        file3.delete()
        file4.delete()
        new File('foo').delete()
        new File('bar').delete()
    }

    def "find all unencrypted files from encrypted file"() {
        File file1 = new File('file1.txt.FileUtilTest')
        File file2 = new File('file2.txt.FileUtilTest')
        File file3 = new File('foo/file3.txt.FileUtilTest')
        File file4 = new File('bar/file4.txt.FileUtilTest')
        file3.getParentFile().mkdirs()
        file4.getParentFile().mkdirs()
        file1.createNewFile()
        file2.createNewFile()
        file3.createNewFile()
        file4.createNewFile()

        when:
        List<File> files = fileUtil.findAllUnencryptedFiles()

        then:
        files.find { it.canonicalPath == new File('file1.txt').canonicalPath }
        files.find { it.canonicalPath == new File('file2.txt').canonicalPath }
        files.find { it.canonicalPath == new File('foo/file3.txt').canonicalPath }
        files.find { it.canonicalPath == new File('bar/file4.txt').canonicalPath }

        cleanup:
        file1.delete()
        file2.delete()
        file3.delete()
        file4.delete()
        new File('foo').delete()
        new File('bar').delete()
    }
}
