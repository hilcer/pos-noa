package com.noa.pos.service;

import com.noa.pos.dto.OrderSalesDto;
import com.noa.pos.model.dto.ReportOrderSalesDetailDto;
import com.noa.pos.model.dto.ReportOrderSalesProdDto;
import com.noa.pos.model.entity.OrderSalesEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    OrderSalesDto saveOrders(OrderSalesDto orders) throws Exception;

    List<OrderSalesDto> findAll();

    OrderSalesDto findById(Long id);

    List<ReportOrderSalesDetailDto> findOrderSalesDetailByOrderId(Long orderId);

    Page<OrderSalesEntity> searchOrderSalesPagination(Integer pageNo, Integer pageSize, String dateFrom, String dateTo);

    List<OrderSalesDto> searchOrderSales(String dateFrom, String dateTo);

    List<ReportOrderSalesProdDto> searchOrderSalesByProd(String dateFrom, String dateTo);

    Page<OrderSalesEntity> getAllOrderSalesPagination(Integer pageNo, Integer pageSize);

}
