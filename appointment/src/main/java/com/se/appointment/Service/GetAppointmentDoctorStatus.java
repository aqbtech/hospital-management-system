package com.se.appointment.Service;

import com.se.appointment.DTO.Response.AvailableDoctorResponse;
import com.se.appointment.DTO.Response.Slot;
import com.se.appointment.Entity.Appointment;
import com.se.appointment.Entity.Doctor;
import com.se.appointment.Interface.IGetAppointmentDoctorStatus;
import com.se.appointment.Repository.AppointmentRepository;
import com.se.appointment.Repository.DoctorRepository;
import com.se.appointment.Utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAppointmentDoctorStatus implements IGetAppointmentDoctorStatus {
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    @Override
    public Page<AvailableDoctorResponse> getAppointmentDoctorStatus(Long startTs, Long endTs, Pageable pageable) {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Doctor> doctors = doctorRepository.findAll();

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime startDateTime = Instant.ofEpochMilli(startTs).atZone(zoneId);
        ZonedDateTime endDateTime = Instant.ofEpochMilli(endTs).atZone(zoneId);


        String startDate = startDateTime.toLocalDate().toString();
        String endDate = endDateTime.toLocalDate().toString();
        if (!(startDate.equals(endDate))){
            throw new IllegalArgumentException("Start time and end time must be on the same date.");
        }

        String startTime = startDateTime.toLocalTime().toString();
        String endTime = endDateTime.toLocalTime().toString();

        Slot slot = Slot.builder()
                .startTime(startTime)
                .endTime(endTime)
                .date(startDate)
                .build();

        List<Appointment> conflictAppointmentList = CheckConflict.checkConflicts(appointments, slot);

        Set<Long> busyDoctorIds = conflictAppointmentList.stream()
                .map(appointment -> appointment.getDoctor().getDoctorId())
                .collect(Collectors.toSet());

        List<Doctor> availableDoctors = doctors.stream()
                .filter(doctor -> !busyDoctorIds.contains(doctor.getDoctorId()))
                .toList();

        List<AvailableDoctorResponse> availableResponses = availableDoctors.stream()
                .map(doctor -> new AvailableDoctorResponse(doctor.getDoctorId(), slot))
                .toList();

        return PaginationUtils.convertListToPage(availableResponses, pageable, availableResponses.size());
    }
}
