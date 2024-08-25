package com.olhaso.urlshortener.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UrlResponseDTO {
    public String url;
    public Boolean isEnabled;
}
