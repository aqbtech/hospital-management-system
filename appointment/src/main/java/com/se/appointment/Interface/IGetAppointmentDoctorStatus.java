package com.se.appointment.Interface;

import com.se.appointment.DTO.Response.AvailableDoctorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGetAppointmentDoctorStatus {
    Page<AvailableDoctorResponse> getAppointmentDoctorStatus(Long startTs, Long endTs, Pageable pageable);
}
