package com.sa.doctors.controller;


import com.sa.doctors.dto.DoctorScheduleResponse;
import com.sa.doctors.dto.PrescriptionRequest;
import com.sa.doctors.dto.ResultObject;
import com.sa.doctors.service.IDoctorScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors/schedule")
public class DoctorScheduleController {

    private final IDoctorScheduleService doctorScheduleService;

    public DoctorScheduleController(IDoctorScheduleService doctorScheduleService) {
        this.doctorScheduleService = doctorScheduleService;
    }

    @GetMapping
    public ResponseEntity<ResultObject> getDoctorSchedule( @RequestParam("doctorId") UUID doctorId,
                                                           @RequestParam("startDate") LocalDate startDate,
                                                           @RequestParam("endDate") LocalDate endDate){


        List<DoctorScheduleResponse> doctorSchedule = doctorScheduleService.getDoctorSchedule(doctorId, startDate, endDate);

        ResultObject response = ResultObject.builder()
                .isSuccess(true)
                .message("Get doctor schedule successfully")
                .httpStatus(HttpStatus.OK)
                .data(doctorSchedule)
                .build();

        return new ResponseEntity(response, HttpStatus.OK);

    }
}
