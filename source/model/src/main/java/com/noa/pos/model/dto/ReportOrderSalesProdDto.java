package com.noa.pos.model.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate dateRegister;
    private Long quantity;
}

