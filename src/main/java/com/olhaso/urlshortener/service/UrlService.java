package com.olhaso.urlshortener.service;

import com.olhaso.urlshortener.dto.UrlRequestCreateDTO;
import com.olhaso.urlshortener.dto.UrlRequestUpdateDTO;
import com.olhaso.urlshortener.dto.UrlResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UrlService {
    ResponseEntity<UrlResponseDTO> create(UrlRequestCreateDTO request, HttpServletRequest servletRequest);

    ResponseEntity<Object> redirect(String id, HttpServletRequest servletRequest);

    ResponseEntity<Object> update(String id, UrlRequestUpdateDTO body);
}
