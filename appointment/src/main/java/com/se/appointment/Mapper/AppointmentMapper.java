package com.se.appointment.Mapper;


import com.se.appointment.DTO.Response.AppointmentStatusResponse;
import com.se.appointment.DTO.Response.Slot;
import com.se.appointment.Entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "appointmentId", source = "appointmentId")
    @Mapping(target = "patientId", source = "patient.patientId")
    @Mapping(target = "doctorId", source = "doctor.doctorId")
    @Mapping(target = "slot", source = "appointment", qualifiedByName = "mapSlot")
    AppointmentStatusResponse toAppointmentStatusResponse(Appointment appointment);

    List<AppointmentStatusResponse> toAppointmentStatusResponses(List<Appointment> appointments);

    @Named("mapSlot")
    default Slot mapSlot(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        return Slot.builder()
                .date(appointment.getDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .build();
    }
}
