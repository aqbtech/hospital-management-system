package com.se.appointment.DTO.Request;

import com.se.appointment.DTO.Response.Slot;
import lombok.Data;

@Data
public class BookAppointmentRequest {
    private Long patientId;
    private Long doctorId;
    private Slot slot;
}
