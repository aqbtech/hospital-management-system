package com.sa.doctors.repository;

import com.sa.doctors.entity.Schedule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DoctorScheduleRepository extends JpaRepository<Schedule, UUID> {

    @EntityGraph(attributePaths = {"timeSlots"})
    List<Schedule> findByDoctorIdAndDateBetween(UUID doctorId, LocalDate startDate, LocalDate endDate);
}
