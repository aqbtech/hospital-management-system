package com.se.appointment.DTO.Response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
public class ConflictDetailsResponse {
    private List<ConflictSlot> conflictSlots;

    @Getter
    @Setter
    public static class ConflictSlot {
        private Long appointmentId;
        private Slot slot;
    }
}
