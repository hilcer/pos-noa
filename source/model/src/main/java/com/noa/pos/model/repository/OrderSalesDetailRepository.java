package com.noa.pos.model.repository;

import com.noa.pos.model.dto.Dashboard7DayDto;
import com.noa.pos.model.dto.DashboardTopProductDto;
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

    @Query("SELECT new com.noa.pos.model.dto.Dashboard7DayDto(dayname(v.dateRegister), v.dateRegister, count(v.orderSalesId), sum(v.totalAmount)) " +
            "FROM OrderSalesEntity v where v.companyId = :companyId group by dayname(v.dateRegister), v.dateRegister order by v.dateRegister desc limit 7")
    List<Dashboard7DayDto> findByOrderLas7Day(Integer companyId);

    @Query("SELECT new com.noa.pos.model.dto.DashboardTopProductDto(p.name, p.price, sum(dv.quantity))" +
            " from OrderSalesEntity v, OrderSalesDetailEntity dv, ProductEntity p where v.orderSalesId  = dv.orderSalesId  and dv.productId  = p.productId  and v.dateRegister = curdate() and v.companyId = :companyId group by p.name, p.price order by p.name desc limit 5")
    List<DashboardTopProductDto> findTopProductDay(Integer companyId);

}
