package com.sa.accountant.Response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InsuranceClaimRes {
    String claimId;
    String patientId;
    String patientName;
    String insuranceProvider;
    String policyNumber;
    Double amount;
    String status;
    String createdAt;
}
