package com.amlab.spring_amlab.account.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CreateUserResponse {
    private final boolean isSuccess;
    private final List<String> errors;
}
