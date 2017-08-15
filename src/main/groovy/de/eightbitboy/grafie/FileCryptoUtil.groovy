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
            writer.write(encryptAndEncodeText(plaintextFile.getText(encoding)))
        }
    }

    void decryptFile(File encryptedFile) {
        fileUtil.checkEncryptedFileName(encryptedFile)
        File plaintextFile = fileUtil.findUnencryptedFileFromEncryptedFile(encryptedFile)
        plaintextFile.createNewFile()

        plaintextFile.withWriter { writer ->
            writer.write(decryptEncodedText(encryptedFile.getText(encoding)))
        }
    }

    String encryptAndEncodeText(String plaintext) {
        Cipher cipher = setupCipher(Cipher.ENCRYPT_MODE)
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(encoding))
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    String decryptEncodedText(String encryptedAndEncodedText) {
        Cipher cipher = setupCipher(Cipher.DECRYPT_MODE)
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedAndEncodedText)
        //TODO Catch and propagate javax.crypto.BadPaddingException, happens when decrypting with wrong password.
        //TODO Test this!
        return new String(cipher.doFinal(encryptedBytes))
    }

    private Cipher setupCipher(int mode) {
        Cipher cipher = Cipher.getInstance('AES/ECB/PKCS5Padding')
        SecretKeySpec secretKeySpec = new SecretKeySpec(EncryptionKey.fromPassword(password), 'AES')
        cipher.init(mode, secretKeySpec)
        return cipher
    }
}
