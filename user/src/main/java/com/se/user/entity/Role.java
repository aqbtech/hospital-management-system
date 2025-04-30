package com.se.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    PATIENT(Set.of()),
    DOCTOR(Set.of(
            Permission.VIEW_MEDICAL_RECORD,
            Permission.UPDATE_MEDICAL_RECORD,
            Permission.CREATE_PRESCRIPTION,
            Permission.VIEW_PATIENT
    )),
    NURSE(Set.of(
            Permission.VIEW_MEDICAL_RECORD,
            Permission.UPDATE_MEDICAL_RECORD_BASIC,
            Permission.VIEW_PATIENT
    )),
    ACCOUNTANT(Set.of(
            Permission.VIEW_PAYMENT,
            Permission.CREATE_PAYMENT,
            Permission.UPDATE_PAYMENT
    )),
    ADMIN(Set.of(
            Permission.ADMIN_ACCESS,
            Permission.VIEW_MEDICAL_RECORD,
            Permission.UPDATE_MEDICAL_RECORD,
            Permission.CREATE_PRESCRIPTION,
            Permission.VIEW_PATIENT,
            Permission.VIEW_PAYMENT,
            Permission.CREATE_PAYMENT,
            Permission.UPDATE_PAYMENT
    ));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
} 