package com.sa.accountant.Interface;



import com.sa.accountant.Request.ReconcileReq;
import com.sa.accountant.Response.InsuranceClaimRes;

import java.util.Date;
import java.util.List;

public interface IInsuranceClaim {
    List<InsuranceClaimRes> getInsuranceClaims(String status, Date startDate, Date endDate);
    String reconcileClaim(String id, ReconcileReq request);
}
