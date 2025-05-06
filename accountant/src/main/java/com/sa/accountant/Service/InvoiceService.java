package com.se.user.Accountant.Service;

import com.se.user.Accountant.Entity.Invoice;
import com.se.user.Accountant.Interface.IInvoice;
import com.se.user.Accountant.Mapper.IInvoiceMapper;
import com.se.user.Accountant.Repository.InvoiceRepo;
import com.se.user.Accountant.Response.InvoiceRes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class InvoiceService implements IInvoice {

    InvoiceRepo invoiceRepo;
    IInvoiceMapper invoiceMapper;

    @Override
    public List<InvoiceRes> getInvoices(String status, Date startDate, Date endDate){
        List<Invoice> invoices = null;
        if(status != null && !status.isEmpty())
            invoices = invoiceRepo.findInvoices(Invoice.Status.valueOf(status), startDate, endDate);
        else if(startDate != null || endDate != null)
            invoices = invoiceRepo.findInvoicesWithin(startDate, endDate);

        if(invoices == null) throw new RuntimeException("Error in getting invoices, maybe cause by nullable argument");
        return invoices.stream().map(invoiceMapper::entityToInvoiceRes).toList();
    }
}
