package com.se.user.Accountant.Interface;

import com.se.user.Accountant.Response.InvoiceRes;

import java.util.Date;
import java.util.List;

public interface IInvoice{
    List<InvoiceRes> getInvoices(String status, Date startDate, Date endDate);
}
