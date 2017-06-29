package de.eightbitboy.grafie

class FileUtil {
    private String fileExtension
    private File root

    FileUtil(String fileExtension, File root) {
        this.fileExtension = checkFileExtension(fileExtension)
        this.root = root
    }

    String getFileExtension(){
        return fileExtension
    }

    // TODO Some more checks for "forbidden" symbols might be necessary
    private String checkFileExtension(String fileExtension) {
        if (!fileExtension.startsWith('.')) {
            return '.' + fileExtension
        } else {
            return fileExtension
        }
    }

    List<File> getEncryptedFiles(){

    }

    List<File> getUnencryptedFiles(){

    }

    // TODO Check files for invalid names and file extensions, handle FileFormatExceptions
}
