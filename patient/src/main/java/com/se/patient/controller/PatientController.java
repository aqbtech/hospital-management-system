package com.se.patient.controller;

import com.se.patient.dto.MedicalRecordDto;
import com.se.patient.model.MedicalRecord;
import com.se.patient.model.Patient;
import com.se.patient.request.RegisterPatientRequest;
import com.se.patient.request.UpdateMedicalRecordRequest;
import com.se.patient.response.ApiResponse;
import com.se.patient.service.MedicalRecord.IMedicalRecordService;
import com.se.patient.service.Patient.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/patient")
public class PatientController {
    private final IMedicalRecordService iMedicalRecordService;
    private final IPatientService iPatientService;
    @GetMapping("/view-medicince-history")
    public ResponseEntity<ApiResponse> getMedicalRecordsByPatientId
            (@RequestParam Long patientId, @RequestParam int limit){
        try {
            List<MedicalRecordDto> records = iPatientService.getAllRecordByPatientId(patientId, limit);
            return ResponseEntity.ok(new ApiResponse("null", "null", records));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e.getMessage(), null));
        }
    }
    @PostMapping("/update-medical-record")
    public ResponseEntity<ApiResponse> updateMedicalRecord
            (@RequestBody UpdateMedicalRecordRequest request)
    {
        try {
            MedicalRecord medicalRecord = iMedicalRecordService.updateMedicalRecord(request);
            MedicalRecordDto medicalRecordDto = iMedicalRecordService.convertoDto(medicalRecord);
            return ResponseEntity.ok(new ApiResponse("null", "null", medicalRecordDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), e.getMessage(), null));
        }
    }

    @PostMapping("/register-patient")
    public ResponseEntity<ApiResponse> registerPatient
            (@RequestBody RegisterPatientRequest request)
    {
        try {
            Patient patient = iPatientService.addPatient(request);
            return ResponseEntity.ok(new ApiResponse("null", "null", patient));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), e.getMessage(), null));
        }
    }


}
