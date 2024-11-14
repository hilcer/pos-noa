package com.noa.pos.service;

import com.noa.pos.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto saveProduct(ProductDto user);

    List<ProductDto> getAllProducts();

    Boolean existProductByNamw(String name);
}
