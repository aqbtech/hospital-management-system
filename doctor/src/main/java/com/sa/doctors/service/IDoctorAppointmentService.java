package com.sa.doctors.service;

import com.sa.doctors.dto.AppointmentPatientResponse;
import com.sa.doctors.dto.PatientDto;
import com.sa.doctors.enums.PatientStatus;

import java.util.List;
import java.util.UUID;

public interface IDoctorAppointmentService {
    List<AppointmentPatientResponse> getDoctorPatients(UUID doctorId, PatientStatus status);
}
