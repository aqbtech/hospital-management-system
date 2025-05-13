package com.se.appointment.Repository;

import com.se.appointment.Entity.Appointment;
import com.se.appointment.Entity.Doctor;
import com.se.appointment.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAppointmentByPatient(Patient patient);
    List<Appointment> findAppointmentByDoctor(Doctor doctor);
}
