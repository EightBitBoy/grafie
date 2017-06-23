package de.eightbitboy.grafie

import org.gradle.api.Plugin
import org.gradle.api.Project

class Grafie implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('grafie', GrafieExtension)

        project.task('encryptFiles') {
            println('### Encrypting files.')
        }

        project.task('decryptFiles') {
            println('### Decrypting files.')
        }
    }
}


class GrafieExtension {
    String fileExtension = 'grafie'
}
