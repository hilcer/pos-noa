package com.noa.pos.model.repository;

import com.noa.pos.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query("select cl from ProductEntity cl where cl.enabled = true")
    public List<ProductEntity> findAllEnable();
    public ProductEntity findByName(String name);
    @Query("select pr from ProductEntity pr where pr.enabled = true and pr.productType = :productType")
    public List<ProductEntity> findByProductType(String productType);

}
