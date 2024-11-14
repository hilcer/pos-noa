package com.noa.pos.service.imp;

import com.noa.pos.dto.ProductDto;
import com.noa.pos.model.entity.ProductEntity;
import com.noa.pos.model.repository.DomainRepository;
import com.noa.pos.model.repository.ProductRepository;
import com.noa.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImp(ProductRepository domainRepository) {
        this.productRepository = domainRepository;
    }

    @Override
    public ProductDto saveProduct(ProductDto user) {
        var entity = dtoToEntity(user);
        entity = productRepository.save(entity);
        return entityToDto(entity);
    }

    @Override
    public Boolean existProductByNamw(String name) {

        var entity = productRepository.findByName(name);
        return entity != null;
    }

    private ProductEntity dtoToEntity(ProductDto dto) {
        var entity = new ProductEntity();
        entity.setCompanyId(dto.getCompanyId());
        entity.setSucursalId(dto.getSucursalId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setProductType(dto.getProductType());
        entity.setImage(dto.getImage());
        entity.setStock(dto.getStock());
        entity.setMarca(dto.getMarca());
        entity.setModelo(dto.getModelo());
        entity.setColor(dto.getColor());
        entity.setInitStamp(dto.getInitStamp());
        entity.setEnabled(dto.getEnabled());
        entity.setLastUser(dto.getLastUser());
        entity.setLastTime(dto.getLastTime());
        return entity;
    }

    private ProductDto entityToDto(ProductEntity productEntity) {
        var product = new ProductDto();
        product.setProductId(productEntity.getProductId());
        product.setCompanyId(productEntity.getCompanyId());
        product.setSucursalId(productEntity.getSucursalId());
        product.setCode(productEntity.getCode());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        product.setProductType(productEntity.getProductType());
        product.setImage(productEntity.getImage());
        product.setStock(productEntity.getStock());
        product.setMarca(productEntity.getMarca());
        product.setModelo(productEntity.getModelo());
        product.setColor(productEntity.getColor());
        product.setInitStamp(productEntity.getInitStamp());
        product.setEnabled(productEntity.getEnabled());
        product.setLastUser(productEntity.getLastUser());
        product.setLastTime(productEntity.getLastTime());
        return product;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return List.of();
    }
}
