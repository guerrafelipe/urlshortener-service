package com.olhaso.urlshortener.utils;

import com.olhaso.urlshortener.exceptions.UrlCodeColisonException;
import com.olhaso.urlshortener.repository.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UrlUtils {
    private final UrlRepository urlRepository;

    public String formatUrlWithProtocol(String url){
        if(!url.contains("https://") && !url.contains("http://")) {
            url = "https://" + url;
        }
        return url;
    }

    public String generateUniqueUrlCode(String fullUrl) {
        String id;
        int maxAttempts = 10;
        int attempts = 0;
        do {
            id = CryptographyUtils.generateHash(fullUrl, 2);
            attempts++;
            if (attempts >= maxAttempts) {
                throw new UrlCodeColisonException("Failed to generate a unique ID after "
                        + maxAttempts + " attempts.");
            }
        } while (urlRepository.existsById(id));
        return id;
    }

    public String generateRedirectionUrl(String requestURL, String urlCode){
        return requestURL.replace("shorten-url", urlCode);
    }
}
