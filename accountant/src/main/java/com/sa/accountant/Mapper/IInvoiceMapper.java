package com.sa.accountant.Mapper;


import com.sa.accountant.Entity.Invoice;
import com.sa.accountant.Response.InvoiceRes;

public interface IInvoiceMapper {
    InvoiceRes entityToInvoiceRes(Invoice invoice);
}
