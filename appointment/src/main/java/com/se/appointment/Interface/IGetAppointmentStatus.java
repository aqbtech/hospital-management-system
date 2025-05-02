package com.se.appointment.Interface;

import com.se.appointment.DTO.Response.AppointmentStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IGetAppointmentStatus {
    Page<AppointmentStatusResponse> getAppointmentStatus(Long patientId, Pageable pageable);
}
