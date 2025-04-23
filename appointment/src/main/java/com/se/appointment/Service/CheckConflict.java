package com.se.appointment.Service;

import com.se.appointment.DTO.Request.BookAppointmentRequest;
import com.se.appointment.DTO.Response.Slot;
import com.se.appointment.Entity.Appointment;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CheckConflict {
    public static List<Appointment> checkConflicts(List<Appointment> existingAppointments, Slot slot) {
        List<Appointment> conflicts = new ArrayList<>();
        String newDate = slot.getDate();
        LocalTime newStart = LocalTime.parse(slot.getStartTime());
        LocalTime newEnd = LocalTime.parse(slot.getEndTime());

        for (Appointment a : existingAppointments) {
            if (!a.getDate().equals(newDate)) continue;

            LocalTime existingStart = LocalTime.parse(a.getStartTime());
            LocalTime existingEnd = LocalTime.parse(a.getEndTime());

            if (!(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd))) {
                conflicts.add(a);
            }
        }

        return conflicts;
    }
}
