package com.se.appointment.Interface;

import com.se.appointment.DTO.Request.BookAppointmentRequest;
import com.se.appointment.DTO.Response.BookAppointmentResult;

public interface IBookAppointment {
    BookAppointmentResult bookAppointment(BookAppointmentRequest bookAppointmentRequest);
}
