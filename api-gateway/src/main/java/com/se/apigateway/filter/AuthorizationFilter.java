package com.se.apigateway.filter;

import com.se.apigateway.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

// @Component (Tạm thời vô hiệu hóa để tránh xung đột với GlobalAuthenticationFilter)
public class AuthorizationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;
    private final List<String> excludedUrls;

    public AuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        // Paths that don't require authentication
        this.excludedUrls = List.of(
            "/api/v1/auth/login", 
            "/api/v1/auth/register",
            "/api/v1/auth/signup",
            "/api/v1/auth/refresh-token"
        );
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        
        // Skip authentication for excluded paths
        if (isExcludedUrl(path)) {
            return chain.filter(exchange);
        }

        // Check for Authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, HttpStatus.UNAUTHORIZED, "No valid authorization token found");
        }

        String token = authHeader.substring(7);
        
        try {
            // Validate token
            if (jwtUtil.isTokenExpired(token)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Token expired");
            }

            // Extract claims from token
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);
            List<String> authorities = jwtUtil.extractAuthorities(token);
            
            // Add user info to headers to be passed to microservices
            ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Username", username)
                .header("X-User-Role", role)
                .header("X-User-Authorities", String.join(",", authorities))
                .build();
            
            // Continue with modified request
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
            
        } catch (ExpiredJwtException e) {
            return onError(exchange, HttpStatus.UNAUTHORIZED, "Token expired");
        } catch (MalformedJwtException | SignatureException e) {
            return onError(exchange, HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (Exception e) {
            return onError(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }

    private boolean isExcludedUrl(String path) {
        return excludedUrls.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // High priority to run before other filters
    }
}
