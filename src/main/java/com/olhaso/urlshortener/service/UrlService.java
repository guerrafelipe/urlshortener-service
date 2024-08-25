package com.olhaso.urlshortener.service;

import com.olhaso.urlshortener.dto.UrlRequestCreateDTO;
import com.olhaso.urlshortener.dto.UrlRequestUpdateDTO;
import com.olhaso.urlshortener.dto.UrlResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface UrlService {
    UrlResponseDTO create(UrlRequestCreateDTO request, String requestUrl);

    Object redirect(String id);

    Object update(String id, UrlRequestUpdateDTO body);
}
