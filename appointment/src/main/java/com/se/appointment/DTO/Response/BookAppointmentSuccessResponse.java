package com.se.appointment.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookAppointmentSuccessResponse {
    private Long appointmentId;
    private String status;
    private Slot slot;
}
