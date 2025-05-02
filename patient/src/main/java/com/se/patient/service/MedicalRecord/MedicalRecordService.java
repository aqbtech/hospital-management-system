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

@Service
@RequiredArgsConstructor
public class MedicalRecordService implements IMedicalRecordService{
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicalRecordRepository medicalRecordRepository;
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
        Doctor doctor = medicalRecord.getDoctor();
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setDoctorId(doctor.getDoctorId());
        doctorDto.setName(doctor.getName());
        doctorDto.setSpecialty(doctor.getSpecialty());

        MedicalRecordDto dto = new MedicalRecordDto();
        dto.setRecordId(medicalRecord.getRecordId());
        dto.setPatientId(medicalRecord.getPatient().getId());
        dto.setDoctor(doctorDto);
        dto.setDate(medicalRecord.getDate());
        dto.setSymptoms(medicalRecord.getSymptoms());
        dto.setDiagnosis(medicalRecord.getDiagnosis());

        return dto;
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
