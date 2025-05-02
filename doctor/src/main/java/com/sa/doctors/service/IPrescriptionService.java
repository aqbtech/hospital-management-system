package com.sa.doctors.service;

import com.sa.doctors.dto.PrescriptionRequest;

import java.util.UUID;

public interface IPrescriptionService {
    UUID createPrescription(UUID patientId, PrescriptionRequest prescriptionRequest);
}
