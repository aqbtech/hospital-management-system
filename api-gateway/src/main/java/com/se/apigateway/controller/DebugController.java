package com.se.apigateway.controller;

import com.se.apigateway.security.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {

    private final JwtUtil jwtUtil;

    public DebugController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/token")
    public Map<String, Object> debugToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                result.put("error", "No valid authorization token found");
                return result;
            }
            
            String token = authHeader.substring(7);
            
            // Basic token info
            result.put("token", token);
            result.put("isExpired", jwtUtil.isTokenExpired(token));
            
            // Claims extraction
            result.put("username", jwtUtil.extractUsername(token));
            result.put("role", jwtUtil.extractRole(token));
            result.put("authorities", jwtUtil.extractAuthorities(token));
            
            result.put("status", "success");
        } catch (Exception e) {
            result.put("status", "error");
            result.put("exception", e.getClass().getName());
            result.put("message", e.getMessage());
        }
        
        return result;
    }
}
