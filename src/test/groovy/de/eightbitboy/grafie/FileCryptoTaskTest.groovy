package de.eightbitboy.grafie

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class FileCryptoTaskTest extends Specification {

    def "add the task to a project"() {
        when:
        Project project = ProjectBuilder.builder().build()
        def task = project.task('customTask', type: FileCryptoTask)

        then:
        task instanceof FileCryptoTask
    }
}
