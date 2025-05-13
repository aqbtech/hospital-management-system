package com.sa.doctors.controller;

import com.sa.doctors.dto.PrescriptionRequest;
import com.sa.doctors.dto.ResultObject;
import com.sa.doctors.service.IPrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/doctors/prescriptions")
public class PrescriptionController {
    private final IPrescriptionService prescriptionService;

    public PrescriptionController(IPrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }


    @PostMapping
    public ResponseEntity<ResultObject> createPrescription(@RequestBody PrescriptionRequest prescriptionRequest){


        UUID prescriptionID = prescriptionService.createPrescription(prescriptionRequest.getPatientId(), prescriptionRequest);

        ResultObject response = ResultObject.builder()
                .isSuccess(true)
                .message("Prescription created successfully")
                .httpStatus(HttpStatus.CREATED)
                .data(prescriptionID)
                .build();

        return new ResponseEntity(response, HttpStatus.OK);

    }

}
