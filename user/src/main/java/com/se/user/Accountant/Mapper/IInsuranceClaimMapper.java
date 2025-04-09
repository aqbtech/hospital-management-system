package com.se.user.Accountant.Mapper;

import com.se.user.Accountant.Entity.InsuranceClaim;
import com.se.user.Accountant.Response.InsuranceClaimRes;

public interface IInsuranceClaimMapper {
    InsuranceClaimRes entityToInsuranceClaimRes(InsuranceClaim insuranceClaim);
}
