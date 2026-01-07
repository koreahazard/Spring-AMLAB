package com.amlab.spring_amlab.account.controller.responseForm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RegisterUserResponseForm {
    private final boolean success;
    private final List<String> errors;
}
