package com.olhaso.urlshortener.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class UrlEntityTest {

    @Test
    void testUrlEntityCreation(){
        var urlEntity = UrlEntity
                .builder()
                .id("xpto")
                .fullUrl("google.com")
                .isEnabled(Boolean.TRUE)
                .expiresAt(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();
        Assertions.assertEquals("xpto", urlEntity.getId(), "ID is not equal to xpto");
    }

}
