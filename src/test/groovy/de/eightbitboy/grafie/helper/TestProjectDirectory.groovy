package de.eightbitboy.grafie.helper

class TestProjectDirectory extends File {
    public TestProjectDirectory() {
        super([System.getProperty('user.dir'), 'build', 'testProject'].join(separator))
        mkdirs()
    }
}
