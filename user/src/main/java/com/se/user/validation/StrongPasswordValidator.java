package com.se.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    
    private static final Pattern HAS_UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern HAS_LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern HAS_DIGIT = Pattern.compile("\\d");
    private static final Pattern HAS_SPECIAL_CHAR = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
    
    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        // No initialization needed
    }
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // Skip validation for empty passwords as @NotBlank or @NotNull should be used for that
        if (password == null || password.isEmpty()) {
            return true;
        }
        
        boolean hasUppercase = HAS_UPPERCASE.matcher(password).find();
        boolean hasLowercase = HAS_LOWERCASE.matcher(password).find();
        boolean hasDigit = HAS_DIGIT.matcher(password).find();
        boolean hasSpecialChar = HAS_SPECIAL_CHAR.matcher(password).find();
        
        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }
} 