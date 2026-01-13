package com.amlab.spring_amlab.account.exception;


public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException(String message) {
        super(message);
    }
}

