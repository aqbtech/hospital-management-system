package com.sa.doctors.service.Impl;

import com.sa.doctors.dto.DoctorScheduleResponse;
import com.sa.doctors.entity.Schedule;
import com.sa.doctors.repository.DoctorScheduleRepository;
import com.sa.doctors.service.IDoctorScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorScheduleServiceImpl implements IDoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final ModelMapper modelMapper;

    public DoctorScheduleServiceImpl(DoctorScheduleRepository doctorScheduleRepository, ModelMapper modelMapper) {
        this.doctorScheduleRepository = doctorScheduleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DoctorScheduleResponse> getDoctorSchedule(UUID doctorId, LocalDate startDate, LocalDate endDate) {

        List<Schedule> schedules = doctorScheduleRepository.findByDoctorIdAndDateBetween(doctorId, startDate, endDate);

        return schedules.stream().map(schedule ->
                modelMapper.map(schedule, DoctorScheduleResponse.class))
                .collect(Collectors.toList());


    }
}
