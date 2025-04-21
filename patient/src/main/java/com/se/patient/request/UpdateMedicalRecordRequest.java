package com.se.patient.request;

import com.se.patient.model.Doctor;
import lombok.Data;

import java.time.ZonedDateTime;
@Data
public class UpdateMedicalRecordRequest {
    private Long recordId;
    private Long patientId;
    private Doctor doctor;
    private ZonedDateTime date;
    private String symptoms;
    private String diagnosis;
}
