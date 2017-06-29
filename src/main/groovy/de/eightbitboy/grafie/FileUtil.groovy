package de.eightbitboy.grafie

class FileUtil {
    private String fileExtension

    FileUtil(String fileExtension) {
        this.fileExtension = checkFileExtension(fileExtension)
    }

    String getFileExtension(){
        return fileExtension
    }

    private String checkFileExtension(String fileExtension) {
        if (!fileExtension.startsWith('.')) {
            return '.' + fileExtension
        } else {
            return fileExtension
        }
    }
}
