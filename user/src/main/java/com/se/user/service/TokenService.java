package com.se.user.service;

import org.springframework.stereotype.Service;

import com.se.user.entity.Token;
import com.se.user.entity.User;
import com.se.user.repository.TokenRepository;
import com.se.user.security.JwtService;

import lombok.RequiredArgsConstructor;

/**
 * Service quản lý các thao tác với token
 */
@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    /**
     * Lưu token mới cho user
     * @param user User cần lưu token
     * @param tokenString Chuỗi token
     * @return Token đã lưu
     */
    public Token saveUserToken(User user, String tokenString) {
        var token = Token.builder()
                .user(user)
                .token(tokenString)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        return tokenRepository.save(token);
    }

    /**
     * Thu hồi tất cả token hợp lệ của user
     * @param user User cần thu hồi token
     */
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.revoke();
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Tạo cặp token mới (access token và refresh token)
     * @param user User cần tạo token
     * @return Object chứa access token và refresh token
     */
    public TokenPair generateTokenPair(User user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new TokenPair(accessToken, refreshToken);
    }

    /**
     * Lớp chứa cặp token
     */
    public record TokenPair(String accessToken, String refreshToken) {}
}
