package com.noa.pos.service;

import com.noa.pos.dto.OrderSalesDto;
import com.noa.pos.model.dto.Dashboard7DayDto;
import com.noa.pos.model.dto.DashboardTopProductDto;
import com.noa.pos.model.dto.ReportOrderSalesDetailDto;
import com.noa.pos.model.dto.ReportOrderSalesProdDto;
import com.noa.pos.model.entity.OrderSalesEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    OrderSalesDto saveOrders(OrderSalesDto orders) throws Exception;

    OrderSalesDto updatePendingToProcessed(OrderSalesDto orders) throws Exception;

    List<OrderSalesDto> findAll();

    OrderSalesDto findById(Long id);

    List<ReportOrderSalesDetailDto> findOrderSalesDetailByOrderId(Long orderId);

    List<Dashboard7DayDto> findByOrderLas7Day(String user);

    List<DashboardTopProductDto> findTopProductDay(String user);

    Page<OrderSalesEntity> searchOrderSalesPagination(Integer pageNo, Integer pageSize, String dateFrom, String dateTo);

    List<OrderSalesDto> searchOrderSales(String dateFrom, String dateTo);

    List<OrderSalesDto> searchOrderSalesPending(String dateFrom, String dateTo, String user);

    List<ReportOrderSalesProdDto> searchOrderSalesByProd(String dateFrom, String dateTo);

    Page<OrderSalesEntity> getAllOrderSalesPagination(Integer pageNo, Integer pageSize);

}
