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
        List<File> files = []
        // TODO Read about traversion; there might be better ways to find files matching the file extension!
        // TODO Exclude git and build directories!
        root.traverse {file->
            if(file.isFile() && file.name.endsWith(fileExtension)){
                files.add(file)
            }
        }
        println(files)
        return files
    }

    List<File> getUnencryptedFiles(){
        List<File> encryptedFiles = getEncryptedFiles()
        List<File> files
        return files
    }

    // TODO Check files for invalid names and file extensions, handle FileFormatExceptions
}
