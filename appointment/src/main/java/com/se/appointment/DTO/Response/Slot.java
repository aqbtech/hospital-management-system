package com.se.appointment.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Slot {
    private String date;
    private String startTime;
    private String endTime;
}
