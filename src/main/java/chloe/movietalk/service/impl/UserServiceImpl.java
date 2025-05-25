package chloe.movietalk.service.impl;

import chloe.movietalk.auth.JwtProvider;
import chloe.movietalk.domain.Refresh;
import chloe.movietalk.domain.SiteUser;
import chloe.movietalk.dto.request.LoginRequest;
import chloe.movietalk.dto.request.SignupRequest;
import chloe.movietalk.dto.response.user.UserInfo;
import chloe.movietalk.dto.response.user.UserInfoResponse;
import chloe.movietalk.exception.auth.AlreadyExistsUserException;
import chloe.movietalk.exception.auth.InvalidPasswordException;
import chloe.movietalk.exception.auth.InvalidRefreshToken;
import chloe.movietalk.exception.auth.UserNotFoundException;
import chloe.movietalk.repository.RefreshRepository;
import chloe.movietalk.repository.UserRepository;
import chloe.movietalk.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final RefreshRepository refreshRepository;

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
    public UserInfoResponse logIn(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        SiteUser user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!encoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw InvalidPasswordException.EXCEPTION;
        }

        String accessToken = jwtProvider.generateAccessToken(UserInfo.fromEntity(user));
        response.setHeader("Authorization", "Bearer " + accessToken);

        String refreshToken = jwtProvider.generateRefreshToken(user.getId());
        Refresh refresh = Refresh.builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .build();
        refreshRepository.save(refresh);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(60 * 60 * 24 * 14); // 2주 후 만료
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);

        return UserInfoResponse.fromEntity(user);
    }

    @Override
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshTokenFromCookie = extractRefreshTokenFromCookie(request);
        System.out.println(refreshTokenFromCookie);
        if (refreshTokenFromCookie == null || !jwtProvider.isValidToken(refreshTokenFromCookie)) {
            throw InvalidRefreshToken.EXCEPTION;
        }
        Long id = jwtProvider.getUserId(refreshTokenFromCookie);
        SiteUser user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!refreshTokenFromCookie.equals(refreshRepository.findByUserId(id).getRefreshToken())) {
            throw InvalidRefreshToken.EXCEPTION;
        }

        String newAccessToken = jwtProvider.generateAccessToken(UserInfo.fromEntity(user));
        response.setHeader("Authorization", "Bearer " + newAccessToken);
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
