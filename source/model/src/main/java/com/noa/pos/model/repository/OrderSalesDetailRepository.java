package com.noa.pos.model.repository;

import com.noa.pos.model.dto.ReportOrderSalesDetailDto;
import com.noa.pos.model.entity.OrderSalesDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderSalesDetailRepository extends JpaRepository<OrderSalesDetailEntity, Long> {

    @Query("select o from OrderSalesDetailEntity o where o.orderSalesId = :orderId")
    List<OrderSalesDetailEntity> findByOrderSalesId(Long orderId);

    @Query("select new com.noa.pos.model.dto.ReportOrderSalesDetailDto(osd.orderSalesDetailId, osd.orderSalesId, osd.productId, p.name, osd.price, osd.quantity, osd.lastUser, osd.lastTime)" +
            " from OrderSalesDetailEntity osd, ProductEntity p where osd.productId = p.productId and osd.orderSalesId = :orderSalesId")
    List<ReportOrderSalesDetailDto> findByOrderSalesDetail(Long orderSalesId);

}
