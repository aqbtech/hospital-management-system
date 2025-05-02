package com.sa.doctors.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MedicineDto {
    private UUID medicineId;
    private String dosage;
    private String frequency;
    private String duration;
    private String notes;
}
