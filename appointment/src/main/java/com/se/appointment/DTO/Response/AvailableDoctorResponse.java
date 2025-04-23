package com.se.appointment.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class AvailableDoctorResponse {
    private Long doctorId;
    private Slot slot;
}
