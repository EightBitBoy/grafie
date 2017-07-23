package de.eightbitboy.grafie

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FileCryptoTask extends DefaultTask {
    enum Mode {
        DECRYPT,
        ENCRYPT
    }

    Mode mode = Mode.ENCRYPT
    String password = ''
    String fileSuffix = ''

    @TaskAction
    void cryptoAction() {
        FileCryptoUtil cryptoUtil = new FileCryptoUtil(password)

        if (mode == Mode.DECRYPT) {
        }
        if (mode == Mode.ENCRYPT) {
        }
    }
}
