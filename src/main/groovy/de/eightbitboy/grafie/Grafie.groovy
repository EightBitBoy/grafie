package de.eightbitboy.grafie

import org.gradle.api.Project
import org.gradle.api.Plugin

class Grafie implements Plugin<Project> {
    final static String ID = 'de.eightbitboy.grafie'
    final static String TASK_GROUP = 'Grafie'

    void apply(Project project) {
        project.extensions.create('grafie', GrafieExtension)

        project.tasks.create('decryptFiles', FileCryptoTask) { task ->
            task.setMode(FileCryptoTask.Mode.DECRYPT)
            task.setGroup(TASK_GROUP)
            task.setDescription('Decrypt all files which have the encryption file name extension, ' +
                    'the default is \'.grafie\'.')
        }

        project.tasks.create('encryptFiles', FileCryptoTask) { task ->
            task.setMode(FileCryptoTask.Mode.ENCRYPT)
            task.setGroup(TASK_GROUP)
            task.setDescription('Encrypt all files for which a file with the same name ' +
                    'and encryption file name extension exists, the default is \'.grafie\'.')
        }
    }
}
