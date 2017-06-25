package de.eightbitboy.grafie;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;

class FileCryptoUtil {
    public void decrypt(String key, File file) {

    }

    public void encrypt(String key, File file) {

    }

    /** Create a 128 bit key from an arbitrary password string. */
    byte[] processPassword(String password) {
        if (!password) {
            throw new IllegalArgumentException("The provided key is null or empty!")
        }
        return Arrays.copyOf(DigestUtils.sha256(password), 16)
    }
}
