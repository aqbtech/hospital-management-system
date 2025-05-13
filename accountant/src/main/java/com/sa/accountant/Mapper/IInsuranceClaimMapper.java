package com.sa.accountant.Mapper;


import com.sa.accountant.Entity.InsuranceClaim;
import com.sa.accountant.Response.InsuranceClaimRes;

public interface IInsuranceClaimMapper {
    InsuranceClaimRes entityToInsuranceClaimRes(InsuranceClaim insuranceClaim);
}
