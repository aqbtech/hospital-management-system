package com.sa.accountant.Service;


import com.sa.accountant.Entity.InsuranceClaim;
import com.sa.accountant.Interface.IInsuranceClaim;
import com.sa.accountant.Mapper.IInsuranceClaimMapper;
import com.sa.accountant.Repository.InsuranceClaimRepo;
import com.sa.accountant.Request.ReconcileReq;
import com.sa.accountant.Response.InsuranceClaimRes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InsuranceClaimService implements IInsuranceClaim {

    InsuranceClaimRepo insuranceClaimRepo;
    IInsuranceClaimMapper insuranceClaimMapper;
    @Override
    public List<InsuranceClaimRes> getInsuranceClaims(String status, Date startDate, Date endDate) {
        List<InsuranceClaim> insuranceClaims = null;
        if(status != null && !status.isEmpty())
            insuranceClaims = insuranceClaimRepo.findInsuranceClaims(
                    InsuranceClaim.Status.valueOf(status),
                    startDate,
                    endDate);
        else if(startDate != null || endDate != null)
            insuranceClaims = insuranceClaimRepo.findInsuranceClaimsWithin(startDate, endDate);

        if(insuranceClaims == null) throw new RuntimeException("Error in getting insurance claims, maybe cause by nullable argument");
        return insuranceClaims.stream().map(insuranceClaimMapper::entityToInsuranceClaimRes).toList();
    }

    @Override
    public String reconcileClaim(String id, ReconcileReq request) {
        InsuranceClaim insuranceClaim = insuranceClaimRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("insurance claim not found"));
        insuranceClaim.setStatus(InsuranceClaim.Status.valueOf(request.getStatus()));
        insuranceClaim.setNote(request.getNote());
        try{
            insuranceClaimRepo.save(insuranceClaim);
        }catch (Exception e){
            throw new RuntimeException("Error to save insurance claim");
        }
        return "Insurance claim reconciled successfully";
    }


}
