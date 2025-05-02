package com.sa.doctors.service.Impl;

import com.sa.doctors.dto.DoctorProfileUpdateRequest;
import com.sa.doctors.entity.Doctor;
import com.sa.doctors.repository.DoctorRepository;
import com.sa.doctors.service.IDoctorProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DoctorProfileServiceImpl implements IDoctorProfileService {

    private final DoctorRepository doctorRepository;

    public DoctorProfileServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }


    @Override
    public void updateDoctorProfile(UUID doctorId, DoctorProfileUpdateRequest profileUpdateRequest) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctor.setName(profileUpdateRequest.getName());
        doctor.setSpeciality(profileUpdateRequest.getSpecialty());
        doctor.setQualifications(profileUpdateRequest.getQualifications());
        doctor.setExperience(profileUpdateRequest.getExperience());

        try {
            doctorRepository.save(doctor);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to update doctor profile", e);
        }
    }

    @Override
    public Doctor getDoctorProfile(UUID doctorId) {
        return null;
    }
}
