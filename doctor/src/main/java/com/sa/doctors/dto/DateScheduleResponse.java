package com.sa.doctors.dto;

import com.sa.doctors.enums.SlotStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DateScheduleResponse {

    private LocalDate date;
    private List<TimeSlot> slots;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class TimeSlot {
        private String startTime;
        private String endTime;
        private SlotStatusEnum slotStatus;
    }
}
