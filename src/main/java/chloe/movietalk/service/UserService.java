package chloe.movietalk.service;

import chloe.movietalk.dto.request.LoginRequest;
import chloe.movietalk.dto.request.SignupRequest;
import chloe.movietalk.dto.response.user.LoginResponse;
import chloe.movietalk.dto.response.user.UserInfoResponse;

public interface UserService {

    public UserInfoResponse signUp(SignupRequest request);

    public LoginResponse logIn(LoginRequest request);
}
