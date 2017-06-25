package de.eightbitboy.grafie

import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.TaskAction

class FileCryptoTask extends DefaultTask {
    enum Mode {
        DECRYPT,
        ENCRYPT
    }

    Mode mode = Mode.ENCRYPT
    final String GRAFIE_KEY = "grafieKey"

    @TaskAction
    void cryptoAction() {
        doLast {
            println(project.projectDir)

            FileCryptoUtil cryptoUtil = new FileCryptoUtil()
            if (mode == Mode.DECRYPT) {
                //cryptoUtil.decrypt()
            }
            if (mode == Mode.ENCRYPT) {
                //cryptoUtil.encrypt()
            }
        }
    }

    /** Get the encryption/decryption key. */
    String getKey() {
        if (project.hasProperty(GRAFIE_KEY)) {
            if (project.property(GRAFIE_KEY)) {
                return project.property(GRAFIE_KEY)
            } else {
                throw new InvalidUserDataException("The property 'grafieKey' has no value!")
            }
        } else {
            throw new InvalidUserDataException("No encryption/decryption key is provided via gradle.properties! Add 'grafieKey=YOUR_KEY' to the gradle.properties!")
        }
    }
}
