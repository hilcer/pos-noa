package com.noa.pos.appweb.controller;

import com.noa.pos.dto.ProductDto;
import com.noa.pos.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index() {
        return "product/list_product";
    }

    @GetMapping("/addproduct")
    public String addProduct() {
        return "product/add_product";
    }

    @PostMapping("/saveproduct")
    public String saveProduct(@ModelAttribute ProductDto product, @RequestParam("imageReq") MultipartFile file, HttpSession session) {

        var imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        product.setImage(imageName);

        var exist = false;
        try {
            exist = productService.existProductByNamw(product.getName());
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Problemas al buscar producto");
            return "redirect:/product/addproduct";
        }
        // validar si es existe el producto
        if (exist) {
            session.setAttribute("errorMsg", "El producto ya existe");
            return "redirect:/product/addproduct";
        }

        var producto = productService.saveProduct(product);

        if (producto == null) {
            session.setAttribute("errorMsg", "No se pudo guardar el producto");
        } else {
            session.setAttribute("succesMsg", "Producto guardado");
        }

        return "redirect:/product/addproduct";
    }

}
