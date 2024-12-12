package com.noa.pos.appweb.controller;

import com.noa.pos.imp.constant.DomainConstant;
import com.noa.pos.service.OrderService;
import com.noa.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public ReportController(final OrderService orderService,
                            ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/reportsales")
    public String reportsales(Model model) {

        model.addAttribute("products", productService.findAllEnable());
        //model.addAttribute("productstype", domainService.getGropupDom(DomainConstant.PRODUCT_TYPE));

        return "report/report_sales";
    }

}
