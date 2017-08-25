package de.eightbitboy.grafie

import de.eightbitboy.grafie.file.FileUtil
import org.gradle.api.GradleException

import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class FileCryptoUtil {
    private final String encoding = 'UTF-8'
    private final String password
    private final String fileSuffix
    private final FileUtil fileUtil
    private final boolean encodeBase64

    FileCryptoUtil(String password, String fileSuffix, boolean encodeBase64 = true) {
        this.password = password
        this.fileSuffix = fileSuffix
        this.encodeBase64 = encodeBase64
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
        return Base64.encoder.encodeToString(encryptedBytes)
    }

    String decryptEncodedText(String encryptedAndEncodedText) {
        Cipher cipher = setupCipher(Cipher.DECRYPT_MODE)
        byte[] encryptedBytes = Base64.decoder.decode(encryptedAndEncodedText)

        String plaintext
        try {
            plaintext = new String(cipher.doFinal(encryptedBytes))
        } catch (BadPaddingException e) {
            throw new GradleException('The decryption was not successful. ' +
                    'Probably the passwords used for encrypting and decrypting the text differ from each other!', e)
        }

        return plaintext
    }

    private Cipher setupCipher(int mode) {
        Cipher cipher = Cipher.getInstance('AES/ECB/PKCS5Padding')
        SecretKeySpec secretKeySpec = new SecretKeySpec(EncryptionKey.fromPassword(password), 'AES')
        cipher.init(mode, secretKeySpec)
        return cipher
    }
}
