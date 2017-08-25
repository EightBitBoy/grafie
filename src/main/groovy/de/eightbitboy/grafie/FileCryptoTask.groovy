package de.eightbitboy.grafie

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FileCryptoTask extends DefaultTask {
    enum Mode {
        DECRYPT,
        ENCRYPT
    }

    private final String fileSuffix = '.grafie'

    Mode mode = Mode.ENCRYPT

    private String password
    private boolean encodeBase64

    @TaskAction
    void cryptoAction() {
        this.password = project.grafie.password
        this.encodeBase64 = project.grafie.encodeBase64

        EncryptionKey.checkPassword(this.password)


        FileCryptoUtil cryptoUtil = new FileCryptoUtil(
                this.password,
                this.fileSuffix,
                this.encodeBase64)

        if (this.mode == Mode.DECRYPT) {
            cryptoUtil.decryptFilesWithSuffix()
        }
        if (this.mode == Mode.ENCRYPT) {
            cryptoUtil.encryptFilesWithSuffix()
        }
    }
}
