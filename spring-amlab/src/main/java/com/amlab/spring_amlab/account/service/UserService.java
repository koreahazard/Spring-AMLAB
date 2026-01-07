package com.amlab.spring_amlab.account.service;

import com.amlab.spring_amlab.account.entity.User;
import com.amlab.spring_amlab.account.service.request.CreateUserRequest;
import com.amlab.spring_amlab.account.service.response.CreateUserResponse;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
}
