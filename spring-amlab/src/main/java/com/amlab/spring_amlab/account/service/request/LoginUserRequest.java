package com.amlab.spring_amlab.account.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginUserRequest {
    private final String username;
    private final String password;
}
