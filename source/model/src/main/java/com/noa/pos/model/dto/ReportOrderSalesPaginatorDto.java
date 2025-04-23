package com.noa.pos.model.dto;


import com.noa.pos.model.entity.OrderSalesEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ReportOrderSalesPaginatorDto {
    private List<OrderSalesEntity> reportsales;
    private int productsSize;
    private String dateFrom;
    private String dateTo;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;
}

