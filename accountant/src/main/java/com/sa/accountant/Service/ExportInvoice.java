package com.se.user.Accountant.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.se.user.Accountant.Entity.Invoice;
import com.se.user.Accountant.Interface.IExportInvoice;
import com.se.user.Accountant.Mapper.InvoiceMapper;
import com.se.user.Accountant.Repository.InvoiceRepo;
import com.se.user.Accountant.Response.InvoiceRes;
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
