package com.olhaso.urlshortener.service.impl;

import com.olhaso.urlshortener.dto.UrlRequestCreateDTO;
import com.olhaso.urlshortener.dto.UrlRequestUpdateDTO;
import com.olhaso.urlshortener.dto.UrlResponseDTO;
import com.olhaso.urlshortener.entities.UrlEntity;
import com.olhaso.urlshortener.exceptions.UrlNotEnabledException;
import com.olhaso.urlshortener.exceptions.UrlNotFoundException;
import com.olhaso.urlshortener.repository.UrlRepository;
import com.olhaso.urlshortener.service.UrlService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.time.LocalDateTime;
import com.olhaso.urlshortener.utils.UrlUtils;


@Service
@AllArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlUtils urlUtils;

    @Override
    public UrlResponseDTO create(UrlRequestCreateDTO request, String requestUrl){
        String fullUrl = request.getFullUrl();
        log.debug("Received URL shortening request for: {}", fullUrl);

        fullUrl = urlUtils.formatUrlWithProtocol(fullUrl);
        log.debug("Full URL with protocol: {}", fullUrl);

        String urlCode = urlUtils.generateUniqueUrlCode(fullUrl);
        String shortUrl = urlUtils.generateRedirectionUrl(requestUrl, urlCode);
        log.debug("Short URL: {}", shortUrl);

        // Saves record into database -> Mapper ou builder
        UrlEntity urlEntity = UrlEntity.builder()
                .id(urlCode)
                .fullUrl(fullUrl)
                .createdAt(LocalDateTime.now())
                .isEnabled(Boolean.TRUE)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();
        urlRepository.save(urlEntity);

        // Mounts response
        UrlResponseDTO responseDTO = new UrlResponseDTO();
        responseDTO.setUrl(shortUrl);
        responseDTO.setIsEnabled(Boolean.TRUE);
        log.debug("Saved successfully short URL: {}", shortUrl);

        return responseDTO;
    }

    @Override
    public Object update(String id, UrlRequestUpdateDTO body) {
        log.debug("Updating for ID: {}", id);
        UrlEntity urlEntity = urlRepository
                .findById(id)
                .orElseThrow(() ->
                        new UrlNotFoundException("Short URL not found for ID:" + id));
        urlEntity.setFullUrl(body.getFullUrl());
        urlEntity.setIsEnabled(body.getIsEnabled());
        urlRepository.save(urlEntity);
        log.debug("Updated short URL with id {}", id);
        return true;
    }

    @Override
    public HttpHeaders redirect(String id) {
        log.debug("Redirecting for ID: {}", id);
        UrlEntity urlEntity = urlRepository
                .findById(id)
                .orElseThrow(()-> new UrlNotFoundException("No URL found for ID: " + id));
        if(Boolean.FALSE.equals(urlEntity.getIsEnabled())){
            throw new UrlNotEnabledException("URL is disabled - ID: " + id);
        }
        // Set location header
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(urlEntity.getFullUrl()));
        return headers;
    }
}
