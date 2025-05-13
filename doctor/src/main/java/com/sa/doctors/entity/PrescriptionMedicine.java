package com.sa.doctors.entity;

import com.sa.doctors.entity.Id.PrescriptionMedicineId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionMedicine {

    @EmbeddedId
    private PrescriptionMedicineId prescriptionMedicineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("prescriptionId")
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicineId")
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private String dosage;

    private String frequency;

    private String duration;

    private String notes;

}
