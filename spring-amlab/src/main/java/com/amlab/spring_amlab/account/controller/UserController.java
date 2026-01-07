package com.amlab.spring_amlab.account.controller;

import com.amlab.spring_amlab.account.controller.requestForm.RegisterUserRequestForm;
import com.amlab.spring_amlab.account.controller.responseForm.RegisterUserResponseForm;
import com.amlab.spring_amlab.account.entity.User;
import com.amlab.spring_amlab.account.repository.UserRepository;
import com.amlab.spring_amlab.account.service.UserService;
import com.amlab.spring_amlab.account.service.request.CreateUserRequest;
import com.amlab.spring_amlab.account.service.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseForm> register(
            @RequestBody RegisterUserRequestForm form
    )
    {
        CreateUserRequest request = new CreateUserRequest(
                form.getUsername(),
                form.getPassword(),
                form.getNickname()
        );

        CreateUserResponse response = userService.createUser(request);

        RegisterUserResponseForm responseForm = new RegisterUserResponseForm(
                response.isSuccess(),
                response.getErrors()
        );

        if (response.isSuccess()) {
            return ResponseEntity.ok(responseForm);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(responseForm);
        }
    }
}
