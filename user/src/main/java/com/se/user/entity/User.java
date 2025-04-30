package com.se.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    // Domain behaviors
    public void activate() {
        this.active = true;
    }
    
    public void deactivate() {
        this.active = false;
    }
    
    public void updateLastLogin() {
        this.lastLogin = new Date();
    }
    
    public boolean hasRole(Role requiredRole) {
        return this.role == requiredRole;
    }
    
    public boolean hasAnyRole(Role... roles) {
        for (Role role : roles) {
            if (this.role == role) {
                return true;
            }
        }
        return false;
    }

    // public void setPassword(String rawPassword) {
    //     // This method should not be used directly
    //     // Use UserDomainService.createUser() instead which properly hashes the password
    //     throw new UnsupportedOperationException("Cannot set password directly. Use UserDomainService.createUser() instead.");
    // }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
} 