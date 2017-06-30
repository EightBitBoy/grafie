package de.eightbitboy.grafie

import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Ignore
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Specification

class GrafieTest extends Specification {

    @Shared
    File projectDir
    File buildFile

    def setupSpec() {
        projectDir = new File('./testProject')
        projectDir.mkdirs()
    }

    def cleanupSpec() {
        FileUtils.deleteDirectory(projectDir)
    }

    def setup() {
        buildFile = new File(projectDir, 'build.gradle')
        buildFile << """
plugins {
    id 'de.eightbitboy.grafie'
}
"""
    }

    def cleanup() {
        buildFile.delete()
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
    https://github.com/gradle/gradle/tree/master/subprojects/docs/src/samples/testKit/gradleRunner
    https://github.com/gradle/gradle/blob/master/design-docs/testing-toolkit.md#milestone-3
    https://blog.gradle.org/introducing-testkit
    https://github.com/ysb33r/gradleTest
    https://github.com/eriwen/gradle-js-plugin/tree/master/src/test/groovy/com/eriwen/gradle/js/util
    https://discuss.gradle.org/t/separate-execution-for-java-unit-and-integration-tests/8713
    https://discuss.gradle.org/t/how-do-i-unit-test-custom-tasks/3815
    https://discuss.gradle.org/t/how-to-execute-a-task-in-unit-test-for-custom-plugin/6771/3
     */

    def "add the plugin to a real project"() {
        when:
        BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments('tasks')
                .build()

        then:
        result.output.contains('Grafie')
        result.output.contains('decryptFiles')
        result.output.contains('encryptFiles')
    }

    @PendingFeature
    def "encrypt a file"() {
        when:
        BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments('encryptFiles')
                .build()

        then:
        result.getOutput().contains('### encrypt')
        result.task(':encryptFiles').getOutcome() == TaskOutcome.SUCCESS
    }

    @PendingFeature
    def "decrypt a file"() {
        when:
        BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments('decryptFiles')
                .build()

        then:
        result.getOutput().contains('### decrypt')
        result.task(':decryptFiles').getOutcome() == TaskOutcome.SUCCESS
    }
}
