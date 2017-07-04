package de.eightbitboy.grafie

import org.gradle.api.Project
import org.gradle.api.Plugin

/*
https://guides.gradle.org/writing-gradle-plugins/
https://guides.gradle.org/implementing-gradle-plugins/
https://github.com/gradle/gradle/tree/master/subprojects
https://speakerdeck.com/bmuschko/gradle-plugin-best-practices-by-example
 */

class Grafie implements Plugin<Project> {
    final static String ID = 'de.eightbitboy.grafie'
    final static String GROUP = 'Grafie'

    void apply(Project project) {
        project.extensions.create('grafie', GrafieExtension)

        project.task('doSomething') {
            doLast { println('### Doing something!') }
        }

        project.task('decryptFiles', type: FileCryptoTask) {
            group = GROUP
            description = 'Decrypt all files which have the encryption file name extension, the default is \'.grafie\'.'
            mode = FileCryptoTask.Mode.DECRYPT
        }

        project.task('encryptFiles', type: FileCryptoTask) {
            group = GROUP
            description = 'Encrypt all files for which a file with the same name and encryption file name extension exists, the default is \'.grafie\'.'
            mode = FileCryptoTask.Mode.ENCRYPT
        }
    }
}
