package com.noa.pos.service.imp;

import com.noa.pos.dto.DomainDto;
import com.noa.pos.dto.OrderSalesDetailDto;
import com.noa.pos.dto.OrderSalesDto;
import com.noa.pos.dto.ProductDto;
import com.noa.pos.imp.constant.DomainConstant;
import com.noa.pos.model.dto.ReportOrderSalesDetailDto;
import com.noa.pos.model.dto.ReportOrderSalesProdDto;
import com.noa.pos.service.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportPdfProdServiceImp implements ExportServiceProduct {

    private final ProductService productService;
    private final OrderService orderService;
    private final DomainService domainService;

    public ExportPdfProdServiceImp(final ProductService productService,
                                   final OrderService orderService,
                                   final DomainService domainService) {
        this.productService = productService;
        this.orderService = orderService;
        this.domainService = domainService;
    }

    @Override
    public byte[] generateReportByProd(List<ReportOrderSalesProdDto> lista) throws IOException {
        var workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ventas");

        Row headerRowDate = sheet.createRow(0);
        headerRowDate.createCell(0).setCellValue("FECHA");
        headerRowDate.createCell(1).setCellValue(LocalDate.now().toString());

        Row headerRow = sheet.createRow(1);
        int numHeader = 0;
        headerRow.createCell(numHeader++).setCellValue("Nro. Ticket");
        var mapProd = new HashMap<String, Integer>();
        var listProduct = productService.findAllEnable();
        for (ProductDto prod : listProduct) {
            headerRow.createCell(numHeader++).setCellValue(prod.getName());
            mapProd.put(prod.getProductId().toString(), numHeader);
        }

        var listTipoPago = domainService.getGropupDom(DomainConstant.PAYMENT_TYPE);
        for (DomainDto dom : listTipoPago) {
            headerRow.createCell(numHeader++).setCellValue(dom.getDescription());
            mapProd.put(dom.getName(), numHeader);
        }

        var listaUnificada = new HashMap<Integer, OrderSalesDto>();
        lista.stream().parallel().forEach(order -> {

            //validar si existe
            var exist = listaUnificada.get(order.getTicketNumber());
            if (exist == null) {
                var listProd = new ArrayList<OrderSalesDetailDto>();
                listProd.add(new OrderSalesDetailDto(null,
                        order.getOrderSalesId(),
                        order.getProductId(),
                        order.getPrice(),
                        null,
                        order.getQuantity().intValue(),
                        null,
                        null));
                listaUnificada.put(order.getTicketNumber(), new OrderSalesDto(
                        order.getOrderSalesId(),
                        order.getTicketNumber(),
                        order.getPrice().multiply(new BigDecimal(order.getQuantity())),
                        null,
                        order.getDateRegister(),
                        null,
                        null,
                        null,
                        order.getTypePayment(),
                        null,
                        listProd
                ));
            } else {
                exist.getDetails().add(new OrderSalesDetailDto(null,
                        order.getOrderSalesId(),
                        order.getProductId(),
                        order.getPrice(),
                        null,
                        order.getQuantity().intValue(),
                        null,
                        null));

                exist.setTotalAmount(exist.getTotalAmount().add(order.getPrice().multiply(new BigDecimal(order.getQuantity()))));
            }
        });


        int numRow = 2;
        int numCol = 0;
        for (Map.Entry<Integer, OrderSalesDto> set : listaUnificada.entrySet()) {
            var orderSalesProdDto = set.getValue();
            Row row = sheet.createRow(numRow++);
            row.createCell(numCol).setCellValue(orderSalesProdDto.getTicketNumber());

            //AGREGAR los productos
            var puntosLlenos = new HashMap<Integer, Integer>();
            for (OrderSalesDetailDto detailDto : orderSalesProdDto.getDetails()) {
                row.createCell(mapProd.get(detailDto.getProductId().toString())).setCellValue(detailDto.getQuantity());
                puntosLlenos.put(mapProd.get(detailDto.getProductId().toString()), mapProd.get(detailDto.getProductId().toString()));
            }

            for (int col = numCol+1; col <= mapProd.size(); col++) {
                if (!puntosLlenos.containsKey(col)) {
                    row.createCell(col).setCellValue(0);
                }
            }

            row.createCell(mapProd.get(orderSalesProdDto.getTypePayment()) - 1).setCellValue(orderSalesProdDto.getTotalAmount().doubleValue());

            numCol = 0;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
