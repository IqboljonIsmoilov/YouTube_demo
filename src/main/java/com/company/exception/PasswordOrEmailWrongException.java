package com.company.exception;

public class PasswordOrEmailWrongException extends GlobalException{
    public PasswordOrEmailWrongException(String message) {
        super(message);
    }
}
