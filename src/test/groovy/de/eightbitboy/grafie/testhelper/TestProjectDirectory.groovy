package de.eightbitboy.grafie.testhelper

import org.apache.commons.io.FileUtils

class TestProjectDirectory extends File {
    public TestProjectDirectory() {
        super([System.getProperty('user.dir'), 'build', 'testProject'].join(separator))
        mkdirs()
    }

    public void deleteRecursively() {
        FileUtils.deleteDirectory(this)
    }
}
