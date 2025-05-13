package com.sa.doctors.service;

import com.sa.doctors.dto.DateScheduleResponse;
import com.sa.doctors.dto.DoctorScheduleResponse;
import com.sa.doctors.repository.DoctorScheduleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IDoctorScheduleService {



    List<DoctorScheduleResponse> getDoctorSchedule(UUID doctorId, LocalDate startDate, LocalDate endDate);
}
