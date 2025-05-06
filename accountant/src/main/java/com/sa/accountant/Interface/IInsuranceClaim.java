package com.se.user.Accountant.Interface;

import com.se.user.Accountant.Request.ReconcileReq;
import com.se.user.Accountant.Response.InsuranceClaimRes;

import java.util.Date;
import java.util.List;

public interface IInsuranceClaim {
    List<InsuranceClaimRes> getInsuranceClaims(String status, Date startDate, Date endDate);
    String reconcileClaim(String id, ReconcileReq request);
}
