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

    /*
    https://docs.gradle.org/current/userguide/test_kit.html
    https://github.com/ysb33r/gradleTest
    https://github.com/eriwen/gradle-js-plugin/tree/master/src/test/groovy/com/eriwen/gradle/js/util
     */

    def "encrypt a file"() {
        setup:
        File projectDir = new File('./testProject')
        projectDir.mkdirs()
        File buildFile = new File(projectDir, 'build.gradle')
        String buildScript = """\
task fooBar{
    println('### foobar')
}
"""
        buildFile.write(buildScript)

        cleanup:
        buildFile.delete()
        projectDir.delete()
    }

    def "decrypt a file"() {

    }
}
