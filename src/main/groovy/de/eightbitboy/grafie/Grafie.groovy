package de.eightbitboy.grafie

import org.gradle.api.Plugin
import org.gradle.api.Project

/*
https://docs.gradle.org/3.3/userguide/custom_plugins.html
https://guides.gradle.org/writing-gradle-plugins/
https://guides.gradle.org/implementing-gradle-plugins/
https://github.com/gradle/gradle/tree/master/subprojects
https://speakerdeck.com/bmuschko/gradle-plugin-best-practices-by-example
 */

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
