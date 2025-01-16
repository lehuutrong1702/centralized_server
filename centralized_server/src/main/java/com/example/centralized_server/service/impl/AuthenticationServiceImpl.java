package com.example.centralized_server.service.impl;

import com.example.centralized_server.constant.ErrorCode;
import com.example.centralized_server.dto.SignInRequest;
import com.example.centralized_server.dto.SignInResponse;
import com.example.centralized_server.dto.UserDto;
import com.example.centralized_server.entity.User;
import com.example.centralized_server.exception.ResourceAlreadyExistException;
import com.example.centralized_server.repository.UserRepository;
import com.example.centralized_server.service.AuthenticationService;
import com.example.centralized_server.service.JwtService;
import com.example.centralized_server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private UserRepository userRepository;

    @Override
    public void signup(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setAddress(passwordEncoder.encode(userDto.getAddress()));
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());

        System.out.println(user.getEmail());
        Optional<User> optionalUser = userRepository.findByAddress(user.getAddress());
        if(optionalUser.isPresent()) {
            throw new ResourceAlreadyExistException(
                    "User has address" + user.getAddress() + " already exists");
        }
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public SignInResponse signIn(SignInRequest signinRequest) {
        String email = signinRequest.getEmail();;
        String password = signinRequest.getAddress();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
        }catch (AuthenticationException ex)
        {
            throw new ErrorResponseException(HttpStatus.NOT_ACCEPTABLE);
        }

        Optional<User> optionalUser = accountRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User userAccount = optionalUser.get();
            var jwt = jwtService.generateToken(userAccount);

            SignInResponse signInResponse = new SignInResponse();
            signInResponse.setToken(jwt);
            return signInResponse;
            // Proceed with the authentication process
        } else {
            System.out.println("fail sigin");
            throw new ErrorResponseException(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
