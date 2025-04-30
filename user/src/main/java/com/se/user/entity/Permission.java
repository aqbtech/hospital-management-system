package com.se.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_ACCESS("admin:access"),
    
    VIEW_MEDICAL_RECORD("medical_record:read"),
    UPDATE_MEDICAL_RECORD("medical_record:write"),
    UPDATE_MEDICAL_RECORD_BASIC("medical_record:write_basic"),
    
    CREATE_PRESCRIPTION("prescription:create"),
    
    VIEW_PATIENT("patient:read"),
    
    VIEW_PAYMENT("payment:read"),
    CREATE_PAYMENT("payment:create"),
    UPDATE_PAYMENT("payment:write");

    @Getter
    private final String permission;
} 