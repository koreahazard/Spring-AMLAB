package com.amlab.spring_amlab.account.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {

        super(message);
    }
}
