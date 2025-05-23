package com.se.patient.model;

import com.se.patient.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String citizenId;
    private String insuranceId;
    private String firstName;
    private String lastName;
    private Date dob;
    private String phone;
    private String address;
    private Gender gender;
    @OneToMany(mappedBy = "patient")
    private List<MedicalRecord> medicalRecordList;

    public Patient(String citizenId, String insuranceId, String firstName, String lastName, Date dob, String phone, String address, Gender gender) {
    }
}
