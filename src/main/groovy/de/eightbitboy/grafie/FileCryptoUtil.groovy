package de.eightbitboy.grafie;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;

class FileCryptoUtil {
    public void decrypt(String key, File file) {

    }

    public void encrypt(String key, File file) {

    }

    byte[] processKey(String key) {
        return DigestUtils.sha256(key)
    }
}
