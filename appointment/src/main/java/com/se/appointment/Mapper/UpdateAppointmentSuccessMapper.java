package com.se.appointment.Mapper;

import com.se.appointment.DTO.Response.BookAppointmentSuccessResponse;
import com.se.appointment.DTO.Response.Slot;
import com.se.appointment.DTO.Response.UpdateAppointmentSuccessResponse;
import com.se.appointment.Entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
@Mapper(componentModel = "spring")
public interface UpdateAppointmentSuccessMapper {
    @Mapping(target = "appointmentId", source = "appointmentId")
    @Mapping(target = "status", constant = "CONFIRMED")
    @Mapping(target = "slot", source = ".", qualifiedByName = "mapSlot")
    UpdateAppointmentSuccessResponse toUpdateAppointmentSuccessResponse(Appointment appointment);

    @Named("mapSlot")
    default Slot mapSlot(Appointment appointment) {
        if (appointment == null) return null;
        return Slot.builder()
                .date(appointment.getDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .build();
    }
}
