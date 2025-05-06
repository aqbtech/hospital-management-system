package com.se.user.Accountant.Repository;

import com.se.user.Accountant.Entity.InsuranceClaim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InsuranceClaimRepo extends JpaRepository<InsuranceClaim, String> {

    @Query("""
        SELECT i FROM InsuranceClaim i
        WHERE (:status IS NULL OR i.status = :status)
          AND (:startDate IS NULL OR i.createDate >= date(:startDate))
          AND (:endDate IS NULL OR i.createDate <= :endDate)
        """)
    List<InsuranceClaim> findInsuranceClaims(
            @Param("status") InsuranceClaim.Status status,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("""
        SELECT i FROM InsuranceClaim i
        WHERE (:startDate IS NULL OR i.createDate >= date(:startDate))
          AND (:endDate IS NULL OR i.createDate <= :endDate)
        """)
    List<InsuranceClaim> findInsuranceClaimsWithin(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
