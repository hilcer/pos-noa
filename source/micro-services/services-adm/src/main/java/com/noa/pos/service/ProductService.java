package com.noa.pos.service;

import com.noa.pos.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto saveProduct(ProductDto user);

    ProductDto mergeProduct(ProductDto user);

    ProductDto getById(Integer id);

    List<ProductDto> getAllProducts();

    String getCode(String name, String productType);

    Boolean existProductByNamw(String name);
}
