package chloe.movietalk.controller;

import chloe.movietalk.dto.request.LoginRequest;
import chloe.movietalk.dto.request.SignupRequest;
import chloe.movietalk.dto.response.user.LoginResponse;
import chloe.movietalk.dto.response.user.UserInfoResponse;
import chloe.movietalk.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponse> signup(@RequestBody @Valid SignupRequest request) {
        UserInfoResponse response = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = userService.logIn(request);
        return ResponseEntity.ok().body(response);
    }
}
