package de.eightbitboy.grafie

import org.gradle.api.Project
import org.gradle.api.Plugin

class Grafie implements Plugin<Project> {
    final static ID = 'de.eightbitboy.grafie'

    void apply(Project project) {
        project.extensions.create('grafie', GrafieExtension)
        project.task('crypto', type: FileCryptoTask)
    }
}
