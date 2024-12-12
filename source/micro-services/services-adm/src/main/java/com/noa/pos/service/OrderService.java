package com.noa.pos.service;

import com.noa.pos.dto.OrderSalesDto;

public interface OrderService {

    OrderSalesDto saveOrders(OrderSalesDto orders) throws Exception;



}
