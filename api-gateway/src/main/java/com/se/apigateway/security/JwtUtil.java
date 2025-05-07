package com.se.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("usrId").toString();
    }
    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        
        // First try direct 'role' claim - this is how our updated User service formats tokens
        if (claims.get("role") != null) {
            return claims.get("role").toString();
        }
        
        // Try 'roles' array if present (for compatibility with other token formats)
        if (claims.get("roles") instanceof List) {
            List<?> roles = (List<?>) claims.get("roles");
            if (!roles.isEmpty()) {
                return roles.get(0).toString();
            }
        }
        
        // If role claim not found, try to extract from authorities
        List<Map<String, String>> authorities = extractAuthoritiesMap(token);
        if (authorities != null) {
            // In Spring Security, roles are typically prefixed with 'ROLE_'
            return authorities.stream()
                .map(auth -> auth.get("authority"))
                .filter(auth -> auth != null && auth.startsWith("ROLE_"))
                .map(role -> role.substring(5)) // Remove 'ROLE_' prefix
                .findFirst()
                .orElse(null);
        }
        
        return null;
    }
    
    public List<String> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        
        // First check for authorities in the format we're using in User service
        if (claims.get("authorities") instanceof List) {
            List<?> authorities = (List<?>) claims.get("authorities");
            
            // Handle the format we set up in User service where authorities are maps with 'authority' key
            if (!authorities.isEmpty() && authorities.get(0) instanceof Map) {
                return authorities.stream()
                    .map(auth -> {
                        if (auth instanceof Map) {
                            Map<?, ?> authMap = (Map<?, ?>) auth;
                            if (authMap.get("authority") != null) {
                                return authMap.get("authority").toString();
                            }
                        }
                        return null;
                    })
                    .filter(auth -> auth != null)
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Handle simple string list format
            if (!authorities.isEmpty() && authorities.get(0) instanceof String) {
                return authorities.stream()
                    .map(Object::toString)
                    .collect(java.util.stream.Collectors.toList());
            }
        }
        
        // Try alternative formats if the primary format isn't found
        List<Map<String, String>> authoritiesMap = extractAuthoritiesMap(token);
        if (authoritiesMap != null) {
            return authoritiesMap.stream()
                .map(auth -> auth.get("authority"))
                .filter(auth -> auth != null)
                .collect(java.util.stream.Collectors.toList());
        }
        
        return List.of();
    }
    
    @SuppressWarnings("unchecked")
    private List<Map<String, String>> extractAuthoritiesMap(String token) {
        Claims claims = extractAllClaims(token);
        
        // Try different claim names where authorities might be stored
        // Standard Spring Security JWT format
        if (claims.get("authorities") instanceof List) {
            return (List<Map<String, String>>) claims.get("authorities");
        }
        
        // Alternative format used in some implementations
        if (claims.get("auth") instanceof List) {
            return (List<Map<String, String>>) claims.get("auth");
        }
        
        // Try 'scope' or 'scopes' for OAuth2 style tokens
        if (claims.get("scope") != null) {
            String scopes = claims.get("scope").toString();
            return Arrays.stream(scopes.split(" "))
                .map(scope -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("authority", scope);
                    return map;
                })
                .collect(java.util.stream.Collectors.toList());
        }
        
        return null;
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
