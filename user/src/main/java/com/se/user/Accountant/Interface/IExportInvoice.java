package com.se.user.Accountant.Interface;

import java.io.File;

public interface IExportInvoice {
    File exportInvoicePDF(String id);
}
