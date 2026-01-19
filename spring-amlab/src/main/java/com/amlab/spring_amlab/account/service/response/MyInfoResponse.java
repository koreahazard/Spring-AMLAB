package com.amlab.spring_amlab.account.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyInfoResponse {
    private Long userId;
    private String userName;
    private String nickname;
}
