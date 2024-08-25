package com.olhaso.urlshortener.exceptions;

public class UrlNotEnabledException extends RuntimeException {
    public static final long serialVersionID = 1;

    public UrlNotEnabledException(String message){
        super(message);
    }
}
