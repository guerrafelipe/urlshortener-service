package com.olhaso.urlshortener.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UrlResponseDTO {
    public String url;
    public Boolean isEnabled;
}
