package com.se.user.Accountant.Mapper;

import com.se.user.Accountant.Entity.InsuranceClaim;
import com.se.user.Accountant.Response.InsuranceClaimRes;
import org.springframework.stereotype.Component;

@Component
public class InsuranceClaimMapper implements IInsuranceClaimMapper {
    @Override
    public InsuranceClaimRes entityToInsuranceClaimRes(InsuranceClaim insuranceClaim) {
        String id = insuranceClaim.getId();
        String patientId = insuranceClaim.getPatientId();
        String patientName = insuranceClaim.getPatientName();
        String provider = insuranceClaim.getProvider();
        String policyNumber = insuranceClaim.getPolicyNumber();
        Double amount = insuranceClaim.getAmount();
        String status = insuranceClaim.getStatus().toString().toUpperCase();
        String createDate = insuranceClaim.getCreateDate().toString();
        return InsuranceClaimRes.builder()
                .claimId(id)
                .insuranceProvider(provider)
                .amount(amount)
                .createdAt(createDate)
                .policyNumber(policyNumber)
                .patientId(patientId)
                .patientName(patientName)
                .status(status)
                .build();
    }

}
