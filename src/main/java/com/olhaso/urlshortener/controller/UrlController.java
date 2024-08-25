package com.olhaso.urlshortener.controller;

import com.olhaso.urlshortener.dto.UrlRequestCreateDTO;
import com.olhaso.urlshortener.dto.UrlRequestUpdateDTO;
import com.olhaso.urlshortener.dto.UrlResponseDTO;
import com.olhaso.urlshortener.service.impl.UrlServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UrlController {
    private final UrlServiceImpl urlService;

    @PostMapping(value="/shorten-url")
    @Operation(description = "Creates a Short URL from a Full URL")
    @ApiResponses(value={
            @ApiResponse(responseCode="201", description="Returns a Short URL"),
            @ApiResponse(responseCode="400", description="Invalid Full URL")
    })
    public ResponseEntity<Object> create(@RequestBody @Valid UrlRequestCreateDTO body,
                                                     HttpServletRequest servletRequest) {
            UrlResponseDTO urlResponseDTO = urlService.create(body, servletRequest
                    .getRequestURL().toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(urlResponseDTO);
    }

    @PutMapping(value="{id}")
    @Operation(description = "Updates a Short URL")
    @ApiResponses(value={
            @ApiResponse(responseCode="204", description="Short URL updated successfully"),
            @ApiResponse(responseCode="400", description="Invalid parameters"),
            @ApiResponse(responseCode="404", description="Short URL not Found")
    })
    public ResponseEntity<Object> update(@PathVariable("id") String id,
                                         @RequestBody @Valid UrlRequestUpdateDTO body){
            urlService.update(id, body);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value="{id}")
    @Operation(description = "Redirect from a Short to Full URL")
    @ApiResponses(value={
            @ApiResponse(responseCode="302", description="Redirection was successfully"),
            @ApiResponse(responseCode="400", description="Invalid parameters"),
            @ApiResponse(responseCode="404", description="Short URL not Found")
    })
    public ResponseEntity<Object> redirect(@PathVariable("id") String id){
            HttpHeaders headers = urlService.redirect(id);
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .headers(headers)
                    .build();
    }
}
