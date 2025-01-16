package com.example.centralized_server.service;

import com.example.centralized_server.dto.SignInRequest;
import com.example.centralized_server.dto.SignInResponse;
import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.User;

public interface AuthenticationService {
    void signup (UserDto signUpRequest);

    SignInResponse signIn(SignInRequest signinRequest);
}
