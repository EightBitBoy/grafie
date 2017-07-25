package de.eightbitboy.grafie

import groovy.util.logging.Log
import org.apache.commons.codec.digest.DigestUtils

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Log
class CryptoUtil {
    private String encoding = 'UTF-8'
    private String password
    private String fileSuffix

    CryptoUtil(String password, String fileSuffix) {
        this.password = password
        this.fileSuffix = fileSuffix
    }

    void encryptFilesWithSuffix() {

    }

    void decryptFilesWithSuffix() {

    }

    void decrypt(File encryptedFile) {
        if (!encryptedFile.getName().endsWith(fileSuffix)) {
            throw new IllegalStateException("The encrypted file has no valid name!")
        }
        File decryptedFile = new File(encryptedFile.getCanonicalPath().substring(
                0, encryptedFile.getCanonicalPath().lastIndexOf(fileSuffix)))
        decryptedFile.createNewFile()

        decryptedFile.withWriter { writer ->
            Cipher cipher = setup(processPassword(password), Cipher.DECRYPT_MODE)

            String encryptedText = encryptedFile.getText(encoding)
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText)

            writer.write(new String(cipher.doFinal(encryptedBytes)))
        }
    }

    void encrypt(File decryptedFile) {
        if (decryptedFile.getName().endsWith(fileSuffix)) {
            throw new IllegalStateException("The encrypted file has no valid name!")
        }
        File encryptedFile = new File(decryptedFile.getCanonicalPath() + fileSuffix)
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
