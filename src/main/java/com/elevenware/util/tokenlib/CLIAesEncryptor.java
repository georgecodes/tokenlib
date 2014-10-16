package com.elevenware.util.tokenlib;

public class CLIAesEncryptor {

    public static void main(String[] args) {

        if(args.length != 2) {
            help();
        }

        String key = args[0];
        String token = args[1];

        SimpleAesEncryptor encryptor = SimpleAesEncryptor.simpleAesForKey(key);
        String[] tokens = encryptor.decryptArray(token);

        System.out.println("The encrypted token contains these fields in order:\n");

        for(String string: tokens) {
            System.out.println(string);
        }

    }

    private static void help() {
        System.out.println(String.format("Aes Encryptor version: %s\n", Version.VERSION));
        System.out.println("Usage:\n");
        System.out.println(String.format("   java -jar tokenlib-%s <encryption key> <token to decrypt>", Version.VERSION));
        System.exit(0);
    }

}
