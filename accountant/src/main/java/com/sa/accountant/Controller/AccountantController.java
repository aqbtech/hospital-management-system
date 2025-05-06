package com.sa.accountant.Controller;


import com.sa.accountant.Entity.InsuranceClaim;
import com.sa.accountant.Entity.Invoice;
import com.sa.accountant.Interface.IAccountant;
import com.sa.accountant.Interface.IExportInvoice;
import com.sa.accountant.Interface.IInsuranceClaim;
import com.sa.accountant.Interface.IInvoice;
import com.sa.accountant.Request.ReconcileReq;
import com.sa.accountant.Request.UpdateProfileReq;
import com.sa.accountant.Response.InsuranceClaimRes;
import com.sa.accountant.Response.InvoiceRes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;

import java.util.Date;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/accountants")
@AllArgsConstructor
public class AccountantController {

    IAccountant accountantService;
    IInvoice invoiceService;
    IInsuranceClaim insuranceClaimService;
    IExportInvoice exportInvoice;

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileReq request){
        try {
            String response = accountantService.updateProfile(request.getUsername(), request);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/invoices")
    public ResponseEntity<?> getInvoices(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
            ){
        if (startDate != null && endDate != null && startDate.after(endDate)) {
            return ResponseEntity.badRequest().body("startDate cannot be after endDate");
        }
        if(status !=  null){
            try{
                Invoice.Status.valueOf(status);
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body("status is not exist");
            }
        }
        try {
            List<InvoiceRes> response = invoiceService.getInvoices(status, startDate, endDate);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/invoices/{invoiceId}/export")
    public ResponseEntity<?> getInvoicePdf(@PathVariable("invoiceId") String id) {
        try {
            File pdf = exportInvoice.exportInvoicePDF(id);
            byte[] fileBytes = Files.readAllBytes(pdf.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdf.getName())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(fileBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/insurance-claims")
    public ResponseEntity<?> getInsuranceClaims(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
    ){
        if (startDate != null && endDate != null && startDate.after(endDate)) {
            return ResponseEntity.badRequest().body("startDate cannot be after endDate");
        }
        if(status !=  null){
            try{
                InsuranceClaim.Status.valueOf(status);
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body("status is not exist");
            }
        }
        try {
            List<InsuranceClaimRes> response = insuranceClaimService.getInsuranceClaims(status, startDate, endDate);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/insurance-claims/{claimId}/reconcile")
    public ResponseEntity<String> reconcileInsuranceClaim(
            @PathVariable String claimId,
            @RequestBody ReconcileReq reconcileReq){
        try {
            String response = insuranceClaimService.reconcileClaim(claimId, reconcileReq);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
