package com.se.user.factory;

import com.se.user.dto.RegisterRequest;
import com.se.user.entity.PatientProfile;
import com.se.user.entity.Role;
import com.se.user.entity.User;
import com.se.user.repository.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory để tạo profile cho bệnh nhân
 */
@Component
@RequiredArgsConstructor
public class PatientProfileFactory implements ProfileFactory {
    private final PatientProfileRepository patientProfileRepository;

    @Override
    public boolean supports(User user) {
        return user.getRole() == Role.PATIENT;
    }

    @Override
    public void createProfile(User user, RegisterRequest request) {
        var patientProfile = PatientProfile.builder()
                .user(user)
                .fullName(request.getFullName())
                .gender(request.getGender())
                .dob(request.getDob())
                .address(request.getAddress())
                .insuranceNumber(request.getInsuranceNumber())
                .emergencyContact(request.getEmergencyContact())
                .build();
        patientProfileRepository.save(patientProfile);
    }
}
