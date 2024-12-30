package com.noa.pos.service.imp;

import com.noa.pos.dto.OrderSalesDto;
import com.noa.pos.dto.ProductDto;
import com.noa.pos.model.dto.ReportOrderSalesDto;
import com.noa.pos.service.ExportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportPdfServiceImp implements ExportService<OrderSalesDto> {

    @Override
    public byte[] generateReport(List<OrderSalesDto> lista) throws IOException {
        var workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ventas");

        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("Nro. Ticket");
        headerRow.createCell(1).setCellValue("Cajero");
        headerRow.createCell(2).setCellValue("Importe Total");
        headerRow.createCell(3).setCellValue("Fecha Registro");

        int num = 1;
        for (OrderSalesDto dto : lista) {
            Row row = sheet.createRow(num++);
            row.createCell(0).setCellValue(dto.getTicketNumber());
            row.createCell(1).setCellValue(dto.getLastUser());
            row.createCell(2).setCellValue(dto.getTotalAmount().toString());
            row.createCell(3).setCellValue(dto.getDateRegister());

        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
