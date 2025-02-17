package com.example.centralized_server.controller;


import com.example.centralized_server.dto.ResponseDto;
import com.example.centralized_server.dto.SignInRequest;
import com.example.centralized_server.dto.SignInResponse;
import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class AuthController {
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody UserDto signUpRequest) {
        authenticationService.signup(signUpRequest);
        return ResponseEntity.ok(new ResponseDto(
                "201",
                "Sign up successfully"
        ));
    }

    @PostMapping("/signin")
    public SignInResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }

}
