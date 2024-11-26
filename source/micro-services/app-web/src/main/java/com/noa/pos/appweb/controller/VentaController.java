package com.noa.pos.appweb.controller;

import com.noa.pos.dto.DomainDto;
import com.noa.pos.dto.PreOrderSalesDetailDto;
import com.noa.pos.dto.UserDto;
import com.noa.pos.service.DomainService;
import com.noa.pos.service.ProductService;
import com.noa.pos.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/venta")
public class VentaController {

    private final ProductService productService;

    @Autowired
    public VentaController(final ProductService userService, ProfileService profileService, PasswordEncoder passwordEncoder) {
        this.productService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("products", productService.getAllProducts());

        return "venta/venta";
    }

    @GetMapping("/orden")
    public String orden(Model model) {

//        model.addAttribute("products", productService.getAllProducts());

        return "venta/orden";
    }

    @PostMapping("/preorden")
    public String preorden(@ModelAttribute List<PreOrderSalesDetailDto> usersDto, Model model) {

        model.addAttribute("products", usersDto);

        return "venta/orden";
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
