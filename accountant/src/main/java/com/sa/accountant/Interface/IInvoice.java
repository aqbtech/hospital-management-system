package com.sa.accountant.Interface;



import com.sa.accountant.Response.InvoiceRes;

import java.util.Date;
import java.util.List;

public interface IInvoice{
    List<InvoiceRes> getInvoices(String status, Date startDate, Date endDate);
}
