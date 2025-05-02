package com.se.patient.request;

import com.se.patient.enums.Gender;
import lombok.Data;

import java.util.Date;
@Data
public class RegisterPatientRequest {
    private String citizenId;
    private String insuranceId;
    private String firstName;
    private String lastName;
    private Date dob;
    private String phone;
    private String address;
    private Gender gender;
}
