package com.se.patient.service.Patient;


import com.se.patient.dto.MedicalRecordDto;
import com.se.patient.model.Patient;
import com.se.patient.request.RegisterPatientRequest;

import java.util.List;

public interface IPatientService {
    Patient getPatientById(Long id);
    List<MedicalRecordDto> getAllRecordByPatientId(Long id, int limit);
    Patient addPatient(RegisterPatientRequest request);
}
