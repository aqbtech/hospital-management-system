package com.sa.doctors.service;

import com.sa.doctors.dto.DoctorProfileUpdateRequest;
import com.sa.doctors.entity.Doctor;

import java.util.UUID;

public interface IDoctorProfileService {
    void updateDoctorProfile(UUID doctorId, DoctorProfileUpdateRequest profileUpdateRequest);
    Doctor getDoctorProfile(UUID doctorId);
}
