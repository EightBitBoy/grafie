package de.eightbitboy.grafie

class FileUtil {
    private String fileSuffix
    private File root

    FileUtil(String fileSuffix, File root) {
        this.fileSuffix = checkFileSuffix(fileSuffix)
        this.root = root
    }

    String getFileSuffix() {
        return fileSuffix
    }

    private String checkFileSuffix(String fileSuffix) {
        // TODO Some checks for "forbidden" symbols might be necessary!
        return fileSuffix
    }

    File findUnencryptedFileFromEncryptedFile(File encryptedFile) {
        return new File(encryptedFile.getCanonicalPath().substring(
                0, encryptedFile.getCanonicalPath().lastIndexOf(fileSuffix)))
    }

    File findEncryptedFileFromUnencryptedFile(File unencryptedFile) {
        return new File(unencryptedFile.getCanonicalPath() + fileSuffix)
    }

    List<File> findAllEncryptedFiles() {
        List<File> files = []
        // TODO Read about traversion; there might be better ways to find files matching the file extension!
        // TODO Exclude git and build directories!
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

    // TODO Check files for invalid names and file extensions, handle FileFormatExceptions
}
