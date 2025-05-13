package com.se.patient.repository;

import com.se.patient.model.Doctor;
import com.se.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
