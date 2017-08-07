package de.eightbitboy.grafie

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskExecutionException

class FileCryptoTask extends DefaultTask {
    enum Mode {
        DECRYPT,
        ENCRYPT
    }

    Mode mode = Mode.ENCRYPT
    private String password
    private String fileSuffix = '.grafie'

    @TaskAction
    void cryptoAction() {
        this.password = project.grafie.password
        checkPassword(this.password)

        FileCryptoUtil cryptoUtil = new FileCryptoUtil(this.password, this.fileSuffix)

        if (mode == Mode.DECRYPT) {
            cryptoUtil.decryptFilesWithSuffix()
        }
        if (mode == Mode.ENCRYPT) {
            cryptoUtil.encryptFilesWithSuffix()
        }
    }

    void checkPassword(String password) {
        if (password == null) {
            throw new TaskExecutionException(this, new IllegalArgumentException(
                    "No password has been provided! Use 'grafie.password' to define a password!"))
        }
        if (password.isEmpty()) {
            throw new TaskExecutionException(this, new IllegalArgumentException(
                    'The provided password is empty!'))
        }
    }
}
