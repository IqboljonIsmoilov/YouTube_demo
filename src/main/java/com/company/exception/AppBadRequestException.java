package com.company.exception;

public class AppBadRequestException extends GlobalException{
    public AppBadRequestException(String message) {
        super(message);
    }
}
