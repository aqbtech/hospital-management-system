package com.sa.doctors.service.Impl;

import com.sa.doctors.dto.AppointmentPatientResponse;
import com.sa.doctors.dto.PatientDto;
import com.sa.doctors.entity.Appointment;
import com.sa.doctors.enums.PatientStatus;
import com.sa.doctors.repository.AppointmentRepository;
import com.sa.doctors.service.IDoctorAppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorAppointmentServiceImpl implements IDoctorAppointmentService {
    private final AppointmentRepository appointmentRepository;

    public DoctorAppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public List<AppointmentPatientResponse> getDoctorPatients(UUID doctorId, PatientStatus status) {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndStatus(doctorId, status);

        List<AppointmentPatientResponse> res = appointments.stream().map(appointment ->
                        new AppointmentPatientResponse(appointment.getPatient().getId(), appointment.getPatient().getName(),
                                appointment.getStatus(), appointment.getWaitingTime()))
                .toList();

        return res;
    }
}
