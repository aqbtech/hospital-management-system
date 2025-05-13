package com.se.appointment.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAppointmentResult {
    private boolean hasConflict;
    private UpdateAppointmentSuccessResponse successData;
    private ConflictDetailsResponse conflictData;
}
