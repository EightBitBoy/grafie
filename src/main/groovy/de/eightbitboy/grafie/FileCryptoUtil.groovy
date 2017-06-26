package de.eightbitboy.grafie

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

class FileCryptoUtil {
    final static String UTF_8 = 'UTF-8'
    final static String fileExtension = '.grafie'

    void decrypt(String password, File encryptedFile) {
        Cipher cipher = setup(processPassword(password), Cipher.DECRYPT_MODE)

        if (!encryptedFile.getCanonicalPath().endsWith(fileExtension)) {
            //TODO Throw some exception!
        }

        /*
        new File(encryptedFile.getCanonicalPath()).withWriter(UTF_8) { writer ->
            writer.write(Base64.getEncoder().encodeToString(cipher.doFinal(encryptedFile.getText(UTF_8).getBytes(UTF_8))))
        }
        */
    }

    void encrypt(String password, File decryptedFile) {
        Cipher cipher = setup(processPassword(password), Cipher.ENCRYPT_MODE)

        File encryptedFile = new File(decryptedFile.getCanonicalPath() + fileExtension)
        if (!encryptedFile.exists()) {
            encryptedFile.createNewFile()
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
