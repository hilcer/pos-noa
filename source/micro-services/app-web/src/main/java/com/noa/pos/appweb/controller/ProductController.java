package com.noa.pos.appweb.controller;

import com.noa.pos.appweb.config.WebResourcesConfigure;
import com.noa.pos.dto.ProductDto;
import com.noa.pos.imp.constant.DomainConstant;
import com.noa.pos.model.entity.ProductEntity;
import com.noa.pos.service.CompanyService;
import com.noa.pos.service.DomainService;
import com.noa.pos.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final DomainService domainService;
    private final CompanyService companyService;

    private final WebResourcesConfigure webResourcesConfigure;

    @Autowired
    public ProductController(final ProductService productService, final DomainService domainService,
                             final CompanyService companyService, WebResourcesConfigure webResourcesConfigure) {
        this.productService = productService;
        this.domainService = domainService;
        this.companyService = companyService;
        this.webResourcesConfigure = webResourcesConfigure;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("products", productService.getAllProducts());

        return "product/list_product";
    }

    @GetMapping("/products")
    public String loadViewProduct(Model m, @RequestParam(defaultValue = "") String ch,
                                  @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<ProductEntity> page = productService.getAllProductsPagination(pageNo, pageSize);
//            page = productService.getAllProductsPagination(pageNo, pageSize);
        m.addAttribute("products", page.getContent());

        List<ProductEntity> products = page.getContent();
        m.addAttribute("products", products);
        m.addAttribute("productsSize", products.size());

        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());

        return "product/list_product";
    }

    @GetMapping("/addproduct")
    public String addProduct(Model model) {

        model.addAttribute("domains", domainService.getGropupDom(DomainConstant.PRODUCT_TYPE));
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
            return "redirect:/product/";
        }
        // validar si es existe el producto
        if (exist) {
            session.setAttribute("errorMsg", "El producto ya existe");
            return "redirect:/product/";
        }

        product.setCode(productService.getCode(product.getName(), product.getProductType()));
        var company = companyService.getByNit(domainService.getDomainGroupAndName(DomainConstant.CONFIGURATION, DomainConstant.DEFAULT_COMPANY).getValue());
        product.setCompanyId(company.getCompanyId());
        var producto = productService.saveProduct(product);

        if (producto == null) {
            session.setAttribute("errorMsg", "No se pudo guardar el producto");
        } else {
            try {
                File saveFile = new File(webResourcesConfigure.pathImages);

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator
                        + file.getOriginalFilename());
                // System.out.println(path);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("errorMsg", "Problemas al guardar la imagen");
            }
            session.setAttribute("succesMsg", "Producto guardado");
        }

        return "redirect:/product/";
    }

    @GetMapping("/editproduct/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getById(id));
        model.addAttribute("domains", domainService.getGropupDom(DomainConstant.PRODUCT_TYPE));
        return "product/edit_product";
    }

    @PostMapping("/saveeditproduct")
    public String saveEditProduct(@ModelAttribute ProductDto product, @RequestParam("imageReq") MultipartFile file, HttpSession session) {

        var exist = productService.getById(product.getProductId());
        // validar si es existe el producto
        if (exist == null) {
            session.setAttribute("errorMsg", "El producto no existe");
            return "redirect:/product/";
        }

        var imageName = file != null && !file.isEmpty() ? file.getOriginalFilename() : exist.getImage();
        product.setImage(imageName);

        var producto = productService.mergeProduct(product);

        if (producto == null) {
            session.setAttribute("errorMsg", "No se pudo guardar el producto");
        } else {
            try {
                File saveFile = new File(webResourcesConfigure.pathImages);

                if (!file.getOriginalFilename().isBlank()) {
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator
                            + file.getOriginalFilename());
                    // System.out.println(path);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                }

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("errorMsg", "Problemas al guardar la imagen");
            }
            session.setAttribute("succesMsg", "Producto guardado");
        }

        return "redirect:/product/";
    }
}
