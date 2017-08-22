package de.eightbitboy.grafie

import org.gradle.api.GradleException

class FileUtil {
    private final String fileSuffix
    private final File root

    FileUtil(String fileSuffix) {
        this.fileSuffix = checkFileSuffix(fileSuffix)
        this.root = new File(System.getProperty('user.dir'))
    }

    FileUtil(String fileSuffix, File root) {
        this.fileSuffix = checkFileSuffix(fileSuffix)
        this.root = root
    }

    String getFileSuffix() {
        return fileSuffix
    }

    //TODO This code needs some improvement. Checking that the string contains
    //any symbol from the list should look better.
    static String checkFileSuffix(String fileSuffix) {
        List<String> invalidSymbols = ['@', '§']

        List<String> result = fileSuffix.findAll { suffix ->
            invalidSymbols.any { symbol ->
                suffix.contains(symbol)
            }
        }

        if (result) {
            throw new GradleException("The file suffix '${fileSuffix}' is not valid!")
        }

        return fileSuffix
    }

    File findUnencryptedFileFromEncryptedFile(File encryptedFile) {
        checkEncryptedFileName(encryptedFile)
        return new File(encryptedFile.canonicalPath.substring(
                0, encryptedFile.canonicalPath.lastIndexOf(fileSuffix)))
    }

    File findEncryptedFileFromUnencryptedFile(File plaintextFile) {
        checkPlaintextFileName(plaintextFile)
        return new File(plaintextFile.canonicalPath + fileSuffix)
    }

    List<File> findAllEncryptedFiles() {
        List<File> files = []
        // TODO Read about traversion; there might be better ways to find files matching the file extension!
        // TODO Exclude git and build directories?
        root.traverse { file ->
            if (file.isFile() && file.name.endsWith(fileSuffix)) {
                files.add(file)
            }
        }
        return files
    }

    List<File> findAllUnencryptedFiles() {
        List<File> encryptedFiles = findAllEncryptedFiles()
        List<File> files = []
        encryptedFiles.each { file ->
            files.add(findUnencryptedFileFromEncryptedFile(file))
        }
        return files
    }

    void checkPlaintextFileName(File file) {
        if (file.getName().endsWith(fileSuffix)) {
            throw new GradleException(
                    "The plaintext file already has the file suffix for encrypted files!")
        }
    }

    void checkEncryptedFileName(File file) {
        if (!file.getName().endsWith(fileSuffix)) {
            throw new GradleException(
                    "The encrypted file does not have a valid file suffix!")
        }
    }
}
