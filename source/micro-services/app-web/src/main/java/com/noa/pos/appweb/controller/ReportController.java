package com.noa.pos.appweb.controller;

import com.noa.pos.dto.OrderSalesDto;
import com.noa.pos.imp.constant.DomainConstant;
import com.noa.pos.model.dto.ReportOrderSalesDto;
import com.noa.pos.model.dto.ReportOrderSalesProdDto;
import com.noa.pos.model.entity.OrderSalesEntity;
import com.noa.pos.service.*;
import com.noa.pos.service.imp.ExportPdfProdServiceImp;
import com.noa.pos.service.imp.ExportPdfServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final OrderService orderService;
    private final ProductService productService;
    private final DomainService domainService;
    private final ExportServiceProduct exportServiceProduct;

    @Autowired
    public ReportController(final OrderService orderService,
                            ProductService productService,
                            DomainService domainService,
                            ExportServiceProduct exportServiceProduct) {
        this.orderService = orderService;
        this.productService = productService;
        this.domainService = domainService;
        this.exportServiceProduct = exportServiceProduct;
    }

    @GetMapping("/reportsales")
    public String reportsales(Model m, @RequestParam(name = "dateFrom", defaultValue = "") String dateFrom,
                              @RequestParam(name = "dateTo", defaultValue = "") String dateTo,
                              @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String ch) {

        if (dateFrom.isEmpty() && dateTo.isEmpty()) {
            dateFrom = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateTo = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        Page<OrderSalesEntity> page;
        page = orderService.searchOrderSalesPagination(pageNo, pageSize, dateFrom, dateTo);

        List<OrderSalesEntity> products = page.getContent();
        m.addAttribute("reportsales", products);
        m.addAttribute("productsSize", products.size());
        m.addAttribute("dateFrom", dateFrom);
        m.addAttribute("dateTo", dateTo);

        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());

        return "report/report_sales";
    }

    @GetMapping("/reportsalesdetail/{id}")
    public String editProduct(@PathVariable long id, Model model) {

        var reportDto = orderService.findById(id);
        var salesDto = new ReportOrderSalesDto();
        salesDto.setOrderSalesId(reportDto.getOrderSalesId());
        salesDto.setTicketNumber(reportDto.getTicketNumber());
        salesDto.setTotalAmount(reportDto.getTotalAmount());
        salesDto.setState(reportDto.getState());
        salesDto.setDateRegister(reportDto.getDateRegister());
        salesDto.setCompanyId(reportDto.getCompanyId());
        salesDto.setSucursalId(reportDto.getSucursalId());
        salesDto.setLastUser(reportDto.getLastUser());
        salesDto.setLastTime(reportDto.getLastTime());
        salesDto.setDetails(orderService.findOrderSalesDetailByOrderId(id));

        model.addAttribute("reportSales", salesDto);


        return "report/report_sales_detail";
    }

    @GetMapping("/exportexcel")
    public ResponseEntity<byte[]> downloadExcel(@RequestParam(name = "dateFrom") String dateFrom,
                                                @RequestParam(name = "dateTo") String dateTo) {
        try {
            ExportService<OrderSalesDto>  serviceExport = new ExportPdfServiceImp();
            var listOrderSales = orderService.searchOrderSales(dateFrom, dateTo);

            var excelbyte = serviceExport.generateReport(listOrderSales);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Reporte-"+ LocalDateTime.now() +".xlsx")
                    .body(excelbyte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/reportsalesprod")
    public String reportsalesProd(Model m, @RequestParam(name = "dateFrom", defaultValue = "") String dateFrom,
                              @RequestParam(name = "dateTo", defaultValue = "") String dateTo,
                              @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String ch) {

        if (dateFrom.isEmpty() && dateTo.isEmpty()) {
            dateFrom = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateTo = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        var reportSalesProd = orderService.searchOrderSalesByProd(dateFrom, dateTo);

        m.addAttribute("reportsalesprod", reportSalesProd);
        m.addAttribute("productsSize", reportSalesProd.size());
        m.addAttribute("dateFrom", dateFrom);
        m.addAttribute("dateTo", dateTo);

//        m.addAttribute("pageNo", page.getNumber());
//        m.addAttribute("pageSize", pageSize);
//        m.addAttribute("totalElements", page.getTotalElements());
//        m.addAttribute("totalPages", page.getTotalPages());
//        m.addAttribute("isFirst", page.isFirst());
//        m.addAttribute("isLast", page.isLast());

        return "report/report_sales_prod";
    }

    @GetMapping("/exportexcelprod")
    public ResponseEntity<byte[]> downloadExcelProd(@RequestParam(name = "dateFrom", defaultValue = "") String dateFrom,
                                                @RequestParam(name = "dateTo", defaultValue = "") String dateTo) {
        try {
            if (dateFrom.isEmpty() && dateTo.isEmpty()) {
                dateFrom = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                dateTo = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            //ExportService<ReportOrderSalesProdDto>  serviceExport = new ExportPdfProdServiceImp();
            var listOrderSales = orderService.searchOrderSalesByProd(dateFrom, dateTo);

            var excelbyte = exportServiceProduct.generateReportByProd(listOrderSales);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ReporteProd-"+ LocalDateTime.now() +".xlsx")
                    .body(excelbyte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}