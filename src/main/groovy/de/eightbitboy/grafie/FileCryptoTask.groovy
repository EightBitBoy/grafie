package de.eightbitboy.grafie

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
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
        checkPassword(password)

        project.fai

        FileCryptoUtil cryptoUtil = new FileCryptoUtil(password, fileSuffix)

        if (mode == Mode.DECRYPT) {
            cryptoUtil.decryptFilesWithSuffix()
        }
        if (mode == Mode.ENCRYPT) {
            cryptoUtil.encryptFilesWithSuffix()
        }
    }

    void checkPassword(String password) {
        if (password == null) {
            throw new GradleException(
                    "No password has been provided! Use 'grafie.password' to define a password!")
        }
        if (password.isEmpty()) {
            throw new GradleException('The provided password is empty!')
        }
    }
}
