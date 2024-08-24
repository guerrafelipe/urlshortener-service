package com.olhaso.urlshortener.controller;

import com.olhaso.urlshortener.dto.UrlRequestCreateDTO;
import com.olhaso.urlshortener.dto.UrlRequestUpdateDTO;
import com.olhaso.urlshortener.dto.UrlResponseDTO;
import com.olhaso.urlshortener.service.impl.UrlServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UrlController {
    private final UrlServiceImpl urlService;

    @PostMapping(value="/shorten-url")
    public ResponseEntity<UrlResponseDTO> shortenUrl(@RequestBody @Valid UrlRequestCreateDTO body,
                                                     HttpServletRequest servletRequest){
        return urlService.create(body, servletRequest);
    }

    @PutMapping(value="{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id,
                                         @RequestBody @Valid UrlRequestUpdateDTO body){
        return urlService.update(id, body);
    }

    @GetMapping(value="{id}")
    public ResponseEntity<Object> redirect(@PathVariable("id") String id,
                                           HttpServletRequest servletRequest){
        return urlService.redirect(id, servletRequest);
    }
}
