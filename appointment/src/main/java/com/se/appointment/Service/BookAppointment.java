package com.se.appointment.Service;

import com.se.appointment.DTO.Request.BookAppointmentRequest;
import com.se.appointment.DTO.Response.BookAppointmentSuccessResponse;
import com.se.appointment.DTO.Response.BookAppointmentResult;
import com.se.appointment.DTO.Response.ConflictDetailsResponse;
import com.se.appointment.Entity.Appointment;
import com.se.appointment.Entity.Doctor;
import com.se.appointment.Entity.Patient;
import com.se.appointment.Interface.IBookAppointment;
import com.se.appointment.Mapper.BookAppointmentSuccessMapper;
import com.se.appointment.Mapper.ConflictSlotMapper;
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
public class BookAppointment implements IBookAppointment {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ConflictSlotMapper conflictSlotMapper;
    private final PatientRepository patientRepository;
    private final BookAppointmentSuccessMapper bookAppointmentSuccessMapper;
    @Override
    public BookAppointmentResult bookAppointment(BookAppointmentRequest bookAppointmentRequest) {

            Doctor doctor = doctorRepository.findById(bookAppointmentRequest.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            List<Appointment> appointmentsOfDoctor = appointmentRepository.findAppointmentByDoctor(doctor);
            List<Appointment> conflictAppointmentList = CheckConflict.checkConflicts(appointmentsOfDoctor, bookAppointmentRequest.getSlot());

            // Check conflict appointment
            if (!conflictAppointmentList.isEmpty()) {
                List<ConflictDetailsResponse.ConflictSlot> slots = conflictSlotMapper.toConflictSlots(conflictAppointmentList);
                ConflictDetailsResponse conflictDetailsResponse = ConflictDetailsResponse.builder()
                        .conflictSlots(slots)
                        .build();

                return BookAppointmentResult.builder()
                        .hasConflict(true)
                        .successData(null)
                        .conflictData(conflictDetailsResponse)
                        .build();
            }

            // There is no conflict appointment
            Patient patient = patientRepository.findById(bookAppointmentRequest.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            Appointment appointment = Appointment.builder()
                    .date(bookAppointmentRequest.getSlot().getDate())
                    .startTime(bookAppointmentRequest.getSlot().getStartTime())
                    .endTime(bookAppointmentRequest.getSlot().getEndTime())
                    .doctor(doctor)
                    .patient(patient)
                    .build();
        try {
            appointmentRepository.save(appointment);
        } catch (Exception e){
            throw new RuntimeException("Error while saving appointment");
        }
            BookAppointmentSuccessResponse response = bookAppointmentSuccessMapper.toBookAppointmentSuccessResponse(appointment);

            return BookAppointmentResult.builder()
                    .hasConflict(false)
                    .successData(response)
                    .conflictData(null)
                    .build();
    }
}
