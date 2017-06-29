package de.eightbitboy.grafie

import spock.lang.Specification

class FileUtilTest extends Specification {

    FileUtil fileUtil

    def setup() {
        fileUtil = new FileUtil('.FileUtilTest', new File('.'))
    }

    def "check the file extension"() {
        when:
        FileUtil util1 = new FileUtil('test1', new File('.'))
        FileUtil util2 = new FileUtil('.test2', new File('.'))

        then:
        util1.getFileExtension() == '.test1'
        util2.getFileExtension() == '.test2'
    }

    def "get the file name of an unencrypted file from an encrypted file"() {

    }

    def "get the file name of an encrypted file from an unencrypted file"() {

    }

    def "find all encrypted files"() {
        setup:
        File file1 = new File('file1.txt.FileUtilTest')
        File file2 = new File('file2.txt.FileUtilTest')
        File file3 = new File('foo/file3.txt.FileUtilTest')
        File file4 = new File('bar/file4.txt.FileUtilTest')
        file3.mkdirs()
        file4.mkdirs()
        file1.createNewFile()
        file2.createNewFile()
        file3.createNewFile()
        file4.createNewFile()

        when:
        List<File> files = fileUtil.getEncryptedFiles()

        then:
        files.find{it.canonicalPath == file1.canonicalPath}
        files.find{it.canonicalPath == file2.canonicalPath}
        files.find{it.canonicalPath == file3.canonicalPath}
        files.find{it.canonicalPath == file4.canonicalPath}

        cleanup:
        file1.delete()
        file2.delete()
        file3.delete()
        file4.delete()
    }

    def "find all unencrypted files from encrypted file"() {

    }
}
