package de.eightbitboy.grafie

import org.gradle.api.Plugin
import org.gradle.api.Project

class Grafie implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('grafie', GrafieExtension)

        project.task('encryptFiles') {
            println('### Encrypting files.')
            println("### Using the file extension ${project.grafie.fileExtension}.")
        }

        project.task('decryptFiles') {
            println('### Decrypting files.')
        }

        project.task('crypto', type: FileCryptoTask)
    }
}


class GrafieExtension {
    String fileExtension = 'grafie'
}
