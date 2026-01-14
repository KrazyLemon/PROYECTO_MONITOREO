package com.odis.monitoreo.demo.config;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGeneratorUtils {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder Encoder = Base64.getUrlEncoder();

    public static String generateKey(){
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        return Encoder.encodeToString(key);
    }

    public static String generateConnectionToken(){
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        return Encoder.encodeToString(key);
    }
}
