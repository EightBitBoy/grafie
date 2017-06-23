package de.eightbitboy.grafie

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class GrafieTest extends Specification {
    def "run a simple test"() {
        setup:
        def numbers = [1, 2]

        when:
        numbers.add(3)

        then:
        numbers.size() == 3
    }

    def "do something"() {
        when:
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply('de.eightbitboy.grafie')

        then:
        project.tasks.crypto instanceof FileCryptoTask
    }
}
