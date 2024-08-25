package com.olhaso.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UrlRequestUpdateDTO {
    @NotBlank
    @NotNull
    public String fullUrl;

    @NotNull
    public Boolean isEnabled;

}
