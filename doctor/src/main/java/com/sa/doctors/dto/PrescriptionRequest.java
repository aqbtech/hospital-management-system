package com.sa.doctors.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PrescriptionRequest {
    private UUID patientId;

    private List<MedicineDto> medications;
}
