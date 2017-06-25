package de.eightbitboy.grafie

import org.gradle.api.Project
import org.gradle.api.Plugin

class Grafie implements Plugin<Project> {
    final static ID = 'de.eightbitboy.grafie'
    final static GROUP = 'Grafie'

    void apply(Project project) {
        project.extensions.create('grafie', GrafieExtension)

        project.task('decryptFiles', type: FileCryptoTask) {
            group = GROUP
            description = 'Decrypt all files which have the encryption file name extension, the default is \'.grafie\' .'
            mode = Mode.DECRYPT
        }

        project.task('encryptFiles', type: FileCryptoTask) {
            group = GROUP
            description = 'Encrypt all files for which a file with the same name and encryption file name extension exists, the default is \'.grafie\' .'
            mode = Mode.ENCRYPT
        }
    }
}
