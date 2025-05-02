package com.se.patient.repository;

import com.se.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long>{

    boolean existsByCitizenId(String citizenId);
}
