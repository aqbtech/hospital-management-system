package com.se.user.service;

import com.se.user.entity.User;
import com.se.user.service.TokenService.TokenPair;

public interface ITokenService {
    /**
     * Thêm token vào danh sách đen (blacklist) khi logout hoặc cần thu hồi token
     * @param token Token cần blacklist
     */
    void blacklistToken(String token);
    
    /**
     * Kiểm tra token có nằm trong blacklist hay không
     * @param token Token cần kiểm tra
     * @return true nếu token đã bị blacklist, false nếu không
     */
    boolean isTokenBlacklisted(String token);
    
    /**
     * Tạo cặp token mới (access token và refresh token)
     * @param user User cần tạo token
     * @return Object chứa access token và refresh token
     */
    TokenPair generateTokenPair(User user);
} 