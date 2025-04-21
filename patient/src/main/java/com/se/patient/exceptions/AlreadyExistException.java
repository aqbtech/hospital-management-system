package com.se.patient.exceptions;

public class AlreadyExistException extends  RuntimeException{
    public AlreadyExistException(String error)
    {
        super(error);
    }
}
