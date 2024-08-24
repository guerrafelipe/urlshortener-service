package com.olhaso.urlshortener.service.impl;

import com.olhaso.urlshortener.dto.UrlRequestCreateDTO;
import com.olhaso.urlshortener.dto.UrlRequestUpdateDTO;
import com.olhaso.urlshortener.dto.UrlResponseDTO;
import com.olhaso.urlshortener.entities.UrlEntity;
import com.olhaso.urlshortener.repository.UrlRepository;
import com.olhaso.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import com.olhaso.urlshortener.utils.UrlUtils;


@Service
@AllArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlUtils urlUtils;

    @Override
    public ResponseEntity<UrlResponseDTO> create(UrlRequestCreateDTO request,
                                                 HttpServletRequest servletRequest){
        log.debug("Received URL shortening request for: {}", request.getFullUrl());

        // Format URL and generate its short code
        String fullUrl = urlUtils.formatUrlWithProtocol(request.getFullUrl());
        String urlCode = urlUtils.generateUniqueUrlCode(fullUrl);
        String redirectUrl = urlUtils.generateRedirectionUrl(servletRequest
                .getRequestURL()
                .toString(), urlCode);

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
        UrlResponseDTO response = UrlResponseDTO.builder()
                .url(redirectUrl)
                .isEnabled(Boolean.TRUE)
                .build();
        log.debug("Generated short URL: {}", redirectUrl);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> redirect(String id,
                                           HttpServletRequest servletRequest) {
        log.debug("Redirecting for ID: {}", id);

        // Find a URL with equivalent code
        Optional<UrlEntity> optionalUrlEntity = urlRepository.findById(id);
        if(optionalUrlEntity.isEmpty()){
            log.warn("No URL found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
        UrlEntity urlEntity = optionalUrlEntity.get();
        if(Boolean.FALSE.equals(urlEntity.getIsEnabled())){
            return ResponseEntity.notFound().build();
        }

        // Set location header
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(urlEntity.getFullUrl()));
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @Override
    public ResponseEntity<Object> update(String id, UrlRequestUpdateDTO body) {
        log.debug("Updating for ID: {}", id);

        Optional<UrlEntity> optionalUrlEntity = urlRepository.findById(id);
        if(optionalUrlEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        UrlEntity urlEntity = optionalUrlEntity.get();
        urlEntity.setFullUrl(body.getFullUrl());
        urlEntity.setIsEnabled(body.getIsEnabled());
        urlRepository.save(urlEntity);

        log.debug("Updated short URL with id {}", id);
        return ResponseEntity.noContent().build();
    }
}
