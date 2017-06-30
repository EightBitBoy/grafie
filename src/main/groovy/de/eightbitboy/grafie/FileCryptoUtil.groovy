package de.eightbitboy.grafie

import groovy.util.logging.Log
import org.apache.commons.codec.digest.DigestUtils

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/*
https://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt-example
PBESpec
https://stackoverflow.com/questions/3451670/java-aes-and-using-my-own-key
https://stackoverflow.com/questions/18362137/encryption-with-aes-256-java
https://stackoverflow.com/questions/3954611/encrypt-and-decrypt-with-aes-and-base64-encoding
 */

@Log
class FileCryptoUtil {
    private String encoding = 'UTF-8'
    private String fileExtension

    FileCryptoUtil(String fileExtension) {
        this.fileExtension = fileExtension
    }

    FileCryptoUtil(String fileExtension, String encoding) {
        this.fileExtension = fileExtension
        this.encoding = encoding
    }

    void decrypt(String password, File encryptedFile) {
        if (!encryptedFile.getName().endsWith(fileExtension)) {
            throw new IllegalStateException("The encrypted file has no valid name!")
        }
        File decryptedFile = new File(encryptedFile.getCanonicalPath().substring(
                0, encryptedFile.getCanonicalPath().lastIndexOf(fileExtension)))
        decryptedFile.createNewFile()

        decryptedFile.withWriter { writer ->
            Cipher cipher = setup(processPassword(password), Cipher.DECRYPT_MODE)

            String encryptedText = encryptedFile.getText(encoding)
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText)

            writer.write(new String(cipher.doFinal(encryptedBytes)))
        }
    }

    void encrypt(String password, File decryptedFile) {
        if (decryptedFile.getName().endsWith(fileExtension)) {
            throw new IllegalStateException("The encrypted file has no valid name!")
        }
        File encryptedFile = new File(decryptedFile.getCanonicalPath() + fileExtension)
        encryptedFile.createNewFile()

        encryptedFile.withWriter { writer ->
            Cipher cipher = setup(processPassword(password), Cipher.ENCRYPT_MODE)

            String decryptedText = decryptedFile.getText(encoding)
            byte[] encryptedBytes = cipher.doFinal(decryptedText.getBytes(encoding))

            writer.write(Base64.getEncoder().encodeToString(encryptedBytes))
        }
    }

    /** Create a 128 bit key from an arbitrary password string. */
    private byte[] processPassword(String password) {
        if (!password) {
            throw new IllegalArgumentException("The provided key is null or empty!")
        }
        return Arrays.copyOf(DigestUtils.sha256(password), 16)
    }

    private Cipher setup(byte[] key, int mode) {
        //TODO use CBC
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, 'AES')
        cipher.init(mode, secretKeySpec)
        return cipher
    }
}
