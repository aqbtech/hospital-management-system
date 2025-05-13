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
@Table(name = "patient_profiles")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long patientId;
//    private String firstName;
//    private String lastName;
//    private String dob;
//    private String gender; // Maybe one of these: [MALE, FEMALE, OTHER]
//    private String phone;
//    private String citizenId;

//    @OneToMany(mappedBy = "patient")
//    private List<Appointment> appointments;
}
