package com.se.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(Long id) {
        super("Không tìm thấy người dùng với ID: " + id);
    }
    
    public UserNotFoundException() {
        super("Không tìm thấy người dùng");
    }
}
