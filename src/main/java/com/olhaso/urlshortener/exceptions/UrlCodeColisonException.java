package com.olhaso.urlshortener.exceptions;

public class UrlCodeColisonException extends RuntimeException {
    public static final long serialVersionID = 1;

    public UrlCodeColisonException(String message){
        super(message);
    }
}
