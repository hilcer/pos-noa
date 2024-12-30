package com.noa.pos.service;

import com.noa.pos.dto.PreOrderSalesDetailDto;
import com.noa.pos.dto.ProductDto;
import com.noa.pos.model.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductDto saveProduct(ProductDto user);

    ProductDto mergeProduct(ProductDto user);

    ProductDto getById(Integer id);

    List<ProductDto> getAllProducts();

    List<ProductDto> findAllEnable();

    List<ProductDto> findByProductType(String productType);

    String getCode(String name, String productType);

    Boolean existProductByNamw(String name);

    Page<ProductDto> searchProductPagination(Integer pageNo, Integer pageSize, String ch);

    Page<ProductEntity> getAllProductsPagination(Integer pageNo, Integer pageSize);

    Page<ProductDto> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String ch);

}
