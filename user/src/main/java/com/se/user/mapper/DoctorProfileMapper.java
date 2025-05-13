package com.se.user.mapper;

import com.se.user.entity.User;
import com.se.user.entity.Role;
import com.se.user.dto.UserDto;
import com.se.user.repository.DoctorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoctorProfileMapper implements ProfileMapper {
    private final DoctorProfileRepository doctorProfileRepository;

    @Override
    public boolean supports(User user) {
        return user.getRole() == Role.DOCTOR;
    }

    @Override
    public UserDto.ProfileDto mapProfile(User user) {
        var doctorOpt = doctorProfileRepository.findByUser(user);
        if (doctorOpt.isPresent()) {
            var doctor = doctorOpt.get();
            return UserDto.ProfileDto.builder()
                    .id(doctor.getId())
                    .fullName(doctor.getFullName())
                    .specialty(doctor.getSpecialty())
                    .department(doctor.getDepartment())
                    .build();
        }
        return null;
    }
}
