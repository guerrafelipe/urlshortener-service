package com.olhaso.urlshortener.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlRequestUpdateDTOTest {

    @Test
    void shouldCreateUrlRequestDTO(){
        var updateRequestDTO = new UrlRequestUpdateDTO();
        updateRequestDTO.setFullUrl("google.com");
        updateRequestDTO.setIsEnabled(Boolean.TRUE);

        Assertions.assertEquals(
                Boolean.TRUE,
                updateRequestDTO.isEnabled,
                "Is enabled was not equal to TRUE");
    }
}
