package com.se.appointment.DTO.Response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentStatusResponse {
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private Slot slot;
}
