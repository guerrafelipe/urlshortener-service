package com.olhaso.urlshortener.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleValidationExceptions() {
        FieldError fieldError = new FieldError("objectName",
                "fieldName",
                "Error message");
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        WebRequest request = mock(WebRequest.class);

        when(exception.getBindingResult()).thenReturn(new MockBindingResult(fieldError));

        ResponseEntity<Object> response = globalExceptionHandler
                .handleValidationExceptions(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("fieldName", "Error message");
        assertEquals(expectedErrors, response.getBody());
    }


    @Test
    void testHandleUrlNotFoundException() {
        // Arrange
        UrlNotFoundException exception = new UrlNotFoundException("URL not found");
        WebRequest request = mock(WebRequest.class);

        // Act
        ResponseEntity<ErrorObject> response = globalExceptionHandler.handleUrlNotFoundException(exception, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatusCode());
        assertEquals("URL not found", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testHandleUrlNotEnabledException() {
        // Arrange
        UrlNotEnabledException exception = new UrlNotEnabledException("URL is not enabled");
        WebRequest request = mock(WebRequest.class);

        // Act
        ResponseEntity<ErrorObject> response = globalExceptionHandler.handleUrlNotEnabledException(exception, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatusCode());
        assertEquals("URL is not enabled", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testHandleUrlColisonException() {
        // Arrange
        UrlCodeColisonException exception = new UrlCodeColisonException("URL code collision");
        WebRequest request = mock(WebRequest.class);

        // Act
        ResponseEntity<ErrorObject> response = globalExceptionHandler.handleUrlColisonException(exception, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatusCode());
        assertEquals("URL code collision", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    // MockBindingResult class to simulate BindingResult behavior
    private static class MockBindingResult extends BeanPropertyBindingResult {
        public MockBindingResult(FieldError fieldError) {
            super(new Object(), "objectName");
            addError(fieldError);
        }
    }
}
