package de.eightbitboy.grafie

import org.gradle.api.Project
import org.gradle.api.Plugin

class Grafie implements Plugin<Project> {
    final static String ID = 'de.eightbitboy.grafie'
    final static String GROUP = 'Grafie'
    final static String suffix = '.grafie'

    void apply(Project project) {
        project.extensions.create('grafie', GrafieExtension)

        project.task('decryptFiles', type: FileCryptoTask) {
            group = GROUP
            description = 'Decrypt all files which have the encryption file name extension, ' +
                    'the default is \'.grafie\'.'
            mode = FileCryptoTask.Mode.DECRYPT
            password = project.grafie.password
            fileSuffix = suffix
        }

        project.task('encryptFiles', type: FileCryptoTask) {
            group = GROUP
            description = 'Encrypt all files for which a file with the same name ' +
                    'and encryption file name extension exists, the default is \'.grafie\'.'
            mode = FileCryptoTask.Mode.ENCRYPT
            password = project.grafie.password
            fileSuffix = suffix
        }
    }

    void validateExtension(Project project) {
        if (!project.grafie.password) {
            throw new IllegalStateException(
                    'No password has been provided for the Grafie plugin!')
        }
    }
}
