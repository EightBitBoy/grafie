package de.eightbitboy.grafie

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class FileCryptoUtil {
    private String encoding = 'UTF-8'
    private String password
    private String fileSuffix
    private FileUtil fileUtil

    FileCryptoUtil(String password, String fileSuffix) {
        this.password = password
        this.fileSuffix = fileSuffix
        //TODO Improve root path argument.
        this.fileUtil = new FileUtil(this.fileSuffix, new File('.'))
    }

    void encryptFilesWithSuffix() {
        fileUtil.findAllUnencryptedFiles().each {
            encryptFile(it)
        }
    }

    void decryptFilesWithSuffix() {
        fileUtil.findAllEncryptedFiles().each {
            decryptFile(it)
        }
    }

    void encryptFile(File plaintextFile) {
        fileUtil.checkPlaintextFileName(plaintextFile)
        File encryptedFile = fileUtil.findEncryptedFileFromUnencryptedFile(plaintextFile)

        encryptedFile.withWriter { writer ->
            Cipher cipher = setupEncryption(Cipher.ENCRYPT_MODE)

            String decryptedText = plaintextFile.getText(encoding)
            byte[] encryptedBytes = cipher.doFinal(decryptedText.getBytes(encoding))

            writer.write(Base64.getEncoder().encodeToString(encryptedBytes))
        }
    }

    void decryptFile(File encryptedFile) {
        fileUtil.checkEncryptedFileName(encryptedFile)
        File plaintextFile = fileUtil.findUnencryptedFileFromEncryptedFile(encryptedFile)
        plaintextFile.createNewFile()

        plaintextFile.withWriter { writer ->
            Cipher cipher = setupEncryption(Cipher.DECRYPT_MODE)

            String encryptedText = encryptedFile.getText(encoding)
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText)

            writer.write(new String(cipher.doFinal(encryptedBytes)))
        }
    }

    private Cipher setupEncryption(int mode) {
        Cipher cipher = Cipher.getInstance('AES/ECB/PKCS5Padding')
        SecretKeySpec secretKeySpec = new SecretKeySpec(EncryptionKey.fromPassword(password), 'AES')
        cipher.init(mode, secretKeySpec)
        return cipher
    }
}
