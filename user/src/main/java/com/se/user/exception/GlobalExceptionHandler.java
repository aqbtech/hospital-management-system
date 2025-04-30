package com.se.user.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        // Gửi stack trace cho công cụ giám sát
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
            errorDetail.setTitle("Lỗi xác thực");
            errorDetail.setDetail("Email hoặc mật khẩu không chính xác");
            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
            errorDetail.setTitle("Tài khoản bị khóa");
            errorDetail.setDetail("Tài khoản của bạn đã bị khóa");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
            errorDetail.setTitle("Không được phép");
            errorDetail.setDetail("Bạn không có quyền truy cập tài nguyên này");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
            errorDetail.setTitle("JWT không hợp lệ");
            errorDetail.setDetail("Chữ ký JWT không hợp lệ");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
            errorDetail.setTitle("JWT hết hạn");
            errorDetail.setDetail("Token JWT đã hết hạn");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            errorDetail.setTitle("Lỗi máy chủ");
            errorDetail.setDetail("Đã xảy ra lỗi không xác định");
        }

        return errorDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
} 