package com.sa.doctors.controller;

import com.sa.doctors.dto.AppointmentPatientResponse;
import com.sa.doctors.dto.DoctorScheduleResponse;
import com.sa.doctors.dto.ResultObject;
import com.sa.doctors.enums.PatientStatus;
import com.sa.doctors.service.IDoctorAppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class DoctorAppointmentController {

    private final IDoctorAppointmentService doctorAppointmentService;

    public DoctorAppointmentController(IDoctorAppointmentService doctorAppointmentService) {
        this.doctorAppointmentService = doctorAppointmentService;
    }

    @GetMapping("/patients")
    public ResponseEntity<ResultObject> getDoctorSchedule( @RequestParam("doctorId") UUID doctorId,
                                                           @RequestParam(value = "status", required = false) PatientStatus status){


        List<AppointmentPatientResponse> doctorPatients = doctorAppointmentService.getDoctorPatients(doctorId, status);

        ResultObject response = ResultObject.builder()
                .isSuccess(true)
                .message("Get doctor schedule successfully")
                .httpStatus(HttpStatus.OK)
                .data(doctorPatients)
                .build();

        return new ResponseEntity(response, HttpStatus.OK);

    }
}
