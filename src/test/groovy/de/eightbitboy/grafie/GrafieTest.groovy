package de.eightbitboy.grafie

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.PendingFeature
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

    /*
    https://docs.gradle.org/current/userguide/test_kit.html
    https://github.com/ysb33r/gradleTest
    https://github.com/eriwen/gradle-js-plugin/tree/master/src/test/groovy/com/eriwen/gradle/js/util
     */
    @PendingFeature
    def "encrypt a file"() {
    }

    @PendingFeature
    def "decrypt a file"(){

    }
}
