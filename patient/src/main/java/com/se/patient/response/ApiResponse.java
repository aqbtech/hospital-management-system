package com.se.patient.response;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ApiResponse {
    private String errorCode;
    private String message;
    private Object data;
}
