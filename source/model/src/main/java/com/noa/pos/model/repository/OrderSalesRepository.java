package com.noa.pos.model.repository;

import com.noa.pos.model.dto.ReportOrderSalesDto;
import com.noa.pos.model.entity.OrderSalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderSalesRepository extends JpaRepository<OrderSalesEntity, Long> {

//    @Query("select new ReportOrderSalesDto(os.orderSalesId, os.ticketNumber, ) from OrderSalesEntity os join ProductEntity pr on os.productId = pr.productId")
//    public List<ReportOrderSalesDto> findByProductType(String productType);

}
