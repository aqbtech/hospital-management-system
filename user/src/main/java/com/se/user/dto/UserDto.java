package com.se.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.se.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Role role;
    private Date createdAt;
    private Date lastLogin;
    private Boolean active;
    private ProfileDto profile;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ProfileDto {
        private Long id;
        private String fullName;
        private String specialty;
        private String department;
        private String gender;
        private String address;
        private String insuranceNumber;
    }
} 