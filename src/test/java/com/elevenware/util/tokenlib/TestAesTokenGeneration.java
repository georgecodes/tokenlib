package com.elevenware.util.tokenlib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAesTokenGeneration {

    @Test
    public void canEncryptAndDecryptAString() throws Exception {

        String email = "george@elevenware.com";
        String key = "Bar12345Bar12345";

        SimpleAesEncryptor aesifier = SimpleAesEncryptor.simpleAesForKey(key);

        String encrypted = aesifier.encrypt(email);

        String unencrypted = SimpleAesEncryptor.simpleAesForKey(key).decrypt(encrypted);

        assertEquals(email, unencrypted);

    }

    @Test
    public void canEncryptAndDecryptAStringUsingPropertyKey() throws Exception {

        String email = "george@elevenware.com";
        String key = "Bar12345Bar12345";
        String propertyName = "encryption.property";
        System.setProperty(propertyName, key);

        SimpleAesEncryptor aesifier = SimpleAesEncryptor.simpleAesForProperty(propertyName);

        String encrypted = aesifier.encrypt(email);

        String unencrypted = SimpleAesEncryptor.simpleAesForKey(key).decrypt(encrypted);

        assertEquals(email, unencrypted);

    }

    @Test
    public void canEncryptAndDecryptArrays() {

        String[] vars = new String[] {
                "george@elevenware.com",
                "19372",
                "Acme Cloud Limited"
        };

        String key = "Bar12345Bar12345";

        SimpleAesEncryptor aesifier = SimpleAesEncryptor.simpleAesForKey(key);

        String encrypted = aesifier.encryptArray(vars);

        String[] unencrypted = SimpleAesEncryptor.simpleAesForKey(key).decryptArray(encrypted);

        assertEquals(vars[0], unencrypted[0]);
        assertEquals(vars[1], unencrypted[1]);
        assertEquals(vars[2], unencrypted[2]);

    }

    @Test
    public void canEncryptAndDecryptArraysWithProperty() {

        String[] vars = new String[] {
                "george@elevenware.com",
                "19372",
                "Acme Cloud Limited"
        };

        String key = "Bar12345Bar12345";
        String propertyName = "encryption.property";
        System.setProperty(propertyName, key);

        SimpleAesEncryptor aesifier = SimpleAesEncryptor.simpleAesForProperty(propertyName);

        String encrypted = aesifier.encryptArray(vars);

        String[] unencrypted = SimpleAesEncryptor.simpleAesForKey(key).decryptArray(encrypted);

        assertEquals(vars[0], unencrypted[0]);
        assertEquals(vars[1], unencrypted[1]);
        assertEquals(vars[2], unencrypted[2]);

    }

}
