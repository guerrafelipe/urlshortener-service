package com.olhaso.urlshortener.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlRequestCreateDTOTest {

    @Test
    void shouldCreateRequestDTO(){
        UrlRequestCreateDTO requestDTO = new UrlRequestCreateDTO();
        requestDTO.setFullUrl("google.com");
        Assertions.assertEquals(
                "google.com",
                requestDTO.fullUrl,
                "Full URL is not equal to google.com");
    }

}
