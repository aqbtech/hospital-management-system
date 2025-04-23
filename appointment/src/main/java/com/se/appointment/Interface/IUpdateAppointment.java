package com.se.appointment.Interface;

import com.se.appointment.DTO.Request.BookAppointmentRequest;
import com.se.appointment.DTO.Request.UpdateAppointmentRequest;
import com.se.appointment.DTO.Response.BookAppointmentResult;
import com.se.appointment.DTO.Response.UpdateAppointmentResult;

public interface IUpdateAppointment {
    UpdateAppointmentResult updateAppointment(UpdateAppointmentRequest updateAppointmentRequest);

}
