package com.se.appointment.Service;

import com.se.appointment.DTO.Request.UpdateAppointmentRequest;
import com.se.appointment.DTO.Response.*;
import com.se.appointment.Entity.Appointment;
import com.se.appointment.Entity.Doctor;
import com.se.appointment.Interface.IUpdateAppointment;
import com.se.appointment.Mapper.ConflictSlotMapper;
import com.se.appointment.Mapper.UpdateAppointmentSuccessMapper;
import com.se.appointment.Repository.AppointmentRepository;
import com.se.appointment.Repository.DoctorRepository;
import com.se.appointment.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateAppointment implements IUpdateAppointment {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ConflictSlotMapper conflictSlotMapper;
    private final UpdateAppointmentSuccessMapper updateAppointmentSuccessMapper;
    @Override
    public UpdateAppointmentResult updateAppointment(UpdateAppointmentRequest updateAppointmentRequest) {
        Appointment appointment = appointmentRepository.findById(updateAppointmentRequest.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        Doctor doctor = doctorRepository.findById(updateAppointmentRequest.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        List<Appointment> appointmentsOfDoctor = appointmentRepository.findAppointmentByDoctor(doctor);
        List<Appointment> conflictAppointmentList = CheckConflict.checkConflicts(appointmentsOfDoctor, updateAppointmentRequest.getSlot());
        if (!conflictAppointmentList.isEmpty()) {
            if (!(conflictAppointmentList.size() == 1 && conflictAppointmentList.getFirst().getPatient().getPatientId().equals(updateAppointmentRequest.getPatientId()))){
                List<ConflictDetailsResponse.ConflictSlot> slots = conflictSlotMapper.toConflictSlots(conflictAppointmentList);
                ConflictDetailsResponse conflictDetailsResponse = ConflictDetailsResponse.builder()
                        .conflictSlots(slots)
                        .build();

                return UpdateAppointmentResult.builder()
                        .hasConflict(true)
                        .successData(null)
                        .conflictData(conflictDetailsResponse)
                        .build();
            }
        }
        appointment.setDate(updateAppointmentRequest.getSlot().getDate());
        appointment.setStartTime(updateAppointmentRequest.getSlot().getStartTime());
        appointment.setEndTime(updateAppointmentRequest.getSlot().getEndTime());
        appointment.setDoctor(doctor);
        try {
            appointmentRepository.save(appointment);
        } catch (Exception e){
            throw new RuntimeException("Error while saving appointment");
        }
        UpdateAppointmentSuccessResponse response = updateAppointmentSuccessMapper.toUpdateAppointmentSuccessResponse(appointment);

        return UpdateAppointmentResult.builder()
                .hasConflict(false)
                .successData(response)
                .conflictData(null)
                .build();
    }
}
