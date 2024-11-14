package com.noa.pos.model.repository;

import com.noa.pos.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    public ProductEntity findByName(String name);

}
