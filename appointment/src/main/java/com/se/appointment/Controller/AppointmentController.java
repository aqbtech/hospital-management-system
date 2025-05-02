package com.se.appointment.Controller;

import com.se.appointment.DTO.Request.BookAppointmentRequest;
import com.se.appointment.DTO.Request.UpdateAppointmentRequest;
import com.se.appointment.DTO.Response.*;
import com.se.appointment.Interface.IBookAppointment;
import com.se.appointment.Interface.IGetAppointmentDoctorStatus;
import com.se.appointment.Interface.IGetAppointmentStatus;
import com.se.appointment.Interface.IUpdateAppointment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {
    private final IGetAppointmentStatus iGetAppointmentStatus;
    private final IBookAppointment iBookAppointment;
    private final IGetAppointmentDoctorStatus iGetAppointmentDoctorStatus;
    private final IUpdateAppointment iUpdateAppointment;
    @GetMapping("/appointment-status")
    public ResponseAPITemplate<?> getAppointmentStatus(
            @RequestParam Long patientId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        try {
            Page<AppointmentStatusResponse> pageResult = iGetAppointmentStatus.getAppointmentStatus(patientId, pageable);
            return ResponseAPITemplate.<Page<AppointmentStatusResponse>>builder()
                    .result(pageResult)
                    .build();
        } catch (Exception e) {
            return ResponseAPITemplate.builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/appointment-doctor-status")
    public ResponseAPITemplate<?> getAppointmentDoctorStatus(
            @RequestParam Long startTs,
            @RequestParam Long endTs,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        try {
            Page<AvailableDoctorResponse> availableDoctor = iGetAppointmentDoctorStatus.getAppointmentDoctorStatus(startTs, endTs, pageable);
            return ResponseAPITemplate.<Page<AvailableDoctorResponse>>builder()
                    .result(availableDoctor)
                    .build();
        } catch (Exception e) {
            return ResponseAPITemplate.builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/book-appointment")
    public ResponseAPITemplate<?> bookAppointment(@RequestBody BookAppointmentRequest bookAppointmentRequest) {
        try {
            BookAppointmentResult bookAppointmentResult = iBookAppointment.bookAppointment(bookAppointmentRequest);
            if (!bookAppointmentResult.isHasConflict()) {
                return ResponseAPITemplate.<BookAppointmentSuccessResponse>builder()
                        .result(bookAppointmentResult.getSuccessData())
                        .build();
            }
            return ResponseAPITemplate.<ConflictDetailsResponse>builder()
                    .code(409)
                    .message("Conflict")
                    .result(bookAppointmentResult.getConflictData())
                    .build();
        } catch (Exception e) {
            return ResponseAPITemplate.builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }
    @PutMapping("/update-appointment")
    public ResponseAPITemplate<?> updateAppointment(@RequestBody UpdateAppointmentRequest updateAppointmentRequest) {
        try {
            UpdateAppointmentResult updateAppointmentResult = iUpdateAppointment.updateAppointment(updateAppointmentRequest);
            if (!updateAppointmentResult.isHasConflict()) {
                return ResponseAPITemplate.<UpdateAppointmentSuccessResponse>builder()
                        .result(updateAppointmentResult.getSuccessData())
                        .build();
            }
            return ResponseAPITemplate.<ConflictDetailsResponse>builder()
                    .code(409)
                    .message("Conflict")
                    .result(updateAppointmentResult.getConflictData())
                    .build();
        } catch (Exception e) {
            return ResponseAPITemplate.builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }
}
