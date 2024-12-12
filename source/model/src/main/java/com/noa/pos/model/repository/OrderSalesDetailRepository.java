package com.noa.pos.model.repository;

import com.noa.pos.model.entity.OrderSalesDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderSalesDetailRepository extends JpaRepository<OrderSalesDetailEntity, Long> {

}
