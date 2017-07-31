package de.eightbitboy.grafie

import org.apache.commons.codec.digest.DigestUtils

/**
 * Provide a 128 bit / 16 byte key from an arbitrary password.
 */
class EncryptionKey {
    static byte[] fromPassword(String password) {
        if (!password) {
            throw new IllegalArgumentException("The provided password is null or empty!")
        }
        return Arrays.copyOf(DigestUtils.sha256(password), 16)
    }
}
