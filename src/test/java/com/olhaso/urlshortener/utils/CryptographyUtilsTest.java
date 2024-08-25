package com.olhaso.urlshortener.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CryptographyUtilsTest {

    @Test
    void shouldGenerateRandomSalt(){
        int saltLength = 3;
        String base64Salt = CryptographyUtils.generateRandomSalt(saltLength);
        Assertions.assertTrue(3 <= base64Salt.length(),
                "Salt size is less then 3");
    }

    @Test
    void shouldGenerateHash(){
        String fullUrl = "google.com";
        String hash = CryptographyUtils.generateHash(fullUrl, 3);
        Assertions.assertTrue(hash.length() < fullUrl.length(),
                "Original full URL is smaller then hash");
    }
}
