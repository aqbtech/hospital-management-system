package com.se.appointment.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_profiles")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long doctorId;
    @Column(name = "full_name")
    private String name;
    private String specialty; // Maybe one of these: [CARDIOLOGY, PEDIATRICS, NEUROLOGY]

//    @OneToMany(mappedBy = "doctor")
//    private List<Appointment> appointments;
}
