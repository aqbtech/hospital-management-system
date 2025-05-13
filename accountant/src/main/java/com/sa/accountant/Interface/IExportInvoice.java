package com.sa.accountant.Interface;

import java.io.File;

public interface IExportInvoice {
    File exportInvoicePDF(String id);
}
