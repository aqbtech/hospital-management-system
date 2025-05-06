package com.se.user.Accountant.Mapper;

import com.se.user.Accountant.Entity.Invoice;
import com.se.user.Accountant.Response.InvoiceRes;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper implements IInvoiceMapper{
    public InvoiceRes entityToInvoiceRes(Invoice invoice){
        String invoiceId = invoice.getId();
        String createdDate = invoice.getCreateDate().toString();
        String status = invoice.getStatus().toString().toUpperCase();
        String patientId = invoice.getPatientId();
        String patientName = invoice.getPatientName();
        Double medicineFee = invoice.getMedicineFee();
        Double serviceFee = invoice.getServiceFee();
        Double insuranceCoverage = invoice.getInsuranceCoverage();
        Double total = (1 - insuranceCoverage) * (medicineFee + serviceFee);
        InvoiceRes.Breakdown breakdown = InvoiceRes.Breakdown.builder()
                .insuranceCoverage(insuranceCoverage)
                .medicineFee(medicineFee)
                .serviceFee(serviceFee)
                .build();
        return InvoiceRes.builder()
                .breakdown(breakdown)
                .createdAt(createdDate)
                .total(total)
                .invoiceId(invoiceId)
                .status(status)
                .patientId(patientId)
                .patientName(patientName)
                .build();

    }

}
