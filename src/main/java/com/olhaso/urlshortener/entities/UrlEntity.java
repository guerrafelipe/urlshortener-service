package com.olhaso.urlshortener.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "urls")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UrlEntity {
    @Id
    @NotBlank
    @NotNull
    private String id;

    @NotBlank
    @NotNull
    private String fullUrl;

    @Indexed(expireAfterSeconds = 0)
    private LocalDateTime expiresAt;

    private Boolean isEnabled;

    private LocalDateTime createdAt;
}
