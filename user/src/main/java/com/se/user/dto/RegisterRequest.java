package com.se.user.dto;

import com.se.user.entity.PatientProfile;
import com.se.user.entity.Role;
import com.se.user.validation.StrongPassword;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50, message = "Tên đăng nhập phải từ 3-50 ký tự")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    @StrongPassword(message = "Mật khẩu phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt")
    private String password;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10,12}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    private Role role;

    private PatientProfile.Gender gender;

    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private Date dob;

    private String address;

    private String insuranceNumber;

    private String emergencyContact;
} 