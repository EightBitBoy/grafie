package de.eightbitboy.grafie

import spock.lang.Specification

class FileUtilTest extends Specification {

    FileUtil fileUtil

    def setup() {
        fileUtil = new FileUtil('.util_test', new File('.'))
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
        //setup:
        //new File()
    }

    def "find all unencrypted files from encrypted file"() {

    }
}
