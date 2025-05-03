package com.se.patient.service.MedicalRecord;

import com.se.patient.dto.MedicalRecordDto;
import com.se.patient.dto.DoctorDto;
import com.se.patient.exceptions.ResourceNotFoundException;
import com.se.patient.model.Doctor;
import com.se.patient.model.MedicalRecord;
import com.se.patient.model.Patient;
import com.se.patient.repository.DoctorRepository;
import com.se.patient.repository.MedicalRecordRepository;
import com.se.patient.repository.PatientRepository;
import com.se.patient.request.UpdateMedicalRecordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class MedicalRecordService implements IMedicalRecordService{
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final ModelMapper modelMapper;
    @Override
    public MedicalRecord addMedicalRecord(UpdateMedicalRecordRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(
                () -> new ResourceNotFoundException("Patient not found")
        );
        Doctor doctor = doctorRepository.findById(request.getDoctor().getDoctorId()).orElseThrow(
                () -> new ResourceNotFoundException("Doctor not found")
        );
        MedicalRecord record = new MedicalRecord();
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setDate(request.getDate());
        record.setDiagnosis(request.getDiagnosis());
        record.setSymptoms(request.getSymptoms());
        return medicalRecordRepository.save(record);
    }

    @Override
    public MedicalRecordDto convertoDto(MedicalRecord medicalRecord) {
        return modelMapper.map(medicalRecord, MedicalRecordDto.class);
    }


    @Override
    public MedicalRecord updateMedicalRecord(UpdateMedicalRecordRequest request) {
        MedicalRecord record = medicalRecordRepository.findById(request.getRecordId()).orElseThrow(
                () -> new ResourceNotFoundException("MedicalRecord not found")
        );
        Doctor doctor = doctorRepository.findById(request.getDoctor().getDoctorId()).orElseThrow(
                () -> new ResourceNotFoundException("Doctor not found")
        );
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(
                () -> new ResourceNotFoundException("Patient not found")
        );
        record.setDoctor(doctor);
        record.setDate(request.getDate());
        record.setPatient(patient);
        record.setDiagnosis(request.getDiagnosis());
        record.setSymptoms(request.getSymptoms());

        return medicalRecordRepository.save(record);
    }


}
