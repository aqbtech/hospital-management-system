package com.se.patient.service.MedicalRecord;

import com.se.patient.dto.MedicalRecordDto;
import com.se.patient.model.MedicalRecord;
import com.se.patient.request.UpdateMedicalRecordRequest;

public interface IMedicalRecordService {
    MedicalRecord updateMedicalRecord(UpdateMedicalRecordRequest request);
    MedicalRecord addMedicalRecord(UpdateMedicalRecordRequest request);
    MedicalRecordDto convertoDto(MedicalRecord medicalRecord);
}
