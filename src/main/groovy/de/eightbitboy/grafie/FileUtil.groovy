package de.eightbitboy.grafie

class FileUtil {
    private String fileExtension

    FileUtil(String fileExtension) {
        this.fileExtension = checkFileExtension(fileExtension)
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

    // TODO Check files for invalid names and file extensions, handle FileFormatExceptions
}
