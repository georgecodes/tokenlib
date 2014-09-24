package com.elevenware.util.tokenlib;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SimpleAesEncryptor {

    private static final char DEFAULT_ENCRYPT_DELIMITER = '|';
    private static final String DEFAULT_DECRYPT_DELIMITER = "\\|";
    private final String encryptionKey;
    private Cipher cipher;
    private SecretKeySpec aesKey;

    public static SimpleAesEncryptor simpleAesForKey(String key) {
        return new SimpleAesEncryptor(key);
    }

    public static SimpleAesEncryptor simpleAesForProperty(String propertyName) {
        String key = System.getProperty(propertyName);
        return simpleAesForKey(key);
    }

    public SimpleAesEncryptor(String key) {
        this.encryptionKey = key;
        try {
            init();
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String plainText) {
        try {
            return doEncrypt(plainText);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            return doDecrypt(encryptedText);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptArray(String[] vars) {
        return encryptArray(vars, DEFAULT_ENCRYPT_DELIMITER);
    }

    public String encryptArray(String[] vars, char delimiter) {
        StringBuilder buf = new StringBuilder();
        for(String string:vars) {
            buf.append(string).append(delimiter);
        }
        return encrypt(buf.toString());
    }

    public String[] decryptArray(String encryptedText) {
        return decryptArray(encryptedText, DEFAULT_DECRYPT_DELIMITER);
    }

    public String[] decryptArray(String encryptedText, String delimiter) {
        String decrypted = decrypt(encryptedText);
        return decrypted.split(delimiter);
    }

    private void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
        aesKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        cipher = Cipher.getInstance("AES");
    }


    private String doEncrypt(String plainText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return new Base64().encodeBase64String(encrypted);


    }



    private String doDecrypt(String encryptedText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] encrypted = new Base64().decode(encryptedText.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(encrypted));
        return decrypted;

    }
}
