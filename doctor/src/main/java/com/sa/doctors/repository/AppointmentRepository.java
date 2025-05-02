package com.sa.doctors.repository;

import com.sa.doctors.entity.Appointment;
import com.sa.doctors.enums.PatientStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @EntityGraph(attributePaths = {"patient"})
    List<Appointment> findByDoctorIdAndStatus(UUID doctorId, PatientStatus status);
}
