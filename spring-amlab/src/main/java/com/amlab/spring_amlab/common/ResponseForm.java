package com.amlab.spring_amlab.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ResponseForm<T> {
    private final boolean success;
    private final int code;
    private final String message;
    private final T data;

    public static <T> ResponseForm<T> success(HttpStatus status, String message, T data) {
        return new ResponseForm<> (true, status.value(), message, data);
    }
    public static <T> ResponseForm<T> fail(HttpStatus status, String message, T data) {
        return new ResponseForm<>(false, status.value(), message, data);
    }
}
