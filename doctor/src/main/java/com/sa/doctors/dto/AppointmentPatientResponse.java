package com.sa.doctors.dto;

import com.sa.doctors.enums.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentPatientResponse {
    private UUID patientId;
    private String name;
    private PatientStatus status;
    private int waitingTime;
}
