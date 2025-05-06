package com.se.user.Accountant.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InsuranceClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String patientId;
    String patientName;

    String provider;
    String policyNumber;
    Double amount;

    public enum Status{PENDING, APPROVED, REJECTED}
    InsuranceClaim.Status status;

    Date createDate;
    String note;
}
