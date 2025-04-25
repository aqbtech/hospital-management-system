package com.sa.doctors.service.Impl;

import com.sa.doctors.dto.PrescriptionRequest;
import com.sa.doctors.entity.Medicine;
import com.sa.doctors.entity.Patient;
import com.sa.doctors.entity.Prescription;
import com.sa.doctors.entity.PrescriptionMedicine;
import com.sa.doctors.repository.PrescriptionRepository;
import com.sa.doctors.service.IPrescriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionServiceImpl implements IPrescriptionService {
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public UUID createPrescription(UUID patientId, PrescriptionRequest prescriptionRequest) {
        Prescription prescription = new Prescription();
        Patient patient = new Patient();
        patient.setId(patientId);

        prescription.setPatient(patient);

        List<PrescriptionMedicine> prescriptionMedicines = prescriptionRequest.getMedications().stream().map(medicineDto -> {
            return PrescriptionMedicine.builder()
                    .notes(medicineDto.getNotes())
                    .dosage(medicineDto.getDosage())
                    .duration(medicineDto.getDuration())
                    .frequency(medicineDto.getFrequency())
                    .medicine(new Medicine(medicineDto.getMedicineId()))
                    .prescription(prescription)
                    .build();

        }).toList();

        prescription.setPrescriptionMedicines(prescriptionMedicines);

        try{
            return prescriptionRepository.save(prescription).getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
