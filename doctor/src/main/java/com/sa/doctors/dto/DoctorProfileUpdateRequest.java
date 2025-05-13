package com.sa.doctors.dto;

import com.sa.doctors.enums.SpecialityEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoctorProfileUpdateRequest {

    private String name;
    private SpecialityEnum specialty;
    private List<String> qualifications;
    private Integer experience;
}
