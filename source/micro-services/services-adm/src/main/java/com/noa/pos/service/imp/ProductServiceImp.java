package com.noa.pos.service.imp;

import com.noa.pos.dto.ProductDto;
import com.noa.pos.model.entity.ProductEntity;
import com.noa.pos.model.repository.DomainRepository;
import com.noa.pos.model.repository.ProductRepository;
import com.noa.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public ProductDto mergeProduct(ProductDto user) {

        var entity = productRepository.findById(user.getProductId()).orElse(null);

        mrgeDtoToEntity(user, entity);
        entity = productRepository.save(entity);
        return entityToDto(entity);
    }

    @Override
    public Boolean existProductByNamw(String name) {

        var entity = productRepository.findByName(name);
        return entity != null;
    }

    @Override
    public ProductDto getById(Integer id) {

        var entity = productRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }

        return entityToDto(entity);
    }

    public String getCode(String name, String productType) {

        if (name.length() <= 4) {
            return name.toUpperCase() + productType.substring(0, 2).toUpperCase();
        }

        var withOutSpace = name.trim();
        var listWords = withOutSpace.split(" ");

        switch (listWords.length) {
            case 1:
                return name.substring(0, 4).toUpperCase() + productType.substring(0, 2).toUpperCase();
            case 2:
                return listWords[0].substring(0, 2).toUpperCase() + listWords[1].substring(0, 2).toUpperCase() + productType.substring(0, 2).toUpperCase();
            case 3: {
                return listWords[0].substring(0, 2).toUpperCase() +
                        listWords[1].substring(0, 1).toUpperCase() +
                        listWords[2].substring(0, 1).toUpperCase() +
                        productType.substring(0, 2).toUpperCase();
            }
            case 4: {
                return listWords[0].substring(0, 2).toUpperCase() +
                        listWords[1].substring(0, 1).toUpperCase() +
                        listWords[2].substring(0, 1).toUpperCase() +
                        productType.substring(0, 2).toUpperCase();
            }
            default: {
                return listWords[0].substring(0, 2).toUpperCase() +
                        listWords[1].substring(0, 1).toUpperCase() +
                        listWords[2].substring(0, 1).toUpperCase() +
                        productType.substring(0, 2).toUpperCase();
            }
        }
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

    private ProductEntity mrgeDtoToEntity(ProductDto dto, ProductEntity entity) {
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
        return productRepository.findAll().stream().parallel().map(this::entityToDto).toList();
    }

    @Override
    public List<ProductDto> findAllEnable() {
        return productRepository.findAllEnable().stream().parallel().map(this::entityToDto).toList();
    }

    @Override
    public List<ProductDto> findByProductType(String productType) {
        return productRepository.findByProductType(productType).stream().parallel().map(this::entityToDto).toList();
    }

    @Override
    public Page<ProductDto> searchProductPagination(Integer pageNo, Integer pageSize, String ch) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        //return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch, pageable);
        return null;
    }

    @Override
    public Page<ProductEntity> getAllProductsPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ProductDto> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String ch) {
        return null;
    }
}
