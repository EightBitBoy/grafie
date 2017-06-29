package de.eightbitboy.grafie

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class GrafieTest extends Specification {
    def "add the plugin to a project"() {
        setup:
        Project project = ProjectBuilder.builder().build()

        when:
        project.pluginManager.apply('de.eightbitboy.grafie')

        then:
        project.tasks.decryptFiles instanceof FileCryptoTask
        project.tasks.encryptFiles instanceof FileCryptoTask
    }

    def "find files for which an encrypted file exists"(){
        setup:
        File file1 = new File('file1.txt.grafie')
    }
}
