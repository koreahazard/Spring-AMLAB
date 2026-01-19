package com.amlab.spring_amlab.account.controller;

import com.amlab.spring_amlab.account.controller.requestForm.LoginUserRequestForm;
import com.amlab.spring_amlab.account.controller.requestForm.RegisterUserRequestForm;
import com.amlab.spring_amlab.account.entity.User;
import com.amlab.spring_amlab.account.service.request.LoginUserRequest;
import com.amlab.spring_amlab.account.service.response.LoginUserResponse;
import com.amlab.spring_amlab.account.service.response.MyInfoResponse;
import com.amlab.spring_amlab.common.ResponseForm;
import com.amlab.spring_amlab.account.service.UserService;
import com.amlab.spring_amlab.account.service.request.CreateUserRequest;
import com.amlab.spring_amlab.account.service.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseForm<Object>> register(
            @RequestBody RegisterUserRequestForm form
    ) {
        CreateUserRequest request = form.toCreateuserRequest();

        CreateUserResponse response = userService.createUser(request);

        if(!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ResponseForm.fail(
                                    HttpStatus.BAD_REQUEST,
                                    "회원 가입 실패",
                                    response.getErrors()
                            )
                    );
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseForm.success(
                                HttpStatus.OK,
                                "회원 가입 성공",
                                null
                        )
                );

    }

    @PostMapping("/login")
    public ResponseEntity<ResponseForm<Object>> login(
            @RequestBody LoginUserRequestForm form
    ) {
        LoginUserRequest request = form.toLoginUserRequest();
        LoginUserResponse response = userService.loginUser(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseForm.success(
                                HttpStatus.OK,
                                "로그인 성공",
                                response
                        )
                );


    }

    @GetMapping("/myinfo")
    public ResponseEntity<ResponseForm<Object>> myInfo() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(
                            ResponseForm.fail(
                                    HttpStatus.UNAUTHORIZED,
                                    "인증이 필요합니다.",
                                    null
                            )
                    );
        }

        User user = (User) authentication.getPrincipal();

        MyInfoResponse response = new MyInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getNickname()
        );

        return ResponseEntity.ok(
                ResponseForm.success(
                        HttpStatus.OK,
                        "내 정보 조회 성공",
                        response
                )
        );
    }





}
