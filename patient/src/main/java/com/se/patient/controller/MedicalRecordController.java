package com.se.patient.controller;

import com.se.patient.dto.MedicalRecordDto;
import com.se.patient.model.MedicalRecord;
import com.se.patient.request.UpdateMedicalRecordRequest;
import com.se.patient.response.ApiResponse;
import com.se.patient.service.MedicalRecord.IMedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/patient")
public class MedicalRecordController {
    private final IMedicalRecordService iMedicalRecordService;
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
}
