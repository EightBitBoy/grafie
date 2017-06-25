package de.eightbitboy.grafie

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

enum Mode {
    DECRYPT,
    ENCRYPT
}

class FileCryptoTask extends DefaultTask {
    /** The task's operation mode. Use "ENCRYPT" so no unencrpyted files are overwritten, no damage will be done. */
    Mode mode = Mode.ENCRYPT

    @TaskAction
    def encrypt() {
    }
}
