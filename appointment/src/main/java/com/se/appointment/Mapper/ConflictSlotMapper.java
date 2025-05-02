package com.se.appointment.Mapper;

import com.se.appointment.DTO.Response.ConflictDetailsResponse;
import com.se.appointment.DTO.Response.Slot;
import com.se.appointment.Entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConflictSlotMapper {
    @Mapping(target = "slot", source = ".", qualifiedByName = "mapToSlot")
    ConflictDetailsResponse.ConflictSlot toConflictSlot(Appointment appointment);

    List<ConflictDetailsResponse.ConflictSlot> toConflictSlots(List<Appointment> appointments);

    @Named("mapToSlot")
    default Slot mapToSlot(Appointment appointment) {
        return Slot.builder()
                .date(appointment.getDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .build();
    }
}
