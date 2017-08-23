package de.eightbitboy.grafie

import de.eightbitboy.grafie.testhelper.TestProjectDirectory
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.gradle.testkit.runner.UnexpectedBuildFailure
import spock.lang.Shared
import spock.lang.Specification

class GrafieTest extends Specification {

    @Shared
    private TestProjectDirectory projectDir

    private File buildFile

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
        
        grafie {
            password = 'myPassword'
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
        BuildResult result = executeTask('tasks')

        then:
        result.output.contains('Grafie')
        result.output.contains('decryptFiles')
        result.output.contains('encryptFiles')
    }

    def "executing a task without defining a password fails"() {
        setup: 'a build file without a password'
        buildFile.text = """
        plugins {
            id 'de.eightbitboy.grafie'
        }
        """

        //TODO Find out how to test for failing tests if they throw an exception.
        /*
        BuildResult result

        when:
        result = executeTask('encryptFiles')
        then:
        result.task(':encryptFiles').getOutcome() == TaskOutcome.FAILED
        */

        when:
        executeTask('encryptFiles')
        then:
        thrown(UnexpectedBuildFailure)

        /*
        when:
        result = executeTask('decryptFiles')
        then:
        result.task(':decryptFiles').getOutcome() == TaskOutcome.FAILED
        */

        when:
        executeTask('decryptFiles')
        then:
        thrown(UnexpectedBuildFailure)
    }

    def "execute the encryptFiles task"() {
        when:
        BuildResult result = executeTask('encryptFiles')

        then:
        result.task(':encryptFiles').outcome == TaskOutcome.SUCCESS
    }

    def "execute the decryptFiles task"() {
        when:
        BuildResult result = executeTask('decryptFiles')

        then:
        result.task(':decryptFiles').outcome == TaskOutcome.SUCCESS
    }

    def "encrypt a file"() {
        setup:
        File file = new File(projectDir, 'aFile.txt')
        file.write('This is a secret!')

        File encryptedFile = new File(projectDir, 'aFile.txt.grafie')
        encryptedFile.createNewFile()

        expect:
        file.exists()
        encryptedFile.exists()
        encryptedFile.text.isEmpty()

        when:
        BuildResult result = executeTask('encryptFiles')

        then:
        result.task(':encryptFiles').outcome == TaskOutcome.SUCCESS
        !encryptedFile.text.isEmpty()
    }

    def "decrypt a file"() {
        setup:
        File file = new File(projectDir, 'anotherFile.txt')
        file.write('This is another secret!')

        File encryptedFile = new File(projectDir, 'anotherFile.txt.grafie')
        encryptedFile.createNewFile()

        executeTask('encryptFiles')

        file.delete()

        expect:
        !file.exists()
        encryptedFile.exists()
        !encryptedFile.text.isEmpty()

        when:
        BuildResult result = executeTask('decryptFiles')

        then:
        result.task(':decryptFiles').outcome == TaskOutcome.SUCCESS
        file.exists()
        !file.text.isEmpty()
        file.text == 'This is another secret!'
    }

    BuildResult executeTask(String task) {
        return GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments(task)
                .build()
    }
}
