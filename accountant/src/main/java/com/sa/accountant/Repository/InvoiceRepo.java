package com.se.user.Accountant.Repository;

import com.se.user.Accountant.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, String>{
    @Query("""
        SELECT i FROM Invoice i
        WHERE (:status IS NULL OR i.status = :status)
          AND (:startDate IS NULL OR i.createDate >= date(:startDate))
          AND (:endDate IS NULL OR i.createDate <= :endDate)
        """)
    List<Invoice> findInvoices(
            @Param("status") Invoice.Status status,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("""
        SELECT i FROM Invoice i
        WHERE (:startDate IS NULL OR i.createDate >= date(:startDate))
          AND (:endDate IS NULL OR i.createDate <= :endDate)
        """)
    List<Invoice> findInvoicesWithin(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

}
