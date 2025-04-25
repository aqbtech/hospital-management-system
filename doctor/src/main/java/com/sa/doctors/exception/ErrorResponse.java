package com.sa.doctors.exception;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private int errorCode;
    private Date timestamp;
    private String message;
    private String details;



}
