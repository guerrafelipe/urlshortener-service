package com.olhaso.urlshortener.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UrlRequestCreateDTO {
    @NotBlank
    @NotNull
    public String fullUrl;
}
