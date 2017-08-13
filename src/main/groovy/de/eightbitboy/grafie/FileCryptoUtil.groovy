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

        Cipher cipher = setupEncryption(Cipher.ENCRYPT_MODE)
        encryptedFile.withWriter { writer ->

            String plaintext = plaintextFile.getText(encoding)
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(encoding))
            String encodedEncryptedText = Base64.getEncoder().encodeToString(encryptedBytes)

            writer.write(encodedEncryptedText)
        }
    }

    void decryptFile(File encryptedFile) {
        fileUtil.checkEncryptedFileName(encryptedFile)
        File plaintextFile = fileUtil.findUnencryptedFileFromEncryptedFile(encryptedFile)
        plaintextFile.createNewFile()

        Cipher cipher = setupEncryption(Cipher.DECRYPT_MODE)
        plaintextFile.withWriter { writer ->

            String encryptedText = encryptedFile.getText(encoding)
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText)
            String plaintext = new String(cipher.doFinal(encryptedBytes))

            writer.write(plaintext)
        }
    }

    private Cipher setupEncryption(int mode) {
        Cipher cipher = Cipher.getInstance('AES/ECB/PKCS5Padding')
        SecretKeySpec secretKeySpec = new SecretKeySpec(EncryptionKey.fromPassword(password), 'AES')
        cipher.init(mode, secretKeySpec)
        return cipher
    }
}
