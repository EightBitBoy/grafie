package de.eightbitboy.grafie

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

enum Mode {
    DECRYPT,
    ENCRYPT
}

class FileCryptoTask extends DefaultTask {
    Mode mode = Mode.ENCRYPT

    @TaskAction
    def cryptoAction() {
        doLast {
            println(project.projectDir)
        }
    }

    void decrypt() {

    }

    void encrypt() {

    }
}
