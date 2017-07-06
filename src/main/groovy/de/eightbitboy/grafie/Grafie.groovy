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
    final static String suffix = '.grafie'

    void apply(Project project) {
        project.extensions.create('grafie', GrafieExtension)
        //validateExtension(project) //FIXME Make sure a password is provided!

        //TODO use project.file in tasks to access files

        //TODO use project.files !!!11!!!1!
        // see https://docs.gradle.org/3.3/userguide/working_with_files.html

        project.task('decryptFiles', type: FileCryptoTask) {
            group = GROUP
            description = 'Decrypt all files which have the encryption file name extension, the default is \'.grafie\'.'
            mode = FileCryptoTask.Mode.DECRYPT
            password = project.grafie.password
        }

        project.task('encryptFiles', type: FileCryptoTask) {
            group = GROUP
            description = 'Encrypt all files for which a file with the same name and encryption file name extension exists, the default is \'.grafie\'.'
            mode = FileCryptoTask.Mode.ENCRYPT
            password = project.grafie.password
        }
    }

    void validateExtension(Project project) {
        if (!project.grafie.password) {
            throw new IllegalStateException('No password has been provided for the Grafie plugin!')
        }
    }
}
