package de.eightbitboy.grafie

import spock.lang.Specification

class FileUtilTest extends Specification {

    FileUtil fileUtil

    def setup() {
        fileUtil = new FileUtil('.util_test')
    }

    def "check the file extension"(){
        when:
        FileUtil util1 = new FileUtil('test1')
        FileUtil util2 = new FileUtil('.test2')

        then:
        util1.getFileExtension() == '.test1'
        util2.getFileExtension() == '.test2'
    }

    def "get the file name of an unencrypted file from an encrypted file"(){

    }

    def "get the file name of an encrypted file from an unencrypted file"(){

    }

    def "find all encrypted files"(){

    }

    def "find all unencrypted files from encrypted file"(){

    }
}
