package com.sa.doctors.entity.Id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
public class PrescriptionMedicineId {

    private UUID prescriptionId;
    private UUID medicineId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionMedicineId that = (PrescriptionMedicineId) o;
        return Objects.equals(prescriptionId, that.prescriptionId) && Objects.equals(medicineId, that.medicineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionId, medicineId);
    }
}
