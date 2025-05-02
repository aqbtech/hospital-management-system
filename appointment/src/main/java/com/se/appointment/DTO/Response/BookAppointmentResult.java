package com.se.appointment.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookAppointmentResult {
    private boolean hasConflict;
    private BookAppointmentSuccessResponse successData;
    private ConflictDetailsResponse conflictData;
}
