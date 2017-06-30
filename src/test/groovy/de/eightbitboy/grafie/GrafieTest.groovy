package de.eightbitboy.grafie

import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.PendingFeature
import spock.lang.Specification

class GrafieTest extends Specification {

    File projectDir
    File buildFile

    def setup() {
        projectDir = new File('./testProject')
        projectDir.mkdirs()

        buildFile = new File(projectDir, 'build.gradle')
        buildFile << """
plugins {
    id 'de.eightbitboy.grafie'
}
"""
    }

    def cleanup() {
        FileUtils.deleteDirectory(projectDir)
    }

    def "add the plugin to a mocked project"() {
        setup:
        Project project = ProjectBuilder.builder().build()

        when:
        project.pluginManager.apply('de.eightbitboy.grafie')

        then:
        project.tasks.decryptFiles instanceof FileCryptoTask
        project.tasks.encryptFiles instanceof FileCryptoTask
    }

    /*
    https://docs.gradle.org/3.3/userguide/test_kit.html
    https://github.com/gradle/gradle/tree/master/subprojects/docs/src/samples/testKit/gradleRunner
     */

    def "add the plugin to a real project"() {
        when:
        BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments('encryptFiles')
                .build()

        then:
        result.task(':encryptFiles').getOutcome() == TaskOutcome.SUCCESS
    }

    /*
    https://github.com/gradle/gradle/blob/master/design-docs/testing-toolkit.md#milestone-3
    https://blog.gradle.org/introducing-testkit
    https://docs.gradle.org/current/userguide/test_kit.html
    https://github.com/ysb33r/gradleTest
    https://github.com/eriwen/gradle-js-plugin/tree/master/src/test/groovy/com/eriwen/gradle/js/util
    https://discuss.gradle.org/t/separate-execution-for-java-unit-and-integration-tests/8713
    https://discuss.gradle.org/t/how-do-i-unit-test-custom-tasks/3815
    https://discuss.gradle.org/t/how-to-execute-a-task-in-unit-test-for-custom-plugin/6771/3
     */

    @PendingFeature
    def "encrypt a file"() {
        setup:
        buildFile << """
task fooBar{
    doLast{
        println('### foobar')
    }
}
"""

        when:
        BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments('fooBar')
                .build()

        then:
        result.getOutput().contains('### foobar')
        result.task(':fooBar').getOutcome() == TaskOutcome.SUCCESS

        cleanup:
        buildFile.delete()
        projectDir.delete()
    }

    def "decrypt a file"() {

    }
}
