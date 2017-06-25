package de.eightbitboy.grafie

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FileCryptoTask extends DefaultTask {
    @TaskAction
    def encrypt() {
        println('Encrypting!')
    }
}
