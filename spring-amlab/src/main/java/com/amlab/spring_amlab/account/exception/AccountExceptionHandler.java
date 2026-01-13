package com.amlab.spring_amlab.account.exception;

import com.amlab.spring_amlab.common.ResponseForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<ResponseForm<Object>> handleUserNameNotFound(
            UserNameNotFoundException e
    ) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseForm.fail(
                        HttpStatus.UNAUTHORIZED,
                        e.getMessage(),
                        null
                ));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ResponseForm<Object>> handleInvalidPassword(
            InvalidPasswordException e
    ) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseForm.fail(
                        HttpStatus.UNAUTHORIZED,
                        e.getMessage(),
                        null
                ));
    }
}
