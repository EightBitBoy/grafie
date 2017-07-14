package de.eightbitboy.grafie.testhelper

class TestProjectDirectory extends File {

    static final FILE_PATH = [System.getProperty('user.dir'), 'build', 'testProject'].join(separator)

    TestProjectDirectory() {
        super(FILE_PATH)
    }

    void deleteRecursively() {
        deleteDirectory(this)
    }

    private void deleteDirectory(File directory) {
        directory.listFiles() { File file ->
            deleteDirectory(file)
        }
        directory.delete()
    }
}
