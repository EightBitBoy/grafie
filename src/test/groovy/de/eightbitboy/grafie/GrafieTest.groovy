package de.eightbitboy.grafie

import de.eightbitboy.grafie.testhelper.TestProjectDirectory
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Specification

class GrafieTest extends Specification {

    @Shared
    TestProjectDirectory projectDir

    File buildFile

    def setupSpec() {
        projectDir = new TestProjectDirectory()
        projectDir.mkdirs()
    }

    def cleanupSpec() {
        projectDir.deleteRecursively()
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

    def "add the plugin to a real project"() {
        when:
        BuildResult result = getResultFromTask('tasks')

        then:
        result.output.contains('Grafie')
        result.output.contains('decryptFiles')
        result.output.contains('encryptFiles')
    }

    def "execute the encryptFiles task"() {
        when:
        BuildResult result = getResultFromTask('encryptFiles')

        then:
        result.task(':encryptFiles').getOutcome() == TaskOutcome.SUCCESS
    }

    def "execute the decryptFiles task"() {
        when:
        BuildResult result = getResultFromTask('decryptFiles')

        then:
        result.task(':decryptFiles').getOutcome() == TaskOutcome.SUCCESS
    }

    @PendingFeature
    def "encrypt a file"() {
    }

    @PendingFeature
    def "decrypt a file"() {
    }

    @PendingFeature
    def "executing a task without a password should fail"() {
    }

    BuildResult getResultFromTask(String task) {
        return GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments(task)
                .build()
    }
}
