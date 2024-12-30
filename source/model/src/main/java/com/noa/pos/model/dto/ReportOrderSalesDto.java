package com.noa.pos.model.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReportOrderSalesDto {
    private Long orderSalesId;
    private Integer ticketNumber;
    private BigDecimal totalAmount;
    private String state;
    private String dateRegister;
    private Integer companyId;
    private Integer sucursalId;
    private String lastUser;
    private LocalDateTime lastTime;
    private List<ReportOrderSalesDetailDto> details;
}

