package de.eightbitboy.grafie

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

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
        EncryptionKey.checkPassword(this.password)

        FileCryptoUtil cryptoUtil = new FileCryptoUtil(this.password, this.fileSuffix)

        if (this.mode == Mode.DECRYPT) {
            cryptoUtil.decryptFilesWithSuffix()
        }
        if (this.mode == Mode.ENCRYPT) {
            cryptoUtil.encryptFilesWithSuffix()
        }
    }
}
