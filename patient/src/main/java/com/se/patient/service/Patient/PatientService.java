package com.se.patient.service.Patient;

import com.se.patient.dto.MedicalRecordDto;
import com.se.patient.exceptions.AlreadyExistException;
import com.se.patient.exceptions.ResourceNotFoundException;
import com.se.patient.model.Patient;
import com.se.patient.model.MedicalRecord;
import com.se.patient.repository.PatientRepository;
import com.se.patient.request.RegisterPatientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService{
    private final PatientRepository patientRepository;

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }

    @Override
    public List<MedicalRecordDto> getAllRecordByPatientId(Long id, int limit) {
        Patient patient = getPatientById(id);
        return patient.getMedicalRecordList().stream()
                .sorted(Comparator.comparing(MedicalRecord::getDate).reversed())
                .limit(limit)
                .map(record -> new MedicalRecordDto(
                        record.getRecordId(),
                        record.getDiagnosis(),
                        record.getSymptoms(),
                        record.getDate(),
                        record.getDoctor().getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Patient addPatient(RegisterPatientRequest request) {
        Patient patient = new Patient(
                request.getCitizenId(),
                request.getInsuranceId(),
                request.getFirstName(),
                request.getLastName(),
                request.getDob(),
                request.getPhone(),
                request.getAddress(),
                request.getGender()
        );
        patientRepository.save(patient);
        return patient;
    }
}
