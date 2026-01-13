package com.amlab.spring_amlab.account.controller.requestForm;

import com.amlab.spring_amlab.account.service.request.LoginUserRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginUserRequestForm {
    private final String username;
    private final String password;

    public LoginUserRequest toLoginUserRequest() {
        return new LoginUserRequest(username, password);
    }
}
