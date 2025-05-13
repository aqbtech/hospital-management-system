package com.sa.accountant.Service;


import com.sa.accountant.Entity.Invoice;
import com.sa.accountant.Interface.IInvoice;
import com.sa.accountant.Mapper.IInvoiceMapper;
import com.sa.accountant.Repository.InvoiceRepo;
import com.sa.accountant.Response.InvoiceRes;
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
	public List<InvoiceRes> getInvoices(String status, Date startDate, Date endDate) {
		List<Invoice> invoices = null;
		if (status != null && !status.isEmpty())
			invoices = invoiceRepo.findInvoices(Invoice.Status.valueOf(status), startDate, endDate);
		else if (startDate != null || endDate != null)
			invoices = invoiceRepo.findInvoicesWithin(startDate, endDate);

		if (invoices == null) throw new RuntimeException("Error in getting invoices, maybe cause by nullable argument");
		return invoices.stream().map(invoiceMapper::entityToInvoiceRes).toList();
	}
}
