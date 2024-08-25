package com.olhaso.urlshortener.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlResponseDTOTest {

    @Test
    void shouldCreateUrlResponseDTO(){
        var urlResponseDTO = new UrlResponseDTO();
        urlResponseDTO.setUrl("google.com");
        urlResponseDTO.setIsEnabled(Boolean.TRUE);

        Assertions.assertEquals("google.com",
                urlResponseDTO.url,
                "URL was not equal to google.com");
    }
}
