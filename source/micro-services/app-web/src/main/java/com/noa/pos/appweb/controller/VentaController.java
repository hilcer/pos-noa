package com.noa.pos.appweb.controller;

import com.noa.pos.dto.*;
import com.noa.pos.imp.constant.DomainConstant;
import com.noa.pos.service.DomainService;
import com.noa.pos.service.OrderService;
import com.noa.pos.service.ProductService;
import com.noa.pos.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/venta")
public class VentaController {

    private final ProductService productService;
    private final OrderService orderService;
    private final DomainService domainService;


    @Autowired
    public VentaController(final ProductService userService, OrderService orderService, DomainService domainService) {
        this.productService = userService;
        this.orderService = orderService;
        this.domainService = domainService;
    }

    @GetMapping("/filterbytype/{name}")
    public String filterbytype(@PathVariable String name, Model model) {

        model.addAttribute("products", productService.findByProductType(name));
        model.addAttribute("productstype", domainService.getGropupDom(DomainConstant.PRODUCT_TYPE));

        return "venta/venta";
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("products", productService.findAllEnable());
        model.addAttribute("productstype", domainService.getGropupDom(DomainConstant.PRODUCT_TYPE));

        return "venta/venta";
    }

    @GetMapping("/orden")
    public String orden(Model model) {

//        model.addAttribute("products", productService.getAllProducts());

        return "venta/orden";
    }

    @GetMapping("/ordenpending")
    public String ordenpending(Model model) {

//        model.addAttribute("products", productService.getAllProducts());

        return "venta/pending/pending_order";
    }

    @PostMapping("/ordenespending")
    public ResponseEntity<?> ordenespending(@RequestBody OrderSalesDto orderSalesDto) {
        var dateFrom = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        var dateTo = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        var listOrderSales = orderService.searchOrderSalesPending(dateFrom, dateTo, orderSalesDto.getLastUser());

        return listOrderSales != null ? ResponseEntity.ok(listOrderSales) : ResponseEntity.notFound().build();
    }

    @PostMapping("/updateorderprocessed")
    public ResponseEntity<?> updatePendingToProcessed(@RequestBody OrderSalesDto orderSalesDto) {
        OrderSalesDto listOrderSales;
        try {
            listOrderSales = orderService.updatePendingToProcessed(orderSalesDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listOrderSales != null ? ResponseEntity.ok(listOrderSales) : ResponseEntity.notFound().build();
    }

    @GetMapping("/notaventa")
    public String notaVenta(Model model) {

//        model.addAttribute("products", productService.getAllProducts());

        return "venta/nota_venta";
    }

    @PostMapping("/registrarorden")
    public ResponseEntity<?> registrarorden(@RequestBody OrderSalesDto orderSalesDto) {

        OrderSalesDto orderSales;
        try {
            orderSales = orderService.saveOrders(orderSalesDto);
            var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            orderSales.setDateRegister(orderSales.getLastTime().format(formatter));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return orderSales != null ? ResponseEntity.ok(orderSales) : ResponseEntity.notFound().build();
    }
//    @PostMapping("/list")
//    public String list(@ModelAttribute DomainDto domainDto) {
//
//        var exist = productService.getGropupDom(domainDto.getGroupDom());
//        if (exist == null) {
//            return "login";
//        }
//
//        return "venta/venta";
//    }


}
