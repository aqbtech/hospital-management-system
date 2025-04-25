package com.sa.doctors.controller;


import com.sa.doctors.dto.AppointmentPatientResponse;
import com.sa.doctors.dto.DoctorProfileUpdateRequest;
import com.sa.doctors.dto.ResultObject;
import com.sa.doctors.enums.PatientStatus;
import com.sa.doctors.service.IDoctorProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors/profile")
public class DoctorProfileController {

    private final IDoctorProfileService doctorProfileService;

    public DoctorProfileController(IDoctorProfileService doctorProfileService) {
        this.doctorProfileService = doctorProfileService;
    }

    @PutMapping
    public ResponseEntity<ResultObject> updateDoctorProfile(@RequestParam("doctorId") UUID doctorId,
                                                            @RequestBody DoctorProfileUpdateRequest profileUpdateRequest){


        doctorProfileService.updateDoctorProfile(doctorId, profileUpdateRequest);

        ResultObject response = ResultObject.builder()
                .isSuccess(true)
                .message("Update doctor profile successfully")
                .httpStatus(HttpStatus.OK)
                .data(null)
                .build();

        return new ResponseEntity(response, HttpStatus.OK);

    }
}
