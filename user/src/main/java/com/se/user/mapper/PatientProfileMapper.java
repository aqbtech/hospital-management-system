package com.se.user.mapper;

import com.se.user.entity.User;
import com.se.user.entity.Role;
import com.se.user.dto.UserDto;
import com.se.user.repository.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientProfileMapper implements ProfileMapper {
    private final PatientProfileRepository patientProfileRepository;

    @Override
    public boolean supports(User user) {
        return user.getRole() == Role.PATIENT;
    }

    @Override
    public UserDto.ProfileDto mapProfile(User user) {
        var patientOpt = patientProfileRepository.findByUser(user);
        if (patientOpt.isPresent()) {
            var patient = patientOpt.get();
            return UserDto.ProfileDto.builder()
                    .id(patient.getId())
                    .fullName(patient.getFullName())
                    .gender(patient.getGender() != null ? patient.getGender().name() : null)
                    .address(patient.getAddress())
                    .insuranceNumber(patient.getInsuranceNumber())
                    .build();
        }
        return null;
    }
}
