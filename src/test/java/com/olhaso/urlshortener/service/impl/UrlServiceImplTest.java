package com.olhaso.urlshortener.service.impl;

import com.olhaso.urlshortener.dto.UrlRequestCreateDTO;
import com.olhaso.urlshortener.dto.UrlRequestUpdateDTO;
import com.olhaso.urlshortener.dto.UrlResponseDTO;
import com.olhaso.urlshortener.entities.UrlEntity;
import com.olhaso.urlshortener.repository.UrlRepository;
import com.olhaso.urlshortener.utils.UrlUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.mockito.Mockito.when;

@SpringBootTest
class UrlServiceImplTest {

    @InjectMocks
    private UrlServiceImpl urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlEntity existingUrlEntity;

    private final UrlUtils urlUtils = Mockito.mock(UrlUtils.class);

    @Test
    void testCreate(){
        UrlRequestCreateDTO urlRequestCreateDTO = new UrlRequestCreateDTO();
        urlRequestCreateDTO.setFullUrl("google.com");
        when(urlUtils.formatUrlWithProtocol(urlRequestCreateDTO.getFullUrl()))
                .thenReturn("https://google.com");
        when(urlUtils.generateUniqueUrlCode(urlRequestCreateDTO.getFullUrl()))
                .thenReturn("xpto");
        when(urlUtils.generateRedirectionUrl("http://localhost:8080/v1/", "xpto"))
                .thenReturn("http://localhost/v1/xpto");
        UrlResponseDTO urlResponseDTO = urlService
                .create(urlRequestCreateDTO, "http://localhost/v1/");
        Assertions.assertEquals(
                Boolean.TRUE,
                urlResponseDTO.getIsEnabled());
    }

    @Test
    void testUpdate(){
        UrlRequestUpdateDTO urlRequestUpdateDTO = new UrlRequestUpdateDTO();
        urlRequestUpdateDTO.setFullUrl("https://twitter.com");
        urlRequestUpdateDTO.setIsEnabled(Boolean.TRUE);
        String id = "xpto";

        when(urlRepository.findById(id)).thenReturn(Optional.of(existingUrlEntity));
        Object response = urlService.update(id, urlRequestUpdateDTO);
        Assertions.assertEquals(true, response);
    }

    @Test
    void testRedirect(){
        UrlEntity urlEntity = new UrlEntity(
                "https://google.com",
                "xpto", LocalDateTime.now(),
                Boolean.TRUE,
                LocalDateTime.now().plusYears(1));

        String id = "xpto";
        when(urlRepository.findById(id)).thenReturn(Optional.of(urlEntity));
        HttpHeaders headers = urlService.redirect(id);
        Assertions.assertFalse(headers.isEmpty());
    }
}
