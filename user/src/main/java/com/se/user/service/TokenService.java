package com.se.user.service;

import org.springframework.stereotype.Service;

import com.se.user.entity.BlacklistedToken;
import com.se.user.entity.User;
import com.se.user.repository.BlacklistedTokenRepository;
import com.se.user.security.IJwtService;

import lombok.RequiredArgsConstructor;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {
    private final IJwtService jwtService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    
    /**
     * Thêm token vào danh sách đen (blacklist) khi logout hoặc cần thu hồi token
     * @param token Token cần blacklist
     */
    @Override
    public void blacklistToken(String token) {
        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .revokedAt(Instant.now())
                .build();
        blacklistedTokenRepository.save(blacklistedToken);
    }
    
    /**
     * Kiểm tra token có nằm trong blacklist hay không
     * @param token Token cần kiểm tra
     * @return true nếu token đã bị blacklist, false nếu không
     */
    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }

    /**
     * Tạo cặp token mới (access token và refresh token)
     * @param user User cần tạo token
     * @return Object chứa access token và refresh token
     */
    @Override
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
