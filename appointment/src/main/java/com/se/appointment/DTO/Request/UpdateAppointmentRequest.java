package com.se.appointment.DTO.Request;

import com.se.appointment.DTO.Response.Slot;
import lombok.Data;
@Data
public class UpdateAppointmentRequest {
    private Long patientId;
    private Long doctorId;
    private Long appointmentId;
    private Slot slot;
}
