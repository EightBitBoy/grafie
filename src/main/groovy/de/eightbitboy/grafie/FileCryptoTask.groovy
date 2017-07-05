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
    String password = ''
    final String GRAFIE_PASSWORD = "grafiePassword"

    @TaskAction
    void cryptoAction() {
        FileCryptoUtil cryptoUtil = new FileCryptoUtil(password)

        if (mode == Mode.DECRYPT) {
        }
        if (mode == Mode.ENCRYPT) {
        }
    }

    /** Get the encryption/decryption key. */
    String getKey() {
        if (project.hasProperty(GRAFIE_PASSWORD)) {
            if (project.property(GRAFIE_PASSWORD)) {
                return project.property(GRAFIE_PASSWORD)
            } else {
                throw new InvalidUserDataException("The property 'grafieKey' has no value!")
            }
        } else {
            throw new InvalidUserDataException("No encryption/decryption key is provided via gradle.properties! Add 'grafieKey=YOUR_KEY' to the gradle.properties!")
        }
    }
}
