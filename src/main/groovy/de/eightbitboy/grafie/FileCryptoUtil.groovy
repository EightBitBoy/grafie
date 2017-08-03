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

    void encryptFile(File file) {
        checkUnencryptedFileName(file)

        File encryptedFile = new File(file.getCanonicalPath() + fileSuffix)
        encryptedFile.createNewFile()

        encryptedFile.withWriter { writer ->
            Cipher cipher = setup(EncryptionKey.fromPassword(password), Cipher.ENCRYPT_MODE)

            String decryptedText = file.getText(encoding)
            byte[] encryptedBytes = cipher.doFinal(decryptedText.getBytes(encoding))

            writer.write(Base64.getEncoder().encodeToString(encryptedBytes))
        }
    }

    void decryptFile(File file) {
        checkEncryptedFileName(file)

        File decryptedFile = new File(file.getCanonicalPath().substring(
                0, file.getCanonicalPath().lastIndexOf(fileSuffix)))
        decryptedFile.createNewFile()

        decryptedFile.withWriter { writer ->
            Cipher cipher = setup(EncryptionKey.fromPassword(password), Cipher.DECRYPT_MODE)

            String encryptedText = file.getText(encoding)
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText)

            writer.write(new String(cipher.doFinal(encryptedBytes)))
        }
    }

    void checkUnencryptedFileName(File file) {
        if (file.getName().endsWith(fileSuffix)) {
            throw new IllegalStateException(
                    "The unencrypted file already has the file suffix for encrypted files!")
        }
    }

    void checkEncryptedFileName(File file) {
        if (!file.getName().endsWith(fileSuffix)) {
            throw new IllegalStateException(
                    "The encrypted file does not have a valid file suffix!")
        }
    }

    private Cipher setup(byte[] key, int mode) {
        //TODO use CBC
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, 'AES')
        cipher.init(mode, secretKeySpec)
        return cipher
    }
}
