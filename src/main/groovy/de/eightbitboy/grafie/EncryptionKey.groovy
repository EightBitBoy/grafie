package de.eightbitboy.grafie

import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.InvalidUserDataException

/**
 * Provide a 128 bit / 16 byte key from an arbitrary password.
 */
class EncryptionKey {
    static byte[] fromPassword(String password) {
        checkPassword(password)
        return Arrays.copyOf(DigestUtils.sha256(password), 16)
    }

    static void checkPassword(String password) {
        if (password == null) {
            throw new InvalidUserDataException(
                    "No password has been provided! Use 'grafie.password' to define a password!")
        }
        if (password.isEmpty()) {
            throw new InvalidUserDataException(
                    'The provided password is empty!')
        }
    }
}
