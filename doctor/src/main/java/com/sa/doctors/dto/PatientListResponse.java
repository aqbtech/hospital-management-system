package com.sa.doctors.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PatientListResponse {

    private List<PatientInfo> patientList;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class PatientInfo {
        private UUID patientId;
        private String name;
        private String status;
        private int waitingTime;
    }
}
