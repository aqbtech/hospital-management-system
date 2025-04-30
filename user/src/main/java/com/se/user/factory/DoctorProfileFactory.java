package com.se.user.factory;

import com.se.user.dto.RegisterRequest;
import com.se.user.entity.DoctorProfile;
import com.se.user.entity.Role;
import com.se.user.entity.User;
import com.se.user.repository.DoctorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory để tạo profile cho bác sĩ
 */
@Component
@RequiredArgsConstructor
public class DoctorProfileFactory implements ProfileFactory {
    private final DoctorProfileRepository doctorProfileRepository;

    @Override
    public boolean supports(User user) {
        return user.getRole() == Role.DOCTOR;
    }

    @Override
    public void createProfile(User user, RegisterRequest request) {
        var doctorProfile = DoctorProfile.builder()
                .user(user)
                .fullName(request.getFullName())
                .department("Chưa phân khoa")
                .build();
        doctorProfileRepository.save(doctorProfile);
    }
}
