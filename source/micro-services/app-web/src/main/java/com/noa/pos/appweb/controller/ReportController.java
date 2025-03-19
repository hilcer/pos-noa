package com.noa.pos.appweb.controller;

import com.noa.pos.dto.DashboarDetailDto;
import com.noa.pos.dto.OrderSalesDto;
import com.noa.pos.dto.UserDto;
import com.noa.pos.imp.constant.DomainConstant;
import com.noa.pos.model.dto.*;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final OrderService orderService;
    private final ProductService productService;
    private final DomainService domainService;
    private final ExportServiceProduct exportServiceProduct;
    private static final Map<String, String> semanas=  Map.of(
            "Sunday", "Domingo",
            "Monday", "Lunes",
            "Tuesday", "Martes",
            "Wednesday", "Miercoles",
            "Thursday", "Jueves",
            "Friday", "Viernes",
            "Saturday", "Sabado"
            );

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

    @GetMapping("/reportsalesdetailul/{id}")
    public ResponseEntity<?> salesDetail(@PathVariable long id, Model model) {
        return ResponseEntity.ok(orderService.findOrderSalesDetailByOrderId(id));
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

//        model.addAttribute("dasboard", id);

        return "dashboard/dashboard";
    }

    @PostMapping("/dashboarddetail")
    public ResponseEntity<?> registrarorden(@RequestBody UserDto userDto) {

        List<Dashboard7DayDto> orderSales;
        List<DashboardTopProductDto> topProductDtos;
        try {
            orderSales = orderService.findByOrderLas7Day(userDto.getUsername());
            topProductDtos = orderService.findTopProductDay(userDto.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DashboarReportDto dashboarReportDto = new DashboarReportDto();
        orderSales.forEach( x -> {
            x.setDay(semanas.get(x.getDay()));
        });
        dashboarReportDto.setDashboard7DayDtos(orderSales);
        dashboarReportDto.setDashboardTopProductDtos(topProductDtos);
        return dashboarReportDto != null ? ResponseEntity.ok(dashboarReportDto) : ResponseEntity.notFound().build();
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
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
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
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(excelbyte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
