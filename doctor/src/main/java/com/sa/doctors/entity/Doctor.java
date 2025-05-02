package com.sa.doctors.entity;

import com.sa.doctors.enums.SpecialityEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "doctors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @JoinColumn(name = "department_id")
    @ManyToOne
    private Department department;

    @Enumerated(EnumType.STRING)
    private SpecialityEnum speciality;

    private List<String> qualifications;

    private int experience;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}
