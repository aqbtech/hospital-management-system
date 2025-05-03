package com.se.patient.controller;

import com.se.patient.dto.MedicalRecordDto;
import com.se.patient.response.ApiResponse;
import com.se.patient.service.Patient.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/patient")
public class PatientRecordController {
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


}
