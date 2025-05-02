package com.se.patient.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class MedicalRecordDto {
    private Long recordId;
    private Long patientId;
    private DoctorDto doctor;
    private ZonedDateTime date;
    private String symptoms;
    private String diagnosis;

    public MedicalRecordDto(Long recordId, String diagnosis, String symptoms, ZonedDateTime date, String name) {
    }

    public MedicalRecordDto() {
    }
}
