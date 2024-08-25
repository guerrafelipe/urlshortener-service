package com.olhaso.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olhaso.urlshortener.dto.UrlRequestUpdateDTO;
import com.olhaso.urlshortener.service.impl.UrlServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.olhaso.urlshortener.dto.UrlRequestCreateDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UrlController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlServiceImpl urlService;

    @InjectMocks
    private UrlController urlController;

    private final HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);

    @InjectMocks
    private ObjectMapper objectMapper;

    @Test
    void shouldRedirectUrl() throws Exception {
        mockMvc.perform(get("/xpto"))
               .andExpect(status().isFound());
    }

    @Test
    void shouldCreateUrl() throws Exception {
        UrlRequestCreateDTO urlRequestCreateDTO = new UrlRequestCreateDTO();
        urlRequestCreateDTO.setFullUrl("https://google.com");

        mockMvc.perform(post( "/shorten-url")
                        .content(objectMapper.writeValueAsString(urlRequestCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateUrl() throws Exception{
        UrlRequestUpdateDTO urlRequestUpdateDTO = new UrlRequestUpdateDTO();
        urlRequestUpdateDTO.setFullUrl("https://twitter.com");
        urlRequestUpdateDTO.setIsEnabled(Boolean.FALSE);

        mockMvc.perform(put("/xpto")
                .content(objectMapper.writeValueAsString(urlRequestUpdateDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
