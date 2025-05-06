package com.sa.accountant.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sa.accountant.Entity.Invoice;
import com.sa.accountant.Interface.IExportInvoice;
import com.sa.accountant.Mapper.InvoiceMapper;
import com.sa.accountant.Repository.InvoiceRepo;
import com.sa.accountant.Response.InvoiceRes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.*;
import java.util.Date;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExportInvoice implements IExportInvoice {

    InvoiceRepo invoiceRepo;
    InvoiceMapper invoiceMapper;
    SpringTemplateEngine templateEngine;

    @Override
    public File exportInvoicePDF(String id) {
        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Invoice not found"));
        Context context = new Context();
        InvoiceRes invoiceRes = invoiceMapper.entityToInvoiceRes(invoice);
        Date date = invoice.getCreateDate();
        context.setVariable("invoice", invoiceRes);
        context.setVariable("date", date);
        String html = templateEngine.process("invoicePDF", context);
        try {
            File outputFile = File.createTempFile("invoice_" + invoice.getId(), ".pdf");
            try (OutputStream os = new FileOutputStream(outputFile)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.withHtmlContent(html, null);
                builder.toStream(os);
                builder.run();
            }
            return outputFile;
        } catch (Exception e) {
            throw new RuntimeException("Failed to export PDF", e);
        }
    }
}
