package com.se.patient.dto;

import lombok.Data;

@Data
public class DoctorDto {
    private Long doctorId;
    private String name;
    private String specialty;
}