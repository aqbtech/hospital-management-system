package com.se.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenType tokenType;

    @Column(nullable = false)
    private boolean expired;

    @Column(nullable = false)
    private boolean revoked;

    @Column(name = "expiration_time")
    private Date expirationTime;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Domain behaviors
    public boolean isValid() {
        if (expired || revoked) {
            return false;
        }
        
        // Check if token has expired based on timestamp
        if (expirationTime != null && expirationTime.before(new Date())) {
            this.expired = true;
            return false;
        }
        
        return true;
    }

    public void revoke() {
        this.expired = true;
        this.revoked = true;
    }

    public static Token createToken(User user, String tokenValue, TokenType tokenType, Date expirationTime) {
        return Token.builder()
                .user(user)
                .token(tokenValue)
                .tokenType(tokenType)
                .expired(false)
                .revoked(false)
                .expirationTime(expirationTime)
                .createdAt(new Date())
                .build();
    }

    public static Token createToken(User user, String tokenValue, TokenType tokenType) {
        return createToken(user, tokenValue, tokenType, null);
    }

    public enum TokenType {
        BEARER,
        REFRESH,
        PASSWORD_RESET
    }
} 