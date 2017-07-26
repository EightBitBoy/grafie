package de.eightbitboy.grafie

import org.apache.commons.codec.digest.DigestUtils

class PasswordConverter {
    static byte[] convert(String password) {
        if (!password) {
            throw new IllegalArgumentException("The provided password is null or empty!")
        }
        return Arrays.copyOf(DigestUtils.sha256(password), 16)
    }
}
