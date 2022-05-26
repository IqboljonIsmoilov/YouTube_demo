package com.company.exception;

public class ItemAlreadyExistsException extends GlobalException {
    public ItemAlreadyExistsException(String message) {
        super(message);
    }
}