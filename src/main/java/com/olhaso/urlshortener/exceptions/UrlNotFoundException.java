package com.olhaso.urlshortener.exceptions;

public class UrlNotFoundException extends RuntimeException {
    public static final long serialVersionID = 1;

    public UrlNotFoundException(String message){
        super(message);
    }
}
