package com.se.user.Accountant.Mapper;

import com.se.user.Accountant.Entity.Invoice;
import com.se.user.Accountant.Response.InvoiceRes;

public interface IInvoiceMapper {
    InvoiceRes entityToInvoiceRes(Invoice invoice);
}
