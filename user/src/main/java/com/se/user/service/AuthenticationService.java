package com.se.user.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.se.user.exception.AuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.user.dto.AuthRequest;
import com.se.user.dto.AuthResponse;
import com.se.user.dto.RegisterRequest;
import com.se.user.entity.Role;
import com.se.user.entity.User;
import com.se.user.factory.ProfileFactory;
import com.se.user.mapper.UserMapper;
import com.se.user.repository.UserRepository;
import com.se.user.security.IJwtService;
import com.se.user.service.TokenService.TokenPair;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final List<ProfileFactory> profileFactories;
    private final ITokenService tokenService;
    

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Tạo User với mã hóa password
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole() != null ? request.getRole() : Role.PATIENT)
                .active(true)
                .createdAt(new Date())
                .build();
        var savedUser = userRepository.save(user);

        // Tạo Profile thông qua factory phù hợp với role
        profileFactories.stream()
                .filter(factory -> factory.supports(savedUser))
                .findFirst()
                .ifPresent(factory -> factory.createProfile(savedUser, request));

        // stateless JWT
        TokenPair tokenPair = tokenService.generateTokenPair(savedUser);

        // Tạo response
        return AuthResponse.builder()
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .user(userMapper.mapToUserDto(savedUser))
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        // Xác thực user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("Người dùng không tồn tại"));

        // Cập nhật last login
        user.setLastLogin(new Date());
        userRepository.save(user);

        // Ở đây không có header nên ta không thể blacklist token cũ
        // Trong trường hợp cần, có thể thêm param HttpServletRequest httpRequest vào phương thức này
        // để lấy token cũ từ header và blacklist
        // Tạo token mới
        TokenPair tokenPair = tokenService.generateTokenPair(user);

        // Tạo response
        return AuthResponse.builder()
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .user(userMapper.mapToUserDto(user))
                .build();
    }



    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
            var user = this.userRepository.findByUsername(userName)
                    .orElseThrow(() -> new AuthenticationException("Người dùng không tồn tại"));
            if (jwtService.isTokenValid(refreshToken, user)) {
                // Thêm refresh token hiện tại vào blacklist
                tokenService.blacklistToken(refreshToken);
                // Tạo token mới
                TokenPair tokenPair = tokenService.generateTokenPair(user);
                var authResponse = AuthResponse.builder()
                        .accessToken(tokenPair.accessToken())
                        .refreshToken(tokenPair.refreshToken())
                        .user(userMapper.mapToUserDto(user))
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


} 