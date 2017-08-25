package de.eightbitboy.grafie.file

class FileReaderWriter {
    private boolean encodeBase64

    FileReaderWriter(boolean encodeBase64) {
        this.encodeBase64 = encodeBase64
    }

    void writeBytes(byte[] bytes, File file) {
        if (encodeBase64) {
            file.withWriter {
                it.write(Base64.encoder.encodeToString(bytes))
            }
        } else {
            file.withOutputStream {
                it.write(bytes)
            }
        }
    }

    byte[] readBytes(File file) {
        if (encodeBase64) {
            return Base64.decoder.decode(file.text)
        } else {
            return file.readBytes()
        }
    }
}
