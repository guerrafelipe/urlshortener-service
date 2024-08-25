package com.olhaso.urlshortener.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlUtilsTest {

    @Autowired
    private UrlUtils urlUtils;

    @Test
    void testFormatUrlWithProtocol(){
        String rawUrl = "google.com";
        String formatedUrl = urlUtils.formatUrlWithProtocol(rawUrl);

        Assertions.assertEquals(formatedUrl,
                "https://" + rawUrl,
                "URLs are different");
    }

    @Test
    void testGenerateUniqueUrlCode(){
        String fullUrl = "https://google.com";
        String hash = urlUtils.generateUniqueUrlCode(fullUrl);
        Assertions.assertTrue( hash.length() <= fullUrl.length(),
                "Original URL is smaller then shorten one");
    }

    @Test
    void testGenerateRedirectionUrl(){
        String url = "http://localhost:8080/urlshortener/v1/shorten-url";
        String redirectionUrl = urlUtils.generateRedirectionUrl(url, "22276d4a");
        String expectedString = "http://localhost:8080/urlshortener/v1/22276d4a";
        Assertions.assertEquals(redirectionUrl,
                expectedString,
                "The URLs are different");

    }
}
