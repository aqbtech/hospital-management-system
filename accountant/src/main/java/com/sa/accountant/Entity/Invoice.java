package com.sa.accountant.Entity;

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
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String patientId;
    String patientName;

    Double serviceFee;
    Double medicineFee;
    Double insuranceCoverage;

    public enum Status{PENDING, PAID, CANCELLED}
    Status status;

    Date createDate;
}
