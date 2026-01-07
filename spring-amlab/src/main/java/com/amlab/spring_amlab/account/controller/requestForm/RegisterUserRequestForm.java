package com.amlab.spring_amlab.account.controller.requestForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterUserRequestForm {
    private final String username;
    private final String password;
    private final String nickname;
}
