package com.se.appointment.Service;

import com.se.appointment.DTO.Response.AppointmentStatusResponse;
import com.se.appointment.Entity.Appointment;
import com.se.appointment.Entity.Patient;
import com.se.appointment.Interface.IGetAppointmentStatus;
import com.se.appointment.Mapper.AppointmentMapper;
import com.se.appointment.Repository.AppointmentRepository;
import com.se.appointment.Repository.PatientRepository;
import com.se.appointment.Utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetAppointmentStatus implements IGetAppointmentStatus {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;
    @Override
    public Page<AppointmentStatusResponse> getAppointmentStatus(Long patientId, Pageable pageable) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Appointment> appointments = appointmentRepository.findAppointmentByPatient(patient);
        List<AppointmentStatusResponse> mapped = appointmentMapper.toAppointmentStatusResponses(appointments);

        return PaginationUtils.convertListToPage(mapped, pageable, mapped.size());
    }

}
