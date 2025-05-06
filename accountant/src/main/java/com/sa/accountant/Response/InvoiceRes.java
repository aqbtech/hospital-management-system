package com.sa.accountant.Response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceRes {
    String invoiceId;
    String patientId;
    String patientName;
    Double total;
    String status;
    String createdAt;
    @Data
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Breakdown{
        Double serviceFee;
        Double medicineFee;
        Double insuranceCoverage;
    }
    Breakdown breakdown;
}
