package com.app.controller.v1.api;

import com.app.controller.v1.request.UserLoginRequest;
import com.app.controller.v1.request.UserSignUpRequest;
import com.app.dto.model.user.UserDto;
import com.app.dto.response.Response;
import com.app.dto.response.Status;
import com.app.security.JwtUtil;
import com.app.service.auth.AuthService;
import com.app.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, AuthService authService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signUp")
    public Response signUp(@RequestBody @Validated UserSignUpRequest userSignUpRequest) {
        return Response.builder()
                .status(Status.OK)
                .payload(signUpUser(userSignUpRequest))
                .message("User registered successfully")
                .build();
    }

    @PostMapping("/login")
    public Response login(@RequestBody @Validated UserLoginRequest userLoginRequest) {
        String jwt = logInUser(userLoginRequest);
        return Response.builder()
                .status(Status.OK)
                .payload(jwt)
                .build();
    }

    private UserDto signUpUser(UserSignUpRequest userSignUpRequest) {
        UserDto userDto = UserDto.builder()
                .username(userSignUpRequest.getUsername())
                .password(userSignUpRequest.getPassword())
                .firstName(userSignUpRequest.getFirstName())
                .lastName(userSignUpRequest.getLastName())
                .build();

        return userService.signUp(userDto);
    }

    private String logInUser(UserLoginRequest userLoginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getUsername(),
                            userLoginRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            log.info(userLoginRequest.getUsername() + "failed login attempt, incorrect username or password");
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = authService.loadUserByUsername(userLoginRequest.getUsername());

        return jwtUtil.generateToken(userDetails);
    }

}
