package chloe.movietalk.service.impl;

import chloe.movietalk.auth.JwtProvider;
import chloe.movietalk.domain.SiteUser;
import chloe.movietalk.dto.request.LoginRequest;
import chloe.movietalk.dto.request.SignupRequest;
import chloe.movietalk.dto.response.user.LoginResponse;
import chloe.movietalk.dto.response.user.UserInfo;
import chloe.movietalk.dto.response.user.UserInfoResponse;
import chloe.movietalk.exception.auth.AlreadyExistsUserException;
import chloe.movietalk.exception.auth.InvalidPasswordException;
import chloe.movietalk.exception.auth.UserNotFoundException;
import chloe.movietalk.repository.UserRepository;
import chloe.movietalk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;

    @Override
    public UserInfoResponse signUp(SignupRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(a -> {
                    throw AlreadyExistsUserException.EXCEPTION;
                });

        SiteUser user = SiteUser.builder()
                .email(request.getEmail())
                .passwordHash(encoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();
        userRepository.save(user);
        return UserInfoResponse.fromEntity(user);
    }

    @Override
    public LoginResponse logIn(LoginRequest request) {
        SiteUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!encoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw InvalidPasswordException.EXCEPTION;
        }

        String accessToken = jwtProvider.generateAccessToken(UserInfo.fromEntity(user));
        String refreshToken = jwtProvider.generateRefreshToken();
        return new LoginResponse(accessToken, refreshToken);
    }
}
