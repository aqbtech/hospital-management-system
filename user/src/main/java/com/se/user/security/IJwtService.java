package com.se.user.security;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface IJwtService {
    /**
     * Trích xuất username từ token
     */
    String extractUsername(String token);
    
    /**
     * Trích xuất claim từ token
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    
    /**
     * Tạo token cho user
     */
    String generateToken(UserDetails userDetails);
    
    /**
     * Tạo token cho user với các claim bổ sung
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    
    /**
     * Tạo refresh token
     */
    String generateRefreshToken(UserDetails userDetails);
    
    /**
     * Kiểm tra token có hợp lệ không
     */
    boolean isTokenValid(String token, UserDetails userDetails);
} 