package com.amlab.spring_amlab.account.controller.requestForm;

import com.amlab.spring_amlab.account.service.request.CreateUserRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterUserRequestForm {
    private final String username;
    private final String password;
    private final String nickname;

    public CreateUserRequest toCreateuserRequest() {
        return new CreateUserRequest(
                this.username,
                this.password,
                this.nickname
        );
    }
}
