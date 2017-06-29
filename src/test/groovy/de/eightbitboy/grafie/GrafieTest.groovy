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

    def "encrypt a file"() {
        setup:
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply('de.eightbitboy.grafie')
        File file = new File('file.txt')
        file.createNewFile()
        file.write('This text is a secret!')

        when:
        File encryptedFile = new File('file.txt.grafie')
        encryptedFile.createNewFile()

        then:
        encryptedFile.getText().isEmpty()

        when:
        //TODO Does this really execute the task?
        project.tasks.encryptFiles

        then:
        !encryptedFile.getText().isEmpty()

        cleanup:
        file.delete()
        encryptedFile.delete()
    }
}
