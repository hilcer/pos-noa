package com.noa.pos.model.repository;

import com.noa.pos.model.dto.ReportOrderSalesDto;
import com.noa.pos.model.dto.ReportOrderSalesProdDto;
import com.noa.pos.model.entity.OrderSalesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderSalesRepository extends JpaRepository<OrderSalesEntity, Long> {

//    @Query("select new ReportOrderSalesDto(os.orderSalesId, os.ticketNumber, ) from OrderSalesEntity os join ProductEntity pr on os.productId = pr.productId")
//    public List<ReportOrderSalesDto> findByProductType(String productType);

    @Query("SELECT sa from OrderSalesEntity sa where sa.lastTime between :from and :to")
    Page<OrderSalesEntity> findByLastTimeBetweenPageable(LocalDateTime from, LocalDateTime to, Pageable pageable);

    @Query("SELECT sa from OrderSalesEntity sa where sa.lastTime between :from and :to")
    List<OrderSalesEntity> findByLastTimeBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new com.noa.pos.model.dto.ReportOrderSalesProdDto(os.orderSalesId ,os.ticketNumber, os.typePayment, p.productId, p.name, p.price, os.dateRegister, SUM(osd.quantity)) from OrderSalesEntity os, OrderSalesDetailEntity osd, ProductEntity p where os.orderSalesId = osd.orderSalesId" +
            " and osd.productId = p.productId and os.lastTime between :from and :to group by os.orderSalesId ,os.ticketNumber, os.typePayment, p.productId, p.name, p.price, os.dateRegister order by 1 desc")
    List<ReportOrderSalesProdDto> findByLastTimeBetweenProd(LocalDateTime from, LocalDateTime to);
}
