package com.amlab.spring_amlab.account.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserResponse {
    private final Long userId;
    private final String username;
    private final String nickname;
    private final String accessToken;

}
