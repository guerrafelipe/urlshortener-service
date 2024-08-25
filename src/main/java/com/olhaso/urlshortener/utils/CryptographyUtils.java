package com.olhaso.urlshortener.utils;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;


class CryptographyUtils {

    // Método para gerar um salt aleatório
    public static String generateRandomSalt(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] saltBytes = new byte[length];
        secureRandom.nextBytes(saltBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(saltBytes);
    }

    // MurmurHash com salt aleatório
    public static String generateHash(String input, int saltLength) {
        String salt = generateRandomSalt(saltLength);
        String inputWithSalt = salt + input;
        return Hashing.murmur3_32_fixed()
                .hashString(inputWithSalt, StandardCharsets.UTF_8)
                .toString();
    }
}
