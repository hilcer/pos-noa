package com.noa.pos.model.dto;


import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReportOrderSalesProdDto {
    private Long orderSalesId;
    private Integer ticketNumber;
    private String typePayment;
    private Integer productId;
    private String name;
    private BigDecimal price;
    private String dateRegister;
    private Long quantity;
}

